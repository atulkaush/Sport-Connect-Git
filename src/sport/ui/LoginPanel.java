package sport.ui;

import sport.Main;
import javax.swing.*;
import java.awt.*;
public class LoginPanel extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPanel(Main main) {

        setLayout(new GridBagLayout());
        setBackground(new Color(240, 245, 250));

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setPreferredSize(new Dimension(320, 280));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Sports Connect", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 25));

        JLabel subtitle = new JLabel(
                "Connect Athletes and Coaches", JLabel.CENTER);

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        panel.add(title);
        panel.add(subtitle);

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Create Account");

        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

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