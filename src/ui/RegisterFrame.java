package ui;

import services.UserService;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    public RegisterFrame() {

        setTitle("Register Student");
        setSize(400, 420);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel title = new JLabel("Register New Student", SwingConstants.CENTER);
        title.setBounds(30, 20, 330, 40);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(title);

        JButton back = new JButton("Back");
        back.setBounds(10, 10, 70, 25);
        back.addActionListener(e -> dispose());
        add(back);

        JLabel f = new JLabel("Full Name:");
        f.setBounds(50, 80, 150, 25);
        add(f);

        JTextField fullName = new JTextField();
        fullName.setBounds(50, 110, 300, 35);
        add(fullName);

        JLabel u = new JLabel("Username:");
        u.setBounds(50, 150, 150, 25);
        add(u);

        JTextField username = new JTextField();
        username.setBounds(50, 180, 300, 35);
        add(username);

        JLabel p = new JLabel("Password:");
        p.setBounds(50, 220, 150, 25);
        add(p);

        JPasswordField password = new JPasswordField();
        password.setBounds(50, 250, 300, 35);
        add(password);

        JButton regBtn = new JButton("Register");
        regBtn.setBounds(50, 300, 300, 40);
        regBtn.addActionListener(e -> {
            UserService service = new UserService();

            boolean ok = service.registerStudent(
                    fullName.getText(),
                    username.getText(),
                    new String(password.getPassword())
            );

            if (ok) {
                JOptionPane.showMessageDialog(this, "Registered Successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration Failed!");
            }
        });

        add(regBtn);
    }
}
