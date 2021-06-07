-- scheme
desc guestbook;

-- select
select no, name, password, message, reg_date
from guestbook;

insert into guestbook values(null,'a','1234','12341421123',sysdate());
select last_insert_id() as no;