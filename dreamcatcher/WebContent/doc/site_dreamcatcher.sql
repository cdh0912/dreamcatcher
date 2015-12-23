DELETE FROM site_detail;

DELETE FROM site;

DELETE FROM location;

DELETE FROM nation;


select *
from site;

SELECT *
FROM nation;


select site_name, latitude, longitude, address, approval
from site;

SELECT * 
FROM location;

SELECT *
FROM site_detail;



----------------- ������ -------------------------------------------- 
DROP SEQUENCE SITE_SEQ;

DROP SEQUENCE LOC_SEQ;

CREATE SEQUENCE SITE_SEQ
START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE LOC_SEQ 
START WITH 1 INCREMENT BY 1; 

                 
                 

COMMIT;




SELECT SITE_SEQ.CURRVAL
FROM DUAL


SELECT * FROM location;

SELECT loc_id
FROM location
WHERE loc_name = '����Ư����' AND nation_code = 'US' 

INSERT ALL       
       INTO location
       VALUES (LOC_SEQ.NEXTVAL, '����Ư����', 'KR')
       INTO site (site_id, loc_id, site_name, latitude, longitude, address, approval)
       VALUES (SITE_SEQ.NEXTVAL, LOC_SEQ.CURRVAL, '����Ư����', 37.566446, 126.977925, '����Ư����', 1)
SELECT * FROM DUAL
       
       
INSERT INTO site_detail(site_id,brief_info,detail_info,id,logtime,recommend)
VALUES (SITE_SEQ.CURRVAL,'���ѹα��� ����', '����Ư���ô� ���ѹα��� �����μ�....', 'dreamcatchul@gmail.com', sysdate, 0) 



INSERT ALL
       INTO location
       VALUES (LOC_SEQ.NEXTVAL, '����������', 'KR')
       INTO site (site_id, loc_id, site_name, latitude, longitude, address, approval)
       VALUES (SITE_SEQ.NEXTVAL, LOC_SEQ.CURRVAL, '����������', 36.350412, 127.384547, '����������', 1)
SELECT * FROM DUAL; 


INSERT INTO site_detail
VALUES (SITE_SEQ.CURRVAL, '���б���� ����', '���������ô� ���ѹα� ���б���� ���ּҷμ�....', 'dreamcatchul@gmail.com', sysdate, 0)


                   
INSERT ALL
       INTO location
       VALUES (LOC_SEQ.NEXTVAL, '�λ걤����', 'KR')
       INTO site (site_id, loc_id, site_name, latitude, longitude, address, approval)
       VALUES (SITE_SEQ.NEXTVAL, LOC_SEQ.CURRVAL, '�λ걤����', 35.179555, 129.075641, '�λ걤����', 1)
SELECT * FROM DUAL; 

INSERT INTO site_detail
VALUES (SITE_SEQ.CURRVAL, '�ѱ� �ֳ��� �޾� ����', '�λ걤���ô´� ������ ���� ���� �������μ�....', 'dreamcatchul@gmail.com', sysdate, 0)

                       
INSERT ALL
       INTO location
       VALUES (LOC_SEQ.NEXTVAL, '���ʽ�', 'KR')
       INTO site (site_id, loc_id, site_name, latitude, longitude, address, approval)
       VALUES (SITE_SEQ.NEXTVAL, LOC_SEQ.CURRVAL, '���ʽ�', 38.207237, 128.591822, '������ ���ʽ�', 1)
SELECT * FROM DUAL; 

INSERT INTO site_detail
VALUES (SITE_SEQ.CURRVAL, '�������� ��ǥ ����', '���ʽô� �������� ��ġ�� ���÷μ�....', 'dreamcatchul@gmail.com', sysdate, 0)


INSERT ALL
       INTO location
       VALUES (LOC_SEQ.NEXTVAL, '���ֱ�����', 'KR')
       INTO site (site_id, loc_id, site_name, latitude, longitude, address, approval)
       VALUES (SITE_SEQ.NEXTVAL, LOC_SEQ.CURRVAL, '���ֱ�����', 38.207237, 128.591822, '���ֱ�����', 1)
SELECT * FROM DUAL; 

INSERT INTO site_detail        
VALUES (SITE_SEQ.CURRVAL, '��ȥ���� ���縦 ���� ����', '���ֱ����ô� ���󵵿� ��ġ�� ���÷μ�....', 'dreamcatchul@gmail.com', sysdate, 0)


SELECT loc_id
FROM location
WHERE loc_name = '���ʽ�' AND nation_code = 'KR' 

----- ���� ---
INSERT INTO location
VALUES(LOC_SEQ.NEXTVAL, '����', 'US');  

