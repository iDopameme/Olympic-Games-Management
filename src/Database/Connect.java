package Database;

import java.io.*;
import java.sql.*;
import java.util.Scanner;
import java.util.Properties;

public class Connect {
    private Connection conn;
    private String userName;
    private String passWord;
    private final String url = "jdbc:mysql://olympics.cs9ujvvnthok.us-east-2.rds.amazonaws.com:3306/?user=admin";
    private final String path = "C:\\Olympic-Games-Management\\src\\Database\\credentials.properties"; // Path for properties file
    Scanner inputStream = new Scanner(System.in);
    Properties props = new Properties(); // Properties instance
    FileInputStream in;{
        try {
            in = new FileInputStream(getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    } // File input for obtaining the user and password during session after input
    FileOutputStream out;{
        try {
            out = new FileOutputStream(getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    } // File output for storing the user and password after initial login

    public void startConn() throws IOException {

        /////////////////////////////////////////////////////////
        props.load(in);
        setUserName(props.getProperty("username"));
        setPassword(props.getProperty("password"));
        // This sets the username and password to the connect
        // class variables. If they are null then the if statement
        // will execute. This happens upon first login
        /////////////////////////////////////////////////////////

        if ((getUserName() == null) || (getPassword() == null)) {
            System.out.print("Username Login: ");
            userName = inputStream.nextLine();
            setUserName(userName);
            System.out.print("Password: ");
            passWord = inputStream.nextLine();
            setPassword(passWord);

            props.setProperty("username", userName);
            props.setProperty("password", passWord);
            props.store(out, "User Credentials");
        } // When user logins at start of program

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(getUrl(), getUserName(), getPassword());
            System.out.println("Connected");
        } // Establishing connection
        catch (Exception e)
        {
            System.err.println("Cannot connect to server");
            System.exit(1);
        }
    }

    public void removeCredentials() throws IOException {
        props.remove("username");
        props.remove("password");
        props.store(out, null);
        in.close();
        out.close();
    } // Removes login details after user quits program

    public void endConn() throws IOException {
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
        return this.conn;
    } // returns connection

    public String getUrl() {
        return this.url;
    } // Return the url used for the db connection

    public String getPath() {
        return this.path;
    }

    public void setUserName(String u){
        this.userName = u;
    }

    public void setPassword(String p){
        this.passWord = p;
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return passWord;
    }
}
