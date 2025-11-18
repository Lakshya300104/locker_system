package ui;

import models.User;
import services.UserService;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    JTextField username;
    JPasswordField password;
    JComboBox<String> role;

    public LoginFrame() {

        setTitle("Login");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel title = new JLabel("Locker System Login", SwingConstants.CENTER);
        title.setBounds(30, 20, 330, 40);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(title);

        JLabel u = new JLabel("Username:");
        u.setBounds(50, 100, 150, 25);
        add(u);

        username = new JTextField();
        username.setBounds(50, 130, 300, 35);
        add(username);

        JLabel p = new JLabel("Password:");
        p.setBounds(50, 180, 150, 25);
        add(p);

        password = new JPasswordField();
        password.setBounds(50, 210, 300, 35);
        add(password);

        JLabel r = new JLabel("Role:");
        r.setBounds(50, 260, 150, 25);
        add(r);

        role = new JComboBox<>(new String[]{"student", "admin"});
        role.setBounds(50, 290, 300, 35);
        add(role);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(50, 340, 300, 40);
        loginBtn.addActionListener(e -> login());
        add(loginBtn);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(50, 390, 300, 40);
        registerBtn.addActionListener(e -> new RegisterFrame().setVisible(true));
        add(registerBtn);
    }

    private void login() {
        UserService service = new UserService();

        String u = username.getText();
        String p = new String(password.getPassword());
        String r = role.getSelectedItem().toString();

        User user = service.validateLogin(u, p);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid Credentials");
            return;
        }

        if (!user.getRole().equals(r)) {
            JOptionPane.showMessageDialog(this, "Wrong role selected!");
            return;
        }

        dispose();

        if (r.equals("student"))
            new StudentDashboard(user).setVisible(true);
        else
            new AdminDashboard(user).setVisible(true);
    }
}
