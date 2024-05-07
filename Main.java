// Path: /mnt/data/Main.java

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // Create a connection to the MySQL database
        Connection conn = PhotoDatabase.createConnection();

        // Verify that the connection is successful before proceeding
        if (conn != null) {
            // Create tables if they don't already exist
            PhotoDatabase.createTables(conn);

            // Insert some initial data (sample data for testing purposes)
            PhotoDatabase.insertPhotosTable(conn, "photo1.jpg", "Album1", new java.sql.Timestamp(System.currentTimeMillis()), "jpg", "user1");
            PhotoDatabase.insertTagsTable(conn, "photo1.jpg", "nature", new java.sql.Timestamp(System.currentTimeMillis()));
            PhotoDatabase.insertUsersTable(conn, "user1", new java.sql.Timestamp(System.currentTimeMillis()));
            PhotoDatabase.insertAlbumsTable(conn, "Album1", "user1", new java.sql.Date(System.currentTimeMillis()));
            PhotoDatabase.insertPermissionsTable(conn, "user2", "user1", new java.sql.Timestamp(System.currentTimeMillis()), "Album1", "photo1.jpg");

            // Print all rows from tables to verify data
            System.out.println("\nPhotosTable Data:");
            PhotoDatabase.selectAllPhotosTable(conn);
            System.out.println("\nTagsTable Data:");
            PhotoDatabase.selectAllTagsTable(conn);
            System.out.println("\nUsersTable Data:");
            PhotoDatabase.selectAllUsersTable(conn);
            System.out.println("\nAlbumsTable Data:");
            PhotoDatabase.selectAllAlbumsTable(conn);
            System.out.println("\nPermissionsTable Data:");
            PhotoDatabase.selectAllPermissionsTable(conn);
        }

        // Start the GUI for the Photo App
        javax.swing.SwingUtilities.invokeLater(() -> new PhotoApp());
    }
}
