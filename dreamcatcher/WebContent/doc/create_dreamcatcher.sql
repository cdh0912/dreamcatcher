

/* ������ �� */

DROP SEQUENCE SITE_SEQ;

DROP SEQUENCE SIMG_SEQ;

DROP SEQUENCE SRE_SEQ;

DROP SEQUENCE ROUTE_SEQ;

DROP SEQUENCE RDET_SEQ;

DROP SEQUENCE PLAN_SEQ;

DROP SEQUENCE RRE_SEQ;

DROP SEQUENCE LOC_SEQ;

DROP SEQUENCE SVOTE_SEQ;

DROP SEQUENCE RVOTE_SEQ;


CREATE SEQUENCE SITE_SEQ
START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE SIMG_SEQ
START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE SRE_SEQ
START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE ROUTE_SEQ
START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE RDET_SEQ
START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE PLAN_SEQ
START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE RRE_SEQ
START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE LOC_SEQ
START WITH 1 INCREMENT BY 1;		

CREATE SEQUENCE SVOTE_SEQ
START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE RVOTE_SEQ
START WITH 1 INCREMENT BY 1;	
ALTER TABLE site
	DROP
		CONSTRAINT FK_location_TO_site
		CASCADE;

ALTER TABLE site_detail
	DROP
		CONSTRAINT FK_site_TO_site_detail
		CASCADE;

ALTER TABLE site_image
	DROP
		CONSTRAINT FK_site_TO_site_image
		CASCADE;

ALTER TABLE site_reply
	DROP
		CONSTRAINT FK_site_TO_site_reply
		CASCADE;

ALTER TABLE route_detail
	DROP
		CONSTRAINT FK_site_TO_route_detail
		CASCADE;

ALTER TABLE route_detail
	DROP
		CONSTRAINT FK_route_TO_route_detail
		CASCADE;

ALTER TABLE plan
	DROP
		CONSTRAINT FK_route_TO_plan
		CASCADE;

ALTER TABLE route_reply
	DROP
		CONSTRAINT FK_route_TO_route_reply
		CASCADE;

ALTER TABLE location
	DROP
		CONSTRAINT FK_nation_TO_location
		CASCADE;

ALTER TABLE site_vote
	DROP
		CONSTRAINT FK_site_TO_site_vote
		CASCADE;

ALTER TABLE route_vote
	DROP
		CONSTRAINT FK_route_TO_route_vote
		CASCADE;

ALTER TABLE member
	DROP
		PRIMARY KEY
		CASCADE
		KEEP INDEX;

ALTER TABLE site
	DROP
		PRIMARY KEY
		CASCADE
		KEEP INDEX;

ALTER TABLE site_detail
	DROP
		PRIMARY KEY
		CASCADE
		KEEP INDEX;

ALTER TABLE site_image
	DROP
		PRIMARY KEY
		CASCADE
		KEEP INDEX;

ALTER TABLE site_reply
	DROP
		PRIMARY KEY
		CASCADE
		KEEP INDEX;

ALTER TABLE route
	DROP
		PRIMARY KEY
		CASCADE
		KEEP INDEX;

ALTER TABLE route_detail
	DROP
		PRIMARY KEY
		CASCADE
		KEEP INDEX;

ALTER TABLE plan
	DROP
		PRIMARY KEY
		CASCADE
		KEEP INDEX;

ALTER TABLE route_reply
	DROP
		PRIMARY KEY
		CASCADE
		KEEP INDEX;

ALTER TABLE location
	DROP
		PRIMARY KEY
		CASCADE
		KEEP INDEX;

ALTER TABLE nation
	DROP
		PRIMARY KEY
		CASCADE
		KEEP INDEX;

ALTER TABLE site_vote
	DROP
		PRIMARY KEY
		CASCADE
		KEEP INDEX;

ALTER TABLE route_vote
	DROP
		PRIMARY KEY
		CASCADE
		KEEP INDEX;

DROP INDEX PK_member;

DROP INDEX PK_site;

DROP INDEX PK_site_detail;

DROP INDEX PK_site_image;

DROP INDEX PK_site_reply;

DROP INDEX PK_route;

DROP INDEX PK_route_detail;

DROP INDEX PK_plan;

DROP INDEX PK_route_reply;

