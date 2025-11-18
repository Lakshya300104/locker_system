package services;

import db.DBConnection;
import models.Locker;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LockerService {

    public List<Locker> getAvailableLockers() {
        List<Locker> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement stmt =
                con.prepareStatement("SELECT lockers.*, users.full_name FROM lockers LEFT JOIN users ON lockers.assigned_to=users.user_id WHERE status='available'");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Locker(
                        rs.getInt("locker_id"),
                        rs.getString("status"),
                        rs.getInt("assigned_to"),
                        rs.getString("locker_password"),
                        rs.getString("full_name")
                ));
            }

        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    public List<Locker> getAllLockers() {
        List<Locker> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement stmt = con.prepareStatement(
                "SELECT lockers.*, users.full_name " +
                "FROM lockers LEFT JOIN users ON lockers.assigned_to = users.user_id"
            );

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Locker(
                        rs.getInt("locker_id"),
                        rs.getString("status"),
                        rs.getInt("assigned_to"),
                        rs.getString("locker_password"),
                        rs.getString("full_name")
                ));
            }

        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    public Locker getLockerByStudent(int studentId) {
        Locker locker = null;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                "SELECT lockers.*, users.full_name " +
                "FROM lockers LEFT JOIN users ON lockers.assigned_to=users.user_id WHERE assigned_to=?"
            );

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                locker = new Locker(
                        rs.getInt("locker_id"),
                        rs.getString("status"),
                        rs.getInt("assigned_to"),
                        rs.getString("locker_password"),
                        rs.getString("full_name")
                );
            }

        } catch (Exception e) { e.printStackTrace(); }

        return locker;
    }

    public boolean assignLocker(int lockerId, int studentId, String password) {
        Locker existing = getLockerByStudent(studentId);
        if (existing != null) return false;

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement stmt =
                    con.prepareStatement("UPDATE lockers SET status='assigned', assigned_to=?, locker_password=? WHERE locker_id=? AND status='available'");

            stmt.setInt(1, studentId);
            stmt.setString(2, password);
            stmt.setInt(3, lockerId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean resetLockerPassword(int lockerId, String password) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement stmt =
                    con.prepareStatement("UPDATE lockers SET locker_password=? WHERE locker_id=?");

            stmt.setString(1, password);
            stmt.setInt(2, lockerId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean unassignLocker(int lockerId) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement stmt =
                    con.prepareStatement("UPDATE lockers SET status='available', assigned_to=NULL, locker_password=NULL WHERE locker_id=?");

            stmt.setInt(1, lockerId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean addLocker(int lockerId) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement stmt =
                con.prepareStatement("INSERT INTO lockers(locker_id, status) VALUES (?, 'available')");

            stmt.setInt(1, lockerId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}
