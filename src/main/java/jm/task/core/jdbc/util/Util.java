package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/test_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";



    public static Connection getConnection() {
       try{
           return DriverManager.getConnection(DB_URL, USER, PASSWORD);

       } catch (SQLException e) {
           e.printStackTrace();
           return null;
       }


}

    }