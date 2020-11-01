package Database;

import java.sql.*;
import java.util.Scanner;

public class Connect {
    private Connection conn = null;
    private final String url = "jdbc:mysql://localhost/olympics";
    Scanner myObj = new Scanner(System.in);

    public void startConn(){
        System.out.print("UserName Login: ");
        String userName = myObj.nextLine();
        System.out.print("Password: ");
        String password = myObj.nextLine();

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
    } // returns connection

    public String getUrl() {
        return url;
    } // Return the url used for the db connection
}
