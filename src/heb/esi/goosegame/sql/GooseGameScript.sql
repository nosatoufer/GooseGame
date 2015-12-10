/*Suppression des tables au cas où ils existent déjà quand on les crée*/
DROP TABLE PLAYERGAME;
DROP TABLE PLAYER;
DROP TABLE GAME;

/*Crééation des tables PLAYER, GAME et PLAYERGAME*/
CREATE TABLE PLAYER(
	pPlayer int primary key,
	pName varchar(50) not null
);

CREATE TABLE GAME(
	gId int primary key,
	gStartDate Date not null,
	gEndDate Date not null,
	gWinner String,
        boolean gOver
);

CREATE TABLE PLAYERGAME(
	pgPlayer int not null,
	pgGame int not null,
	pgColor String, 
        pgPosition int,
        PRIMARY KEY (pgPlayer, pgGame),
	FOREIGN KEY(pgPlayer) REFERENCES Player(pId),
	FOREIGN KEY(pgGame) REFERENCES Game(gId)
);