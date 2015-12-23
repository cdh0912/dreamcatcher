package com.dreamcatcher.member.model.dao;

import java.sql.*;
import java.util.Map;

import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.util.db.DBClose;
import com.dreamcatcher.util.db.DBConnection;

public class MemberDaoImpl implements MemberDao {

	private static MemberDao memberDao;

	private MemberDaoImpl() {
	}

	static {
		memberDao = new MemberDaoImpl();
	}

	public static MemberDao getInstance() {
		return memberDao;
	}

	// ȸ������ �� ��й�ȣ ������ ���� ���̵� �ߺ� �˻�
	@Override
	public int idCheck(String id) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(id) FROM member \n");
			sql.append("WHERE id = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			cnt = -1;
		} finally{
			DBClose.close(rs, pstmt, conn);
		}
		return cnt;
	}

	// �ű� ȸ������
	@Override
	public int join(MemberDto memberDto) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO member(id, password, name, m_level, m_state) \n");
			sql.append("VALUES(?, ?, ?, ?, ?)");	
			
			pstmt = conn.prepareStatement(sql.toString());
			int index = 0;
			pstmt.setString(++index, memberDto.getId());
			pstmt.setString(++index, memberDto.getPassword());
			pstmt.setString(++index, memberDto.getName());
			pstmt.setInt(++index, memberDto.getM_level());
			pstmt.setInt(++index, memberDto.getM_state());
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			cnt = -1;
		} finally{
			DBClose.close(pstmt, conn);
		}
		return cnt;
	}

	// �α���
	@Override
	public MemberDto login(Map<String, String> validMap) {
		MemberDto memberDto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT id, name, m_level, m_state \n");
			sql.append("FROM member \n");
			sql.append("WHERE id = ? AND password = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, validMap.get("id"));
			pstmt.setString(2, validMap.get("password"));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				memberDto = new MemberDto();
				memberDto.setId(rs.getString("id"));
				memberDto.setName(rs.getString("name"));
				memberDto.setM_level(rs.getInt("m_level"));
				memberDto.setM_state(rs.getInt("m_state"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBClose.close(rs, pstmt, conn);
		}
		return memberDto;
	}

	// ȸ������ ����
	@Override
	public int modifyInfo(MemberDto memberDto) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE member \n");
			sql.append("SET name = ?, password = ? \n");
			sql.append("WHERE id = ?");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, memberDto.getName());
			pstmt.setString(2, memberDto.getPassword());
			pstmt.setString(3, memberDto.getId());
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			cnt = -1;
		} finally{
			DBClose.close(pstmt, conn);
		}
		return cnt;
	}

	// ��й�ȣ ���� �� ȸ������ ����
	@Override
	public int resetPassword(MemberDto memberDto) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE member \n");
			sql.append("SET password = ?, m_state = ? \n");
			sql.append("WHERE id = ?");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, memberDto.getPassword());
			pstmt.setInt(2, memberDto.getM_state());
			pstmt.setString(3, memberDto.getId());
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			cnt = -1;
		} finally{
			DBClose.close(pstmt, conn);
		}
		return cnt;
	}

	// ȸ������
	@Override
	public MemberDto getMemberInfo(String id) {
		MemberDto memberDto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT id, name, m_level, m_state \n");
			sql.append("FROM member \n");
			sql.append("WHERE id = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				memberDto = new MemberDto();
				memberDto.setId(rs.getString("id"));
				memberDto.setName(rs.getString("name"));
				memberDto.setM_level(rs.getInt("m_level"));
				memberDto.setM_state(rs.getInt("m_state"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBClose.close(rs, pstmt, conn);
		}
		return memberDto;
	}

	// �н����� �˻�
	@Override
	public int passwordCheck(Map<String, String> validMap) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(id) \n");
			sql.append("FROM member \n");
			sql.append("WHERE id = ? AND password = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, validMap.get("id"));
			pstmt.setString(2, validMap.get("password"));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			cnt = -1;
		} finally{
			DBClose.close(rs, pstmt, conn);
		}
		return cnt;
	}

}
