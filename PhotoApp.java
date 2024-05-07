// Path: /mnt/data/PhotoApp.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PhotoApp extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton createAccountButton;
    private JButton loginButton;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public PhotoApp() {
        // Setup frame
        setTitle("Photo App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout(10, 10));

        // Initialize the CardLayout for switching panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Login Panel
        JPanel loginPanel = createLoginPanel();

        // Albums Panel
        JPanel albumsPanel = createAlbumsPanel();

        // Upload Panel
        JPanel uploadPanel = createUploadPanel();

        // Add panels to CardLayout
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(albumsPanel, "Albums");
        mainPanel.add(uploadPanel, "Upload");

        // Set the main panel
        add(mainPanel, BorderLayout.CENTER);

        // Start with the Login Panel
        cardLayout.show(mainPanel, "Login");
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Username label and field
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        // Password label and field
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        // Create Account button
        gbc.gridx = 0;
        gbc.gridy++;
        createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Account creation logic here
                JOptionPane.showMessageDialog(null, "Account creation feature to be implemented.");
            }
        });
        panel.add(createAccountButton, gbc);

        // Login button
        gbc.gridx = 1;
        loginButton = new JButton("Log In");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Check credentials
                // If valid, navigate to Albums Panel
                cardLayout.show(mainPanel, "Albums");
            }
        });
        panel.add(loginButton, gbc);

        return panel;
    }

    private JPanel createAlbumsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Example albums content
        JTextArea albumsArea = new JTextArea("Albums content here...");
        albumsArea.setEditable(false);

        panel.add(new JScrollPane(albumsArea), BorderLayout.CENTER);

        // Button to navigate to upload photos
        JButton uploadButton = new JButton("Upload Photos");
        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Switch to Upload Panel
                cardLayout.show(mainPanel, "Upload");
            }
        });

        panel.add(uploadButton, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createUploadPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // File chooser for uploading photos
        JButton uploadButton = new JButton("Select Photo to Upload");
        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    // Implement logic to upload the selected photo
                    JOptionPane.showMessageDialog(null, "Photo selected: " + fileChooser.getSelectedFile().getName());
                }
            }
        });

        panel.add(uploadButton, BorderLayout.CENTER);

        // Button to return to albums view
        JButton backButton = new JButton("Back to Albums");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Albums");
            }
        });

        panel.add(backButton, BorderLayout.SOUTH);
        return panel;
    }

    public static void main(String[] args) {
        new PhotoApp();
    }
}