INSERT INTO site (site_id, loc_id, site_name, latitude, longitude, address, approval)
       VALUES (SITE_SEQ.NEXTVAL, 21, '����ư', 40.7590615, -73.969231, '�̱� ���� �� ����ư', 1)

INSERT INTO site_detail
VALUES (SITE_SEQ.CURRVAL, '�̱� ���� �� ������ ��ġ��', '����ư�� �̱� ���� �� ������ ��ġ�� �� ���� �α� �е�....', 'dreamcatchul@gmail.com', sysdate, 5, 7)


INSERT INTO site (site_id, loc_id, site_name, latitude, longitude, address, approval)
       VALUES (SITE_SEQ.NEXTVAL, 21, 'Ÿ���� ������', 40.758895, -73.985131, '�̱� ���� �� ����ư Ÿ���� ������', 1)

INSERT INTO site_detail
VALUES (SITE_SEQ.CURRVAL, '������ ������', 'Ÿ�� ������� �̱� ���� �̵�Ÿ�� ����ư�� �ִ� ������ ����� �����η�....', 'dreamcatchul@gmail.com', sysdate, 5, 7)


----- ������ --- 

INSERT INTO location
VALUES(LOC_SEQ.NEXTVAL, '�׹ٴ�', 'US');  

INSERT INTO site (site_id, loc_id, site_name, latitude, longitude, address, approval)
       VALUES (SITE_SEQ.NEXTVAL, 42, '�󽺺��̰Ž�', 36.125, -115.175, '�̱� �׹ٴ� �󽺺��̰Ž�', 1)

INSERT INTO site_detail
VALUES (SITE_SEQ.CURRVAL, '������ ������ ����', '�󽺺��̰Ž��� �̱� �׹ٴ� �� ������ �縷 ����� �ִ� �����̴�.', 'dreamcatchul@gmail.com', sysdate, 3, 19)

SELECT *
FROM location;

COMMIT;

SELECT route_id, route_url FROM route
----------------- site ��ǥ �̹��� ���� ----------------------------------

DROP SEQUENCE SIMG_SEQ;

CREATE SEQUENCE SIMG_SEQ
START WITH 1 INCREMENT BY 1;

SELECT *
FROM site_image;

INSERT INTO site_image(simg_id, site_id, origin_picture, saved_picture, savefolder, type)
VALUES(SIMG_SEQ.NEXTVAL, SITE_SEQ.CURRVAL, ?, ?, ?, ?)
 
----------------- ���� �˻� ---------------------------------------------

SELECT s.site_name, s.address, l.loc_name, l.nation_code, l.kor_name, l.eng_name                     
FROM site s LEFT OUTER JOIN (SELECT l.loc_id, l.loc_name, n.nation_code, n.kor_name, n.eng_name
                             FROM location l LEFT OUTER JOIN nation n
                                  ON l.nation_code = n.nation_code
                             )l
     ON s.loc_id = l.loc_id
WHERE s.approval = 1     
     
----------------- Ű���� �˻� ------------------------------------------- 
SELECT keyword     
FROM(SELECT site_name AS keyword             
    FROM site JOIN site_detail USING(site_id)
    WHERE approval = 0 OR id = 'dreamcatchul@gmail.com'
    UNION
    SELECT address AS keyword 
    FROM site JOIN site_detail USING(site_id)
    WHERE approval = 0 OR id = 'dreamcatchul@gmail.com'
    UNION 
    SELECT loc_name AS keyword 
    FROM (site JOIN site_detail USING(site_id)) LEFT OUTER JOIN location USING(loc_id)
    WHERE approval = 0 OR id = 'dreamcatchul@gmail.com'
    UNION
    SELECT kor_name AS keyword 
    FROM ((site JOIN site_detail USING(site_id)) LEFT OUTER JOIN location USING(loc_id)) LEFT OUTER JOIN nation USING(nation_code)
    WHERE approval = 0 OR id = 'dreamcatchul@gmail.com'
    UNION
    SELECT eng_name AS keyword 
    FROM ((site JOIN site_detail USING(site_id)) LEFT OUTER JOIN location USING(loc_id)) LEFT OUTER JOIN nation USING(nation_code)
    WHERE approval = 0 OR id = 'dreamcatchul@gmail.com')
WHERE LOWER(keyword) LIKE LOWER('%'||?||'%')
                             

SELECT keyword     
FROM(SELECT site_name AS keyword             
    FROM site 
    WHERE approval = 1 
    UNION
    SELECT address AS keyword 
    FROM site 
    WHERE approval = 1 
    UNION 
    SELECT loc_name AS keyword 
    FROM site LEFT OUTER JOIN location USING(loc_id)
    WHERE approval = 1 
    UNION
    SELECT kor_name AS keyword 
    FROM (site LEFT OUTER JOIN location USING(loc_id)) LEFT OUTER JOIN nation USING(nation_code)
    WHERE approval = 1 
    UNION
    SELECT eng_name AS keyword 
    FROM (site LEFT OUTER JOIN location USING(loc_id)) LEFT OUTER JOIN nation USING(nation_code)
    WHERE approval = 1 ) 
