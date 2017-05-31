
use Romania;

drop function dbo.isValidEmail
go
CREATE FUNCTION dbo.isValidEmail(@EMAIL varchar(100))		--verifica daca emailul e valid
RETURNS bit as
BEGIN     
  DECLARE @bitRetVal as Bit
  IF (@EMAIL <> '' AND @EMAIL NOT LIKE '_%@__%.__%')
     SET @bitRetVal = 0  -- Invalid
  ELSE 
    SET @bitRetVal = 1   -- Valid
  RETURN @bitRetVal
END 
GO

drop function isValidNume
go
CREATE FUNCTION isValidNume(@name VARCHAR(100))				--verifica daca numele contine altceva decat litere
RETURNS BIT
AS
BEGIN
	DECLARE @bitSgn AS BIT;
	IF ( LEN(@name) < 2 OR @name LIKE '%[^a-zA-Z ]%')
		SET @bitSgn = 0;
	ELSE 
		SET @bitSgn = 1;
	RETURN @bitSgn;
END
GO

drop function normalizeazaNume
go
CREATE FUNCTION normalizeazaNume(@nume VARCHAR(100))		--face uppercase la primul caracter  
RETURNS VARCHAR(100) AS
BEGIN
	SET @nume=UPPER(LEFT(@nume,1))+LOWER(SUBSTRING(@nume,2,LEN(@nume)))
	RETURN @nume
END
GO

drop function isValidVarsta;
go

CREATE FUNCTION isValidVarsta(@varsta INT)					--verifica daca varsta e valida: > 18  
RETURNS BIT
AS
BEGIN
	RETURN CASE WHEN @varsta>=18 THEN 1 ELSE 0 
			END
END
GO


drop function isValidCapital;
go
CREATE FUNCTION isValidCapital(@capital INT)	
RETURNS BIT
AS
BEGIN
	RETURN CASE WHEN @capital>0 THEN 1 ELSE 0 
			END
END
GO


--Creaţi o procedură stocată ce inserează date pentru entităţi ce se află într-o relaţie m-n. Dacă o operaţie de inserare eşuează, trebuie facut roll-back pe întreaga procedură stocată. 
DROP PROC adaugaClientBanca
go
CREATE PROC adaugaClientBanca (@numeClient VARCHAR(100), @cuantumClient NUMERIC, @varstaC INT, @mailC VARCHAR(50), @numeB VARCHAR(50), @capitalB INT)
AS
BEGIN
	BEGIN TRAN
		BEGIN TRY
			IF (dbo.isValidNume(@numeClient) <> 1) 
			BEGIN
				RAISERROR('Invalid client name!',14,1)
			END
			IF (ISNUMERIC(@cuantumClient) <> 1)
			BEGIN
				RAISERROR('Invalid client cuantum (it should be positive)!',14,1)
			END
			IF (dbo.isValidVarsta(@varstaC) <> 1)
			BEGIN
				RAISERROR('Invalid client age (he should be older than 18 to have a bank account)!',14,1)
			END
			IF (dbo.isValidEmail(@mailC) <> 1 )
			BEGIN
				RAISERROR('Invalid client email!',14,1)
			END

			SET @numeClient = dbo.normalizeazaNume(@numeClient);
			INSERT INTO Client VALUES((SELECT ISNULL(MAX(codC) + 1, 1) FROM Client), @numeClient,@cuantumClient,@varstaC,@mailC);


			IF (dbo.isValidCapital(@capitalB) <> 1)
			BEGIN
				RAISERROR('Invalid Bank capital!',14,1)
			END
			IF (dbo.isValidNume(@numeB) <> 1)
			BEGIN
				RAISERROR('Invalid bank name!',14,1)
			END
			INSERT INTO Banca VALUES ((SELECT ISNULL(MAX(codB) + 1, 1) FROM Banca), @numeB, @capitalB)
			INSERT INTO Banca_client VALUES ((SELECT codC FROM Client WHERE nume = @numeClient),(SELECT codB FROM Banca WHERE nume = @numeB))

			COMMIT TRAN
			SELECT 'Transaction committed'
		END TRY
		BEGIN CATCH
			ROLLBACK TRAN
			SELECT 'Transaction rollbacked'
			SELECT ERROR_MESSAGE()
		END CATCH
END
GO

SELECT * FROM Banca B INNER JOIN Banca_client BC ON  BC.codb = B.codb INNER JOIN Client C ON C.codc = BC.codc
ORDER BY B.codb DESC


SELECT * FROM Client ORDER BY codC DESC
SELECT * FROM Banca ORDER BY codB DESC
exec adaugaClientBanca 'Sebastian', 33322,21,'sebi@cs.com', 'Sebi Bank', 3400099			--commit
exec adaugaClientBanca 'Sebastian33', 33322,21,'sebi@cs.com', 'Sebi Bank', 3400099			--rollback
SELECT * FROM Client ORDER BY codC DESC
SELECT * FROM Banca ORDER BY codB DESC



