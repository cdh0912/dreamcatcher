package com.dreamcatcher.admin.model.dao;

import java.sql.*;
import java.util.*;

import com.dreamcatcher.admin.model.StatisticsDto;
import com.dreamcatcher.common.model.KeywordDto;
import com.dreamcatcher.util.db.DBClose;
import com.dreamcatcher.util.db.DBConnection;

public class AdminDaoImpl implements AdminDao {
	
	private static AdminDao adminDao;
	
	private AdminDaoImpl(){}
	
	static{
		adminDao = new AdminDaoImpl();
	}
	
	public static AdminDao getInstance(){
		return adminDao;
	}

	// 회원상태 변경
	@Override
	public int memberStateChange(Map memberStateMap) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE member \n");
			sql.append("SET m_state = ? \n");
			sql.append("WHERE id = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, (int) memberStateMap.get("m_state"));
			pstmt.setString(2, (String)memberStateMap.get("id"));

			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			cnt = -1;
		} finally{
			DBClose.close(pstmt, conn);
		}
		return cnt;
	}

	@Override
	public int siteStateChange(Map siteStateMap) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE site \n");
			sql.append("SET approval = ? \n");
			sql.append("WHERE site_id = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, (int) siteStateMap.get("approval"));
			pstmt.setInt(2, (int) siteStateMap.get("site_id"));

			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			cnt = -1;
		} finally{
			DBClose.close(pstmt, conn);
		}
		return cnt;
	}

	@Override
	public List<StatisticsDto> locationRankByRecommend(int range) {
		List<StatisticsDto>  statisticsList = new ArrayList<StatisticsDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT rank, item, count, percentage \n");
			sql.append("FROM(SELECT RANK() OVER(ORDER BY count DESC, loc_name ) rank, count, loc_name item, \n");
			sql.append("	ROUND(ratio_to_report(count) over()*100, 1) percentage \n");
			sql.append("	FROM(SELECT COUNT(recommend) count, loc_name \n");
			sql.append("    	FROM location JOIN site USING(loc_id) JOIN site_detail USING(site_id) \n");
			sql.append("	GROUP BY loc_name)) result \n");
			sql.append("WHERE rank <= ? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, range);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				StatisticsDto statisticsDto = new StatisticsDto();
				statisticsDto.setRank(rs.getInt("rank"));
				statisticsDto.setItem(rs.getString("item"));
				statisticsDto.setCount(rs.getInt("count"));
				statisticsDto.setPercentage(rs.getDouble("percentage"));
				statisticsList.add(statisticsDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBClose.close(rs, pstmt, conn);
		}
		return statisticsList;
	}

	@Override
	public List<StatisticsDto>  siteRankByRecommend(int range) {
		List<StatisticsDto>  statisticsList = new ArrayList<StatisticsDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT rank, item, count, percentage \n");
			sql.append("FROM(SELECT RANK() OVER(ORDER BY count DESC, site_name ) rank, count, site_name item, \n");
			sql.append("	ROUND(ratio_to_report(count) over()*100, 1) percentage \n");
			sql.append("	FROM(SELECT COUNT(recommend) count, site_name \n");
			sql.append("    	FROM site JOIN site_detail USING(site_id) \n");
			sql.append("	GROUP BY site_name)) result \n");
			sql.append("WHERE rank <= ? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, range);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				StatisticsDto statisticsDto = new StatisticsDto();
				statisticsDto.setRank(rs.getInt("rank"));
				statisticsDto.setItem(rs.getString("item"));
				statisticsDto.setCount(rs.getInt("count"));
				statisticsDto.setPercentage(rs.getDouble("percentage"));
				statisticsList.add(statisticsDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBClose.close(rs, pstmt, conn);
		}
		return statisticsList;
	}

	@Override
	public List<StatisticsDto>  siteRankByRoute(int range) {
		List<StatisticsDto>  statisticsList = new ArrayList<StatisticsDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT rank, item, count, percentage \n");
			sql.append("FROM(SELECT RANK() OVER(ORDER BY count DESC, site_name ) rank, count, site_name item, \n");
			sql.append("	ROUND(ratio_to_report(count) over()*100, 1) percentage \n");
			sql.append("	FROM(SELECT COUNT(site_id) count, site_name \n");
			sql.append("    	FROM site JOIN route_detail USING(site_id) \n");
			sql.append("	GROUP BY site_name)) result \n");
			sql.append("WHERE rank <= ? \n");
			     
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, range);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				StatisticsDto statisticsDto = new StatisticsDto();
				statisticsDto.setRank(rs.getInt("rank"));
				statisticsDto.setItem(rs.getString("item"));
				statisticsDto.setCount(rs.getInt("count"));
				statisticsDto.setPercentage(rs.getDouble("percentage"));
				statisticsList.add(statisticsDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBClose.close(rs, pstmt, conn);
		}
		return statisticsList;
	}

}
