package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;


public class UserDaoHibernateImpl implements UserDao {


    private static final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createNativeQuery("CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)" ).executeUpdate();
        session.getTransaction().commit();

    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
        session.getTransaction().commit();


    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        session.save(user);
        session.getTransaction().commit();

    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = (User) session.get(User.class, id);
        session.delete(user);
        session.getTransaction().commit();

    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<User> users = session.createQuery("from User").list();
        session.getTransaction().commit();
        return users;

    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createNativeQuery("DELETE FROM users").executeUpdate();
        session.getTransaction().commit();
    }
}