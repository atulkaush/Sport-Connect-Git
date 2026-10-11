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

        setLayout(new GridBagLayout());
        setBackground(new Color(240, 245, 250));

        JPanel panel = new JPanel(new GridLayout(0, 1, 8, 8));
        panel.setPreferredSize(new Dimension(350, 430));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Create Account", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        nameField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        ageField = new JTextField();

        roleBox = new JComboBox<>(new String[] {
                "Athlete", "Coach"
        });

        JButton createButton = new JButton("Register");
        JButton backButton = new JButton("Back to Login");

        panel.add(title);

        panel.add(new JLabel("Full Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        panel.add(new JLabel("Age:"));
        panel.add(ageField);

        panel.add(new JLabel("Register As:"));
        panel.add(roleBox);

        panel.add(createButton);
        panel.add(backButton);

        add(panel);

        createButton.addActionListener(e -> {

            String name = nameField.getText().trim();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
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