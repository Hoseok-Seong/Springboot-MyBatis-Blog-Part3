insert into user_tb(username, password, email, created_at) values('ssar', '1234', 'ssar@nate.com', now());
insert into user_tb(username, password, email, created_at) values('cos', '1234', 'cos@nate.com', now());
insert into user_tb(username, password, email, created_at) values('love', '1234', 'love@nate.com', now());

insert into board_tb(user_id, title, content, thumbnail, created_at) values('1', '제목1', '글내용1', '/images/dora.png', now());
insert into board_tb(user_id, title, content, thumbnail, created_at) values('2', '제목2', '글내용2', '/images/dora.png', now());
insert into board_tb(user_id, title, content, thumbnail, created_at) values('3', '제목3', '글내용3', '/images/dora.png', now());
insert into board_tb(user_id, title, content, thumbnail, created_at) values('1', '제목4', '글내용4', '/images/dora.png', now());
insert into board_tb(user_id, title, content, thumbnail, created_at) values('2', '제목5', '글내용5', '/images/dora.png', now());
insert into board_tb(user_id, title, content, thumbnail, created_at) values('3', '제목6', '글내용6', '/images/dora.png', now());
insert into board_tb(user_id, title, content, thumbnail, created_at) values('1', '제목7', '글내용7', '/images/dora.png', now());
insert into board_tb(user_id, title, content, thumbnail, created_at) values('2', '제목8', '글내용8', '/images/dora.png', now());
insert into board_tb(user_id, title, content, thumbnail, created_at) values('3', '제목9', '글내용9', '/images/dora.png', now());
insert into board_tb(user_id, title, content, thumbnail, created_at) values('1', '제목10', '글내용10', '/images/dora.png', now());
insert into board_tb(user_id, title, content, thumbnail, created_at) values('2', '제목11', '글내용11', '/images/dora.png', now());
insert into board_tb(user_id, title, content, thumbnail, created_at) values('3', '제목12', '글내용12', '/images/dora.png', now());

insert into reply_tb(comment, user_id, board_id, created_at) values('댓글1', '1', '1', now());
insert into reply_tb(comment, user_id, board_id, created_at) values('댓글2', '2', '2', now());
insert into reply_tb(comment, user_id, board_id, created_at) values('댓글3', '3', '3', now());
insert into reply_tb(comment, user_id, board_id, created_at) values('댓글4', '1', '4', now());
insert into reply_tb(comment, user_id, board_id, created_at) values('댓글5', '2', '5', now());
insert into reply_tb(comment, user_id, board_id, created_at) values('댓글6', '3', '6', now());

commit;