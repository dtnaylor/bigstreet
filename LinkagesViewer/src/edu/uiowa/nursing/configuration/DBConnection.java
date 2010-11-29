package edu.uiowa.nursing.configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public abstract class DBConnection {
	public static Connection connection;
	private static String address, username, password, database;
	
	public static void openConnection()
	{
		
		try {
			java.net.URL url =  ClassLoader.getSystemResource("db.ini");
		    BufferedReader input =  new BufferedReader(new FileReader(url.getFile()));
		    try {
		    	String line = null;
		        while ((line = input.readLine()) != null) {
		        	String[] splitLine = line.split("=");
		        	if(splitLine[0].equals("address"))
		        		address = splitLine[1];
		        	else if (splitLine[0].equals("username"))
		        		username = splitLine[1];
		        	else if (splitLine[0].equals("password"))
		        		password = splitLine[1];
		        	else if (splitLine[0].equals("database"))
		        		database = splitLine[1];
		        }
		    }
		    finally {
		    	input.close();
		    }
		} catch (IOException ex){
		      ex.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection("jdbc:sqlserver:" + address 
					+ ";user=" + username 
					+ ";password=" + password 
					+ ";database=" + database);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static  void closeConnection()
	{
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
