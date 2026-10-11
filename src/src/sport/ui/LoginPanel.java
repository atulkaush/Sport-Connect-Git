
package sport.ui;

import sport.Main;
import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPanel(Main main) {

        setLayout(new BorderLayout());
        setBackground(new Color(240, 244, 248));

        // Header
        JPanel header = new JPanel(new GridLayout(2, 1, 5, 5));
        header.setBackground(new Color(25, 45, 75));
        header.setBorder(
                BorderFactory.createEmptyBorder(25, 10, 25, 10));

        JLabel title = new JLabel(
                "SPORTS CONNECT", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "Connect. Compete. Grow.", JLabel.CENTER);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(Color.WHITE);

        header.add(title);
        header.add(subtitle);

        add(header, BorderLayout.NORTH);

        // Login form
        JPanel form = new JPanel(new GridLayout(0, 1, 10, 10));
        form.setBackground(Color.WHITE);
        form.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JLabel welcome = new JLabel(
                "Welcome Back", JLabel.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 22));
        welcome.setForeground(new Color(25, 45, 75));

        JLabel instruction = new JLabel(
                "Log in to continue", JLabel.CENTER);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Create Account");

        loginButton.setBackground(new Color(35, 90, 160));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);

        registerButton.setFocusPainted(false);

        form.add(welcome);
        form.add(instruction);

        form.add(new JLabel("Username"));
        form.add(usernameField);

        form.add(new JLabel("Password"));
        form.add(passwordField);

        form.add(loginButton);
        form.add(registerButton);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(240, 244, 248));
        center.add(form);

        add(center, BorderLayout.CENTER);

        // Footer
        JLabel footer = new JLabel(
                "Sports Connect | Athlete and Coach Network",
                JLabel.CENTER);
        footer.setBorder(
                BorderFactory.createEmptyBorder(10, 5, 15, 5));
        footer.setForeground(Color.GRAY);

        add(footer, BorderLayout.SOUTH);

        // Button actions
        loginButton.addActionListener(e -> {

            String username = usernameField.getText().trim();
            String password =
                    new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "Please enter username and password.");
                return;
            }

            main.login(username, password);
        });

        registerButton.addActionListener(e -> {
            main.showRegister();
        });
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }
}
