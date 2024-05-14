import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class PhotoApp extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, addButton, refreshButton;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private String currentUser;
    private JList<String> albumList;
    private DefaultListModel<String> albumListModel;
    private JTable photoTable;
    private DefaultTableModel photoTableModel;

    public PhotoApp() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Photo Management App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel loginPanel = createLoginPanel();
        JPanel albumsPanel = createAlbumsPanel();

        mainPanel.add(loginPanel, "Login");
        mainPanel.add(albumsPanel, "Albums");

        add(mainPanel);
        setVisible(true);
        cardLayout.show(mainPanel, "Login");
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(null); // Using null layout for custom positioning
        panel.setBackground(new Color(255, 192, 203)); // Light Pink Background
    
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setBounds(50, 50, 100, 30);
        panel.add(usernameLabel);
    
        usernameField = new JTextField();
        usernameField.setBounds(160, 50, 200, 30);
        panel.add(usernameField);
    
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setBounds(50, 100, 100, 30);
        panel.add(passwordLabel);
    
        passwordField = new JPasswordField();
        passwordField.setBounds(160, 100, 200, 30);
        panel.add(passwordField);
    
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(255, 20, 147)); // Hot Pink Button
        loginButton.setForeground(Color.WHITE);
        loginButton.setBounds(160, 150, 200, 40);
        loginButton.addActionListener(this::performLogin);
        panel.add(loginButton);
    
        return panel;
    }

    private void performLogin(ActionEvent e) {
        currentUser = usernameField.getText();
        String password = new String(passwordField.getPassword());
        if (PhotoDatabase.checkLogin(currentUser, password)) {
            JOptionPane.showMessageDialog(this, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainPanel, "Albums");
            refreshAlbumList();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createAlbumsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        albumListModel = new DefaultListModel<>();
        albumList = new JList<>(albumListModel);
        albumList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        albumList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showPhotosInAlbum(albumList.getSelectedValue());
            }
        });
        panel.add(new JScrollPane(albumList), BorderLayout.WEST);

        String[] columnNames = {"Filename", "Date Taken", "Tags"};
        photoTableModel = new DefaultTableModel(columnNames, 0);
        photoTable = new JTable(photoTableModel);
        panel.add(new JScrollPane(photoTable), BorderLayout.CENTER);

        addButton = new JButton("Add Photo");
        addButton.addActionListener(this::addPhoto);
        panel.add(addButton, BorderLayout.SOUTH);

        refreshButton = new JButton("Refresh Albums");
        refreshButton.addActionListener(e -> refreshAlbumList());
        panel.add(refreshButton, BorderLayout.NORTH);

        return panel;
    }

    private void refreshAlbumList() {
        List<String> albums = PhotoDatabase.fetchAlbums(currentUser);
        albumListModel.clear();
        albums.forEach(albumListModel::addElement);
    }

    private void showPhotosInAlbum(String albumName) {
        List<String[]> photoDetails = PhotoDatabase.fetchPhotoDetails(albumName);
        photoTableModel.setRowCount(0); // Clear existing rows
        for (String[] detail : photoDetails) {
            photoTableModel.addRow(detail);
        }
    }

    private void addPhoto(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filename = file.getName();
            String path = file.getAbsolutePath();
            String albumName = albumList.getSelectedValue();
            if (PhotoDatabase.addPhoto(filename, albumName, path, currentUser)) {
                showPhotosInAlbum(albumName); // Refresh the photo details view
                JOptionPane.showMessageDialog(this, "Photo added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add photo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PhotoApp::new);
    }
}
