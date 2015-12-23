package com.dreamcatcher.route.model.dao;

import java.sql.*;
import java.util.*;

import com.dreamcatcher.common.model.KeywordDto;
import com.dreamcatcher.route.model.RouteDto;
import com.dreamcatcher.route.model.RouteReplyDto;
import com.dreamcatcher.util.StringCheck;
import com.dreamcatcher.util.db.DBClose;
import com.dreamcatcher.util.db.DBConnection;

public class RouteDaoImpl implements RouteDao {
	
	private static RouteDao routeDao;
	
	private RouteDaoImpl() {}

	static{
		routeDao = new RouteDaoImpl();
	}
	
	public static RouteDao getInstance(){
		return routeDao;
	}
	
	@Override
	public int totalArticleCount(Map searchMap){
		
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String searchWord = StringCheck.nullToBlank((String)searchMap.get("searchWord"));
		try {					

			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(DISTINCT route_id)    \n");
			sql.append("FROM site LEFT OUTER JOIN (SELECT loc_id, loc_name, nation_code, kor_name, eng_name \n");
			sql.append("                             FROM location LEFT OUTER JOIN nation \n");
			sql.append("                                  USING (nation_code) \n");
			sql.append("                             ) \n");
			sql.append("     USING(loc_id) JOIN route_detail USING(site_id) \n");
			sql.append("WHERE approval = 1 \n");
			if(!searchWord.isEmpty()){
				sql.append("				AND ( LOWER(site_name) LIKE '%' || LOWER(?) || '%' \n");
				sql.append("        		OR LOWER(address) LIKE '%' || LOWER(?) || '%' \n");
				sql.append("				OR LOWER(loc_name) LIKE '%' || LOWER(?) || '%' \n");
				sql.append("				OR LOWER(kor_name) LIKE '%' || LOWER(?) || '%' \n");
				sql.append("				OR LOWER(eng_name) LIKE '%' || LOWER(?) || '%' )    \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			int index = 0;
			if(!searchWord.isEmpty()){
				pstmt.setString(++index, searchWord);
				pstmt.setString(++index, searchWord);
				pstmt.setString(++index, searchWord);
				pstmt.setString(++index, searchWord);
				pstmt.setString(++index, searchWord);

			}
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			count = -1;
		} finally {
			DBClose.close(rs, pstmt, conn);
		}
		return count;
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
			sql.append("    FROM site JOIN route_detail USING (site_id) \n");
			sql.append("    WHERE approval = 1 \n");
			sql.append("    UNION \n");
			sql.append("    SELECT address AS keyword  \n");
			sql.append("    FROM site JOIN route_detail USING (site_id)  \n");
			sql.append("    WHERE approval = 1 \n");
			sql.append("    UNION \n");
			sql.append("    SELECT loc_name AS keyword  \n");
			sql.append("    FROM site LEFT OUTER JOIN location USING(loc_id) JOIN route_detail USING (site_id) \n");
			sql.append("    WHERE approval = 1 \n");
			sql.append("    UNION \n");
			sql.append("    SELECT kor_name AS keyword  \n");
			sql.append("    FROM (site LEFT OUTER JOIN location USING(loc_id)) LEFT OUTER JOIN nation USING(nation_code) JOIN route_detail USING (site_id) \n");
			sql.append("    WHERE approval = 1 \n");
			sql.append("    UNION \n");
			sql.append("    SELECT eng_name AS keyword  \n");
			sql.append("    FROM (site LEFT OUTER JOIN location USING(loc_id)) LEFT OUTER JOIN nation USING(nation_code) JOIN route_detail USING (site_id) \n");
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
	public List<RouteDto> routeArticleList(Map searchMap) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RouteDto> routeArticleList = new ArrayList<RouteDto>();
		String searchMode = StringCheck.nullToBlank((String)searchMap.get("searchMode")); 
		//boolean isAdmin = (boolean)searchMap.get("isAdmin");
		String searchWord = StringCheck.nullToBlank((String)searchMap.get("searchWord"));
		String viewMode = StringCheck.nullToBlank((String)searchMap.get("viewMode"));
		
		int start = (int)searchMap.get("start");
		int end = (int)searchMap.get("end");
		
		try {					

			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT item.* \n");
			//System.out.println(searchMode);
			if(searchMode.isEmpty() || "date".equals(searchMode))
				sql.append("FROM (SELECT RANK() OVER(ORDER BY logtime DESC, res.route_id DESC) num, \n");				
			else
				sql.append("FROM (SELECT RANK() OVER(ORDER BY recommend DESC, logtime DESC, res.route_id DESC) num,  \n");
			sql.append("              	res.route_id, res.title, res.id, res.name, res.route_url, res.recommend, res.reply_count,  \n");	
			sql.append("              	DECODE(TO_CHAR(res.logtime, 'yymmdd'), TO_CHAR(sysdate, 'yymmdd'),  \n");
			sql.append("				TO_CHAR(res.logtime, 'hh24:mi:ss'), TO_CHAR(res.logtime, 'yy.mm.dd')) logtime, \n");			
			sql.append("       	ROUND(ratio_to_report(res.recommend) over(),2)*85 rec_percent,  \n");
			sql.append("       	ROUND(ratio_to_report(res.reply_count) over(),2)*85 rep_percent \n");
			sql.append("      	FROM(  SELECT DISTINCT route_id, title, id, name, \n");
			sql.append("       			logtime, route_url, recommend, reply_count \n");
			sql.append("        FROM route LEFT OUTER JOIN (SELECT route_id, site_id, site_name, address, loc_name, nation_code, kor_name, eng_name \n");
			sql.append("			 FROM site s LEFT OUTER JOIN (SELECT loc_id, loc_name, nation_code, kor_name, eng_name \n");
			sql.append("				FROM location LEFT OUTER JOIN nation \n");
			sql.append("      			 	USING( nation_code )   \n");
			sql.append("             	) USING(loc_id)  JOIN route_detail USING(site_id) \n");
			//if( !isAdmin ){
				sql.append("      		 WHERE approval = 1 \n" );
			//}
			sql.append("		         ) USING(route_id) \n");
			sql.append("       			 WHERE LOWER(site_name) LIKE '%' || LOWER(?) || '%' \n");
			sql.append("                	 	OR LOWER(address) LIKE '%' || LOWER(?) || '%' \n");
			sql.append("                 		OR LOWER(loc_name) LIKE '%' || LOWER(?) || '%' \n");
			sql.append("                  		OR LOWER(kor_name) LIKE '%' || LOWER(?) || '%' \n");
			sql.append("                  		OR LOWER(eng_name) LIKE '%' || LOWER(?) || '%' \n");
			sql.append(" 				)res \n");
			if( "myView".equals(viewMode)){
				sql.append("	WHERE id=?	 \n");
			}
			sql.append("      ) item \n");
			sql.append("WHERE item.num BETWEEN ? AND ? \n");
			
			pstmt = conn.prepareStatement(sql.toString());

			int index = 0;
			pstmt.setString(++index, searchWord);
			pstmt.setString(++index, searchWord);
			pstmt.setString(++index, searchWord);
			pstmt.setString(++index, searchWord);
			pstmt.setString(++index, searchWord);			
			if( "myView".equals(viewMode)){
				String id = (String)searchMap.get("id");
				pstmt.setString(++index, id);
			}
			pstmt.setInt(++index, start);
			pstmt.setInt(++index, end);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				RouteDto routeDto = new RouteDto();
				routeDto.setRoute_id(rs.getInt("route_id"));
				routeDto.setTitle(rs.getString("title"));
				routeDto.setId(rs.getString("id"));
				routeDto.setName(rs.getString("name"));
				routeDto.setLogtime(rs.getString("logtime"));
				routeDto.setRoute_url(rs.getString("route_url"));
				routeDto.setRecommend(rs.getInt("recommend"));
				routeDto.setRec_percent(rs.getInt("rec_percent"));
				routeDto.setReply_count(rs.getInt("reply_count"));
				routeDto.setRep_percent(rs.getInt("rep_percent"));
				routeArticleList.add(routeDto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, conn);
		}		
		
		return routeArticleList;
	}
	
	
	@Override
	public List<RouteReplyDto> replyList(Map<String,Integer> replyListMap) {
		List<RouteReplyDto> list = new ArrayList<RouteReplyDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT item.* \n");
			sql.append("FROM(SELECT  RANK() OVER(ORDER BY RRE_ID DESC) num, res.* \n");
			sql.append("     FROM(SELECT rre_id, id, name, content, logtime \n");
			sql.append("                FROM route_reply \n");
			sql.append("                WHERE route_id = ?) res) item \n");
			sql.append("WHERE item.num BETWEEN ? AND ? \n");

			pstmt = conn.prepareStatement(sql.toString());
			int index = 0;
			pstmt.setInt(++index, replyListMap.get("route_id"));
			pstmt.setInt(++index, replyListMap.get("start"));
			pstmt.setInt(++index, replyListMap.get("end"));
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				RouteReplyDto routeReplyDto = new RouteReplyDto();
				routeReplyDto.setRre_id(rs.getInt("rre_id"));
				routeReplyDto.setId(rs.getString("id"));
				routeReplyDto.setName(rs.getString("name"));
				routeReplyDto.setContent(rs.getString("content"));
				routeReplyDto.setLogtime(rs.getString("logtime"));				
				list.add(routeReplyDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, conn);
		}
		return list;

	}
	
	@Override
	public int totalReplyCount(int route_id){		
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {					
			
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(rre_id) \n");
			sql.append("FROM route_reply \n");
			sql.append("WHERE route_id = ? \n");
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, route_id);

			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			count = -1;
		} finally {
			DBClose.close(rs, pstmt, conn);
		}
		return count;
	}