WHERE LOWER(keyword) LIKE LOWER('%'||?||'%')                     
                                  
------------------- ����Ʈ ������ ------------------------------------------


SELECT item.*
FROM (SELECT RANK() OVER(ORDER BY recommend DESC, logtime DESC) num, sld.site_id, sld.site_name, sld.address, sld.loc_name,
       sld.nation_code, sld.kor_name, sld.id,  sld.brief_info, 
       DECODE(TO_CHAR(sld.logtime, 'yymmdd'),
              TO_CHAR(sysdate, 'yymmdd'), TO_CHAR(sld.logtime, 'hh24:mi:ss'),
              TO_CHAR(sld.logtime, 'yy.mm.dd')) logtime, sld.recommend, sld.rec_percent,
              sld.reply_count, sld.rep_percent,
               si.origin_picture,si.saved_picture,si.savefolder,si.type
       FROM (SELECT sl.*, sd.id, sd.brief_info, sd.logtime, sd.recommend, sd.reply_count,
       ROUND(ratio_to_report(reply_count) over(),2)*100 rep_percent,
       ROUND(ratio_to_report(recommend) over(),2)*100 rec_percent
       FROM(  SELECT s.site_id, s.site_name, s.address, l.loc_name, l.nation_code, l.kor_name, l.eng_name                     
             FROM site s LEFT OUTER JOIN (SELECT l.loc_id, l.loc_name, n.nation_code, n.kor_name, n.eng_name
                                     FROM location l LEFT OUTER JOIN nation n
                                          ON l.nation_code = n.nation_code
                                     )l
             ON s.loc_id = l.loc_id
      WHERE s.approval = 1) sl JOIN site_detail sd
                               ON sl.site_id = sd.site_id
                               WHERE sd.id=''
                               ) sld LEFT OUTER JOIN site_image si
                                                               ON sld.site_id = si.site_id 
	 WHERE LOWER(sld.site_name) LIKE '%' || LOWER(?) || '%'
        		OR LOWER(sld.address) LIKE '%' || LOWER(?) || '%'
				OR LOWER(sld.loc_name) LIKE '%' || LOWER(?) || '%'
				OR LOWER(sld.kor_name) LIKE '%' || LOWER(?) || '%'
				OR LOWER(sld.eng_name) LIKE '%' || LOWER(?) || '%'    
      ) item                                                  
WHERE item.num BETWEEN :pg * 10 - 9 AND :pg*10;

------------ ��ü ������ �� ------------------------------- 

SELECT COUNT(site_id)                    
FROM site LEFT OUTER JOIN (SELECT loc_id, loc_name, nation_code, kor_name, eng_name
                             FROM location LEFT OUTER JOIN nation
                                  USING (nation_code)
                             )
     USING(loc_id)
WHERE approval = 1 AND ( LOWER(site_name) LIKE '%' || LOWER('') || '%'
        		OR LOWER(address) LIKE '%' || LOWER('') || '%'
				OR LOWER(loc_name) LIKE '%' || LOWER('') || '%'
				OR LOWER(kor_name) LIKE '%' || LOWER('') || '%'
				OR LOWER(eng_name) LIKE '%' || LOWER('') || '%' )   
COMMIT;


------------------------ location & nation ī�װ� -------------------------------------------------

----- nation

select nation_code, kor_name, eng_name
from nation
			
			
SELECT DISTINCT kor_name, nation_code
FROM((SELECT DISTINCT loc_name, nation_code                    
    FROM site s LEFT OUTER JOIN location l 
         USING (loc_id) 
    WHERE approval = 1)sl LEFT OUTER JOIN nation n
                        USING(nation_code))
ORDER BY kor_name


SELECT DISTINCT kor_name, nation_code                    
FROM site LEFT OUTER JOIN (SELECT loc_id, loc_name, nation_code, kor_name, eng_name
                             FROM location LEFT OUTER JOIN nation
                                  USING (nation_code)
                             )
     USING(loc_id)
WHERE approval = 1 AND ( LOWER(site_name) LIKE '%' || LOWER('') || '%'
        		OR LOWER(address) LIKE '%' || LOWER('') || '%'
				OR LOWER(loc_name) LIKE '%' || LOWER('') || '%'
				OR LOWER(kor_name) LIKE '%' || LOWER('') || '%'
				OR LOWER(eng_name) LIKE '%' || LOWER('') || '%' ) 
