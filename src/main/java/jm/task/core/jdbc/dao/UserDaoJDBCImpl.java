package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection;

    private void executeUpdate(String sql) {
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
           statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public UserDaoJDBCImpl() {
        this.connection = getConnection();

    }

    public void createUsersTable() {
        executeUpdate("CREATE TABLE IF NOT EXISTS users (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50), lastName VARCHAR(50), age TINYINT)");

    }

    public void dropUsersTable() {
        executeUpdate("DROP TABLE IF EXISTS users");

    }

    public void saveUser(String name, String lastName, byte age) {

        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO users(name, lastName, age) VALUES (?,?,?)")) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных");

        }catch(SQLException e) {
            e.printStackTrace();

    }
    }

    public void removeUserById(long id) {
        String sql = ("DELETE FROM users WHERE id = " + id);
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users";
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица очищена");

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
