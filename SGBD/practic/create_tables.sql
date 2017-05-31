USE practic_sgbd;

DROP TABLE ingredients_products;
DROP TABLE purchases;
DROP TABLE ingredients;
DROP TABLE products;
DROP TABLE product_types;
DROP TABLE clients;


CREATE TABLE ingredients(
	id INT PRIMARY KEY,
	name VARCHAR(100)
);

CREATE TABLE product_types(
	typeId INT PRIMARY KEY,
	name VARCHAR(100),
	description VARCHAR(1000)
);

CREATE TABLE products(
	id INT PRIMARY KEY,
	typeId INT REFERENCES product_types(typeId),
	name VARCHAR(100),
	weight INT,
	price INT
);

CREATE TABLE ingredients_products(
	productId INT REFERENCES products(id),
	ingredientId INT REFERENCES ingredients(id),
	quantity INT,
	CONSTRAINT PK_ingredients_prodcts PRIMARY KEY (productId, ingredientId)
);

CREATE TABLE clients(
	id INT PRIMARY KEY,
	name VARCHAR(50),
);

CREATE TABLE purchases(
	productId INT REFERENCES products(id),
	clientId INT REFERENCES clients(id),
);

INSERT INTO product_types(typeId, name, description) VALUES 
	(1, 'paine', 'Produs simplu, dospit, din faina de grau'),
	(2, 'corn', 'Corn din aluat dospit'),
	(3, 'croissant', 'Corn din foietaj'),
	(4, 'pateu', 'Foietaj umplut');

INSERT INTO ingredients(id, name) VALUES
(1, 'faina de grau'), (2, 'drojdie'), (3, 'apa'), (4, 'sare'), (5, 'oua'), (6, 'zahar'), (7, 'unt');

INSERT INTO products(id, typeId, name, weight, price) VALUES
	(1, 1, 'paine alba', 500, 3),
	(2, 1, 'paine neagra', 500, 4);

INSERT INTO ingredients_products(productId, ingredientId, quantity) VALUES
	(1, 1, 400),
	(1, 2, 20),
	(1, 3, 300),
	(1, 4, 5),
	(1, 7, 10),
	(2, 1, 450),
	(2, 2, 10),
	(2, 3, 325),
	(2, 4, 5);

INSERT INTO clients(id, name) VALUES
(1, 'radu'),
(2, 'mihai'),
(3, 'gigi');

INSERT INTO purchases(clientId, productId) VALUES
	(1, 1),
	(1, 2),
	(1, 2),
	(2, 2),
	(3, 2);