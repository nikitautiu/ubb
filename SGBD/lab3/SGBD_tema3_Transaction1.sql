--transaction 1

use lab3;
GO


BEGIN TRAN											--dirty reads
	UPDATE People SET age = 160
		WHERE name = 'Geo Gheorghe';
	WAITFOR DELAY '00:00:10';
	ROLLBACK TRANSACTION;							
GO


select * from People


BEGIN TRAN											--non repeatable reads
	WAITFOR DELAY '00:00:05'
	UPDATE People SET age = 130
		WHERE name = 'Ion Ion'
COMMIT TRAN
GO



BEGIN TRAN											--phantom reads
	INSERT INTO People VALUES('Dorel Constantin', 60)
COMMIT TRAN
GO


SET TRANSACTION ISOLATION LEVEL SERIALIZABLE
go

CREATE PROCEDURE deadlock2
AS
BEGIN TRAN											--DEADLOCK
	UPDATE People SET name = name + 'TR 1' WHERE code = 1
	WAITFOR DELAY '00:00:10'
	UPDATE Cars SET name = name + 'TR 1' WHERE code = 3
COMMIT TRAN
GO

EXEC deadlock2;




----------------------------------------------------------------------------

