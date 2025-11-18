package services;

import db.DBConnection;
import models.User;

import java.sql.*;

public class UserService {

    public User validateLogin(String username, String password) {
        User user = null;

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement stmt =
                    con.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("full_name")
                );
            }

        } catch (Exception e) { e.printStackTrace(); }

        return user;
    }


    public boolean registerStudent(String fullName, String username, String password) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement stmt =
                    con.prepareStatement("INSERT INTO users(full_name, username, password, role) VALUES (?,?,?,'student')");

            stmt.setString(1, fullName);
            stmt.setString(2, username);
            stmt.setString(3, password);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
