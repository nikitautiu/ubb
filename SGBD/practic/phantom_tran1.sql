USE practic_sgbd;
GO

CREATE PROCEDURE phantom1
AS
BEGIN TRAN
	SELECT * FROM ingredients_products
	WHERE quantity BETWEEN 300 AND 500
	WAITFOR DELAY '00:00:05'
	SELECT * FROM ingredients_products
	WHERE quantity BETWEEN 300 AND 500
COMMIT TRAN
GO


CREATE PROCEDURE phantom2
AS
BEGIN TRAN											
	INSERT INTO ingredients_products(productId, ingredientId, quantity) VALUES(2, 7, 444)
COMMIT TRAN
GO

SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;					--phantom reads
SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;

EXEC phantom1;
GO;

DELETE FROM ingredients_products WHERE productId=2 AND ingredientId=7 AND quantity=444;