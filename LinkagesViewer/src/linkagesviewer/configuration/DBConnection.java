package linkagesviewer.configuration;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
//        java.net.URL url;
//        java.net.URI uri;
//        try {
//            url = ClassLoader.getSystemResource(database_path);
//            uri = url.toURI();
//            
//            File file = new File(uri);
//            if (!file.exists()) {
//                System.out.println("Database path does not exist.");
//                System.exit(1);
//            }
//        } catch (URISyntaxException ex) {
//            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        

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
