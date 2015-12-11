/* Suppression des tables au cas où elles existent déjà */
DROP TABLE PLAYERINGAME;
DROP TABLE GAME;
DROP TABLE PLAYER;

/* Création des tables Player, Game et PlayerInGame */
CREATE TABLE Player (
	pName varchar(50) not null primary key
);

CREATE TABLE Game (
        gName varchar(50) not null primary key,
	gCurrentPlayer int not null,
        gOver boolean not null
);

CREATE TABLE PlayerInGame (
	pigPlayerName varchar(50) not null,
	pigGameName varchar(50) not null,
	pigPlayerColor varchar(10) not null, 
        pigOrder int not null,
        pigPosition int not null,
        PRIMARY KEY(pigPlayerName, pigGameName),
	FOREIGN KEY(pigPlayerName) REFERENCES Player(pName),
	FOREIGN KEY(pigGameName) REFERENCES Game(gName)
);

INSERT INTO Player(pName) VALUES('Maxime');
INSERT INTO Player(pName) VALUES('Guillaume');
INSERT INTO Player(pName) VALUES('Joselyne');

INSERT INTO Game VALUES('Trio de fou', 1, False);

INSERT INTO PlayerInGame VALUES('Maxime', 'Trio de fou', '0x000000ff', 0, 3);
INSERT INTO PlayerInGame VALUES('Guillaume', 'Trio de fou', '0xffffffff', 1, 5);
INSERT INTO PlayerInGame VALUES('Joselyne', 'Trio de fou', '0x0000ffff', 1, 9);