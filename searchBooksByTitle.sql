CREATE DEFINER=`admin`@`localhost` PROCEDURE `searchBooksByTitle`(
	in titlePart varchar(255))
BEGIN
 select * from books where title LIKE concat('%', titlePart, '%') 
 limit 10
 offset 0;
END