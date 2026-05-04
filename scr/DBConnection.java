import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConnection {

    public static Connection getConnection() {
        Connection con = null;

        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");

            DataSource ds = (DataSource) envContext.lookup("jdbc/cafeDB");
            con = ds.getConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }
}
