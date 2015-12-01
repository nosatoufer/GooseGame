CREATE TABLE Player(
	pPlayer int primary key,
	pName varchar(100) not null
);

CREATE TABLE Game(
	gId int primary key,
	gStartDate Date not null,
	gEndDate Date not null,
	gWinner String 
);

CREATE TABLE PlayerGame(
	pgPlayer int,
	pgGame int,
	pgColor String, 
	FOREIGN KEY(pgPlayer) REFERENCES Player(pId),
	FOREIGN KEY(pgGame) REFERENCES Game(gId)
);