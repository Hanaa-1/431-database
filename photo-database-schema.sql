-- Ensure the database exists
CREATE DATABASE IF NOT EXISTS photo_app;
USE photo_app;

-- Temporarily disable checks to avoid issues during table creation
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- Create Users table
CREATE TABLE IF NOT EXISTS Users (
  UserID VARCHAR(20) NOT NULL,
  Password VARCHAR(30) NOT NULL DEFAULT 'changeme',
  JoinedSince DATETIME NOT NULL,
  PRIMARY KEY (UserID)
) ENGINE = InnoDB;

-- Create Albums table
CREATE TABLE IF NOT EXISTS Albums (
  AlbumID INT AUTO_INCREMENT PRIMARY KEY,
  AlbumName VARCHAR(50) NOT NULL,
  CreatorUserID VARCHAR(20) NOT NULL,
  CreationDate DATETIME NOT NULL,
  FOREIGN KEY (CreatorUserID) REFERENCES Users(UserID) ON UPDATE CASCADE
) ENGINE = InnoDB;

-- Create Photos table with FilePath column
CREATE TABLE IF NOT EXISTS Photos (
  PhotoID INT AUTO_INCREMENT PRIMARY KEY,
  Filename VARCHAR(50) NOT NULL,
  AlbumID INT,
  DateTaken DATETIME NOT NULL,
  Filetype VARCHAR(4) NOT NULL,
  UploaderUserID VARCHAR(20) NOT NULL,
  FilePath VARCHAR(255) NOT NULL,
  FOREIGN KEY (AlbumID) REFERENCES Albums(AlbumID) ON UPDATE CASCADE,
  FOREIGN KEY (UploaderUserID) REFERENCES Users(UserID) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB;

-- Create Permissions table
CREATE TABLE IF NOT EXISTS Permissions (
  PermissionID INT AUTO_INCREMENT PRIMARY KEY,
  AccessUserID VARCHAR(20) NOT NULL,
  OwnerUserID VARCHAR(20) NOT NULL,
  PermissionAdded DATETIME NOT NULL,
  AlbumID INT,
  PhotoID INT,
  FOREIGN KEY (AccessUserID) REFERENCES Users(UserID) ON DELETE RESTRICT ON UPDATE CASCADE,
  FOREIGN KEY (OwnerUserID) REFERENCES Users(UserID) ON DELETE RESTRICT ON UPDATE CASCADE,
  FOREIGN KEY (PhotoID) REFERENCES Photos(PhotoID) ON DELETE RESTRICT ON UPDATE CASCADE,
  FOREIGN KEY (AlbumID) REFERENCES Albums(AlbumID) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB;

-- Create Tags table
CREATE TABLE IF NOT EXISTS Tags (
  TagID INT AUTO_INCREMENT PRIMARY KEY,
  PhotoID INT NOT NULL,
  Tag VARCHAR(20) NOT NULL,
  TimeTagAdded DATETIME NOT NULL,
  FOREIGN KEY (PhotoID) REFERENCES Photos(PhotoID) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB;

-- Insert sample users
INSERT INTO Users (UserID, Password, JoinedSince) VALUES
('Bobby98', 'bob', '2012-12-18 09:46:00'),
('Carolz', 'carol', '2013-01-12 09:32:00'),
('JimmyJim24', 'jim', '2013-02-01 10:56:00');

-- Insert sample albums
INSERT INTO Albums (AlbumName, CreatorUserID, CreationDate) VALUES
('Jim and Bob', 'Bobby98', '2019-10-08 20:43:00'),
('Carol\'s Adventures', 'Carolz', '2020-05-12 14:12:00'),
('Family Vacation', 'JimmyJim24', '2021-06-18 10:20:00');

-- Insert sample photos with paths
INSERT INTO Photos (Filename, Filetype, DateTaken, UploaderUserID, AlbumID, FilePath) VALUES
('bob_and_jim_fishing.jpg', 'jpg', '2018-06-28 11:23:00', 'Bobby98', (SELECT AlbumID FROM Albums WHERE AlbumName='Jim and Bob'), '/images/Bobby98/Jim and Bob/bob_and_jim_fishing.jpg'),
('bob_cat.png', 'png', '2020-10-07 20:18:00', 'Bobby98', NULL, '/images/Bobby98/bob_cat.png'),
('bob_and_carol.jpg', 'jpg', '2019-10-07 10:04:00', 'Bobby98', NULL, '/images/Bobby98/bob_and_carol.jpg'),
('carol_cooking.jpeg', 'jpeg', '2020-03-23 21:31:00', 'Carolz', NULL, '/images/Carolz/carol_cooking.jpeg'),
('carol_hiking.jpg', 'jpg', '2020-07-18 09:15:00', 'Carolz', (SELECT AlbumID FROM Albums WHERE AlbumName='Carol\'s Adventures'), '/images/Carolz/Carol\'s Adventures/carol_hiking.jpg'),
('jim_family_vacation.jpg', 'jpg', '2021-07-19 12:00:00', 'JimmyJim24', (SELECT AlbumID FROM Albums WHERE AlbumName='Family Vacation'), '/images/JimmyJim24/Family Vacation/jim_family_vacation.jpg');

-- Insert permissions
INSERT INTO Permissions (AccessUserID, OwnerUserID, PermissionAdded, AlbumID, PhotoID) VALUES
('Bobby98', 'JimmyJim24', '2014-09-14 14:07:00', NULL, NULL),
('Carolz', 'Bobby98', '2020-01-10 12:00:00', (SELECT AlbumID FROM Albums WHERE AlbumName='Jim and Bob'), NULL);

-- Insert tags
INSERT INTO Tags (PhotoID, Tag, TimeTagAdded) VALUES
((SELECT PhotoID FROM Photos WHERE Filename='bob_and_jim_fishing.jpg'), 'Bob', '2019-11-06 20:43:00'),
((SELECT PhotoID FROM Photos WHERE Filename='bob_and_jim_fishing.jpg'), 'Jim', '2019-11-06 09:12:00'),
((SELECT PhotoID FROM Photos WHERE Filename='carol_cooking.jpeg'), 'Carol', '2021-10-23 21:35:00'),
((SELECT PhotoID FROM Photos WHERE Filename='bob_and_carol.jpg'), 'Bob', '2021-10-23 21:46:00'),
((SELECT PhotoID FROM Photos WHERE Filename='bob_and_carol.jpg'), 'Carol', '2021-10-23 22:01:00'),
((SELECT PhotoID FROM Photos WHERE Filename='bob_cat.png'), 'Cat', '2020-10-07 20:22:00'),
((SELECT PhotoID FROM Photos WHERE Filename='carol_hiking.jpg'), 'Hiking', '2020-07-19 10:00:00'),
((SELECT PhotoID FROM Photos WHERE Filename='jim_family_vacation.jpg'), 'Family', '2021-07-20 15:30:00'),
((SELECT PhotoID FROM Photos WHERE Filename='jim_family_vacation.jpg'), 'Vacation', '2021-07-20 16:00:00');

-- Restore previous server settings
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- Update bob_cat.png and bob_and_carol.jpg to be in the "Jim and Bob" album
UPDATE Photos 
SET AlbumID = (SELECT AlbumID FROM Albums WHERE AlbumName = 'Jim and Bob')
WHERE Filename IN ('bob_cat.png', 'bob_and_carol.jpg');

-- Update carol_cooking.jpeg to be in the "Carol's Adventures" album
UPDATE Photos 
SET AlbumID = (SELECT AlbumID FROM Albums WHERE AlbumName = 'Carol\'s Adventures')
WHERE Filename = 'carol_cooking.jpeg';
