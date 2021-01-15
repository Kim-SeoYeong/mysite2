-- 테이블 삭제
drop table board;

-- 시퀀스 삭제
drop sequence seq_board_no;

--테이블 생성
create table board (
    no number,        --pk
    title varchar2(500) not null,
    content varchar2(4000),
    hit number default 0,
    reg_date date not null,
    user_no number not null,
    primary key(no),
    constraint board_fk foreign key (user_no)
    references users(no)
);

--시퀀스 생성
create sequence seq_board_no
increment by 1
start with 1;

--테이블 조회
select  no,
        title,
        content,
        hit,
        reg_date,
        user_no
from board;

-- insert문
insert into board
values(seq_board_no.nextval, '반가워요', '테스트내용', default, sysdate, 1);

insert into board
values(seq_board_no.nextval, '잘보이나', '테스트다 테스트', default, sysdate, 2);

rollback;
commit;

-- 전체 게시판 목록 조회 select
select  board.no,
        board.title,
        users.name,
        board.content,
        board.hit,
        board.reg_date,
        board.user_no
from users, board
where users.no = board.user_no
order by reg_date desc;

--특정 게시판 글 조회 select
select  board.no,
        users.name,
        board.hit,
        board.reg_date,
        board.title,
        board.content,
        board.user_no
from users, board
where users.no = board.user_no
and board.no = 1;

--like문 사용해서 검색 select
select  board.no,
        board.title,
        users.name,
        board.content,
        board.hit,
        board.reg_date,
        board.user_no
from users, board
where users.no = board.user_no
and board.title like '%안%'
order by reg_date desc;

-- 조회수 update
update board
set hit = 1
where no = 1;

-- 게시글 update
update board
set title = '김서영테스트중',
content = '내용테스트!!'
where no = 1;

-- 게시글 삭제
delete from board
where no = 1;









