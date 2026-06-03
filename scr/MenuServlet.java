import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.sql.*;

@WebServlet("/MenuServlet")
public class MenuServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();

        // Require login via session
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            res.setStatus(401);
            out.print("{\"success\":false,\"message\":\"Not logged in.\"}");
            return;
        }

        String sql = "SELECT id, item_name, description, price, emoji FROM menu ORDER BY id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            StringBuilder json = new StringBuilder("{\"success\":true,\"items\":[");
            boolean first = true;
            while (rs.next()) {
                if (!first) json.append(",");
                first = false;
                json.append("{")
                    .append("\"id\":").append(rs.getInt("id")).append(",")
                    .append("\"name\":\"").append(escJson(rs.getString("item_name"))).append("\",")
                    .append("\"description\":\"").append(escJson(rs.getString("description"))).append("\",")
                    .append("\"price\":").append(rs.getDouble("price")).append(",")
                    .append("\"emoji\":\"").append(escJson(rs.getString("emoji"))).append("\"")
                    .append("}");
            }
            json.append("]}");
            out.print(json.toString());

        } catch (SQLException e) {
            res.setStatus(500);
            getServletContext().log("MenuServlet DB error", e);
            out.print("{\"success\":false,\"message\":\"Failed to load menu.\"}");
        }
    }

    private String escJson(String s) {
        return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
