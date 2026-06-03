import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.sql.*;
import java.util.*;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();

        // Session check
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            res.setStatus(401);
            out.print("{\"success\":false,\"message\":\"Not logged in.\"}");
            return;
        }
        int sessionUserId = (int) session.getAttribute("userId");

        // Parse body
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
        }
        String body = sb.toString();

        int userId = parseIntField(body, "userId");
        if (userId != sessionUserId) {
            res.setStatus(403);
            out.print("{\"success\":false,\"message\":\"Forbidden.\"}");
            return;
        }

        // Parse items array: [{menuId:1,quantity:2}, ...]
        List<int[]> items = parseItems(body);
        if (items.isEmpty()) {
            res.setStatus(400);
            out.print("{\"success\":false,\"message\":\"No items in order.\"}");
            return;
        }

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false); // transaction

            // 1. Compute total
            double total = 0;
            String priceSQL = "SELECT price FROM menu WHERE id = ?";
            for (int[] item : items) {
                PreparedStatement ps = con.prepareStatement(priceSQL);
                ps.setInt(1, item[0]);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) total += rs.getDouble("price") * item[1];
                ps.close();
            }

            // 2. Insert order
            PreparedStatement orderPS = con.prepareStatement(
                "INSERT INTO orders (user_id, total) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);
            orderPS.setInt(1, userId);
            orderPS.setDouble(2, total);
            orderPS.executeUpdate();
            ResultSet keys = orderPS.getGeneratedKeys();
            if (!keys.next()) throw new SQLException("Order insert failed.");
            int orderId = keys.getInt(1);
            orderPS.close();

            // 3. Insert order_items
            PreparedStatement itemPS = con.prepareStatement(
                "INSERT INTO order_items (order_id, menu_id, quantity) VALUES (?, ?, ?)");
            for (int[] item : items) {
                itemPS.setInt(1, orderId);
                itemPS.setInt(2, item[0]);
                itemPS.setInt(3, item[1]);
                itemPS.addBatch();
            }
            itemPS.executeBatch();
            itemPS.close();

            con.commit();
            out.print("{\"success\":true,\"orderId\":" + orderId + ",\"total\":" + total + "}");

        } catch (SQLException e) {
            if (con != null) { try { con.rollback(); } catch (SQLException ex) { /* ignore */ } }
            res.setStatus(500);
            getServletContext().log("OrderServlet DB error", e);
            out.print("{\"success\":false,\"message\":\"Failed to place order.\"}");
        } finally {
            if (con != null) { try { con.setAutoCommit(true); con.close(); } catch (SQLException e) { /* ignore */ } }
        }
    }

    private int parseIntField(String json, String key) {
        String search = "\"" + key + "\"";
        int idx = json.indexOf(search);
        if (idx < 0) return -1;
        idx = json.indexOf(":", idx + search.length());
        if (idx < 0) return -1; idx++;
        while (idx < json.length() && Character.isWhitespace(json.charAt(idx))) idx++;
        int end = idx;
        while (end < json.length() && (Character.isDigit(json.charAt(end)) || json.charAt(end) == '-')) end++;
        try { return Integer.parseInt(json.substring(idx, end)); }
        catch (NumberFormatException e) { return -1; }
    }

    /** Parses "items":[{menuId:N,quantity:N},...] from JSON body. */
    private List<int[]> parseItems(String json) {
        List<int[]> list = new ArrayList<>();
        int start = json.indexOf("\"items\"");
        if (start < 0) return list;
        start = json.indexOf("[", start);
        int end = json.indexOf("]", start);
        if (start < 0 || end < 0) return list;
        String arr = json.substring(start + 1, end);
        // Each object: {menuId:N,quantity:N}
        int pos = 0;
        while (pos < arr.length()) {
            int ob = arr.indexOf("{", pos);
            int cb = arr.indexOf("}", pos);
            if (ob < 0 || cb < 0) break;
            String obj = arr.substring(ob, cb + 1);
            int menuId   = parseIntField(obj, "menuId");
            int quantity = parseIntField(obj, "quantity");
            if (menuId > 0 && quantity > 0) list.add(new int[]{menuId, quantity});
            pos = cb + 1;
        }
        return list;
    }
}
