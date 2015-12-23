package com.dreamcatcher.plan.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.dreamcatcher.common.model.KeywordDto;
import com.dreamcatcher.plan.model.PlanDto;
import com.dreamcatcher.site.model.SiteImageDto;
import com.dreamcatcher.util.NumberCheck;
import com.dreamcatcher.util.StringCheck;
import com.dreamcatcher.util.db.DBClose;
import com.dreamcatcher.util.db.DBConnection;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class PlanDaoImpl implements PlanDao {
	
	private static PlanDao PlanDao;

	static {
		PlanDao = new PlanDaoImpl();
	}

	private PlanDaoImpl() {
	}

	public static PlanDao getInstance() {
		return PlanDao;
	}

	@Override
	public List<HashMap> getRouteDetailList(int route_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<HashMap> routeDetailList = new ArrayList<HashMap>();
		try {		
			conn = DBConnection.makeConnection();
			String sql = "";
			sql += "select site_id, site_name, latitude, longitude  \n";
			sql += "from route_detail \n";
			sql += "join site using (site_id) \n";
			sql += "join site_image using (site_id) \n";
			sql += "where route_id = ? \n";
			sql += "order by route_order \n";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, route_id);			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				HashMap map = new HashMap();
				map.put("route_id", route_id);
				map.put("site_id", rs.getInt("site_id"));
				map.put("site_name", rs.getString("site_name"));
				map.put("latitude", rs.getDouble("latitude"));
				map.put("longitude", rs.getDouble("longitude"));
				routeDetailList.add(map);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, conn);
		}

//		System.out.println("routeDetailList in dao ==== " + routeDetailList.size());

		return routeDetailList;
	}

	
	
	
	@Override
	public int getRouteModifyArticle(int route_id, String jsonStringFromMap) {
		//json parse		
		int cnt = 0;
		
		Gson gson = new Gson();
		JsonObject obj = gson.fromJson(jsonStringFromMap, JsonObject.class);
		JsonArray polyarr = obj.get("polylist").getAsJsonArray();

		int site_id;
		String route_url = obj.get("route_url").getAsString();

		//		int site_id = polyarr.get(0).getAsJsonObject().get("site_id").getAsInt(); // 1
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {		
			conn = DBConnection.makeConnection();
			conn.setAutoCommit(false);
			
			String delete_routeDetail = "";
			delete_routeDetail += "delete from route_detail \n";
			delete_routeDetail += "where route_id= ?";
			pstmt = conn.prepareStatement(delete_routeDetail);
			pstmt.setInt(1, route_id);
			pstmt.executeUpdate();
			pstmt.close();
			
			
			for (int i=0; i<polyarr.size(); i++) {
				site_id = polyarr.get(i).getAsJsonObject().get("site_id").getAsInt();
			
				String insert_routeDetail = "";
				insert_routeDetail += "insert into route_detail (rdet_id, route_id, site_id, route_order)  \n";
				insert_routeDetail += "values (rdet_seq.nextval, ?, ?, ?) \n";
				pstmt = conn.prepareStatement(insert_routeDetail);
//	System.out.println(">>>>>>>>>>>>" + route_id);
				pstmt.setInt(1, route_id);
				pstmt.setInt(2, site_id);
				pstmt.setInt(3, i+1);
				pstmt.executeUpdate();
				pstmt.close();
	
			}
			
			
			String update_routeURL = "";
			update_routeURL += "update route \n";
			update_routeURL += "set route_url = ?";
			update_routeURL += "where route_id= ?";
			pstmt = conn.prepareStatement(update_routeURL);
			pstmt.setString(1, route_url);
			pstmt.setInt(2, route_id);
			pstmt.executeUpdate();
			pstmt.close();			
			
			
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
			DBClose.close(pstmt, conn);
		}

		return cnt;
	}

