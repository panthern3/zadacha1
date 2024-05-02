package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Util util = new Util();
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try {
            Statement statement = util.getConnection().createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS test1 (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50), last_name VARCHAR(50), age TINYINT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void dropUsersTable() {
        try {
            Statement statement = util.getConnection().createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS test1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement preparedStatement = util.getConnection().prepareStatement("INSERT INTO test1 (name, last_name, age) VALUES (?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void removeUserById(long id) {
        try {
            PreparedStatement preparedStatement = util.getConnection().prepareStatement("DELETE FROM test1 WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = util.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM test1");
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                byte age = resultSet.getByte("age");
                User user = new User(id, name, lastName, age);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    @Override
    public void cleanUsersTable() {
        try {
            Statement statement = util.getConnection().createStatement();
            statement.executeUpdate("TRUNCATE TABLE test1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
