-- scheme
desc guestbook;

-- select
select no, name, password, message, reg_date
from guestbook;

insert into guestbook values(null,'a','1234','12341421123',sysdate());
select last_insert_id() as no;

select no, name, message, date_format(reg_date, '%Y/%m/%d %H:%i:%s') as regDate
from guestbook
order by reg_date desc 
limit 0, 3;