DROP INDEX PK_location;

DROP INDEX PK_nation;

DROP INDEX PK_site_vote;

DROP INDEX PK_route_vote;

/* 회원 */
DROP TABLE member 
	CASCADE CONSTRAINTS;

/* 관광지 */
DROP TABLE site 
	CASCADE CONSTRAINTS;

/* 관광지정보 */
DROP TABLE site_detail 
	CASCADE CONSTRAINTS;

/* 관광지사진 */
DROP TABLE site_image 
	CASCADE CONSTRAINTS;

/* 관광지리플 */
DROP TABLE site_reply 
	CASCADE CONSTRAINTS;

/* 여행경로 */
DROP TABLE route 
	CASCADE CONSTRAINTS;

/* 경로정보 */
DROP TABLE route_detail 
	CASCADE CONSTRAINTS;

/* 여행일정 */
DROP TABLE plan 
	CASCADE CONSTRAINTS;

/* 경로리플 */
DROP TABLE route_reply 
	CASCADE CONSTRAINTS;

/* 지역 */
DROP TABLE location 
	CASCADE CONSTRAINTS;

/* 국가 */
DROP TABLE nation 
	CASCADE CONSTRAINTS;

/* 관광지추천인리스트 */
DROP TABLE site_vote 
	CASCADE CONSTRAINTS;

/* 경로추천인리스트 */
DROP TABLE route_vote 
	CASCADE CONSTRAINTS;

/* 회원 */
CREATE TABLE member (
	id VARCHAR2(40) NOT NULL, /* 아이디 */
	password VARCHAR2(16) NOT NULL, /* 비밀번호 */
	name VARCHAR2(20), /* 이름 */
	m_level NUMBER(1), /* 회원등급 */
	m_state NUMBER(1) /* 회원상태 */
);

COMMENT ON TABLE member IS '회원';

COMMENT ON COLUMN member.id IS '아이디';

COMMENT ON COLUMN member.password IS '비밀번호';

COMMENT ON COLUMN member.name IS '이름';

COMMENT ON COLUMN member.m_level IS '회원등급';

COMMENT ON COLUMN member.m_state IS '회원상태';

CREATE UNIQUE INDEX PK_member
	ON member (
		id ASC
	);

ALTER TABLE member
	ADD
		CONSTRAINT PK_member
		PRIMARY KEY (
			id
		);

/* 관광지 */
CREATE TABLE site (
	site_id NUMBER NOT NULL, /* 관광지번호 */
	loc_id NUMBER, /* 지역아이디 */
	site_name VARCHAR2(100), /* 관광지이름 */
	latitude NUMBER, /* 위도 */
	longitude NUMBER, /* 경도 */
	address VARCHAR2(1000), /* 전체주소 */
	approval NUMBER(1) /* 승인여부 */
);

COMMENT ON TABLE site IS '관광지';

COMMENT ON COLUMN site.site_id IS '관광지번호';

COMMENT ON COLUMN site.loc_id IS '지역아이디';

COMMENT ON COLUMN site.site_name IS '관광지이름';

COMMENT ON COLUMN site.latitude IS '위도';

COMMENT ON COLUMN site.longitude IS '경도';

COMMENT ON COLUMN site.address IS '전체주소';

COMMENT ON COLUMN site.approval IS '승인여부';

CREATE UNIQUE INDEX PK_site
	ON site (
		site_id ASC
	);

ALTER TABLE site
	ADD
		CONSTRAINT PK_site
		PRIMARY KEY (
			site_id
		);

/* 관광지정보 */
CREATE TABLE site_detail (
	site_id NUMBER NOT NULL, /* 관광지번호 */
	brief_info VARCHAR2(300), /* 간략설명 */
	detail_info CLOB, /* 상세설명 */
	id VARCHAR2(40), /* 아이디 */
	logtime DATE, /* 등록일 */
	recommend NUMBER, /* 추천수 */
	reply_count NUMBER /* 관광지리플수 */
);

COMMENT ON TABLE site_detail IS '관광지정보';

COMMENT ON COLUMN site_detail.site_id IS '관광지번호';

COMMENT ON COLUMN site_detail.brief_info IS '간략설명';

COMMENT ON COLUMN site_detail.detail_info IS '상세설명';

