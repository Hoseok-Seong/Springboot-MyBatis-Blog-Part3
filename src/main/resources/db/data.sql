insert into user_tb(username, password, email, created_at) values('ssar', '1234', 'ssar@nate.com', now());
insert into user_tb(username, password, email, created_at) values('cos', '1234', 'cos@nate.com', now());
insert into user_tb(username, password, email, created_at) values('love', '1234', 'love@nate.com', now());

insert into board_tb(username, title, created_at) values('ssar', '제목1', now());
insert into board_tb(username, title, created_at) values('cos', '제목2', now());
insert into board_tb(username, title, created_at) values('love', '제목3', now());

commit;