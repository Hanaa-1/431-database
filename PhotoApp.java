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
      private JButton uploadPictureButton;
      private JLabel statusLabel;
      private int loggedInUserId = -1;

      public PhotoApp() {
          // Set Up GUI components
          setTitle("Photo App");
          setDefaultCloseOperation(EXIT_ON_CLOSE);
          setSize(400, 300);
          setLayout(new BorderLayout(10, 10)); // Add padding

          JPanel inputPanel = new JPanel(new GridBagLayout());
          GridBagConstraints gbc = new GridBagConstraints();
          gbc.insets = new Insets(5, 5, 5, 5); // Margins between components
          gbc.fill = GridBagConstraints.HORIZONTAL;
          gbc.gridx = 0;
          gbc.gridy = 0;

          // Username label and field
          inputPanel.add(new JLabel("Username:"), gbc);
          gbc.gridx = 1;
          usernameField = new JTextField();
          inputPanel.add(usernameField, gbc);
          usernameField.setToolTipText("Enter your username");

          // Password label and field
          gbc.gridx = 0;
          gbc.gridy++;
          inputPanel.add(new JLabel("Password:"), gbc);
          gbc.gridx = 1;
          passwordField = new JPasswordField();
          inputPanel.add(passwordField, gbc);
          passwordField.setToolTipText("Enter your password");

          // Create Account button
          gbc.gridx = 0;
          gbc.gridy++;
          createAccountButton = new JButton("Create Account");
          createAccountButton.addActionListener(new CreateAccountActionListener());
          inputPanel.add(createAccountButton, gbc);

          // Login button
          gbc.gridx = 1;
          loginButton = new JButton("Log In");
          loginButton.addActionListener(new LoginActionListener());
          inputPanel.add(loginButton, gbc);

          // Upload Picture button
          gbc.gridx = 0;
          gbc.gridy++;
          gbc.gridwidth = 2;
          uploadPictureButton = new JButton("Upload Picture");
          uploadPictureButton.addActionListener(new UploadPictureActionListener());
          uploadPictureButton.setEnabled(false); // Disabled until logged in
          inputPanel.add(uploadPictureButton, gbc);
          uploadPictureButton.setToolTipText("Upload a picture after logging in");

          // Status label
          gbc.gridy++;
          statusLabel = new JLabel("Please log in or create an account.");
          inputPanel.add(statusLabel, gbc);

          // Add input panel to frame
          add(inputPanel, BorderLayout.CENTER);

          // Set a background color
          inputPanel.setBackground(new Color(240, 240, 240));
          getContentPane().setBackground(new Color(220, 220, 220));

          // Set an application icon (optional)
          // setIconImage(new ImageIcon("path/to/icon.png").getImage());

          setLocationRelativeTo(null); // Center the window
          setVisible(true);
      }

      // Action listeners for buttons (placeholders)
      private class CreateAccountActionListener implements ActionListener {
          public void actionPerformed(ActionEvent e) {
              statusLabel.setText("Creating account...");
          }
      }

      private class LoginActionListener implements ActionListener {
          public void actionPerformed(ActionEvent e) {
              statusLabel.setText("Logging in...");
              uploadPictureButton.setEnabled(true);
          }
      }

      private class UploadPictureActionListener implements ActionListener {
          public void actionPerformed(ActionEvent e) {
              statusLabel.setText("Uploading picture...");
          }
      }

      public static void main(String[] args) {
          new PhotoApp();
      }
  }
