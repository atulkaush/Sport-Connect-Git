package sport.ui;

import javax.swing.*;
import java.awt.*;

import sport.model.User;
import sport.model.AthleteProfile;
import sport.model.CoachProfile;
import sport.data.FileManager;

public class ProfilePanel extends JPanel {

    private User user;
    private FileManager fileManager;

    private JTextField sportField;
    private JTextField positionField;
    private JTextField achievementsField;
    private JTextField locationField;

    private JTextField specializationField;
    private JTextField experienceField;
    private JTextField bioField;

    public ProfilePanel(User user) {
        this.user = user;
        fileManager = new FileManager();

        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("My Sports Profile", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        add(heading, BorderLayout.NORTH);

        JPanel fields = new JPanel(new GridLayout(0, 2, 10, 10));
        fields.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        fields.add(new JLabel("Name:"));
        fields.add(new JLabel(user.getName()));

        fields.add(new JLabel("Username:"));
        fields.add(new JLabel(user.getUsername()));

        fields.add(new JLabel("Role:"));
        fields.add(new JLabel(user.getRole()));

        sportField = new JTextField();
        positionField = new JTextField();
        achievementsField = new JTextField();
        locationField = new JTextField();

        specializationField = new JTextField();
        experienceField = new JTextField();
        bioField = new JTextField();

        if (user.getRole().equalsIgnoreCase("Athlete")) {

            fields.add(new JLabel("Sport:"));
            fields.add(sportField);

            fields.add(new JLabel("Position:"));
            fields.add(positionField);

            fields.add(new JLabel("Achievements:"));
            fields.add(achievementsField);

            fields.add(new JLabel("Location:"));
            fields.add(locationField);

        } else {

            fields.add(new JLabel("Specialization:"));
            fields.add(specializationField);

            fields.add(new JLabel("Experience (years):"));
            fields.add(experienceField);

            fields.add(new JLabel("Location:"));
            fields.add(locationField);

            fields.add(new JLabel("Short Bio:"));
            fields.add(bioField);
        }

        add(fields, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save Profile");

        saveButton.addActionListener(e -> saveProfile());

        JPanel bottom = new JPanel();
        bottom.add(saveButton);

        add(bottom, BorderLayout.SOUTH);
    }

    private void saveProfile() {

        String location = locationField.getText().trim();

        if (location.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Please enter your location.");
            return;
        }

        if (user.getRole().equalsIgnoreCase("Athlete")) {

            String sport = sportField.getText().trim();
            String position = positionField.getText().trim();
            String achievements = achievementsField.getText().trim();

            if (sport.isEmpty() || position.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "Please enter your sport and position.");
                return;
            }

            AthleteProfile profile = new AthleteProfile(
                    user.getUsername(),
                    sport,
                    position,
                    achievements,
                    location
            );

            fileManager.saveAthleteProfile(profile);

        } else {

            String specialization =
                    specializationField.getText().trim();
            String experienceText =
                    experienceField.getText().trim();
            String bio = bioField.getText().trim();

            if (specialization.isEmpty() || experienceText.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter specialization and experience.");
                return;
            }

            try {
                int experience = Integer.parseInt(experienceText);

                if (experience < 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Experience cannot be negative.");
                    return;
                }

                CoachProfile profile = new CoachProfile(
                        user.getUsername(),
                        specialization,
                        experience,
                        location,
                        bio
                );

                fileManager.saveCoachProfile(profile);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Experience must be a valid whole number.");
                return;
            }
        }

        JOptionPane.showMessageDialog(
                this, "Profile saved successfully!");
    }
}