-- Creaţi o procedură stocată ce inserează date pentru entităţi ce se află într-o relaţie m-n. Dacă o operaţie de inserare eşuează va trebui să se păstreze cât mai mult posibil din ceea
-- ce s-a modificat până în acel moment. De exemplu, daca se incearcă inserarea unei cărţi şi a autorilor acesteia, iar autorii au fost inseraţi cu succes însă apare o problemă la
-- inserarea cărţii, atunci să se facă roll-back la inserarea de carte însă autorii acesteia să rămână în baza de date.
DROP PROC adaugaClientBanca2
go
CREATE PROC adaugaClientBanca2 (@numeClient VARCHAR(100), @cuantumClient NUMERIC, @varstaC INT, @mailC VARCHAR(50), @numeB VARCHAR(50), @capitalB INT)
AS
BEGIN
	BEGIN TRAN
		BEGIN TRY
			IF (dbo.isValidNume(@numeClient) <> 1) 
			BEGIN
				RAISERROR('Invalid client name!',14,1)
			END
			IF (ISNUMERIC(@cuantumClient) <> 1)
			BEGIN
				RAISERROR('Invalid client cuantum (it should be positive)!',14,1)
			END
			IF (dbo.isValidVarsta(@varstaC) <> 1)
			BEGIN
				RAISERROR('Invalid client age (he should be older than 18 to have a bank account)!',14,1)
			END
			IF (dbo.isValidEmail(@mailC) <> 1 )
			BEGIN
				RAISERROR('Invalid client email!',14,1)
			END

			SET @numeClient = dbo.normalizeazaNume(@numeClient);
			INSERT INTO Client VALUES((SELECT ISNULL(MAX(codC) + 1, 1) FROM Client), @numeClient,@cuantumClient,@varstaC,@mailC)
			
			COMMIT TRAN
			SELECT 'Client Transaction committed'
		END TRY
		BEGIN CATCH
			ROLLBACK TRAN
			SELECT 'Client Transaction rollbacked'
			SELECT ERROR_MESSAGE()
		END CATCH--Client

	BEGIN TRAN
		BEGIN TRY
			IF (dbo.isValidCapital(@capitalB) <> 1)
			BEGIN
				RAISERROR('Invalid Bank capital!',14,1)
			END
			IF (dbo.isValidNume(@numeB) <> 1)
			BEGIN
				RAISERROR('Invalid bank name!',14,1)
			END
			INSERT INTO Banca VALUES ((SELECT ISNULL(MAX(codB) + 1, 1) FROM Banca), @numeB, @capitalB)

			COMMIT TRAN
			SELECT 'Bank Transaction committed'
		END TRY
		BEGIN CATCH
			ROLLBACK TRAN
			SELECT 'Bank Transaction rollbacked'
			SELECT ERROR_MESSAGE()
		END CATCH--Bank

	BEGIN TRAN
		BEGIN TRY
			DECLARE @idc INT, @idB INT;
			SELECT @idC = codC FROM Client WHERE nume = @numeClient;
			SELECT @idB = codB FROM Banca WHERE nume = @numeB;

			IF NOT(@idc > 0 AND @idB > 0)
			BEGIN
				RAISERROR('Id invalid!',14,1)
			END

			INSERT INTO Banca_client VALUES (@idC, @idB)

			COMMIT TRAN
			SELECT 'Relation Transaction committed'
		END TRY
		BEGIN CATCH
			ROLLBACK TRAN
			SELECT 'Relation Transaction rollbacked'
			SELECT ERROR_MESSAGE()
		END CATCH--Relation: Banca_client
END
GO



SELECT * FROM Banca B INNER JOIN Banca_client BC ON  BC.codb = B.codb INNER JOIN Client C ON C.codc = BC.codc
ORDER BY B.codb DESC

SELECT * FROM Client ORDER BY codC DESC
SELECT * FROM Banca ORDER BY codB DESC
exec adaugaClientBanca2 'Miriam', 33663,20,'adsss@sdf.com', 'Garanti Bank', 340030				--commit
exec adaugaClientBanca2 'Miriam', 33663,20,'adsss@sdf.com', 'Garanti Bank@#%$^&*', 340030		--rollback
SELECT * FROM Client ORDER BY codC DESC
SELECT * FROM Banca ORDER BY codB DESC



--Creaţi 4 scenarii ce reproduc următoarele situaţii generate de execuţia concurentă: dirty reads, non-repeatable reads, phantom reads şi un deadlock. Puteţi implementa aceste
--scenarii atât ca proceduri stocate cât şi ca interogări de sine stătătoare. De asemenea, pentru fiecare dintre scenariile create, găsiţi soluţii de rezolvare/evitare a acestor situaţii.



