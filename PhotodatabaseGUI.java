import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PhotoDatabaseGUI {
    private JFrame frame;
    private JLabel imageLabel;
    private JButton loadButton;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PhotoDatabaseGUI window = new PhotoDatabaseGUI();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PhotoDatabaseGUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Photo Database GUI");
        frame.setBounds(100, 100, 600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new FlowLayout());

        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(600, 600));
        frame.getContentPane().add(imageLabel);

        loadButton = new JButton("Load Image");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", ImageIO.getReaderFileSuffixes()));
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        File selectedFile = fileChooser.getSelectedFile();
                        Image image = ImageIO.read(selectedFile);
                        Image scaledImage = image.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
                        imageLabel.setIcon(new ImageIcon(scaledImage));
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Error loading image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        frame.getContentPane().add(loadButton);
    }
}
