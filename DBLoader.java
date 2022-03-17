import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBLoader {

    static Connection conn;

    public static ResultSet executeStatement(String sqlquery) throws Exception {
        /////  ##CODE //////
        Class.forName("com.mysql.jdbc.Driver");
        // System.out.println("driver loaded successfully");
        
        if (conn == null) {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hellogram", "root", "system");
            // System.out.println("connection built");
        }
        
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        // System.out.println("statement created");

        ResultSet rs = stmt.executeQuery(sqlquery);
        // System.out.println("Resultset created");

        return rs;
        //////////  ##Code Ends Here ////////
    }
}
