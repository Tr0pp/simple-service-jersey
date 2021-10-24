package com.example;

import com.mysql.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    Connection connection = null;

    public UserRepository () {
        String url = "jdbc:mysql://localhost:3306/app"; //app?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTF8
        String username = "root";
        String password = "Root@1234";

        try {
//            Class.forName("com.mysql.jdbc.Driver"); /* Antigo */
            Class.forName("com.mysql.cj.jdbc.Driver"); /* Novo */
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception err) {
            err.printStackTrace();
        }


    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM user";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setEmail(resultSet.getString(3));

                users.add(user);
            }
        } catch (Exception err) {
            System.out.println(err);
        }

        return users;
    }

    public User getUser(int id) {
        User user = new User();
        String sql = "SELECT * FROM user WHERE id = "+id;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

           if(resultSet.next()){
               user.setId(resultSet.getInt(1));
               user.setName(resultSet.getString(2));
               user.setEmail(resultSet.getString(3));
           } else {
               user.setId(0);
               user.setName("Not Found.");
               user.setEmail("Not Found.");
           }

        } catch (Exception err) {
            System.out.println(err);
        }

        return user;
    }

    public User create(User user) {
        String sql = "INSERT INTO user (name, email) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());

            preparedStatement.executeUpdate();
        } catch (Exception err) {
            System.out.println(err);
        }

        return user;
    }

    public User update(User user) {
        String sql = "UPDATE user SET name = ?, email = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getId());

            preparedStatement.executeUpdate();
        } catch (Exception err) {
            System.out.println(err);
        }

        return user;
    }

    public boolean delete(User user){
        String sql = "DELETE FROM user WHERE id = ?";

        if(user.getName().equals("Not Found.")) {
            return false;
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, user.getId());

            preparedStatement.executeUpdate();
        } catch (Exception err) {
            System.out.println(err);
        }

        return true;
    }
}