//===========================================================================================================
	@Override
	public int registerSchedule(PlanDto pDto3, List<PlanDto> list, String jsonStringFromMap) {
		int route_id = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Gson gson = new Gson();
		JsonObject obj = gson.fromJson(jsonStringFromMap, JsonObject.class);
		JsonArray polyarr = obj.get("polylist").getAsJsonArray();
		int site_id;
		String route_url = obj.get("route_url").getAsString();

		//		int site_id = polyarr.get(0).getAsJsonObject().get("site_id").getAsInt(); // 1

		try {
			conn = DBConnection.makeConnection();
			conn.setAutoCommit(false);

			String insert_route = "";
			insert_route += "insert into route(route_id, title, id, name, logtime, route_url, recommend, reply_count) \n";
			insert_route += "values (route_seq.nextval, ?, ?, ?, sysdate, ?, 0, 0)";
            pstmt = conn.prepareStatement(insert_route);
			
			pstmt.setString(1,pDto3.getTitle());
			pstmt.setString(2,pDto3.getId());
			pstmt.setString(3,pDto3.getName());
			pstmt.setString(4,route_url);
			pstmt.executeUpdate();
			pstmt.close();

			for (int i=0; i<polyarr.size(); i++) {
				site_id = polyarr.get(i).getAsJsonObject().get("site_id").getAsInt();

				String insert_routeDetail = "";
				insert_routeDetail += "insert into route_detail (rdet_id, route_id, site_id, route_order)  \n";
				insert_routeDetail += "values (rdet_seq.nextval, route_seq.currval, ?, ?) \n";
				pstmt = conn.prepareStatement(insert_routeDetail);
				pstmt.setInt(1, site_id);
				pstmt.setInt(2, i+1);
				pstmt.executeUpdate();
				pstmt.close();
			}
			
			
			if(list.size() != 0){
				for (int i = 0; i < list.size(); i++) {
					PlanDto pDto2= list.get(i);
					String insert_plan = "";
					insert_plan += "insert into plan(plan_id, route_id, site_name, id, stay_date, budget, currency, content) \n";
					insert_plan += "values (plan_seq.nextval, route_seq.currval, ?, ?, ?, ?, ?, ?)";
	
					pstmt = conn.prepareStatement(insert_plan);
					pstmt.setString(1, pDto2.getSite_name());
					pstmt.setString(2, pDto2.getId());
					pstmt.setString(3, pDto2.getStay_date());
					pstmt.setInt(4, pDto2.getBudget());
					pstmt.setString(5, pDto2.getCurrency());
					pstmt.setString(6, pDto2.getContent());
					pstmt.executeUpdate();
					pstmt.close();
				}
			}
			
			String select_routeId = "select max(route_id) from route";
			pstmt = conn.prepareStatement(select_routeId);
			rs = pstmt.executeQuery();
			if(rs.next()){
				route_id = rs.getInt("max(route_id)");
			}
			
			conn.commit();			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(pstmt, conn);
		}	
		return route_id;
	}
	
	@Override
	public String getRoute_url(int route_id) {
		String route_url = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.makeConnection();
			String select_routeUrl = "select route_url from route where route_id = ?";
			pstmt = conn.prepareStatement(select_routeUrl);
			pstmt.setInt(1, route_id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				route_url = rs.getString("route_url");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, conn);
		}
		
		return route_url;
	}

	@Override
	public List<PlanDto> getScheduleList(int route_id) {
		List<PlanDto> list = new ArrayList<PlanDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBConnection.makeConnection();
			String select_plan = "";
			select_plan += "SELECT r.route_id, r.title, r.id, r.name, p.plan_id, r.logtime, r.reply_count, r.recommend, p.site_name, p.stay_date, p.budget, p.currency, p.content \n";
			select_plan += "FROM route r LEFT OUTER JOIN plan p \n";
			select_plan += "ON r.route_id = p.route_id WHERE r.route_id=? \n";
			select_plan += "order by plan_id";
			pstmt = conn.prepareStatement(select_plan);
			pstmt.setInt(1, route_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {	
				PlanDto pDto = new PlanDto();
				pDto.setRoute_id(rs.getInt("route_id"));
				pDto.setTitle(StringCheck.nullToNoName(rs.getString("title")));
				pDto.setId(rs.getString("id"));
				pDto.setName(rs.getString("name"));
				pDto.setLogtime(rs.getString("logtime"));
				pDto.setRecommend(rs.getInt("recommend"));
				pDto.setReply_count(rs.getInt("reply_count"));
				pDto.setPlan_id(rs.getInt("plan_id"));
				pDto.setSite_name(StringCheck.nullToBlank(rs.getString("site_name")));
				pDto.setStay_date(StringCheck.nullToBlank(rs.getString("stay_date")));
				pDto.setBudget(rs.getInt("budget"));
				pDto.setCurrency(StringCheck.nullToBlank(rs.getString("currency")));
				pDto.setContent(StringCheck.nullToBlank(rs.getString("content")));
				list.add(pDto);
			}	
			} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, conn);
		}
		return list;
	}


	@Override
	public int updateSchedule(PlanDto pDto, List<PlanDto> list, int route_id) {
//		System.out.println("update DaoImpl >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + route_id);
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.makeConnection();
			conn.setAutoCommit(false);

			String sql = "";
			sql += "update route set title=? where route_id=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, pDto.getTitle());

//			System.out.println(pDto.getTitle());
			pstmt.setInt(2, route_id);

			pstmt.executeUpdate();	
			pstmt.close();
			
			
			String sql2="";
			sql2 += "delete from plan where route_id=?";
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, route_id);
			pstmt.executeUpdate();
			pstmt.close();

			if(list.size() != 0){
				for (int i = 0; i < list.size(); i++) {
					//insert
					PlanDto pDto2= list.get(i);
					String sql3="";
					sql3 += "insert into plan(plan_id, route_id, site_name, id, stay_date, budget, currency, content) \n";
					sql3 += "values (plan_seq.nextval, ?, ?, ?, ?, ?, ?, ?)";
	
					pstmt = conn.prepareStatement(sql3);
					int idx2 = 1;
					
					pstmt.setInt(idx2++, route_id);
					pstmt.setString(idx2++, pDto2.getSite_name());
					pstmt.setString(idx2++,pDto.getId());
					pstmt.setString(idx2++, pDto2.getStay_date());
					pstmt.setInt(idx2++, pDto2.getBudget());
					pstmt.setString(idx2++, pDto2.getCurrency());
					pstmt.setString(idx2++, pDto2.getContent());
					pstmt.executeUpdate();
					cnt++;
					pstmt.close();
				}
			}
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			cnt = 0;
		} finally {
			DBClose.close(pstmt, conn);
		}	
		return cnt;
	}

	@Override
	public int deleteSchedule(int route_id) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.makeConnection();
			conn.setAutoCommit(false);

			String delete_routeDetail = "";
			delete_routeDetail += "delete from route_detail \n";
			delete_routeDetail += "where route_id = ? \n";
			pstmt = conn.prepareStatement(delete_routeDetail);
			pstmt.setInt(1, route_id);
			pstmt.executeUpdate();
			pstmt.close();

			String delete_plan = "";
			delete_plan += "delete from plan \n";
			delete_plan += "where route_id = ? \n";
			pstmt = conn.prepareStatement(delete_plan);
			pstmt.setInt(1, route_id);
			pstmt.executeUpdate();
			pstmt.close();

			String delete_route_reply = "";
			delete_route_reply += "delete from route_reply \n";
			delete_route_reply += "where route_id = ? \n";
			pstmt = conn.prepareStatement(delete_route_reply);
			pstmt.setInt(1, route_id);
			pstmt.executeUpdate();
			pstmt.close();

			String delete_route = "";
			delete_route += "delete from route \n";
			delete_route += "where route_id = ? \n";
			pstmt = conn.prepareStatement(delete_route);
			pstmt.setInt(1, route_id);
			pstmt.executeUpdate();
			
			cnt = 1;
			conn.commit();
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBClose.close(pstmt, conn);
		}
		return cnt;
	}

