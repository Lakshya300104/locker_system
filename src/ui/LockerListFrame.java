package ui;

import models.User;
import models.Locker;
import services.LockerService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LockerListFrame extends JFrame {

    private JTable table;
    private LockerService service;
    private User user;
    private StudentDashboard dashboard;

    public LockerListFrame(User user, StudentDashboard dashboard) {
        this.user = user;
        this.dashboard = dashboard;
        this.service = new LockerService();

        setTitle("Available Lockers");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Back Button
        JButton back = new JButton("Back");
        back.addActionListener(e -> dispose());
        add(back, BorderLayout.NORTH);

        table = new JTable();
        load();

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton assign = new JButton("Assign Locker");
        assign.addActionListener(e -> assignLocker());
        add(assign, BorderLayout.SOUTH);
    }

    private void load() {
        List<Locker> list = service.getAvailableLockers();

        DefaultTableModel model =
                new DefaultTableModel(new String[]{"Locker ID", "Status"}, 0);

        for (Locker l : list) {
            model.addRow(new Object[]{
                    l.getLockerId(),
                    l.getStatus()
            });
        }

        table.setModel(model);
    }

    private void assignLocker() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a locker.");
            return;
        }

        // *** ONE LOCKER ONLY RULE ***
        Locker existing = service.getLockerByStudent(user.getUserId());
        if (existing != null) {
            JOptionPane.showMessageDialog(this,
                    "You already have locker ID: " + existing.getLockerId() +
                            "\nYou cannot have more than one locker.");
            return;
        }

        int lid = (int) table.getValueAt(row, 0);

        String pass = JOptionPane.showInputDialog("Enter password for this locker:");
        if (pass == null || pass.isBlank()) {
            JOptionPane.showMessageDialog(this, "Password cannot be empty.");
            return;
        }

        if (service.assignLocker(lid, user.getUserId(), pass)) {
            JOptionPane.showMessageDialog(this, "Locker Assigned!");
            dashboard.refreshLockerInfo();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to assign locker!");
        }
    }
}