COMMENT ON COLUMN site_detail.id IS '아이디';

COMMENT ON COLUMN site_detail.logtime IS '등록일';

COMMENT ON COLUMN site_detail.recommend IS '추천수';

COMMENT ON COLUMN site_detail.reply_count IS '관광지리플수';

CREATE UNIQUE INDEX PK_site_detail
	ON site_detail (
		site_id ASC
	);

ALTER TABLE site_detail
	ADD
		CONSTRAINT PK_site_detail
		PRIMARY KEY (
			site_id
		);

/* 관광지사진 */
CREATE TABLE site_image (
	simg_id NUMBER NOT NULL, /* 관광지사진번호 */
	site_id NUMBER, /* 관광지번호 */
	origin_picture VARCHAR2(100), /* 원본사진이름 */
	saved_picture VARCHAR2(100), /* 저장사진이름 */
	savefolder VARCHAR2(100), /* 저장폴더 */
	type NUMBER(1) /* 사진타입 */
);

COMMENT ON TABLE site_image IS '관광지사진';

COMMENT ON COLUMN site_image.simg_id IS '관광지사진번호';

COMMENT ON COLUMN site_image.site_id IS '관광지번호';

COMMENT ON COLUMN site_image.origin_picture IS '원본사진이름';

COMMENT ON COLUMN site_image.saved_picture IS '저장사진이름';

COMMENT ON COLUMN site_image.savefolder IS '저장폴더';

COMMENT ON COLUMN site_image.type IS '사진타입';

CREATE UNIQUE INDEX PK_site_image
	ON site_image (
		simg_id ASC
	);

ALTER TABLE site_image
	ADD
		CONSTRAINT PK_site_image
		PRIMARY KEY (
			simg_id
		);

/* 관광지리플 */
CREATE TABLE site_reply (
	sre_id NUMBER NOT NULL, /* 관광지리플번호 */
	site_id NUMBER, /* 관광지번호 */
	id VARCHAR2(40), /* 아이디 */
	name VARCHAR2(20), /* 이름 */
	content VARCHAR2(300), /* 리플내용 */
	logtime DATE DEFAULT sysdate /* 작성일 */
);

COMMENT ON TABLE site_reply IS '관광지리플';

COMMENT ON COLUMN site_reply.sre_id IS '관광지리플번호';

COMMENT ON COLUMN site_reply.site_id IS '관광지번호';

COMMENT ON COLUMN site_reply.id IS '아이디';

COMMENT ON COLUMN site_reply.name IS '이름';

COMMENT ON COLUMN site_reply.content IS '리플내용';

COMMENT ON COLUMN site_reply.logtime IS '작성일';

CREATE UNIQUE INDEX PK_site_reply
	ON site_reply (
		sre_id ASC
	);

ALTER TABLE site_reply
	ADD
		CONSTRAINT PK_site_reply
		PRIMARY KEY (
			sre_id
		);

/* 여행경로 */
CREATE TABLE route (
	route_id NUMBER NOT NULL, /* 경로번호 */
	title VARCHAR2(100), /* 경로제목 */
	id VARCHAR2(40), /* 아이디 */
	name VARCHAR2(20), /* 이름 */
	logtime DATE, /* 작성일 */
	route_url VARCHAR2(4000), /* 경로사진URL */
	recommend NUMBER, /* 추천수 */
	reply_count NUMBER /* 경로리플수 */
);

COMMENT ON TABLE route IS '여행경로';

COMMENT ON COLUMN route.route_id IS '경로번호';

COMMENT ON COLUMN route.title IS '경로제목';

COMMENT ON COLUMN route.id IS '아이디';

COMMENT ON COLUMN route.name IS '이름';

COMMENT ON COLUMN route.logtime IS '작성일';

COMMENT ON COLUMN route.route_url IS '경로사진URL';

COMMENT ON COLUMN route.recommend IS '추천수';

COMMENT ON COLUMN route.reply_count IS '경로리플수';

CREATE UNIQUE INDEX PK_route
	ON route (
		route_id ASC
	);

ALTER TABLE route
	ADD
		CONSTRAINT PK_route
		PRIMARY KEY (
			route_id
		);

