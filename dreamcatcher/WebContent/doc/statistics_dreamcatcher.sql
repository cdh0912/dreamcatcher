SELECT RANK() OVER(ORDER BY recommend DESC), loc_name
FROM location JOIN site USING(loc_id) JOIN site_detail USING(site_id)


----- location ���� ----- 
SELECT rank, item, count, percentage
FROM(SELECT RANK() OVER(ORDER BY count DESC, loc_name ) rank, count, loc_name item,
            ROUND(ratio_to_report(count) over()*100, 1) percentage
    FROM(SELECT COUNT(recommend) count, loc_name 
        FROM location JOIN site USING(loc_id) JOIN site_detail USING(site_id)
        GROUP BY loc_name)) result
WHERE rank <= 5


----- site ���� ----- 
SELECT rank, item, count, percentage
FROM(SELECT RANK() OVER(ORDER BY count DESC, site_name ) rank, count, site_name item,
            ROUND(ratio_to_report(count) over()*100, 1) percentage
    FROM(SELECT COUNT(recommend) count, site_name 
        FROM site JOIN site_detail USING(site_id)
        GROUP BY site_name)) result
WHERE rank <= 5



----- ��κ� ���� �α� ���� ----- 

SELECT rank, item, count, percentage
FROM(SELECT RANK() OVER(ORDER BY count DESC, site_name ) rank, count, site_name item,
            ROUND(ratio_to_report(count) over()*100, 1) percentage
    FROM(SELECT COUNT(site_id) count, site_name 
        FROM site JOIN route_detail USING(site_id)
        GROUP BY site_name)) result
WHERE rank <= 5



