DROP TABLE member;
CREATE TABLE member (
        id BIGINT(20) Auto_Increment,
        email char(50),
        pw char(50),
        name char(50),
        phone char(13),
        address char(50),
        PRIMARY KEY(id)
);

INSERT INTO member (email, pw, name, phone, address) values ('root@induk.ac.kr', 'cometrue','관리자', '9507620','korea');
INSERT INTO member (email, pw, name, phone, address) values ('kdg@induk.ac.kr', 'cometrue','관리자', '9507620','korea');

select * from member;

select pw from member where email = 'kdg@induk.ac.kr';

update member set name='comso', phone='7777', address='nowon, seoul' where email='comso1@induk.ac.kr' and pw='cometrue';

delete from member where id = 2;

drop table member;