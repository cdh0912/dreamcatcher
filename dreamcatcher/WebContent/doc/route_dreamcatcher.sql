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
DROP SEQUENCE ROUTE_SEQ;

DROP SEQUENCE RDET_SEQ;

CREATE SEQUENCE ROUTE_SEQ
START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE RDET_SEQ 
START WITH 1 INCREMENT BY 1; 

SELECT ROUTE_SEQ.CURRVAL
FROM DUAL                 

SELECT RDET_SEQ.CURRVAL
FROM DUAL                 

COMMIT;

----------------- ��� ���� -----------------------------------------

INSERT
INTO route(route_id, title, id, name, logtime, route_url, recommend, reply_count)
VALUES( ROUTE_SEQ.NEXTVAL, '�̱�����', 'dreamcatchul@gmail.com', '�帲ĳó', sysdate, '', 6, 9)

INSERT
INTO route_detail(rdet_id, route_id, site_id, route_order)
VALUES(RDET_SEQ.NEXTVAL, ROUTE_SEQ.CURRVAL, 21, 1) 

INSERT
INTO route_detail(rdet_id, route_id, site_id, route_order)
VALUES(RDET_SEQ.NEXTVAL, ROUTE_SEQ.CURRVAL, 22, 2) 

INSERT
INTO route_detail(rdet_id, route_id, site_id, route_order)
VALUES(RDET_SEQ.NEXTVAL, ROUTE_SEQ.CURRVAL, 41, 3) 

INSERT
INTO route_detail(rdet_id, route_id, site_id, route_order)
VALUES(RDET_SEQ.NEXTVAL, ROUTE_SEQ.CURRVAL, 42, 4) 

INSERT
INTO route_detail(rdet_id, route_id, site_id, route_order)
VALUES(RDET_SEQ.NEXTVAL, ROUTE_SEQ.CURRVAL, 21, 5)


SELECT * FROM route JOIN route_detail USING(route_id);


INSERT
INTO route(route_id, title, id, name, logtime, route_url, recommend, reply_count)
VALUES( ROUTE_SEQ.NEXTVAL, '�ѱ�����3', 'dreamcatchul@gmail.com', '�帲ĳó', sysdate, '', 3, 1)

INSERT
INTO route_detail(rdet_id, route_id, site_id, route_order)
VALUES(RDET_SEQ.NEXTVAL, ROUTE_SEQ.CURRVAL, 1, 1) 

INSERT
INTO route_detail(rdet_id, route_id, site_id, route_order)
VALUES(RDET_SEQ.NEXTVAL, ROUTE_SEQ.CURRVAL, 2, 2) 

INSERT
INTO route_detail(rdet_id, route_id, site_id, route_order)
VALUES(RDET_SEQ.NEXTVAL, ROUTE_SEQ.CURRVAL, 3, 3) 

INSERT
INTO route_detail(rdet_id, route_id, site_id, route_order)
VALUES(RDET_SEQ.NEXTVAL, ROUTE_SEQ.CURRVAL, 4, 4) 

INSERT
INTO route_detail(rdet_id, route_id, site_id, route_order)
VALUES(RDET_SEQ.NEXTVAL, ROUTE_SEQ.CURRVAL, 5, 5) 

INSERT
INTO route_detail(rdet_id, route_id, site_id, route_order)
VALUES(RDET_SEQ.NEXTVAL, ROUTE_SEQ.CURRVAL, 6, 6) 


SELECT * FROM route JOIN route_detail USING(route_id);


COMMIT;

 
SELECT * FROM site;
----------------- ���� �˻� ---------------------------------------------

SELECT s.site_name, s.address, l.loc_name, l.nation_code, l.kor_name, l.eng_name                     
FROM site s LEFT OUTER JOIN (SELECT l.loc_id, l.loc_name, n.nation_code, n.kor_name, n.eng_name
                             FROM location l LEFT OUTER JOIN nation n
                                  ON l.nation_code = n.nation_code
                             )l
     ON s.loc_id = l.loc_id
WHERE s.approval = 1  

