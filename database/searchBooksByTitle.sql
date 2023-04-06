CREATE DEFINER=`admin`@`localhost` PROCEDURE `searchBooksByTitle`(
	in titlePart varchar(255))
BEGIN
    DECLARE EXIT HANDLER FOR sqlexception
    BEGIN
 	SELECT CONCAT(' Some error appeared!!') AS message;
    END;
 select * from books where title LIKE concat('%', titlePart, '%') 
 limit 10
 offset 0;
END