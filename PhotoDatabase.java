import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhotoDatabase {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/photo_app";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    // Create a connection to the database
    public static Connection createConnection() {
        try {
            return DriverManager.getConnection(DATABASE_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.err.println("Failed to create a connection to the database.");
            e.printStackTrace();
            return null;
        }
    }

    // Check login credentials
    public static boolean checkLogin(String userId, String password) {
        String query = "SELECT Password FROM Users WHERE UserID = ?";
        try (Connection conn = createConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String retrievedPassword = rs.getString("Password");
                return retrievedPassword.equals(password);
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error checking login credentials: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Fetch the list of albums for a user
    public static List<String> fetchAlbums(String userId) {
        List<String> albums = new ArrayList<>();
        String query = "SELECT AlbumName FROM Albums WHERE CreatorUserID = ?";
        try (Connection conn = createConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                albums.add(rs.getString("AlbumName"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching albums: " + e.getMessage());
            e.printStackTrace();
        }
        return albums;
    }

    // Create a new album
    public static boolean createAlbum(String userId, String albumName) {
        String query = "INSERT INTO Albums (AlbumName, CreatorUserID, CreationDate) VALUES (?, ?, NOW())";
        try (Connection conn = createConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, albumName);
            pstmt.setString(2, userId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error creating album: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

// Fetch photo details for a specific album
public static List<String[]> fetchPhotoDetails(String albumName) {
    List<String[]> details = new ArrayList<>();
    String query = "SELECT Filename, DateTaken, Filetype, FilePath " +
                   "FROM Photos " +
                   "WHERE AlbumID = (SELECT AlbumID FROM Albums WHERE AlbumName = ?)";
    try (Connection conn = createConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, albumName);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            String[] row = {
                rs.getString("Filename"),
                rs.getString("DateTaken"),
                rs.getString("Filetype"),
                rs.getString("FilePath"),
                "" // Empty string for Tags (not retrieved in this query)
            };
            details.add(row);
        }
    } catch (SQLException e) {
        System.err.println("Error fetching photo details: " + e.getMessage());
        e.printStackTrace();
    }
    return details;
}

    // Add a photo to an album
    public static boolean addPhoto(String filename, String albumName, String filePath, String userId) {
        String query = "INSERT INTO Photos (Filename, AlbumID, DateTaken, Filetype, UploaderUserID, FilePath) " +
                       "VALUES (?, (SELECT AlbumID FROM Albums WHERE AlbumName = ?), NOW(), ?, ?, ?)";
        try (Connection conn = createConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, filename);
            pstmt.setString(2, albumName);
            pstmt.setString(3, getFileType(filePath));
            pstmt.setString(4, userId);
            pstmt.setString(5, filePath);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error adding photo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to get the file type from a file path
    private static String getFileType(String filePath) {
        int dotIndex = filePath.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filePath.length() - 1) {
            return filePath.substring(dotIndex + 1);
        }
        return "";
    }

    // Test method to print photo details to the console
    public static void printPhotoDetails(String albumName) {
        String query = "SELECT p.Filename, p.DateTaken, p.Filetype, p.FilePath, GROUP_CONCAT(t.Tag SEPARATOR ', ') AS Tags " +
                       "FROM Photos p LEFT JOIN Tags t ON p.PhotoID = t.PhotoID " +
                       "WHERE p.AlbumID = (SELECT AlbumID FROM Albums WHERE AlbumName = ?) " +
                       "GROUP BY p.Filename, p.DateTaken, p.Filetype, p.FilePath";
        try (Connection conn = createConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, albumName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String filename = rs.getString("Filename");
                String dateTaken = rs.getString("DateTaken");
                String filetype = rs.getString("Filetype");
                String filePath = rs.getString("FilePath");
                String tags = rs.getString("Tags") != null ? rs.getString("Tags") : "No Tags";
                System.out.println("Filename: " + filename + ", Date Taken: " + dateTaken + ", Filetype: " + filetype +
                                   ", FilePath: " + filePath + ", Tags: " + tags);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching photo details: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