SELECT *
FROM(SELECT site_id, site_name, address, loc_name, nation_code, kor_name, eng_name
    FROM site s JOIN (SELECT loc_id, loc_name, nation_code, kor_name, eng_name
                     FROM location JOIN nation
                     USING (nation_code))
    USING (loc_id)) JOIN route_detail
                         USING(site_id)
WHERE route_id = 4
ORDER BY route_order
     
----------------- Ű���� �˻� ------------------------------------------- 
 
                             

SELECT keyword     
FROM(SELECT site_name AS keyword          
    FROM site JOIN route_detail USING (site_id)
    WHERE approval = 1 
    UNION
    SELECT address AS keyword 
    FROM site JOIN route_detail USING (site_id) 
    WHERE approval = 1 
    UNION 
    SELECT loc_name AS keyword 
    FROM site JOIN location USING(loc_id) JOIN route_detail USING (site_id)
    WHERE approval = 1 
    UNION
    SELECT kor_name AS keyword 
    FROM (site LEFT OUTER JOIN location USING(loc_id)) LEFT OUTER JOIN nation USING(nation_code) JOIN route_detail USING (site_id)
    WHERE approval = 1 
    UNION
    SELECT eng_name AS keyword        
    FROM (site LEFT OUTER JOIN location USING(loc_id)) LEFT OUTER JOIN nation USING(nation_code) JOIN route_detail USING (site_id)
    WHERE approval = 1 ) 
WHERE LOWER(keyword) LIKE LOWER('%'||'�̱�'||'%')  
ORDER BY keyword                   
                                  
------------------- ����Ʈ ������ ------------------------------------------


SELECT item.*
FROM ( SELECT  RANK() OVER(ORDER BY recommend DESC, logtime DESC) num,
               res.route_id, res.title, res.id, res.name, res.route_url, res.recommend, res.reply_count,
                   DECODE(TO_CHAR(res.logtime, 'yymmdd'), TO_CHAR(sysdate, 'yymmdd'), 
                 TO_CHAR(res.logtime, 'hh24:mi:ss'), TO_CHAR(res.logtime, 'yy.mm.dd')) logtime,
             ROUND(ratio_to_report(res.recommend) over(),2)*100 rec_percent,
             ROUND(ratio_to_report(res.reply_count) over(),2)*100 rep_percent 
     FROM(
         SELECT DISTINCT route_id, title, id, name,

                 logtime, route_url, recommend, reply_count    
         FROM route LEFT OUTER JOIN (SELECT route_id, site_id, site_name, address, loc_name, nation_code, kor_name, eng_name                     
                                    FROM site s LEFT OUTER JOIN (SELECT loc_id, loc_name, nation_code, kor_name, eng_name
                                         FROM location LEFT OUTER JOIN nation
                                              USING( nation_code )     
                                         ) USING(loc_id)  JOIN route_detail USING(site_id)
                                        WHERE approval = 1     
                ) USING(route_id)
                WHERE LOWER(site_name) LIKE '%' || '����' || '%'
        		OR LOWER(address) LIKE '%' || '����' || '%'
				OR LOWER(loc_name) LIKE '%' || '����' || '%'
				OR LOWER(kor_name) LIKE '%' || '����' || '%'
				OR LOWER(eng_name) LIKE '%' || '����' || '%'            
                ) res
         WHERE id = 'dreamcatchul@gmail.com'      
      ) item                                                  
WHERE item.num BETWEEN :pg * 10 - 9 AND :pg*10;



