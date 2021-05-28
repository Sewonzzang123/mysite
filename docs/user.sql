-- --scheme
desc user;

-- join(insert)
insert 
into user
values(null, '관리자', 'admin@mysite.com', '1234', 'female');

-- user list
select no, name, email, password, gender
from user;

-- login(select)
select no, name
from user
where email ='jangsewon@email.com' 
and password = 1234;

-- update
update user
set name=? , password=? , gender=?
where email=?;

update user
set name='마루123',gender='female'
where email='maru@gmail.com';
