import java.sql.*;

public class Photodatabase{

   // Create a connection to a SQLite in-memory database
   // Returns Connection object
   public static Connection createConnection() {
       Connection conn = null;
       try {
           conn = DriverManager.getConnection("jdbc:sqlite::memory:");
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
       return conn;
   }

   // Create tables: PhotosTable, TagsTable, UsersTable, AlbumsTable, PermissionsTable
   public static void createTables(Connection conn) {
       try (Statement stmt = conn.createStatement()) {
           stmt.execute("CREATE TABLE PhotosTable (" +
                        "FileName VARCHAR(50), " +
                        "AlbumName VARCHAR(50), " +
                        "DateTaken DATETIME, " +
                        "FileType VARCHAR(4), " +
                        "UploadUserID VARCHAR(20))");

           stmt.execute("CREATE TABLE TagsTable (" +
                        "FileName VARCHAR(50), " +
                        "Tag VARCHAR(20), " +
                        "TimeTagAdded DATETIME)");

           stmt.execute("CREATE TABLE UsersTable (" +
                        "UserID VARCHAR(20), " +
                        "JoinedSince DATETIME)");

           stmt.execute("CREATE TABLE AlbumsTable (" +
                        "AlbumName VARCHAR(50), " +
                        "CreatorUserID VARCHAR(20), " +
                        "CreationDate DATE)");

           stmt.execute("CREATE TABLE PermissionsTable (" +
                        "AccessUserID VARCHAR(20), " +
                        "OwnerUserID VARCHAR(20), " +
                        "PermissionAdded DATETIME, " +
                        "AlbumName VARCHAR(50), " +
                        "FileName VARCHAR(50))");
       } catch (SQLException e) {
           System.out.println(e.getMessage());
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
           System.out.println(e.getMessage());
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
           System.out.println(e.getMessage());
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
           System.out.println(e.getMessage());
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
           System.out.println(e.getMessage());
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
           System.out.println(e.getMessage());
       }
   }

   // Select and print all rows from a table
   public static void selectAllPhotosTable(Connection conn) {
       String sql = "SELECT * FROM PhotosTable";
       try (Statement stmt = conn.createStatement()) {
           ResultSet rs = stmt.executeQuery(sql);
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
           System.out.println(e.getMessage());
       }
   }

   public static void selectAllTagsTable(Connection conn) {
       String sql = "SELECT * FROM TagsTable";
       try (Statement stmt = conn.createStatement()) {
           ResultSet rs = stmt.executeQuery(sql);
           while (rs.next()) {
               System.out.println("(" +
                   rs.getString("FileName") + ", " +
                   rs.getString("Tag") + ", " +
                   rs.getTimestamp("TimeTagAdded") +
                   ")");
           }
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
   }

   public static void selectAllUsersTable(Connection conn) {
       String sql = "SELECT * FROM UsersTable";
       try (Statement stmt = conn.createStatement()) {
           ResultSet rs = stmt.executeQuery(sql);
           while (rs.next()) {
               System.out.println("(" +
                   rs.getString("UserID") + ", " +
                   rs.getTimestamp("JoinedSince") +
                   ")");
           }
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
   }

   public static void selectAllAlbumsTable(Connection conn) {
       String sql = "SELECT * FROM AlbumsTable";
       try (Statement stmt = conn.createStatement()) {
           ResultSet rs = stmt.executeQuery(sql);
           while (rs.next()) {
               System.out.println("(" +
                   rs.getString("AlbumName") + ", " +
                   rs.getString("CreatorUserID") + ", " +
                   rs.getDate("CreationDate") +
                   ")");
           }
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
   }

   public static void selectAllPermissionsTable(Connection conn) {
       String sql = "SELECT * FROM PermissionsTable";
       try (Statement stmt = conn.createStatement()) {
           ResultSet rs = stmt.executeQuery(sql);
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
           System.out.println(e.getMessage());
       }
   }

   // DO NOT MODIFY main
   public static void main(String[] args) {
       // Create connection to SQLite in-memory database
       Connection conn = createConnection();

       // Create tables
       createTables(conn);

       // Insert sample data into tables
       insertPhotosTable(conn, "photo1.jpg", "Album1", new Timestamp(System.currentTimeMillis()), "jpg", "user1");
       insertTagsTable(conn, "photo1.jpg", "nature", new Timestamp(System.currentTimeMillis()));
       insertUsersTable(conn, "user1", new Timestamp(System.currentTimeMillis()));
       insertAlbumsTable(conn, "Album1", "user1", new Date(System.currentTimeMillis()));
       insertPermissionsTable(conn, "user2", "user1", new Timestamp(System.currentTimeMillis()), "Album1", "photo1.jpg");

       // Select and print all rows from each table
       selectAllPhotosTable(conn);
       selectAllTagsTable(conn);
       selectAllUsersTable(conn);
       selectAllAlbumsTable(conn);
       selectAllPermissionsTable(conn);
   }
}
