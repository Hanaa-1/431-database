import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

public class PhotoApp extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, addPhotoButton, backButton, refreshButton;
    private JPanel albumsPanel, albumContentsPanel;
    private String currentUser;
    private DefaultListModel<String> albumListModel;
    private JTable photoTable;
    private DefaultTableModel photoTableModel;

    // Declare mainPanel and cardLayout as instance variables
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public PhotoApp() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Personal Photo App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel loginPanel = createLoginPanel();
        albumsPanel = createAlbumsPanel();
        albumContentsPanel = createAlbumContentsPanel();

        mainPanel.add(loginPanel, "Login");
        mainPanel.add(albumsPanel, "Albums");
        mainPanel.add(albumContentsPanel, "AlbumContents");

        add(mainPanel);
        setVisible(true);
        cardLayout.show(mainPanel, "Login");
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(255, 228, 225));

        JLabel welcomeLabel = new JLabel("Welcome,");
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBounds(320, 100, 160, 30);
        panel.add(welcomeLabel);

        JLabel subtextLabel = new JLabel("Sign in to Continue!");
        subtextLabel.setForeground(Color.BLACK);
        subtextLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        subtextLabel.setBounds(300, 140, 200, 30);
        panel.add(subtextLabel);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setBounds(300, 200, 100, 30);
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(300, 230, 200, 30);
        usernameField.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setBounds(300, 280, 100, 30);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(300, 310, 200, 30);
        passwordField.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
        panel.add(passwordField);

        loginButton = new JButton("SIGN IN");
        loginButton.setBackground(new Color(255, 105, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBounds(300, 370, 200, 40);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.addActionListener(this::performLogin);
        makeButtonShiny(loginButton);
        panel.add(loginButton);

        JLabel createAccountLabel = new JLabel("Don't have an account?");
        createAccountLabel.setForeground(Color.BLACK);
        createAccountLabel.setBounds(300, 420, 200, 30);
        panel.add(createAccountLabel);

        JButton createAccountButton = new JButton("Create New");
        createAccountButton.setForeground(new Color(255, 105, 180));
        createAccountButton.setBackground(new Color(255, 228, 225));
        createAccountButton.setBorderPainted(false);
        createAccountButton.setBounds(300, 450, 200, 30);
        panel.add(createAccountButton);

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
        panel.setBackground(new Color(255, 228, 225));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel headerLabel = new JLabel("Albums");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(headerLabel, BorderLayout.NORTH);

        JPanel albumsContainer = new JPanel(new GridLayout(0, 3, 10, 10));
        albumsContainer.setBackground(new Color(255, 228, 225));
        JScrollPane scrollPane = new JScrollPane(albumsContainer);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void refreshAlbumList() {
        JPanel albumsContainer = (JPanel) ((JScrollPane) albumsPanel.getComponent(1)).getViewport().getView();
        albumsContainer.removeAll();

        List<String> albums = PhotoDatabase.fetchAlbums(currentUser);
        for (String album : albums) {
            JButton albumButton = createAlbumButton(album);
            albumsContainer.add(albumButton);
        }

        JButton addAlbumButton = createAddAlbumButton();
        albumsContainer.add(addAlbumButton);

        albumsContainer.revalidate();
        albumsContainer.repaint();
    }

    private JButton createAlbumButton(String albumName) {
        JButton button = new JButton(albumName);
        button.setPreferredSize(new Dimension(200, 200));
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setBackground(new Color(255, 192, 203));
        button.setForeground(Color.WHITE);
        button.setBorder(new LineBorder(Color.WHITE, 2, true));
        button.addActionListener(e -> showAlbumContents(albumName));
        makeButtonShiny(button);
        return button;
    }

    private JButton createAddAlbumButton() {
        JButton button = new JButton("+");
        button.setPreferredSize(new Dimension(200, 200));
        button.setFont(new Font("Arial", Font.PLAIN, 50));
        button.setBackground(new Color(255, 192, 203));
        button.setForeground(Color.WHITE);
        button.setBorder(new LineBorder(Color.WHITE, 2, true));
        button.addActionListener(e -> addNewAlbum());
        makeButtonShiny(button);
        return button;
    }

    private void showAlbumContents(String albumName) {
        cardLayout.show(mainPanel, "AlbumContents");
        loadAlbumContents(albumName);
    }

    private JPanel createAlbumContentsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 228, 225));

        JLabel headerLabel = new JLabel("Album Contents");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(headerLabel, BorderLayout.NORTH);

        String[] columnNames = {"Filename", "Date Taken", "Filetype", "FilePath", "Tags"};
        photoTableModel = new DefaultTableModel(columnNames, 0);
        photoTable = new JTable(photoTableModel);
        panel.add(new JScrollPane(photoTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(255, 228, 225));

        backButton = new JButton("Back to Albums");
        backButton.setBackground(new Color(255, 192, 203));
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(new LineBorder(Color.WHITE, 2, true));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Albums"));
        makeButtonShiny(backButton);
        buttonPanel.add(backButton);

        addPhotoButton = new JButton("Add Photo");
        addPhotoButton.setBackground(new Color(255, 192, 203));
        addPhotoButton.setForeground(Color.WHITE);
        addPhotoButton.setBorder(new LineBorder(Color.WHITE, 2, true));
        addPhotoButton.addActionListener(this::addPhoto);
        makeButtonShiny(addPhotoButton);
        buttonPanel.add(addPhotoButton);

        refreshButton = new JButton("Refresh Albums");
        refreshButton.setBackground(new Color(255, 192, 203));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setBorder(new LineBorder(Color.WHITE, 2, true));
        refreshButton.addActionListener(e -> refreshAlbumList());
        makeButtonShiny(refreshButton);
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void loadAlbumContents(String albumName) {
        List<String[]> photos = PhotoDatabase.fetchPhotoDetails(albumName);
        if (photos != null) {
            photoTableModel.setRowCount(0); // Clear existing rows
            for (String[] photo : photos) {
                photoTableModel.addRow(photo);
            }
        }
    }

    private void addNewAlbum() {
        String albumName = JOptionPane.showInputDialog(this, "Enter the album name:", "New Album", JOptionPane.PLAIN_MESSAGE);
        if (albumName != null && !albumName.trim().isEmpty()) {
            if (PhotoDatabase.createAlbum(currentUser, albumName)) {
                refreshAlbumList();
                JOptionPane.showMessageDialog(this, "Album created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create album. It may already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addPhoto(ActionEvent e) {
        String albumName = JOptionPane.showInputDialog(this, "Enter the album name to add photo:", "Add Photo", JOptionPane.PLAIN_MESSAGE);
        if (albumName != null && !albumName.trim().isEmpty()) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filename = selectedFile.getName();
                String filePath = selectedFile.getAbsolutePath();
                if (PhotoDatabase.addPhoto(filename, albumName, filePath, currentUser)) {
                    loadAlbumContents(albumName);
                    JOptionPane.showMessageDialog(this, "Photo added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add photo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void makeButtonShiny(JButton button) {
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(button.getBackground().brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(button.getBackground().darker());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PhotoApp::new);
    }
}