	@Override
	public int replyRegister(RouteReplyDto routeReplyDto) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.makeConnection();
			conn.setAutoCommit(false); 
			//System.out.println("----------------"+routeReplyDto.getRoute_id());
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO route_reply(rre_id, route_id, id, name, content, logtime) \n");
			sql.append("VALUES (rre_seq.NEXTVAL, ?, ?, ?, ?, sysdate)");
			pstmt = conn.prepareStatement(sql.toString());
			int idx = 0;
			pstmt.setInt(++idx, routeReplyDto.getRoute_id());
			pstmt.setString(++idx, routeReplyDto.getId());
			pstmt.setString(++idx, routeReplyDto.getName());
			pstmt.setString(++idx, routeReplyDto.getContent());
			cnt = pstmt.executeUpdate();
			pstmt.close();
			
			StringBuffer insert_update = new StringBuffer();
			insert_update.append("UPDATE route SET reply_count = reply_count + 1 \n");
			insert_update.append("WHERE route_id = ? ");
			pstmt = conn.prepareStatement(insert_update.toString());
			pstmt.setInt(1, routeReplyDto.getRoute_id());
			pstmt.executeUpdate();
			pstmt.close();
			
			conn.commit();					
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			cnt = -1;
		} finally {
			DBClose.close(pstmt, conn);
		}
		return cnt;
	}

	@Override
	public int replyDelete(Map<String,Integer> replyMap) {
		int cnt = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.makeConnection();

			StringBuffer delete_reply = new StringBuffer();
			delete_reply.append("DELETE FROM route_reply \n");
			delete_reply.append("WHERE rre_id = ?");
			pstmt = conn.prepareStatement(delete_reply.toString());
			pstmt.setInt(1, replyMap.get("rre_id"));
			pstmt.executeUpdate();
			pstmt.close();

			StringBuffer delete_update = new StringBuffer();
			delete_update.append("UPDATE route SET reply_count = reply_count - 1 \n");
			delete_update.append("WHERE route_id = ? ");
			pstmt = conn.prepareStatement(delete_update.toString());
			pstmt.setInt(1, replyMap.get("route_id"));
			pstmt.executeUpdate();

			conn.commit();
			cnt = 1;
		} catch (SQLException e) {
			e.printStackTrace();
			cnt = -1;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			DBClose.close(pstmt, conn);
		}
		return cnt;
	}

	@Override
	public int replyModify(RouteReplyDto routeReplyDto) {
		int cnt = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.makeConnection();

			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE route_reply SET content = ?, logtime = sysdate \n");
			sql.append("WHERE rre_id = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, routeReplyDto.getContent());
			pstmt.setInt(2, routeReplyDto.getRre_id());
			cnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(pstmt, conn);
		}
		return cnt;
	}

	@Override
	public RouteReplyDto replyInfo(int rre_id) {
		RouteReplyDto routeReplyDto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT rre_id, id, name, content, logtime \n");
			sql.append("FROM route_reply \n");
			sql.append("WHERE rre_id = ? \n");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, rre_id);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				routeReplyDto = new RouteReplyDto();
				routeReplyDto.setRre_id(rs.getInt("rre_id"));
				routeReplyDto.setId(rs.getString("id"));
				routeReplyDto.setName(rs.getString("name"));
				routeReplyDto.setContent(rs.getString("content"));
				routeReplyDto.setLogtime(rs.getString("logtime"));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, conn);
		}
		return routeReplyDto;
	}

}
