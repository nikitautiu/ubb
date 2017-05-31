USE lab3;

DROP TABLE People;
DROP TABLE Cars;

CREATE TABLE People(
	code INTEGER PRIMARY KEY IDENTITY(1, 1),
	name VARCHAR(100),
	age INTEGER
	);

CREATE TABLE Cars(
	code INTEGER PRIMARY KEY IDENTITY(1, 1),
	name VARCHAR(100),
);

INSERT INTO People(name, age) VALUES
	('Geo Gheroghe', 40),
	('Ion Ion', 55);

INSERT INTO Cars(name) VALUES('Volkswagen');

GO