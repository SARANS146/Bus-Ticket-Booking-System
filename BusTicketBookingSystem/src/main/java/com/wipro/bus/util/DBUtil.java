package com.wipro.bus.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    
    public static Connection getDBConnection() {
        Connection connection = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            String user = "SYSTEM";
            String pass = "admin";
            
            System.out.println("Attempting to connect to Oracle Database...");
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Database connected successfully!");
            
            return connection;
        }
        catch(ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver not found!");
            e.printStackTrace();
            return null;
        }
        catch(SQLException e) {
            System.out.println("Database connection failed!");
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Error Message: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}