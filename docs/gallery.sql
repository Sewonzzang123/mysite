desc gallery;

insert into gallery
values(null, '이미지1','/assets/gallery-examples/im1.jpg');
insert into gallery
values(null, '이미지2','/assets/gallery-examples/im2.jpg');
insert into gallery
values(null, '이미지3','/assets/gallery-examples/im3.jpg');
insert into gallery
values(null, '이미지4','/assets/gallery-examples/im4.jpg');
insert into gallery
values(null, '이미지5','/assets/gallery-examples/im5.jpg');
insert into gallery
values(null, '이미지6','/assets/gallery-examples/im6.jpg');
insert into gallery
values(null, '이미지7','/assets/gallery-examples/im7.jpg');
insert into gallery
values(null, '이미지8','/assets/gallery-examples/im8.jpg');
insert into gallery
values(null, '이미지9','/assets/gallery-examples/im9.jpg');
insert into gallery
values(null, '이미지10','/assets/gallery-examples/im10.jpg');
insert into gallery
values(null, '이미지11','/assets/gallery-examples/im11.jpg');
insert into gallery
values(null, '이미지12','/assets/gallery-examples/im12.jpg');
insert into gallery
values(null, '이미지13','/assets/gallery-examples/im13.jpg');
insert into gallery
values(null, '이미지14','/assets/gallery-examples/im14.jpg');
insert into gallery
values(null, '이미지15','/assets/gallery-examples/im15.jpg');

delete from gallery where no=15;

select no, comment, url 
from gallery
limit 0,5;