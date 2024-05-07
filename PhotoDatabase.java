// Path: /mnt/data/PhotoDatabase.java

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;

public class PhotoDatabase {
    // MySQL database connection details
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/photo_app";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    // Create a connection to the MySQL database
    public static Connection createConnection() {
        Connection conn = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            System.out.println("Connection to MySQL database established successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to establish connection to MySQL database.");
            e.printStackTrace();
        }
        return conn;
    }

    // Create tables if they don't exist
    public static void createTables(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            // Create PhotosTable
            stmt.execute("CREATE TABLE IF NOT EXISTS PhotosTable (" +
                         "FileName VARCHAR(50), " +
                         "AlbumName VARCHAR(50), " +
                         "DateTaken DATETIME, " +
                         "FileType VARCHAR(4), " +
                         "UploadUserID VARCHAR(20))");

            // Create TagsTable
            stmt.execute("CREATE TABLE IF NOT EXISTS TagsTable (" +
                         "FileName VARCHAR(50), " +
                         "Tag VARCHAR(20), " +
                         "TimeTagAdded DATETIME)");

            // Create UsersTable
            stmt.execute("CREATE TABLE IF NOT EXISTS UsersTable (" +
                         "UserID VARCHAR(20), " +
                         "JoinedSince DATETIME)");

            // Create AlbumsTable
            stmt.execute("CREATE TABLE IF NOT EXISTS AlbumsTable (" +
                         "AlbumName VARCHAR(50), " +
                         "CreatorUserID VARCHAR(20), " +
                         "CreationDate DATE)");

            // Create PermissionsTable
            stmt.execute("CREATE TABLE IF NOT EXISTS PermissionsTable (" +
                         "AccessUserID VARCHAR(20), " +
                         "OwnerUserID VARCHAR(20), " +
                         "PermissionAdded DATETIME, " +
                         "AlbumName VARCHAR(50), " +
                         "FileName VARCHAR(50))");
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }

    // Insert data into PhotosTable
    public static void insertPhotosTable(Connection conn, String fileName, String albumName, Timestamp dateTaken, String fileType, String uploadUserID) {
        String sql = "INSERT INTO PhotosTable (FileName, AlbumName, DateTaken, FileType, UploadUserID) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fileName);
            pstmt.setString(2, albumName);
            pstmt.setTimestamp(3, dateTaken);
            pstmt.setString(4, fileType);
            pstmt.setString(5, uploadUserID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting into PhotosTable: " + e.getMessage());
        }
    }

    // Insert data into TagsTable
    public static void insertTagsTable(Connection conn, String fileName, String tag, Timestamp timeTagAdded) {
        String sql = "INSERT INTO TagsTable (FileName, Tag, TimeTagAdded) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fileName);
            pstmt.setString(2, tag);
            pstmt.setTimestamp(3, timeTagAdded);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting into TagsTable: " + e.getMessage());
        }
    }

    // Insert data into UsersTable
    public static void insertUsersTable(Connection conn, String userID, Timestamp joinedSince) {
        String sql = "INSERT INTO UsersTable (UserID, JoinedSince) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            pstmt.setTimestamp(2, joinedSince);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting into UsersTable: " + e.getMessage());
        }
    }

    // Insert data into AlbumsTable
    public static void insertAlbumsTable(Connection conn, String albumName, String creatorUserID, Date creationDate) {
        String sql = "INSERT INTO AlbumsTable (AlbumName, CreatorUserID, CreationDate) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, albumName);
            pstmt.setString(2, creatorUserID);
            pstmt.setDate(3, creationDate);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting into AlbumsTable: " + e.getMessage());
        }
    }

    // Insert data into PermissionsTable
    public static void insertPermissionsTable(Connection conn, String accessUserID, String ownerUserID, Timestamp permissionAdded, String albumName, String fileName) {
        String sql = "INSERT INTO PermissionsTable (AccessUserID, OwnerUserID, PermissionAdded, AlbumName, FileName) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, accessUserID);
            pstmt.setString(2, ownerUserID);
            pstmt.setTimestamp(3, permissionAdded);
            pstmt.setString(4, albumName);
            pstmt.setString(5, fileName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting into PermissionsTable: " + e.getMessage());
        }
    }

    // Select and print data from tables
    public static void selectAllPhotosTable(Connection conn) {
        String sql = "SELECT * FROM PhotosTable";
        try (Statement stmt = conn.createStatement()) {
            var rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("(" +
                    rs.getString("FileName") + ", " +
                    rs.getString("AlbumName") + ", " +
                    rs.getTimestamp("DateTaken") + ", " +
                    rs.getString("FileType") + ", " +
                    rs.getString("UploadUserID") +
                    ")");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving data from PhotosTable: " + e.getMessage());
        }
    }

    public static void selectAllTagsTable(Connection conn) {
        String sql = "SELECT * FROM TagsTable";
        try (Statement stmt = conn.createStatement()) {
            var rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("(" +
                    rs.getString("FileName") + ", " +
                    rs.getString("Tag") + ", " +
                    rs.getTimestamp("TimeTagAdded") +
                    ")");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving data from TagsTable: " + e.getMessage());
        }
    }

    public static void selectAllUsersTable(Connection conn) {
        String sql = "SELECT * FROM UsersTable";
        try (Statement stmt = conn.createStatement()) {
            var rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("(" +
                    rs.getString("UserID") + ", " +
                    rs.getTimestamp("JoinedSince") +
                    ")");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving data from UsersTable: " + e.getMessage());
        }
    }

    public static void selectAllAlbumsTable(Connection conn) {
        String sql = "SELECT * FROM AlbumsTable";
        try (Statement stmt = conn.createStatement()) {
            var rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("(" +
                    rs.getString("AlbumName") + ", " +
                    rs.getString("CreatorUserID") + ", " +
                    rs.getDate("CreationDate") +
                    ")");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving data from AlbumsTable: " + e.getMessage());
        }
    }

    public static void selectAllPermissionsTable(Connection conn) {
        String sql = "SELECT * FROM PermissionsTable";
        try (Statement stmt = conn.createStatement()) {
            var rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("(" +
                    rs.getString("AccessUserID") + ", " +
                    rs.getString("OwnerUserID") + ", " +
                    rs.getTimestamp("PermissionAdded") + ", " +
                    rs.getString("AlbumName") + ", " +
                    rs.getString("FileName") +
                    ")");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving data from PermissionsTable: " + e.getMessage());
        }
    }
}
