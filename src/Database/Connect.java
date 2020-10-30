package Database;

import java.sql.*;

public class Connect {
    private Connection conn = null;
    private String url = "jdbc:mysql://127.0.0.1/olympics";
    private String userName = "Richie";
    private String password = "8yWc1!IZ";

    public void startConn(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("Connected");
        }
        catch (Exception e)
        {
            System.err.println("Cannot connect to server");
            System.exit(1);
        }
    }

    public void endConn(){
        if (conn != null)
        {
            try {
                conn.close();
                System.out.println("Disconnected");
            }
            catch (Exception e){/* ignore close errors */ }
        }
    }

    public Connection getConn(){
        return conn;
    }

    public static void main(String[] args) {
    }
}
