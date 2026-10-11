
package sport.ui;

import sport.Main;
import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {

    private JTextField nameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField ageField;
    private JComboBox<String> roleBox;

    public RegisterPanel(Main main) {

        setLayout(new BorderLayout());
        setBackground(new Color(240, 244, 248));

        // Header
        JPanel header = new JPanel(new GridLayout(2, 1, 5, 5));
        header.setBackground(new Color(25, 45, 75));
        header.setBorder(
                BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JLabel title = new JLabel(
                "JOIN SPORTS CONNECT", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "Create your athlete or coach account",
                JLabel.CENTER);
        subtitle.setForeground(Color.WHITE);

        header.add(title);
        header.add(subtitle);

        add(header, BorderLayout.NORTH);

        // Registration form
        JPanel form = new JPanel(new GridLayout(0, 1, 8, 8));
        form.setBackground(Color.WHITE);
        form.setBorder(
                BorderFactory.createEmptyBorder(20, 30, 20, 30));

        nameField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        ageField = new JTextField();

        roleBox = new JComboBox<>(
                new String[]{"Athlete", "Coach"});

        JButton createButton = new JButton("Create Account");
        JButton backButton = new JButton("Back to Login");

        createButton.setBackground(new Color(35, 90, 160));
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        backButton.setFocusPainted(false);

        form.add(new JLabel("Full Name"));
        form.add(nameField);

        form.add(new JLabel("Username"));
        form.add(usernameField);

        form.add(new JLabel("Password"));
        form.add(passwordField);

        form.add(new JLabel("Age"));
        form.add(ageField);

        form.add(new JLabel("Register As"));
        form.add(roleBox);

        form.add(createButton);
        form.add(backButton);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(240, 244, 248));
        center.add(form);

        add(center, BorderLayout.CENTER);

        JLabel footer = new JLabel(
                "Build your sports network", JLabel.CENTER);
        footer.setForeground(Color.GRAY);
        footer.setBorder(
                BorderFactory.createEmptyBorder(8, 5, 12, 5));

        add(footer, BorderLayout.SOUTH);

        // Registration action
        createButton.addActionListener(e -> {

            String name = nameField.getText().trim();
            String username = usernameField.getText().trim();
            String password =
                    new String(passwordField.getPassword());
            String ageText = ageField.getText().trim();
            String role = (String) roleBox.getSelectedItem();

            if (name.isEmpty() || username.isEmpty()
                    || password.isEmpty() || ageText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this, "Please fill in all fields.");
                return;
            }

            if (name.contains("|") || username.contains("|")
                    || password.contains("|")
                    || name.contains("\n") || username.contains("\n")
                    || password.contains("\n")) {

                JOptionPane.showMessageDialog(
                        this, "Please remove the | character and new lines.");
                return;
            }

            int age;

            try {
                age = Integer.parseInt(ageText);

                if (age < 1 || age > 120) {
                    JOptionPane.showMessageDialog(
                            this, "Age must be between 1 and 120.");
                    return;
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this, "Please enter a valid age.");
                return;
            }

            main.register(name, username, password, age, role);
        });

        backButton.addActionListener(e -> {
            main.showLogin();
        });
    }

    public void clearFields() {
        nameField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        ageField.setText("");
        roleBox.setSelectedIndex(0);
    }
}
		