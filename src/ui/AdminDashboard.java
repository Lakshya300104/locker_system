package ui;

import models.Locker;
import models.User;
import services.LockerService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame {

    private LockerService service;
    private JTable table;

    public AdminDashboard(User admin) {
        this.service = new LockerService();

        setTitle("Admin Dashboard");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Locker Management (Admin)", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        table = new JTable();
        loadTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel();

        JButton reset = new JButton("Reset Password");
        JButton unassign = new JButton("Unassign");
        JButton addLocker = new JButton("Add Locker");
        JButton logout = new JButton("Logout");

        reset.addActionListener(e -> resetPass());
        unassign.addActionListener(e -> unassignLocker());
        addLocker.addActionListener(e -> addNewLocker());
        logout.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        panel.add(reset);
        panel.add(unassign);
        panel.add(addLocker);
        panel.add(logout);

        add(panel, BorderLayout.SOUTH);
    }

    private void loadTable() {
        List<Locker> list = service.getAllLockers();

        DefaultTableModel model =
                new DefaultTableModel(new String[]{"ID", "Status", "Assigned To (Name)", "Password"}, 0);

        for (Locker l : list) {
            String name = l.getAssignedName();
            if (name == null) name = "—";

            model.addRow(new Object[]{
                    l.getLockerId(),
                    l.getStatus(),
                    name,
                    l.getLockerPassword()
            });
        }

        table.setModel(model);
    }

    private void resetPass() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        int id = (int) table.getValueAt(row, 0);

        if (service.resetLockerPassword(id, "0000")) {
            JOptionPane.showMessageDialog(this, "Password reset to 0000");
            loadTable();
        }
    }

    private void unassignLocker() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        int id = (int) table.getValueAt(row, 0);

        if (service.unassignLocker(id)) {
            JOptionPane.showMessageDialog(this, "Locker Unassigned");
            loadTable();
        }
    }

    private void addNewLocker() {
        String input = JOptionPane.showInputDialog("Enter new locker ID:");
        if (input == null || input.isBlank()) return;

        try {
            int id = Integer.parseInt(input);

            if (service.addLocker(id)) {
                JOptionPane.showMessageDialog(this, "Locker Added!");
                loadTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Locker ID!");
        }
    }
}
