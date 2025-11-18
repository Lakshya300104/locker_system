package ui;

import models.User;
import models.Locker;
import services.LockerService;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {

    private User user;
    private JLabel lockerInfo;

    public StudentDashboard(User user) {
        this.user = user;

        setTitle("Student Dashboard");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel welcome = new JLabel("Welcome, " + user.getFullName());
        welcome.setBounds(20, 20, 350, 30);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(welcome);

        lockerInfo = new JLabel();
        lockerInfo.setBounds(20, 80, 450, 30);
        lockerInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(lockerInfo);

        refreshLockerInfo();

        JButton viewLockers = new JButton("View Available Lockers");
        viewLockers.setBounds(20, 140, 220, 40);
        viewLockers.addActionListener(e -> {
            new LockerListFrame(user, this).setVisible(true);
        });
        add(viewLockers);

        JButton changePass = new JButton("Change Locker Password");
        changePass.setBounds(20, 200, 250, 40);
        changePass.addActionListener(e -> new SetPasswordFrame(user, this).setVisible(true));
        add(changePass);

        JButton logout = new JButton("Logout");
        logout.setBounds(20, 260, 100, 35);
        logout.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        add(logout);
    }

    public void refreshLockerInfo() {
        LockerService service = new LockerService();
        Locker locker = service.getLockerByStudent(user.getUserId());

        if (locker == null)
            lockerInfo.setText("You have no locker assigned.");
        else
            lockerInfo.setText(
                    "Locker ID: " + locker.getLockerId() + 
                    " | Password: " + locker.getLockerPassword()
            );
    }
}
