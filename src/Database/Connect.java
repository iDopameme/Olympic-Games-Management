package Database;

import java.sql.*;
import java.util.Scanner;

public class Connect {
    private Connection conn;
    private String userName;
    private String password;
    private final String url = "jdbc:mysql://olympics.cs9ujvvnthok.us-east-2.rds.amazonaws.com:3306/?user=admin";

    Scanner myObj = new Scanner(System.in);

    public void startConn(){
        if ((userName == null) || (password == null)) {
            System.out.print("UserName Login: ");
            userName = myObj.nextLine();
            setUserName(userName);
            System.out.print("Password: ");
            password = myObj.nextLine();
            setPassword(password);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, getUserName(), getPassword());
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

    public void setUserName(String u){
        this.userName = u;
    }

    public void setPassword(String p){
        this.password = p;
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    } // MAJOR SECURITY FLAW????? -- Will look into this for a better solution (coding practice)
}