ORDER BY kor_name 

SELECT DISTINCT nation_code, kor_name, eng_name
FROM(SELECT item.*
    FROM(SELECT RANK() OVER(ORDER BY recommend DESC, logtime DESC, site_id DESC) num, nation_code, kor_name, eng_name, logtime, recommend           
         FROM site LEFT OUTER JOIN (SELECT loc_id, loc_name, nation_code, kor_name, eng_name
                                 FROM location LEFT OUTER JOIN nation
                                      USING (nation_code)
                                 )
                   USING(loc_id) JOIN site_detail USING(site_id)
         WHERE approval = 1 AND ( LOWER(site_name) LIKE '%' || LOWER('') || '%'
                    		OR LOWER(address) LIKE '%' || LOWER('') || '%'
    			        	OR LOWER(loc_name) LIKE '%' || LOWER('') || '%'
    				        OR LOWER(kor_name) LIKE '%' || LOWER('') || '%'
               				OR LOWER(eng_name) LIKE '%' || LOWER('') || '%' ) 
        ) item 
    WHERE item.num BETWEEN 1 AND 21
)
WHERE nation_code IS NOT NULL
ORDER BY kor_name 



----- location

SELECT loc_name
FROM((SELECT DISTINCT loc_name, nation_code                    
    FROM site s LEFT OUTER JOIN location l 
         USING (loc_id) 
    WHERE approval = 1)sl LEFT OUTER JOIN nation n
                        USING(nation_code))
WHERE nation_code = 'KR'
ORDER BY loc_name



SELECT DISTINCT loc_name                  
FROM site LEFT OUTER JOIN (SELECT loc_id, loc_name, nation_code, kor_name, eng_name
                             FROM location LEFT OUTER JOIN nation
                                  USING (nation_code)
                             )
     USING(loc_id)
               WHERE ( LOWER(site_name) LIKE '%' || LOWER('') || '%'
        		OR LOWER(address) LIKE '%' || LOWER('') || '%'
				OR LOWER(loc_name) LIKE '%' || LOWER('') || '%'
				OR LOWER(kor_name) LIKE '%' || LOWER('') || '%'
				OR LOWER(eng_name) LIKE '%' || LOWER('') || '%' )
				AND nation_code = 'KR'
            AND approval = 1 

ORDER BY loc_name  


SELECT DISTINCT loc_name
FROM(SELECT item.*
    FROM(SELECT RANK() OVER(ORDER BY recommend DESC, logtime DESC, site_id DESC) num,
          nation_code, loc_name, logtime, recommend           
         FROM site LEFT OUTER JOIN (SELECT loc_id, loc_name, nation_code, kor_name, eng_name
                                 FROM location LEFT OUTER JOIN nation
                                      USING (nation_code)
                                 )
                   USING(loc_id) JOIN site_detail USING(site_id)
         WHERE approval = 1 AND ( LOWER(site_name) LIKE '%' || LOWER('') || '%'
                    		OR LOWER(address) LIKE '%' || LOWER('') || '%'
    			        	OR LOWER(loc_name) LIKE '%' || LOWER('') || '%'
    				        OR LOWER(kor_name) LIKE '%' || LOWER('') || '%'
               				OR LOWER(eng_name) LIKE '%' || LOWER('') || '%' )
        ) item 
    WHERE item.num BETWEEN 1 AND 21
    AND nation_code='KR'
)
WHERE loc_name IS NOT NULL
ORDER BY loc_name 


-------------------  --------------------------------------------------


SELECT * FROM site where site_id =  148

SELECT DISTINCT loc_name 
FROM(SELECT item.*  

		FROM(SELECT RANK() OVER(ORDER BY logtime DESC, site_id DESC) num, 
	
			nation_code, loc_name, logtime, recommend 
					FROM site LEFT OUTER JOIN (SELECT loc_id, loc_name, nation_code, kor_name, eng_name 
											FROM location LEFT OUTER JOIN nation USING (nation_code))
						USING(loc_id) JOIN site_detail USING(site_id)

			WHERE ( LOWER(site_name) LIKE '%' || LOWER(?) || '%' 
	        	OR LOWER(address) LIKE '%' || LOWER(?) || '%' 
				OR LOWER(loc_name) LIKE '%' || LOWER(?) || '%' 
						OR LOWER(kor_name) LIKE '%' || LOWER(?) || '%' 
					OR LOWER(eng_name) LIKE '%' || LOWER(?) || '%' )  
				AND approval = 1 	
	        ) item 
    WHERE item.num BETWEEN 1 AND 21			
				AND nation_code = ? ) 
WHERE loc_name IS NOT NULL 
ORDER BY loc_name 

SELECT * FROM site_image JOIN site USING(site_id)