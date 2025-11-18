package ui;

import models.User;
import models.Locker;
import services.LockerService;

import javax.swing.*;

public class SetPasswordFrame extends JFrame {

    public SetPasswordFrame(User user, StudentDashboard dash) {

        LockerService service = new LockerService();
        Locker locker = service.getLockerByStudent(user.getUserId());

        if (locker == null) {
            JOptionPane.showMessageDialog(this, "You do not have a locker!");
            dispose();
            return;
        }

        setTitle("Change Password");
        setSize(300, 220);
        setLocationRelativeTo(null);
        setLayout(null);

        // Back Button
        JButton back = new JButton("Back");
        back.setBounds(10, 10, 70, 25);
        back.addActionListener(e -> dispose());
        add(back);

        JTextField pass = new JTextField();
        pass.setBounds(20, 60, 240, 30);
        add(pass);

        JButton save = new JButton("Save");
        save.setBounds(20, 110, 240, 30);
        add(save);

        save.addActionListener(e -> {
            if (service.resetLockerPassword(locker.getLockerId(), pass.getText())) {
                JOptionPane.showMessageDialog(this, "Password Updated!");
                dash.refreshLockerInfo();
                dispose();
            }
        });
    }
}
