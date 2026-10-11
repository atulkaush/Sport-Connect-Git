
package sport;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

import sport.model.User;
import sport.model.AthleteProfile;
import sport.model.CoachProfile;
import sport.data.FileManager;
import sport.ui.LoginPanel;
import sport.ui.RegisterPanel;
import sport.ui.ProfilePanel;

public class Main extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;

    private ArrayList<User> users;
    private User currentUser;

    private FileManager fileManager;

    private static final String ACCOUNT_FILE = "accounts.txt";

    public Main() {

        users = new ArrayList<>();
        fileManager = new FileManager();

        loadUsers();

        setTitle("Sports Connect");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        loginPanel = new LoginPanel(this);
        registerPanel = new RegisterPanel(this);

        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(registerPanel, "REGISTER");

        add(mainPanel);

        showLogin();

        setVisible(true);
    }

    // Show login screen
    public void showLogin() {
        loginPanel.clearFields();
        cardLayout.show(mainPanel, "LOGIN");
    }

    // Show registration screen
    public void showRegister() {
        registerPanel.clearFields();
        cardLayout.show(mainPanel, "REGISTER");
    }

    // Register a new user
    public void register(String name, String username,
                         String password, int age, String role) {

        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                JOptionPane.showMessageDialog(
                        this, "Username already exists.");
                return;
            }
        }

        User newUser = new User(
                name, username, password, age, role);

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(ACCOUNT_FILE, true))) {

            writer.write(name + "|" + username + "|"
                    + password + "|" + age + "|" + role);
            writer.newLine();

            users.add(newUser);

            JOptionPane.showMessageDialog(
                    this, "Account created successfully!");

            showLogin();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this, "Error saving account: " + e.getMessage());
        }
    }

    // Log in an existing user
    public void login(String username, String password) {

        for (User user : users) {

            if (user.getUsername().equalsIgnoreCase(username)
                    && user.getPassword().equals(password)) {

                currentUser = user;

                JOptionPane.showMessageDialog(
                        this, "Welcome, " + user.getName() + "!");

                showHome();
                return;
            }
        }

        JOptionPane.showMessageDialog(
                this, "Invalid username or password.");
    }

    // Load registered accounts
    private void loadUsers() {

        File file = new File(ACCOUNT_FILE);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("\\|", -1);

                if (parts.length == 5) {
                    try {
                        int age = Integer.parseInt(parts[3]);

                        User user = new User(
                                parts[0],
                                parts[1],
                                parts[2],
                                age,
                                parts[4]);

                        users.add(user);

                    } catch (NumberFormatException e) {
                        System.out.println(
                                "Skipping invalid account.");
                    }
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this, "Error loading accounts: " + e.getMessage());
        }
    }

    // Display the home screen
    private void showHome() {

        JPanel homePanel = new JPanel(new BorderLayout(10, 10));
        homePanel.setBackground(new Color(240, 244, 248));

        // Header
        JPanel header = new JPanel(new GridLayout(2, 1, 5, 5));
        header.setBackground(new Color(25, 45, 75));
        header.setBorder(
                BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JLabel title = new JLabel(
                "Sports Connect", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(Color.WHITE);

        JLabel welcome = new JLabel(
                "Welcome, " + currentUser.getName(),
                JLabel.CENTER);
        welcome.setFont(new Font("Arial", Font.PLAIN, 15));
        welcome.setForeground(Color.WHITE);

        header.add(title);
        header.add(welcome);

        homePanel.add(header, BorderLayout.NORTH);

        // Switch between menu and MDI profile area
        CardLayout contentLayout = new CardLayout();
        JPanel contentPanel = new JPanel(contentLayout);

        // Main menu: four buttons in a 2 x 2 grid
        JPanel menuPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        menuPanel.setBackground(new Color(240, 244, 248));
        menuPanel.setBorder(
                BorderFactory.createEmptyBorder(30, 45, 30, 45));

        JButton profileButton = new JButton("My Profile");
        JButton discoverButton = new JButton("Discover");
        JButton connectionsButton = new JButton("Connection");
        JButton aboutButton = new JButton("Help/About");

        JButton[] menuButtons = {
                profileButton,
                discoverButton,
                connectionsButton,
                aboutButton
        };

        for (JButton button : menuButtons) {
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setBackground(Color.WHITE);
            button.setForeground(new Color(25, 45, 75));
            button.setFocusPainted(false);
        }

        menuPanel.add(profileButton);
        menuPanel.add(discoverButton);
        menuPanel.add(connectionsButton);
        menuPanel.add(aboutButton);

        // MDI desktop area
        JDesktopPane desktop = new JDesktopPane();
        desktop.setBackground(new Color(240, 244, 248));

        JPanel desktopPanel = new JPanel(new BorderLayout());
        desktopPanel.setBackground(new Color(240, 244, 248));

        JButton backButton = new JButton("Back to Home");
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.setBackground(new Color(240, 244, 248));
        backPanel.add(backButton);

        desktopPanel.add(backPanel, BorderLayout.NORTH);
        desktopPanel.add(desktop, BorderLayout.CENTER);

        contentPanel.add(menuPanel, "MENU");
        contentPanel.add(desktopPanel, "DESKTOP");

        homePanel.add(contentPanel, BorderLayout.CENTER);

        // Logout at the bottom
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.setBackground(new Color(240, 244, 248));
        bottom.setBorder(
                BorderFactory.createEmptyBorder(0, 10, 15, 10));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setBackground(new Color(25, 45, 75));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);

        bottom.add(logoutButton);
        homePanel.add(bottom, BorderLayout.SOUTH);

        // My Profile: open an internal window
        profileButton.addActionListener(e -> {

            desktop.removeAll();

            JInternalFrame profileFrame = new JInternalFrame(
                    "My Profile", true, true, true, true);

            profileFrame.setSize(550, 420);
            profileFrame.setLayout(new BorderLayout());

            profileFrame.add(
                    new ProfilePanel(currentUser),
                    BorderLayout.CENTER);

            desktop.add(profileFrame);
            profileFrame.setVisible(true);

            try {
                profileFrame.setSelected(true);
            } catch (java.beans.PropertyVetoException ex) {
                System.out.println(ex.getMessage());
            }

            contentLayout.show(contentPanel, "DESKTOP");
            desktop.revalidate();
            desktop.repaint();
        });

        // Back to menu
        backButton.addActionListener(e -> {
            contentLayout.show(contentPanel, "MENU");
        });

        // Discover profiles
        discoverButton.addActionListener(e -> showDiscover());

        // Connection placeholder
        connectionsButton.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Connection feature will be added next."));

        // Help/About
        aboutButton.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Sports Connect\n\n"
                        + "A platform connecting athletes and coaches.\n"
                        + "Create your profile and discover other sports members.",
                        "Help/About",
                        JOptionPane.INFORMATION_MESSAGE));

        // Logout
        logoutButton.addActionListener(e -> {
            currentUser = null;

            mainPanel.removeAll();

            loginPanel = new LoginPanel(this);
            registerPanel = new RegisterPanel(this);

            mainPanel.add(loginPanel, "LOGIN");
            mainPanel.add(registerPanel, "REGISTER");

            mainPanel.revalidate();
            mainPanel.repaint();

            showLogin();
        });

        mainPanel.add(homePanel, "HOME");
        cardLayout.show(mainPanel, "HOME");

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // Display saved athlete and coach profiles
    private void showDiscover() {

        ArrayList<AthleteProfile> athletes =
                fileManager.loadAthleteProfiles();

        ArrayList<CoachProfile> coaches =
                fileManager.loadCoachProfiles();

        StringBuilder result = new StringBuilder();

        result.append("ATHLETES\n");
        result.append("-------------------------\n");

        for (AthleteProfile athlete : athletes) {
            result.append("Username: ")
                    .append(athlete.getUsername())
                    .append("\nSport: ")
                    .append(athlete.getSport())
                    .append("\nPosition: ")
                    .append(athlete.getPosition())
                    .append("\nLocation: ")
                    .append(athlete.getLocation())
                    .append("\n\n");
        }

        result.append("COACHES\n");
        result.append("-------------------------\n");

        for (CoachProfile coach : coaches) {
            result.append("Username: ")
                    .append(coach.getUsername())
                    .append("\nSpecialization: ")
                    .append(coach.getSpecialization())
                    .append("\nExperience: ")
                    .append(coach.getExperience())
                    .append(" years\nLocation: ")
                    .append(coach.getLocation())
                    .append("\n\n");
        }

        if (athletes.isEmpty() && coaches.isEmpty()) {
            result.append("No profiles found yet.\n")
                    .append("Create and save a profile first.");
        }

        JTextArea textArea = new JTextArea(result.toString());
        textArea.setEditable(false);
        textArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 350));

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Discover Profiles",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
