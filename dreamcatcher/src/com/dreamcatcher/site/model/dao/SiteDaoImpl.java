package com.dreamcatcher.site.model.dao;

import java.sql.*;
import java.util.*;

import com.dreamcatcher.site.model.*;
import com.dreamcatcher.util.StringCheck;
import com.dreamcatcher.util.db.DBClose;
import com.dreamcatcher.util.db.DBConnection;

public class SiteDaoImpl implements SiteDao {	
	
	private static SiteDao siteDao;

	static {
		siteDao = new SiteDaoImpl();
	}

	private SiteDaoImpl() {
	}

	public static SiteDao getInstance() {
		return siteDao;
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
			sql.append("SELECT COUNT(site_id) \n");
			sql.append("FROM site LEFT OUTER JOIN (SELECT loc_id, loc_name, nation_code, kor_name, eng_name \n");
			sql.append("                             FROM location LEFT OUTER JOIN nation \n");
			sql.append("                                  USING (nation_code) \n");
			sql.append("                             ) \n");
			sql.append("     USING(loc_id) \n");
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
	public List<SiteDetailDto> siteArticleList(Map searchMap) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SiteDetailDto> siteDetailList = new ArrayList<SiteDetailDto>();
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
	
			if(searchMode.isEmpty() || "date".equals(searchMode))
				sql.append("FROM (SELECT RANK() OVER(ORDER BY logtime DESC, sld.site_id DESC) num, sld.site_id, sld.site_name, sld.address, sld.loc_name, \n");				
			else
				sql.append("FROM (SELECT RANK() OVER(ORDER BY recommend DESC, logtime DESC, sld.site_id DESC) num, sld.site_id, sld.site_name, sld.address, sld.loc_name, \n");
			sql.append("       sld.nation_code, sld.kor_name, sld.id, sld.brief_info,  \n");
			sql.append("       DECODE(TO_CHAR(sld.logtime, 'yymmdd'), \n");
			sql.append("              TO_CHAR(sysdate, 'yymmdd'), TO_CHAR(sld.logtime, 'hh24:mi:ss'), \n");
			sql.append("              TO_CHAR(sld.logtime, 'yy.mm.dd')) logtime, sld.recommend, sld.rec_percent, \n");
			sql.append("			sld.reply_count, sld.rep_percent, \n");
			sql.append("       		si.origin_picture,si.saved_picture,si.savefolder,si.type \n");
			sql.append("       FROM (SELECT sl.*, sd.id, sd.brief_info, sd.logtime, \n");
			sql.append("			sd.recommend, ROUND(ratio_to_report(recommend) over(),2)*85 rec_percent, \n");
			sql.append("			sd.reply_count, ROUND(ratio_to_report(reply_count) over(),2)*85 rep_percent \n");
			sql.append("       FROM(  SELECT s.site_id, s.site_name, s.address, l.loc_name, l.nation_code, l.kor_name, l.eng_name                      \n");
			sql.append("             FROM site s LEFT OUTER JOIN (SELECT l.loc_id, l.loc_name, n.nation_code, n.kor_name, n.eng_name \n");
			sql.append("                                     FROM location l LEFT OUTER JOIN nation n \n");
			sql.append("                                          ON l.nation_code = n.nation_code \n");
			sql.append("                                     )l \n");
			sql.append("             ON s.loc_id = l.loc_id \n");
			//if( !isAdmin ){
				sql.append("      		 WHERE s.approval = 1 \n" );
			//}
			sql.append("		                           ) sl JOIN site_detail sd \n");
			sql.append("                               ON sl.site_id = sd.site_id \n");
			if( "myView".equals(viewMode)){
				sql.append("							WHERE sd.id=?	 \n");
			}
			sql.append("								) sld LEFT OUTER JOIN site_image si \n");
			sql.append("                                                               ON sld.site_id = si.site_id \n");
			if(!searchWord.isEmpty()){
				sql.append("      WHERE LOWER(sld.site_name) LIKE '%' || LOWER(?) || '%'\n");
				sql.append("      		OR LOWER(sld.address) LIKE '%' || LOWER(?) || '%'\n");
				sql.append("			OR LOWER(sld.loc_name) LIKE '%' || LOWER(?) || '%'\n");
				sql.append("			OR LOWER(sld.kor_name) LIKE '%' || LOWER(?) || '%'\n");
				sql.append("			OR LOWER(sld.eng_name) LIKE '%' || LOWER(?) || '%'\n");
			}
			sql.append("      ) item \n");
			sql.append("WHERE item.num BETWEEN ? AND ? \n");
			
			pstmt = conn.prepareStatement(sql.toString());

			int index = 0;
			
			if( "myView".equals(viewMode)){
				String id = (String)searchMap.get("id");
				pstmt.setString(++index, id);
			}
			if(!searchWord.isEmpty()){
				pstmt.setString(++index, searchWord);
				pstmt.setString(++index, searchWord);
				pstmt.setString(++index, searchWord);
				pstmt.setString(++index, searchWord);
				pstmt.setString(++index, searchWord);

			}
			pstmt.setInt(++index, start);
			pstmt.setInt(++index, end);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				SiteDetailDto siteDetailDto = new SiteDetailDto();
				siteDetailDto.setSite_id(rs.getInt("site_id"));
				siteDetailDto.setSite_name(rs.getString("site_name"));
				siteDetailDto.setAddress(rs.getString("address"));
				siteDetailDto.setLoc_name(rs.getString("loc_name"));
				siteDetailDto.setNation_code(rs.getString("nation_code"));
				siteDetailDto.setKor_name(rs.getString("kor_name"));
				siteDetailDto.setId(rs.getString("id"));
				siteDetailDto.setBrief_info(rs.getString("brief_Info"));
				siteDetailDto.setLogtime(rs.getString("logtime"));
				siteDetailDto.setRecommend(rs.getInt("recommend"));
				siteDetailDto.setRec_percent(rs.getInt("rec_percent"));
				siteDetailDto.setReply_count(rs.getInt("reply_count"));
				siteDetailDto.setRep_percent(rs.getInt("rep_percent"));
				siteDetailDto.setOrigin_picture(rs.getString("origin_picture"));
				siteDetailDto.setSaved_picture(rs.getString("saved_picture"));
				siteDetailDto.setSavefolder(rs.getString("savefolder"));
				siteDetailDto.setType(rs.getInt("type"));
				siteDetailList.add(siteDetailDto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, conn);
		}