SELECT item.*
FROM ( SELECT  RANK() OVER(ORDER BY recommend DESC, logtime DESC) num,
               res.route_id, res.title, res.id, res.name, res.route_url, res.recommend, res.reply_count,
                   DECODE(TO_CHAR(res.logtime, 'yymmdd'), TO_CHAR(sysdate, 'yymmdd'), 
                 TO_CHAR(res.logtime, 'hh24:mi:ss'), TO_CHAR(res.logtime, 'yy.mm.dd')) logtime,
             ROUND(ratio_to_report(res.recommend) over(),2)*100 rec_percent,
             ROUND(ratio_to_report(res.reply_count) over(),2)*100 rep_percent 
     FROM(
         SELECT DISTINCT route_id, title, id, name,

                 logtime, route_url, recommend, reply_count    
         FROM route LEFT OUTER JOIN (SELECT route_id, site_id, site_name, address, loc_name, nation_code, kor_name, eng_name                     
                                    FROM site s LEFT OUTER JOIN (SELECT loc_id, loc_name, nation_code, kor_name, eng_name
                                         FROM location LEFT OUTER JOIN nation
                                              USING( nation_code )     
                                         ) USING(loc_id)  JOIN route_detail USING(site_id)
                WHERE approval = 1     
                ) USING(route_id)
                WHERE LOWER(site_name) LIKE '%' || '����' || '%'
        		OR LOWER(address) LIKE '%' || '����' || '%'
				OR LOWER(loc_name) LIKE '%' || '����' || '%'
				OR LOWER(kor_name) LIKE '%' || '����' || '%'
				OR LOWER(eng_name) LIKE '%' || '����' || '%' 
                ) res
         WHERE id = 'dreamcatchul@gmail.com'      
      ) item                                                  
WHERE item.num BETWEEN :pg * 10 - 9 AND :pg*10;

SELECT item.*
FROM ( SELECT  RANK() OVER(ORDER BY logtime DESC) num,
               res.route_id, res.title, res.id, res.name, res.route_url, res.recommend, res.reply_count,
                   DECODE(TO_CHAR(res.logtime, 'yymmdd'), TO_CHAR(sysdate, 'yymmdd'), 
                 TO_CHAR(res.logtime, 'hh24:mi:ss'), TO_CHAR(res.logtime, 'yy.mm.dd')) logtime,
             ROUND(ratio_to_report(res.recommend) over(),2)*100 rec_percent,
             ROUND(ratio_to_report(res.reply_count) over(),2)*100 rep_percent 
     FROM(
         SELECT DISTINCT route_id, title, id, name,

                 logtime, route_url, recommend, reply_count    
         FROM route LEFT OUTER JOIN (SELECT route_id, site_id, site_name, address, loc_name, nation_code, kor_name, eng_name                     
                                    FROM site s LEFT OUTER JOIN (SELECT loc_id, loc_name, nation_code, kor_name, eng_name
                                         FROM location LEFT OUTER JOIN nation
                                              USING( nation_code )     
                                         ) USING(loc_id)  JOIN route_detail USING(site_id)
                WHERE approval = 1
              
                ) USING(route_id)
                WHERE LOWER(site_name) LIKE '%' || '����' || '%'
        		OR LOWER(address) LIKE '%' || '����' || '%'
				OR LOWER(loc_name) LIKE '%' || '����' || '%'
				OR LOWER(kor_name) LIKE '%' || '����' || '%'
				OR LOWER(eng_name) LIKE '%' || '����' || '%' 
                ) res
         WHERE id = 'dreamcatchul@gmail.com'      
      ) item                                                  
WHERE item.num BETWEEN :pg * 10 - 9 AND :pg*10;



------------ ��ü ��� �� ------------------------------- 

SELECT COUNT(DISTINCT route_id)                    
FROM site LEFT OUTER JOIN (SELECT loc_id, loc_name, nation_code, kor_name, eng_name
                             FROM location LEFT OUTER JOIN nation
                                  USING (nation_code)
                             )
     USING(loc_id) JOIN route_detail USING(site_id)
WHERE approval = 1 AND ( LOWER(site_name) LIKE '%' || LOWER('') || '%'
        		OR LOWER(address) LIKE '%' || LOWER('') || '%'
				OR LOWER(loc_name) LIKE '%' || LOWER('') || '%'
				OR LOWER(kor_name) LIKE '%' || LOWER('') || '%'
				OR LOWER(eng_name) LIKE '%' || LOWER('') || '%' )   
COMMIT;


------------------- ��õ --------------------------------------------------
INSERT  