package secured;

import java.sql.*;

public class DbConnection {
    static Connection _connection;
    static String driver = "com.mysql.cj.jdbc.Driver";
    static String url = "jdbc:mysql://localhost/posdb";
    static String uname = "root";
    static String pass = "";
    
    public static Connection getConnection() throws Exception {
        if (_connection == null) {
            Class.forName(driver);
            _connection = DriverManager.getConnection(url, uname, pass);
        }
        return _connection;
    }
}
