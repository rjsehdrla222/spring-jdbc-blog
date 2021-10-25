drop table blog;
create table blog (
    id BIGINT(20) Auto_Increment,
	title char(50) not null,
	content char(200),
	filepath char(30),
	blogger	char(30),
    created_date CHAR(50),
    PRIMARY KEY(id)
);

INSERT INTO blog (title, content, filepath, blogger, created_date) values ('제목-1','블로그 내용-1','01.png','root@induk.ac.kr', '09.16(2021)');

select * from blog;

update blog set title='update제목', content='update 내용', filepath='ddochi.png' where id=48;

update blog set filepath='섬.png' where id=3;

update blog set title='update제목', content='update 내용', filepath='ddochi.png',