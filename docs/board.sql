desc board;
select * from board;
select * from user;

-- 게시글 리스트 보기
select a.no, a.title, a.depths, a.hit, b.no , b.name, a.reg_date
from board a, user b
where a.user_no = b.no
order by a.group_no DESC, a.order_no ASC
limit 0,5;

select count(*)
from board;


-- 글쓰기
select max(group_no)
from board;

insert into board 
values(null,'title6','contents',sysdate(),0,41,0,0,2,0);

-- 게시글 보기(update 후 select)
update board
set hit=hit+1
where no=1;

select a.no, a.title, a.contents, a.reg_date, a.hit, a.group_no, a.order_no, a.depths, b.name
from board a, user b
where a.user_no = b.no
and a.no=68;

-- 답글 쓰기 (update 후 insert)
update board
set order_no=order_no+1
where group_no=? and order_no>=1;

insert into board 
values(null,'title','contents',sysdate(),0,1,order_no,depths+1,2);

-- 게시글 수정
update board
set title='수정1', contents='수정내용123', reg_date=sysdate()
where no=4;

-- 게시글 삭제
delete from board
where no=7;

-- 검색
select distinct a.no, a.title, a.depths, a.hit, a.user_no,b.name, a.reg_date, a.contents
from board a
join user b on a.user_no = b.no 
-- where a.title like concat('%','마루','%') or a.contents like concat('%','마루','%') -- TC
where a.title like concat('%','마루','%') -- T
-- where a.contents like concat('%','마루','%')
order by a.group_no DESC, a.order_no ASC
limit 0,5;
            
select count(*)
from (select distinct a.no, a.title, a.depths, a.hit, a.user_no,b.name, a.reg_date
		from board a
		join user b on a.user_no = b.no 
		where a.title like '%ㄴㅇㄹ%' or a.contents like '%ㄴㅇㄹ%')c;
        
-- 부모 삭제시 자식 parent_no 를 -1으로 변경
update board
set parent_no=-1
where parent_no=48;