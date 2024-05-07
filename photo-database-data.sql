INSERT INTO Users (UserID, JoinedSince, Password)
VALUES ("Bobby98", "2012-12-18 09:46:00", "Cats"),
	("Carolz", "2013-01-12 09:32:00", "Password123"),
    ("JimmyJim24", "2013-02-01 10:56:00", "BobbyBob");

INSERT INTO Photos (Filename, Filetype, DateTaken, UploaderUserID, AlbumName)
VALUES ("bob_and_jim_fishing", "jpg", "2018-06-28 11:23:00", "Bobby98", NULL),
 	 ("bob_cat", ".png", "2020-10-07 20:18:00", "Bobby98", NULL),
     ("bob_and_carol", "jpg", "2019-10-07 10:04:00", "Bobby98", NULL),
     ("carol_cooking", "jpeg", "2020-03-23 21:31:00", "Carolz", NULL);
    
INSERT INTO Albums (AlbumName, CreatorUserID, CreationDate)
VALUES ("Jim and Bob", "Bobby98", "2019-10-08 20:43:00");    
    
INSERT INTO Permissions (AccessUserID, OwnerUserID, PermissionAdded, AlbumName, Filename)
VALUES ("Bobby98", "JimmyJim24", "2014-09-14 14:07:00", "Bobby98", NULL);

INSERT INTO Tags (FileName, Tag, TimeTagAdded)
VALUES ("bob_and_jim_fishing", "Bob", "2019-11-06 20:43:00"),
	("bob_and_jim_fishing", "Jim", "2019-11-06 09:12:00"),
    ("carol_cooking", "Carol", "2021-10-23 21:35:00"),
    ("bob_and_carol", "Bob", "2021-10-23 21:46:00"),
    ("bob_and_carol", "Carol", "2021-10-23 22:01:00"),
    ("bob_cat", "Cat", "2020-10-07 20:22:00");