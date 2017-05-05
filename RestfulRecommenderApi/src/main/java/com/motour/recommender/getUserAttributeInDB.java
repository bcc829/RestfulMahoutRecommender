package com.motour.recommender;
import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;

public class getUserAttributeInDB {
	
public static ArrayList returnPreferProperty (int User_Id){
		
		Connection conn;
		Statement stmt=null;
		ArrayList PropertyList = new ArrayList();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.err.print("ClassNotFoundException");
		}
			
		try {
			String jdbcUrl = "jdbcmysqllocalhost3306tourofall";
			String userId = "root";
			String userPass = "465651";

			conn = DriverManager.getConnection(jdbcUrl, userId, userPass);
			stmt = conn.createStatement();
			
			String sql = "select Property_Id from user_prefer_property where User_Id =  + User_Id";
			
			ResultSet rs= stmt.executeQuery(sql);
			while(rs.next()){
				PropertyList.add(rs.getString("Property_Id"));
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException  + e.getMessage()");
			}
		return PropertyList;
		}
}
