#-------------------------------------
drop procedure myspInOut;
CREATE PROCEDURE myspInOut (
IN s_key VARCHAR(100),
OUT s_value VARCHAR(100),
OUT n_value INTEGER,
OUT d_value DATETIME)
SELECT svalue, nvalue, dvalue
INTO s_value, n_value, d_value
FROM demo_single_key where skey = s_key;

#-------------------------------------
DROP PROCEDURE myspNoOut;
CREATE PROCEDURE myspNoOut (
IN s_key VARCHAR(100))
SELECT svalue, nvalue, dvalue
FROM demo_single_key where skey = s_key;


#-------------------------------------
DROP PROCEDURE myspNoIn;
CREATE PROCEDURE myspNoIn (
OUT s_value VARCHAR(100),
OUT n_value INTEGER,
OUT d_value DATETIME)
SELECT svalue, nvalue, dvalue
INTO s_value, n_value, d_value
FROM demo_single_key GROUP BY nstatus_id ORDER BY nstatus_id DESC LIMIT 1;


#-------------------------------------
DROP PROCEDURE myspNoInOut;
CREATE PROCEDURE myspNoInOut() 
SELECT svalue, nvalue, dvalue
FROM demo_single_key;


