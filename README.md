# 431-database
Photo Database


--Personal Photos Database
  This is a program for manipulating, managing, and sharing a database of photos.
  Description
  This program features two main components: a database and a UI for navigating and manipulating the database.
--Database
  The database is made up of five tables. Users, Photos, Albums, Tags and Permissions. The Users table tracks unique primary key UserID, and DateJoined. The Photos table tracks unique primary key filename, datetaken for date   photo was taken, filetype, uploaderUserID, and AlbumName if the photo belongs to an album. The Albums table tracks albums via unique primary key AlbumName, CreatorUserID for creator of the album, and DataCreated. The         Permissions table is a log of all the permissions users give to other users for access to photos or albums. It tracks the AccessUserID, OwnerUserID, PermissionAdded date, and then AlbumName or FileName for whatever the       user   was given access to.
--Database Diagram

--UI
  Buttons: log in allows you to log in based on previous entry in the database create account allows you to create a new account and give it a password. The user account is connected to user entries in the database. To         upload a picture, you enter the characteristics for said picture. 
Getting Started
Dependencies
There’s no need to install any software or modify any program files to run this program.
Executing program
Buttons 
Log in: allows you to log in based on previous entry in the database
Create account: allows you to create a new account and give it a password. The user account is connected to user entries in the database. 

Uploading Photos
To upload a picture, you enter the characteristics of said picture. 

When the UI is launched, it will connect to the database and begin running. 
Authors
Precious Akinnodi
Hanaa Salim 
Alexis Osueke
Nicole Balay