/* 경로정보 */
CREATE TABLE route_detail (
	rdet_id NUMBER NOT NULL, /* 경로정보번호 */
	route_id NUMBER, /* 경로번호 */
	site_id NUMBER, /* 관광지번호 */
	route_order NUMBER /* 경로순서 */
);

COMMENT ON TABLE route_detail IS '경로정보';

COMMENT ON COLUMN route_detail.rdet_id IS '경로정보번호';

COMMENT ON COLUMN route_detail.route_id IS '경로번호';

COMMENT ON COLUMN route_detail.site_id IS '관광지번호';

COMMENT ON COLUMN route_detail.route_order IS '경로순서';

CREATE UNIQUE INDEX PK_route_detail
	ON route_detail (
		rdet_id ASC
	);

ALTER TABLE route_detail
	ADD
		CONSTRAINT PK_route_detail
		PRIMARY KEY (
			rdet_id
		);

/* 여행일정 */
CREATE TABLE plan (
	plan_id NUMBER NOT NULL, /* 여행일정번호 */
	route_id NUMBER, /* 경로번호 */
	site_name VARCHAR2(30), /* 관광지이름 */
	id VARCHAR2(40), /* 아이디 */
	stay_date VARCHAR2(10), /* 체류날짜 */
	budget NUMBER, /* 예산 */
	currency VARCHAR(100), /* 화폐단위 */
	content VARCHAR2(500) /* 내용 */
);

COMMENT ON TABLE plan IS '여행일정';

COMMENT ON COLUMN plan.plan_id IS '여행일정번호';

COMMENT ON COLUMN plan.route_id IS '경로번호';

COMMENT ON COLUMN plan.site_name IS '관광지이름';

COMMENT ON COLUMN plan.id IS '아이디';

COMMENT ON COLUMN plan.stay_date IS '체류날짜';

COMMENT ON COLUMN plan.budget IS '예산';

COMMENT ON COLUMN plan.currency IS '화폐단위';

COMMENT ON COLUMN plan.content IS '내용';

CREATE UNIQUE INDEX PK_plan
	ON plan (
		plan_id ASC
	);

ALTER TABLE plan
	ADD
		CONSTRAINT PK_plan
		PRIMARY KEY (
			plan_id
		);

/* 경로리플 */
CREATE TABLE route_reply (
	rre_id NUMBER NOT NULL, /* 경로리플번호 */
	route_id NUMBER, /* 경로번호 */
	id VARCHAR2(40), /* 아이디 */
	name VARCHAR2(20), /* 이름 */
	content VARCHAR2(300), /* 리플내용 */
	logtime DATE DEFAULT sysdate /* 작성일 */
);

COMMENT ON TABLE route_reply IS '경로리플';

COMMENT ON COLUMN route_reply.rre_id IS '경로리플번호';

COMMENT ON COLUMN route_reply.route_id IS '경로번호';

COMMENT ON COLUMN route_reply.id IS '아이디';

COMMENT ON COLUMN route_reply.name IS '이름';

COMMENT ON COLUMN route_reply.content IS '리플내용';

COMMENT ON COLUMN route_reply.logtime IS '작성일';

CREATE UNIQUE INDEX PK_route_reply
	ON route_reply (
		rre_id ASC
	);

ALTER TABLE route_reply
	ADD
		CONSTRAINT PK_route_reply
		PRIMARY KEY (
			rre_id
		);

/* 지역 */
CREATE TABLE location (
	loc_id NUMBER NOT NULL, /* 지역아이디 */
	loc_name VARCHAR2(30), /* 지역이름 */
	nation_code VARCHAR2(2) /* 국가코드 */
);

COMMENT ON TABLE location IS '지역';

COMMENT ON COLUMN location.loc_id IS '지역아이디';

COMMENT ON COLUMN location.loc_name IS '지역이름';

COMMENT ON COLUMN location.nation_code IS '국가코드';

CREATE UNIQUE INDEX PK_location
	ON location (
		loc_id ASC
	);

ALTER TABLE location
	ADD
		CONSTRAINT PK_location
		PRIMARY KEY (
			loc_id
		);

/* 국가 */
CREATE TABLE nation (
	nation_code VARCHAR2(2) NOT NULL, /* 국가코드 */
	kor_name VARCHAR2(255), /* 한글이름 */
	eng_name VARCHAR2(255) /* 영문이름 */
);

