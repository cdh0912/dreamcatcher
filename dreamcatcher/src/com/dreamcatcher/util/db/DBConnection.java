package com.dreamcatcher.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.*;
import javax.sql.DataSource;

public class DBConnection {
//	static{
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static Connection makeConnection() throws SQLException{
//		return  DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.100:1521:orcl","kitri","kitri");
//	}
	
	public static Connection makeConnection() throws SQLException {
		try {
			Context context = new InitialContext();
			Context rootContext = (Context) context.lookup("java:comp/env");
			DataSource ds = (DataSource) rootContext.lookup("jdbc/dreamcatcher");
			return ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
}
