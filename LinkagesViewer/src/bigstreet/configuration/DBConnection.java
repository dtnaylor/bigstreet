package bigstreet.configuration;

import java.io.File;
import java.sql.*;

public abstract class DBConnection {
    public static Connection connection;

    public static void openConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String database_path = "correlation_linkages_data.db";

        File file = new File(database_path);
        if (!file.exists()) {
            System.out.println("Database path does not exist.");
            System.exit(1);
        }

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + database_path);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static  void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
