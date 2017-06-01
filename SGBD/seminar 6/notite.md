# Tuning-ul performanantei

- planul de executie poate disparea din cauza presiunii pe cache
- procedure cache(istoric) `FREEPROCCACHE` -- de fapt se cheama planning cache
- SQL_HANDLE - tine planul de executie al acelui rand(nu e readable implciit)
	* sys.dm_exec_sql_text(sql_handle)  -- returneaza textul interogarii(func scalara). poate face parte din select

- operator CROSS APPLY. `SELECT ... CROSS APPLY sys.dm_exec_sql_text(sql_handle)`


## Exemplu Eurovision
Medlodie--(1-\*)--Voturi--(\*-1)--Telefoane

Voturi:
- 1200 pagini - nd.idx 
```
CREATE PROC FilterVoters(@CodM INT)
AS
SELECT * FROM Voturi
WHERE CodM=@CodM
```
- Ctrl+M -- putem include planul de executie
- Non-clsutered index seek + R(ow)ID(Bookmark) lookup
- `EXEC FilterVoters 13` -- se executa aproape imediat, nu presiune pe cache
	* non-clustered index seek + RID lookup
	* RID lookup-ul neeficient in acest caz -- preferam un table scan + filter
```
ALTER AS
S
F
W
OPTION(RECOMPILE)
```

- `OPTION(OPTIMIZE FOR(@COdm=14))` -- economisim costul de recompilare
- costul pentru restul creste pentru cele care ar fi mai optim un seek 
- `DBCC SHOW_STATISTICS('TAbleName', IdX)` -- histograma
 
```
ALTER
AS
	DECLARE @CodM INT
	SET @CodMel = @CodM
	S
	F
	W Codm = @CodMel
```

```
S
F
W
UNION
S
F
W
``

```
S
F
GB CodM
OPTION (HASH GROUP ORDER ...)
...
OPTION (HASH UNION MERGE CONCAT ...)
```

```
S
F V IJ  M ON m.CodM = v.Cam)
OPTION(LOOP JOIN, HASh JOIN, MERGE JOIN, etc)