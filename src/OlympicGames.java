import Database.Connect;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.io.File;

public class OlympicGames {
    public static void main(String[] args) throws SQLException {
        // Class Instances
        Sports mySports = new Sports();
        Participants players = new Participants();
        Connect database = new Connect();
        Countries country = new Countries();

        country.getCountryID("Belgium");

    }
}