//==========================================================================================================	
	
	@Override
	public List<SiteImageDto> getSiteList() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<SiteImageDto> sitelist = new ArrayList<SiteImageDto>();
		try {		
			conn = DBConnection.makeConnection();
			String sql = "";
			sql += "select s.site_id, site_name, latitude, longitude, address, approval, saved_picture, savefolder   \n";
			sql += "from site s, site_image i \n";
			sql += "where s.site_id = i.site_id(+) ";

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				SiteImageDto siteImageDto = new SiteImageDto();
				siteImageDto.setSite_id(rs.getInt("site_id"));
				siteImageDto.setSite_name(rs.getString("site_name"));
				siteImageDto.setLatitude(rs.getDouble("latitude"));
				siteImageDto.setLongitude(rs.getDouble("longitude"));
				siteImageDto.setAddress(rs.getString("address"));
				siteImageDto.setApproval(rs.getInt("approval"));
				siteImageDto.setSaved_picture(rs.getString("saved_picture"));
				siteImageDto.setSavefolder(rs.getString("savefolder"));
				sitelist.add(siteImageDto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(rs, stmt, conn);
		}

//		System.out.println("listsize in dao ==== " + sitelist.size());
//		System.out.println("seoul lat in dao ==== " + sitelist.get(0).getLatitude());

		return sitelist;
	}

	
	@Override
	public List<KeywordDto> autoCompleteInMap(String keyword) {
		List<KeywordDto> keywordList = new ArrayList<KeywordDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT keyword, address, site_id  \n");
			sql.append("FROM(SELECT site_name AS keyword, site_id, address  \n");
			sql.append("    FROM site \n");
			sql.append("    WHERE approval = 1) \n");
			sql.append("WHERE LOWER(keyword) LIKE LOWER('%'||?||'%') \n");
		    
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, keyword);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				KeywordDto keywordDto = new KeywordDto();
				keywordDto.setKeyword(rs.getString("keyword"));
				keywordDto.setAddress(rs.getString("address"));
				keywordDto.setSite_id(rs.getInt("site_id"));
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
	public int likeSchedule(int route_id, String id) {
//		System.out.println("update DaoImpl >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + route_id);
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.makeConnection();
			conn.setAutoCommit(false);

			String sql="";
			sql += "insert into route_vote(rvote_id, id, route_id) \n";
			sql += "values (rvote_seq.nextval,? ,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, route_id);
			pstmt.executeUpdate();
			pstmt.close();

			String sql3 = "";
			sql3 += "update route set recommend=recommend+1 where route_id=?";
			
			pstmt = conn.prepareStatement(sql3);
			pstmt.setInt(1, route_id);

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
		return cnt;
	}

	@Override
	public int likeCheck(int route_id, String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt=0;

		PlanDto pDto = new PlanDto();
		try {
			conn = DBConnection.makeConnection();
			String sql = "";
			sql += "select rvote_id \n";
			sql += "from route_vote where route_id=? and id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, route_id);
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
	public int dislikeSchedule(int route_id, String id) {
//		System.out.println("update DaoImpl >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + route_id);
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.makeConnection();
			conn.setAutoCommit(false);

			String sql="";
			sql += "delete route_vote"
					+ " where id=? and route_id=?";
			//delete route_vote where id='anonymous' and route_id=10
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, route_id);
			pstmt.executeUpdate();
			pstmt.close();

			String sql3 = "";
			sql3 += "update route set recommend=recommend-1 where route_id=?";
			
			pstmt = conn.prepareStatement(sql3);
			pstmt.setInt(1, route_id);

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
		return cnt;
	}

	@Override
	public int dislikeCheck(int route_id, String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt=0;

		PlanDto pDto = new PlanDto();
		try {
			conn = DBConnection.makeConnection();
			String sql = "";
			sql += "select rvote_id \n";
			sql += "from route_vote where route_id=? and id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, route_id);
			pstmt.setString(2, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
				cnt=0;
			
			} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, conn);
		}
		return cnt;
	}
	

}
