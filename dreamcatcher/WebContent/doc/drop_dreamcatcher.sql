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

ALTER TABLE site
	DROP
		CONSTRAINT FK_location_TO_site
		CASCADE;

ALTER TABLE site_detail
	DROP
		CONSTRAINT FK_member_TO_site_detail
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

ALTER TABLE site_reply
	DROP
		CONSTRAINT FK_member_TO_site_reply
		CASCADE;

ALTER TABLE route
	DROP
		CONSTRAINT FK_member_TO_route
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
		CONSTRAINT FK_member_TO_plan
		CASCADE;

ALTER TABLE plan
	DROP
		CONSTRAINT FK_route_TO_plan
		CASCADE;

ALTER TABLE route_reply
	DROP
		CONSTRAINT FK_route_TO_route_reply
		CASCADE;

ALTER TABLE route_reply
	DROP
		CONSTRAINT FK_member_TO_route_reply
		CASCADE;

ALTER TABLE location
	DROP
		CONSTRAINT FK_nation_TO_location
		CASCADE;

ALTER TABLE site_vote
	DROP
		CONSTRAINT FK_member_TO_site_vote
		CASCADE;

ALTER TABLE site_vote
	DROP
		CONSTRAINT FK_site_TO_site_vote
		CASCADE;

ALTER TABLE route_vote
	DROP
		CONSTRAINT FK_route_TO_route_vote
		CASCADE;

ALTER TABLE route_vote
	DROP
		CONSTRAINT FK_member_TO_route_vote
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