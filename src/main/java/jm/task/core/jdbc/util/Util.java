package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/test_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DIALECT = "org.hibernate.dialect.MySQL5Dialect";
    private static SessionFactory sessionFactory;


    public static Connection getConnection() {
       try{
           return DriverManager.getConnection(DB_URL, USER, PASSWORD);

       } catch (SQLException e) {
           e.printStackTrace();
           return null;
       }


}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration config = new Configuration();
                Properties props = new Properties();
                props.put(Environment.DRIVER, DB_DRIVER);
                props.put(Environment.URL, DB_URL);
                props.put(Environment.USER, USER);
                props.put(Environment.PASS, PASSWORD);
                props.put(Environment.DIALECT, DIALECT);
                config.setProperties(props);

                config.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(config.getProperties()).build();
                sessionFactory = config.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                System.out.println("Problem creating session factory");
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }


    }