		return siteDetailList;
	}

	
	@Override
	public List<NationDto> getNationList() {
		List<NationDto> nationlist = new ArrayList<NationDto>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = DBConnection.makeConnection();
			String sql = "";
			sql += "select nation_code, kor_name, eng_name \n";
			sql += "from nation \n";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				NationDto nationDto = new NationDto();
				nationDto.setKor_name(rs.getString("kor_name"));
				nationDto.setEng_name(rs.getString("eng_name"));
				nationDto.setNation_code(rs.getString("nation_code"));
				nationlist.add(nationDto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(rs, stmt, conn);
		}

		return nationlist;
	}
	


	@Override
	public int siteMake(SiteDetailDto siteDetailDto) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		
			conn = DBConnection.makeConnection();
			conn.setAutoCommit(false);
			
			Double latitude = siteDetailDto.getLatitude();
			Double longitude = siteDetailDto.getLongitude();
			String nation_code = siteDetailDto.getNation_code();
			String address = siteDetailDto.getAddress();
			String site_name = siteDetailDto.getSite_name();
			String brief_info = siteDetailDto.getBrief_info();
			String detail_info = siteDetailDto.getDetail_info();
			String loc_name = siteDetailDto.getLoc_name();
			String id = siteDetailDto.getId();
			int type = siteDetailDto.getType();
			
			String origin_picture = siteDetailDto.getOrigin_picture();
			String saved_picture = siteDetailDto.getSaved_picture();
			String savefolder = siteDetailDto.getSavefolder();
			
		
			
			// site의 location이 이미 존재하는지 검색
			StringBuffer selectLocation = new StringBuffer();
			selectLocation.append("SELECT loc_id \n");
			selectLocation.append("FROM location \n");
			selectLocation.append("WHERE loc_name = ? AND nation_code = ? \n");
			
			pstmt = conn.prepareStatement(selectLocation.toString());
			
			pstmt.setString(1, loc_name);
			pstmt.setString(2, nation_code);
			rs = pstmt.executeQuery();


			int loc_id = 0; 
			if (rs.next()) 
				loc_id = rs.getInt(1);
			
			pstmt.close();
			// site 정보 입력
			StringBuffer insertSite = new StringBuffer();
			
			int index = 0;
			if( loc_id == 0 ){ // location이 존재하지 않는다면 

				insertSite.append("INSERT ALL \n");
				insertSite.append("INTO location(loc_id, loc_name, nation_code ) \n");
				insertSite.append("VALUES (LOC_SEQ.NEXTVAL, ?, ?) \n");
				insertSite.append("INTO site (site_id, loc_id, site_name, latitude, longitude, address, approval) \n");
				insertSite.append("VALUES (SITE_SEQ.NEXTVAL, LOC_SEQ.CURRVAL, ?, ?, ?, ?, ?) \n");
				insertSite.append("SELECT * FROM DUAL \n");
				pstmt = conn.prepareStatement(insertSite.toString());
				
				pstmt.setString(++index, loc_name);
				pstmt.setString(++index, nation_code);


				//System.out.println(insertSite.toString());
									
			}else{ 	// location이 존재한다면 
				insertSite.append("INSERT INTO site (site_id, loc_id, site_name, latitude, longitude, address, approval) \n");
				insertSite.append("VALUES (SITE_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?) \n");
				
				pstmt = conn.prepareStatement(insertSite.toString());
				
				pstmt.setInt(++index, loc_id);
				
				//System.out.println(insertSite.toString());
			}
			
			pstmt.setString(++index, site_name);
			pstmt.setDouble(++index, latitude);
			pstmt.setDouble(++index, longitude);
			pstmt.setString(++index, address);
			pstmt.setInt(++index, 1);
			
			pstmt.executeUpdate();
			pstmt.close();
			
			// site detail 정보 입력
			StringBuffer insertSiteDetail = new StringBuffer();
			insertSiteDetail.append("INSERT ALL INTO site_detail(site_id, brief_info, detail_info, id, logtime, recommend, reply_count) \n");
			insertSiteDetail.append("VALUES (SITE_SEQ.CURRVAL, ?, ?, ?, sysdate, 0, 0) \n");
			insertSiteDetail.append("INTO site_image(simg_id, site_id, origin_picture, saved_picture, savefolder, type) \n");
			insertSiteDetail.append("VALUES(SIMG_SEQ.NEXTVAL, SITE_SEQ.CURRVAL, ?, ?, ?, ?) \n");
			insertSiteDetail.append("SELECT * FROM DUAL \n");
			
			int index2 = 0;
			pstmt = conn.prepareStatement(insertSiteDetail.toString());
			pstmt.setString(++index2, brief_info);
			pstmt.setString(++index2, detail_info);
			pstmt.setString(++index2, id);
			pstmt.setString(++index2, origin_picture);
			pstmt.setString(++index2, saved_picture);
			pstmt.setString(++index2, savefolder);
			pstmt.setInt(++index2, type);

			
			pstmt.executeUpdate();
			
			conn.commit();
			
			cnt = 1;
		} catch (SQLException e) {
			cnt = -1;
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			DBClose.close(rs, pstmt, conn);
		}
		return cnt;
	}

	@Override
	public SiteDetailDto siteView(int site_id) {
		SiteDetailDto siteDetailDto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.makeConnection();
			String sql = "SELECT site_id, site_name, id, TO_CHAR(logtime, 'yyyy-mm-dd hh:mi') logtime, brief_info, detail_info,origin_picture,saved_picture,savefolder, recommend, reply_count \n";
			sql += "FROM site LEFT OUTER JOIN site_detail USING(site_id) LEFT OUTER JOIN site_image USING(site_id) \n";
			sql += "where site_id = ? \n";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, site_id);

			rs = pstmt.executeQuery();
			
			if(rs.next()){
				siteDetailDto = new SiteDetailDto();
				siteDetailDto.setSite_id(rs.getInt("site_id"));
				siteDetailDto.setSite_name(rs.getString("site_name"));
				siteDetailDto.setId(rs.getString("id"));
				siteDetailDto.setLogtime(rs.getString("logtime"));
				siteDetailDto.setBrief_info(rs.getString("brief_info"));
				siteDetailDto.setDetail_info(rs.getString("detail_info"));
				siteDetailDto.setSaved_picture(rs.getString("saved_picture"));
				siteDetailDto.setOrigin_picture(rs.getString("origin_picture"));
				siteDetailDto.setSavefolder(rs.getString("savefolder"));
				siteDetailDto.setRecommend(rs.getInt("recommend"));
				siteDetailDto.setReply_count(rs.getInt("reply_count"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBClose.close(rs, pstmt, conn);
		}
		return siteDetailDto;
	}

	@Override
	public int deleteSite(int site_id) {
		
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//System.out.println("site_id : "+site_id);
		try {
			conn = DBConnection.makeConnection();
			conn.setAutoCommit(false);
			
			String delete_sitereply = "";
			delete_sitereply += "delete from site_reply \n";
			delete_sitereply += "where site_id = ? \n";
			pstmt = conn.prepareStatement(delete_sitereply);
			pstmt.setInt(1, site_id);
			pstmt.executeUpdate();
			pstmt.close();
			
			String delete_sitedetail = "";
			delete_sitedetail +="delete from site_detail \n";
			delete_sitedetail += "where site_id =? \n";
			pstmt = conn.prepareStatement(delete_sitedetail);
			pstmt.setInt(1, site_id);
			pstmt.executeUpdate();
			pstmt.close();
			
			String delete_siteimage = "";
			delete_siteimage += "delete from site_image \n";
			delete_siteimage += "where site_id = ? \n";
			pstmt = conn.prepareStatement(delete_siteimage);
			pstmt.setInt(1, site_id);
			pstmt.executeUpdate();
			pstmt.close();
			
			int loc_id = 0;
			String select_locationId = "";
			select_locationId += "SELECT loc_id FROM site \n";
			select_locationId += "WHERE site_id = ? \n";
			pstmt = conn.prepareStatement(select_locationId);
			pstmt.setInt(1, site_id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				loc_id = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			
			String select_locationCount = "";
			select_locationCount += "SELECT COUNT(loc_id) \n";
			select_locationCount += "FROM site RIGHT OUTER JOIN location USING(loc_id) \n";
			select_locationCount += "WHERE loc_id = ? \n";
			pstmt = conn.prepareStatement(select_locationCount);
			pstmt.setInt(1, loc_id);
			rs = pstmt.executeQuery();
			int location_count = Integer.MAX_VALUE;
			if(rs.next()){
				location_count = rs.getInt(1);
			}
			rs.close();
			pstmt.close();		
			
			String delete_site = "";
			delete_site += "delete from site \n";
			delete_site += "where site_id = ? \n";
			pstmt = conn.prepareStatement(delete_site);
			pstmt.setInt(1, site_id);
			pstmt.executeUpdate();
			pstmt.close();
			
			if(location_count == 1){
				String delete_location = "";
				delete_location += "delete from location \n";
				delete_location += "where loc_id = ? \n";
				pstmt = conn.prepareStatement(delete_location);
				pstmt.setInt(1, loc_id);
				pstmt.executeUpdate();
				pstmt.close();
			}
			
			conn.commit();
			cnt = 1;
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBClose.close(rs, pstmt, conn);
		}
		
		return cnt;
	}

	@Override
	public int siteModify(SiteDetailDto siteDetailDto) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		
		try {
			conn = DBConnection.makeConnection();
			conn.setAutoCommit(false);
			
			StringBuffer update_siteimage = new StringBuffer();
			update_siteimage.append( "UPDATE site_image \n");
			update_siteimage.append( "SET origin_picture = ?, saved_picture = ?, savefolder = ?, type = ? \n");
			update_siteimage.append( "WHERE site_id = ? \n");
			pstmt = conn.prepareStatement(update_siteimage.toString());
			
			int index = 0;
			pstmt.setString(++index, siteDetailDto.getOrigin_picture());
			pstmt.setString(++index, siteDetailDto.getSaved_picture());
			pstmt.setString(++index, siteDetailDto.getSavefolder());
			pstmt.setInt(++index, siteDetailDto.getType());
			pstmt.setInt(++index, siteDetailDto.getSite_id());
			pstmt.executeUpdate();
			pstmt.close();
			
			StringBuffer update_sitedetail = new StringBuffer();
			update_sitedetail.append( "UPDATE site_detail \n");
			update_sitedetail.append( "SET brief_info = ?, detail_info = ?, logtime = sysdate \n" );
			update_sitedetail.append( "WHERE site_id = ? \n" );
			pstmt = conn.prepareStatement(update_sitedetail.toString());
			
			int index2 = 0;
			pstmt.setString(++index2, siteDetailDto.getBrief_info());
			pstmt.setString(++index2, siteDetailDto.getDetail_info());
			pstmt.setInt(++index2, siteDetailDto.getSite_id());
			pstmt.executeUpdate();
			pstmt.close();

			
			StringBuffer update_site = new StringBuffer();	 
			update_site.append( "UPDATE site SET site_name = ? \n" );
			update_site.append( "WHERE site_id = ? \n" );
			pstmt = conn.prepareStatement(update_site.toString());
			pstmt.setString(1, siteDetailDto.getSite_name());
			pstmt.setInt(2, siteDetailDto.getSite_id());
			pstmt.executeUpdate();
			
			conn.commit();
			cnt = 1;
		} catch (SQLException e) {
			cnt = -1;
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBClose.close(pstmt, conn);
		}
		return cnt;
	}

	@Override
	public SiteImageDto siteImage(int site_id) {
		SiteImageDto siteImageDto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT site_id, origin_picture, saved_picture, savefolder, type \n");
			sql.append("FROM site_image \n");
			sql.append("WHERE site_id = ? \n");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, site_id);

			rs = pstmt.executeQuery();
			
			if(rs.next()){
				siteImageDto = new SiteImageDto();
				siteImageDto.setSite_id(rs.getInt("site_id"));
				siteImageDto.setOrigin_picture(rs.getString("origin_picture"));
				siteImageDto.setSaved_picture(rs.getString("saved_picture"));
				siteImageDto.setSavefolder(rs.getString("savefolder"));
				siteImageDto.setType(rs.getInt("type"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBClose.close(rs, pstmt, conn);
		}
		return siteImageDto;
	}	
	
	
	@Override
	public List<SiteReplyDto> replyList(Map<String,Integer> replyListMap) {
		List<SiteReplyDto> list = new ArrayList<SiteReplyDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT item.* \n");
			sql.append("FROM(SELECT  RANK() OVER(ORDER BY sre_id DESC) num, res.* \n");
			sql.append("     FROM(SELECT sre_id, id, name, content, logtime \n");
			sql.append("                FROM site_reply \n");
			sql.append("                WHERE site_id = ?) res) item \n");
			sql.append("WHERE item.num BETWEEN ? AND ? \n");

			pstmt = conn.prepareStatement(sql.toString());
			int index = 0;
			pstmt.setInt(++index, replyListMap.get("site_id"));
			pstmt.setInt(++index, replyListMap.get("start"));
			pstmt.setInt(++index, replyListMap.get("end"));
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				SiteReplyDto siteReplyDto = new SiteReplyDto();
				siteReplyDto.setSre_id(rs.getInt("sre_id"));
				siteReplyDto.setId(rs.getString("id"));
				siteReplyDto.setName(rs.getString("name"));
				siteReplyDto.setContent(rs.getString("content"));
				siteReplyDto.setLogtime(rs.getString("logtime"));				
				list.add(siteReplyDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, conn);
		}
		return list;

	}
	
	@Override
	public int totalReplyCount(int site_id){		
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {					
			
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(sre_id) \n");
			sql.append("FROM site_reply \n");
			sql.append("WHERE site_id = ? \n");
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, site_id);

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
	public int replyRegister(SiteReplyDto siteReplyDto) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.makeConnection();
			conn.setAutoCommit(false); 

			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO site_reply(sre_id, site_id, id, name, content, logtime) \n");
			sql.append("VALUES (sre_seq.NEXTVAL, ?, ?, ?, ?, sysdate)");
			pstmt = conn.prepareStatement(sql.toString());
			int idx = 0;
			pstmt.setInt(++idx, siteReplyDto.getSite_id());
			pstmt.setString(++idx, siteReplyDto.getId());
			pstmt.setString(++idx, siteReplyDto.getName());
			pstmt.setString(++idx, siteReplyDto.getContent());
			cnt = pstmt.executeUpdate();
			pstmt.close();
			
			StringBuffer insert_update = new StringBuffer();
			insert_update.append("UPDATE site_detail SET reply_count = reply_count + 1 \n");
			insert_update.append("WHERE site_id = ? ");
			pstmt = conn.prepareStatement(insert_update.toString());
			pstmt.setInt(1, siteReplyDto.getSite_id());
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
			delete_reply.append("DELETE FROM site_reply \n");
			delete_reply.append("WHERE sre_id = ?");
			pstmt = conn.prepareStatement(delete_reply.toString());
			pstmt.setInt(1, replyMap.get("sre_id"));
			pstmt.executeUpdate();
			pstmt.close();

			StringBuffer delete_update = new StringBuffer();
			delete_update.append("UPDATE site_detail SET reply_count = reply_count - 1 \n");
			delete_update.append("WHERE site_id = ? ");
			pstmt = conn.prepareStatement(delete_update.toString());
			pstmt.setInt(1, replyMap.get("site_id"));
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
	public int replyModify(SiteReplyDto siteReplyDto) {
		int cnt = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.makeConnection();

			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE site_reply SET content = ?, logtime = sysdate \n");
			sql.append("WHERE sre_id = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, siteReplyDto.getContent());
			pstmt.setInt(2, siteReplyDto.getSre_id());
			cnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(pstmt, conn);
		}
		return cnt;
	}

	@Override
	public SiteReplyDto replyInfo(int sre_id) {
		SiteReplyDto siteReplyDto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT sre_id, id, name, content, logtime \n");
			sql.append("FROM site_reply \n");
			sql.append("WHERE sre_id = ? \n");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, sre_id);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				siteReplyDto = new SiteReplyDto();
				siteReplyDto.setSre_id(rs.getInt("sre_id"));
				siteReplyDto.setId(rs.getString("id"));
				siteReplyDto.setName(rs.getString("name"));
				siteReplyDto.setContent(rs.getString("content"));
				siteReplyDto.setLogtime(rs.getString("logtime"));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, conn);
		}
		return siteReplyDto;
	}
	
	@Override
	public int likeCheck(int site_id, String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt=0;

		SiteDto sDto = new SiteDto();
		try {
			conn = DBConnection.makeConnection();
			String sql = "";
			sql += "select svote_id \n";
			sql += "from site_vote where site_id=? and id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, site_id);
			pstmt.setString(2, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
				cnt=1;
			
			} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, conn);
		}
		return cnt;
	}
	
	
	@Override
	public void likeSite(int site_id, String id) {
//		System.out.println("likeSite >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + site_id);
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.makeConnection();
			conn.setAutoCommit(false);

			String sql="";
			sql += "insert into site_vote(svote_id, id, site_id) \n";
			sql += "values (svote_seq.nextval,? ,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, site_id);
			pstmt.executeUpdate();
			pstmt.close();

			String sql3 = "";
			sql3 += "update site_detail set recommend=recommend+1 where site_id=?";
			
			pstmt = conn.prepareStatement(sql3);
			pstmt.setInt(1, site_id);

			pstmt.executeUpdate();	
			pstmt.close();
			
			cnt++;
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			cnt = 0;
		} finally {
			DBClose.close(rs, pstmt, conn);
		}	
	}

	@Override
	public void disLikeSite(int site_id, String id) {
//		System.out.println("disLikeSite >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + site_id);
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.makeConnection();
			conn.setAutoCommit(false);

			String sql="";
			sql += "delete site_vote"
					+ " where id=? and site_id=?";
			//delete route_vote where id='anonymous' and site_id=10
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, site_id);
			pstmt.executeUpdate();
			pstmt.close();

			String sql3 = "";
			sql3 += "update site_detail set recommend=recommend-1 where site_id=?";
			
			pstmt = conn.prepareStatement(sql3);
			pstmt.setInt(1, site_id);

			pstmt.executeUpdate();	
			pstmt.close();
			
			cnt++;
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			cnt = 0;
		} finally {
			DBClose.close(rs, pstmt, conn);
		}	
	}
}