COMMENT ON TABLE nation IS '국가';

COMMENT ON COLUMN nation.nation_code IS '국가코드';

COMMENT ON COLUMN nation.kor_name IS '한글이름';

COMMENT ON COLUMN nation.eng_name IS '영문이름';

CREATE UNIQUE INDEX PK_nation
	ON nation (
		nation_code ASC
	);

ALTER TABLE nation
	ADD
		CONSTRAINT PK_nation
		PRIMARY KEY (
			nation_code
		);

/* 관광지추천인리스트 */
CREATE TABLE site_vote (
	svote_id NUMBER NOT NULL, /* 추천인리스트번호 */
	id VARCHAR2(40), /* 아이디 */
	site_id NUMBER /* 관광지번호 */
);

COMMENT ON TABLE site_vote IS '관광지추천인리스트';

COMMENT ON COLUMN site_vote.svote_id IS '추천인리스트번호';

COMMENT ON COLUMN site_vote.id IS '아이디';

COMMENT ON COLUMN site_vote.site_id IS '관광지번호';

CREATE UNIQUE INDEX PK_site_vote
	ON site_vote (
		svote_id ASC
	);

ALTER TABLE site_vote
	ADD
		CONSTRAINT PK_site_vote
		PRIMARY KEY (
			svote_id
		);

/* 경로추천인리스트 */
CREATE TABLE route_vote (
	rvote_id NUMBER NOT NULL, /* 추천인리스트번호 */
	id VARCHAR2(40), /* 아이디 */
	route_id NUMBER /* 경로번호 */
);

COMMENT ON TABLE route_vote IS '경로추천인리스트';

COMMENT ON COLUMN route_vote.rvote_id IS '추천인리스트번호';

COMMENT ON COLUMN route_vote.id IS '아이디';

COMMENT ON COLUMN route_vote.route_id IS '경로번호';

CREATE UNIQUE INDEX PK_route_vote
	ON route_vote (
		rvote_id ASC
	);

ALTER TABLE route_vote
	ADD
		CONSTRAINT PK_route_vote
		PRIMARY KEY (
			rvote_id
		);

ALTER TABLE site
	ADD
		CONSTRAINT FK_location_TO_site
		FOREIGN KEY (
			loc_id
		)
		REFERENCES location (
			loc_id
		);

ALTER TABLE site_detail
	ADD
		CONSTRAINT FK_site_TO_site_detail
		FOREIGN KEY (
			site_id
		)
		REFERENCES site (
			site_id
		);

ALTER TABLE site_image
	ADD
		CONSTRAINT FK_site_TO_site_image
		FOREIGN KEY (
			site_id
		)
		REFERENCES site (
			site_id
		);

ALTER TABLE site_reply
	ADD
		CONSTRAINT FK_site_TO_site_reply
		FOREIGN KEY (
			site_id
		)
		REFERENCES site (
			site_id
		);

ALTER TABLE route_detail
	ADD
		CONSTRAINT FK_site_TO_route_detail
		FOREIGN KEY (
			site_id
		)
		REFERENCES site (
			site_id
		);

ALTER TABLE route_detail
	ADD
		CONSTRAINT FK_route_TO_route_detail
		FOREIGN KEY (
			route_id
		)
		REFERENCES route (
			route_id
		);

ALTER TABLE plan
	ADD
		CONSTRAINT FK_route_TO_plan
		FOREIGN KEY (
			route_id
		)
		REFERENCES route (
			route_id
		);

ALTER TABLE route_reply
	ADD
		CONSTRAINT FK_route_TO_route_reply
		FOREIGN KEY (
			route_id
		)
		REFERENCES route (
			route_id
		);

ALTER TABLE location
	ADD
		CONSTRAINT FK_nation_TO_location
		FOREIGN KEY (
			nation_code
		)
		REFERENCES nation (
			nation_code
		);

ALTER TABLE site_vote
	ADD
		CONSTRAINT FK_site_TO_site_vote
		FOREIGN KEY (
			site_id
		)
		REFERENCES site (
			site_id
		);

ALTER TABLE route_vote
	ADD
		CONSTRAINT FK_route_TO_route_vote
		FOREIGN KEY (
			route_id
		)
		REFERENCES route (
			route_id
		);