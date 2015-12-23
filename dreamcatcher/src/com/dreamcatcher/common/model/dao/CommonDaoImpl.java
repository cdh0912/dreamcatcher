package com.dreamcatcher.common.model.dao;

import java.sql.*;
import java.util.*;

import com.dreamcatcher.common.model.KeywordDto;
import com.dreamcatcher.site.model.LocationDto;
import com.dreamcatcher.site.model.NationDto;
import com.dreamcatcher.util.StringCheck;
import com.dreamcatcher.util.db.DBClose;
import com.dreamcatcher.util.db.DBConnection;

public class CommonDaoImpl implements CommonDao {

	private static CommonDao commonDao;
	
	private CommonDaoImpl() {}

	static{
		commonDao = new CommonDaoImpl();
	}
	
	public static CommonDao getInstance(){
		return commonDao;
	}

	@Override
	public List<KeywordDto> autoComplete(String keyword) {
		List<KeywordDto> keywordList = new ArrayList<KeywordDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT keyword  \n");
			sql.append("FROM(SELECT site_name AS keyword  \n");
			sql.append("    FROM site \n");
			sql.append("    WHERE approval = 1 \n");
			sql.append("    UNION \n");
			sql.append("    SELECT address AS keyword  \n");
			sql.append("    FROM site \n");
			sql.append("    WHERE approval = 1 \n");
			sql.append("    UNION \n");
			sql.append("    SELECT loc_name AS keyword  \n");
			sql.append("    FROM site LEFT OUTER JOIN location USING(loc_id) \n");
			sql.append("    WHERE approval = 1 \n");
			sql.append("    UNION \n");
			sql.append("    SELECT kor_name AS keyword  \n");
			sql.append("    FROM (site LEFT OUTER JOIN location USING(loc_id)) LEFT OUTER JOIN nation USING(nation_code) \n");
			sql.append("    WHERE approval = 1 \n");
			sql.append("    UNION \n");
			sql.append("    SELECT eng_name AS keyword  \n");
			sql.append("    FROM (site LEFT OUTER JOIN location USING(loc_id)) LEFT OUTER JOIN nation USING(nation_code) \n");
			sql.append("    WHERE approval = 1 ) \n");
			sql.append("WHERE LOWER(keyword) LIKE LOWER('%'||?||'%') \n");
			sql.append("ORDER BY keyword");
		    
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, keyword);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				KeywordDto keywordDto = new KeywordDto();
				keywordDto.setKeyword(rs.getString("keyword"));
				keywordList.add(keywordDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBClose.close(rs, pstmt, conn);
		}
		return keywordList;
	}

	@Override
	public List<NationDto> getNationList(Map searchMap) {
		List<NationDto> nationList = new ArrayList<NationDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String categoryMode = (String)searchMap.get("categoryMode");
		String searchWord = (String)searchMap.get("searchWord");
		try {
			
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT DISTINCT kor_name, nation_code        \n");
			sql.append("FROM site LEFT OUTER JOIN (SELECT loc_id, loc_name, nation_code, kor_name, eng_name \n");
			sql.append("                             FROM location LEFT OUTER JOIN nation \n");
			sql.append("                                  USING (nation_code) \n");
			sql.append("                             ) \n");
			sql.append("     USING(loc_id) \n");
			sql.append("		WHERE ( LOWER(site_name) LIKE '%' || LOWER(?) || '%' \n");
			sql.append("        		OR LOWER(address) LIKE '%' || LOWER(?) || '%' \n");
			sql.append("				OR LOWER(loc_name) LIKE '%' || LOWER(?) || '%' \n");
			sql.append("				OR LOWER(kor_name) LIKE '%' || LOWER(?) || '%' \n");
			sql.append("				OR LOWER(eng_name) LIKE '%' || LOWER(?) || '%' )  \n");
			if(categoryMode.isEmpty() || !"total".equals(categoryMode))
				sql.append("AND approval = 1  \n");
			sql.append("ORDER BY kor_name   \n");

			
		    
			pstmt = conn.prepareStatement(sql.toString());
			int index = 0;

			pstmt.setString(++index, searchWord);
			pstmt.setString(++index, searchWord);
			pstmt.setString(++index, searchWord);
			pstmt.setString(++index, searchWord);
			pstmt.setString(++index, searchWord);

			rs = pstmt.executeQuery();
			while(rs.next()) {
				NationDto nationDto = new NationDto();
				nationDto.setKor_name(rs.getString("kor_name"));
				nationDto.setNation_code(rs.getString("nation_code"));
				nationList.add(nationDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBClose.close(rs, pstmt, conn);
		}
		return nationList;
	}
	
	@Override
	public List<NationDto> getNationListWithPaging(Map searchMap) {
		List<NationDto> nationList = new ArrayList<NationDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String searchMode = (String)searchMap.get("searchMode"); 
		String categoryMode = (String)searchMap.get("categoryMode");
		String searchWord = (String)searchMap.get("searchWord");
		int start = (int)searchMap.get("start");
		int end = (int)searchMap.get("end");
		//System.out.println(searchMode + " "+ categoryMode + " "+searchWord + " "+start + " "+end);
		try {
			
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT DISTINCT nation_code, kor_name, eng_name \n");
			sql.append("FROM(SELECT item.* \n");
			if(searchMode.isEmpty() || "date".equals(searchMode))
				sql.append("	FROM(SELECT RANK() OVER(ORDER BY logtime DESC, site_id DESC) num, \n");
			else
				sql.append("FROM (SELECT RANK() OVER(ORDER BY recommend DESC, logtime DESC, site_id DESC) num, \n");
			sql.append("		nation_code, kor_name, eng_name, logtime, recommend \n");
			sql.append("		FROM site LEFT OUTER JOIN (SELECT loc_id, loc_name, nation_code, kor_name, eng_name \n");
			sql.append("									FROM location LEFT OUTER JOIN nation \n");
			sql.append("													USING (nation_code)) \n");
			sql.append("					USING(loc_id) JOIN site_detail USING(site_id) \n");

			sql.append("	WHERE ( LOWER(site_name) LIKE '%' || LOWER(?) || '%' \n");
			sql.append("        	OR LOWER(address) LIKE '%' || LOWER(?) || '%' \n");
			sql.append("			OR LOWER(loc_name) LIKE '%' || LOWER(?) || '%' \n");
			sql.append("			OR LOWER(kor_name) LIKE '%' || LOWER(?) || '%' \n");
			sql.append("			OR LOWER(eng_name) LIKE '%' || LOWER(?) || '%' )  \n");
			
			if(categoryMode.isEmpty() || !"total".equals(categoryMode))
							sql.append("	AND approval = 1  \n");
			sql.append("		) item  \n");
			sql.append("	WHERE item.num BETWEEN ? AND ? )\n");
			sql.append("WHERE nation_code IS NOT NULL \n");
			sql.append("ORDER BY kor_name        \n");
			                     	
		    //System.out.println(sql.toString());
			pstmt = conn.prepareStatement(sql.toString());
			int index = 0;

			pstmt.setString(++index, searchWord);
			pstmt.setString(++index, searchWord);
			pstmt.setString(++index, searchWord);
			pstmt.setString(++index, searchWord);
			pstmt.setString(++index, searchWord);

			pstmt.setInt(++index, start);
			pstmt.setInt(++index, end);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				NationDto nationDto = new NationDto();
				nationDto.setKor_name(rs.getString("kor_name"));
				nationDto.setEng_name(rs.getString("eng_name"));
				nationDto.setNation_code(rs.getString("nation_code"));
				nationList.add(nationDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBClose.close(rs, pstmt, conn);
		}
		return nationList;
	}

	@Override
	public List<LocationDto> getLocationList(Map searchMap) {
		List<LocationDto> locationList = new ArrayList<LocationDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String categoryMode = (String)searchMap.get("categoryMode");
		String nation_code = (String)searchMap.get("nation_code");
		String searchWord = (String)searchMap.get("searchWord");
		try {
			
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT DISTINCT loc_name                   \n");
			sql.append("FROM site LEFT OUTER JOIN (SELECT loc_id, loc_name, nation_code, kor_name, eng_name \n");
			sql.append("                             FROM location LEFT OUTER JOIN nation \n");
			sql.append("                                  USING (nation_code) \n");
			sql.append("                             ) \n");
			sql.append("     USING(loc_id) \n");

			sql.append("		WHERE ( LOWER(site_name) LIKE '%' || LOWER(?) || '%' \n");
			sql.append("        		OR LOWER(address) LIKE '%' || LOWER(?) || '%' \n");
			sql.append("				OR LOWER(loc_name) LIKE '%' || LOWER(?) || '%' \n");
			sql.append("				OR LOWER(kor_name) LIKE '%' || LOWER(?) || '%' \n");
			sql.append("				OR LOWER(eng_name) LIKE '%' || LOWER(?) || '%' )  \n");
			if(categoryMode.isEmpty() || !"total".equals(categoryMode))
				sql.append("AND approval = 1  \n");
			sql.append("		AND nation_code = ? \n");
			sql.append("ORDER BY loc_name   \n");
			
									    
			pstmt = conn.prepareStatement(sql.toString());
			int index = 0;

			pstmt.setString(++index, searchWord);
			pstmt.setString(++index, searchWord);
			pstmt.setString(++index, searchWord);
			pstmt.setString(++index, searchWord);
			pstmt.setString(++index, searchWord);

			pstmt.setString(++index, nation_code);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				LocationDto locationDto = new LocationDto();
				locationDto.setLoc_name(rs.getString("loc_name"));
				locationList.add(locationDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBClose.close(rs, pstmt, conn);
		}
		return locationList;
	}
	
	@Override
	public List<LocationDto> getLocationListWithPaging(Map searchMap) {
		List<LocationDto> locationList = new ArrayList<LocationDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String searchMode = StringCheck.nullToBlank((String)searchMap.get("searchMode")); 
		String categoryMode = (String)searchMap.get("categoryMode");
		String nation_code = (String)searchMap.get("nation_code");
		String searchWord = (String)searchMap.get("searchWord");
		
		int start = (int)searchMap.get("start");
		int end = (int)searchMap.get("end");

		try {
			
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT DISTINCT loc_name  \n");
			sql.append("FROM(SELECT item.*  \n");
			if(searchMode.isEmpty() || "date".equals(searchMode))
				sql.append("	FROM(SELECT RANK() OVER(ORDER BY logtime DESC, site_id DESC) num, \n");
			else
				sql.append("	FROM (SELECT RANK() OVER(ORDER BY recommend DESC, logtime DESC, site_id DESC) num, \n");
			sql.append("		nation_code, loc_name, logtime, recommend  \n");
			sql.append("			FROM site LEFT OUTER JOIN (SELECT loc_id, loc_name, nation_code, kor_name, eng_name \n");
			sql.append("									FROM location LEFT OUTER JOIN nation USING (nation_code)) \n");
			sql.append("					USING(loc_id) JOIN site_detail USING(site_id) \n");
			sql.append("	WHERE ( LOWER(site_name) LIKE '%' || LOWER(?) || '%' \n");
			sql.append("        	OR LOWER(address) LIKE '%' || LOWER(?) || '%' \n");
			sql.append("			OR LOWER(loc_name) LIKE '%' || LOWER(?) || '%' \n");
			sql.append("			OR LOWER(kor_name) LIKE '%' || LOWER(?) || '%' \n");
			sql.append("			OR LOWER(eng_name) LIKE '%' || LOWER(?) || '%' )  \n");
			if(categoryMode.isEmpty() || !"total".equals(categoryMode))
				sql.append("			AND approval = 1  \n");	
			sql.append("			) item   \n");		
			sql.append("		WHERE item.num BETWEEN ? AND ?  \n");		
			sql.append("				AND nation_code = ? ) \n");
			sql.append("WHERE loc_name IS NOT NULL \n");
			sql.append("ORDER BY loc_name \n");
			
        

			pstmt = conn.prepareStatement(sql.toString());
			int index = 0;
	
			pstmt.setString(++index, searchWord);
			pstmt.setString(++index, searchWord);
			pstmt.setString(++index, searchWord);
			pstmt.setString(++index, searchWord);
			pstmt.setString(++index, searchWord);

			pstmt.setInt(++index, start);
			pstmt.setInt(++index, end);
			pstmt.setString(++index, nation_code);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				LocationDto locationDto = new LocationDto();
				locationDto.setLoc_name(rs.getString("loc_name"));
				locationList.add(locationDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBClose.close(rs, pstmt, conn);
		}
		return locationList;
	}

}
