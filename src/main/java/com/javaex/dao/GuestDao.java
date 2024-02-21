package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestVo;

public class GuestDao {
	
	// 필드
		private Connection conn = null;
		private PreparedStatement pstmt = null;
		private ResultSet rs = null;
		private String driver = "com.mysql.cj.jdbc.Driver";
		private String url = "jdbc:mysql://localhost:3306/web_db";
		private String id = "web";
		private String pw = "web";
		
		// 연결
		public void getConnection() {
			try {
				// 1. JDBC 드라이버 (Oracle) 로딩
				Class.forName(driver);

				// 2. Connection 얻어오기
				conn = DriverManager.getConnection(url, id, pw);

			} catch (ClassNotFoundException e) {
				System.out.println("error: 드라이버 로딩 실패 - " + e);

			} catch (SQLException e) {
				System.out.println("error:" + e);

			}
		}

		// 종료
		public void close() {
			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}

	public int insertGuestbook(GuestVo guestVo) {
		
		int count = -1;
		
		this.getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = " insert into guestbook "
					+ " values(null, ?, ?, ?, now()) ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, guestVo.getName());
			pstmt.setString(2, guestVo.getPw());
			pstmt.setString(3, guestVo.getContent());

			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 등록되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		this.close();
		
		return count;
	}//
	
	public List<GuestVo> guestSelect() {
		
		List<GuestVo> guestList = new ArrayList<GuestVo>();
		
		this.getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = " select  no,"
						+ "		     name,"
						+ "          reg_date,"
						+ "          content"
						+ " from guestbook ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			
			rs = pstmt.executeQuery();

			// 4.결과처리
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String rd = rs.getString("reg_date");
				String content = rs.getString("content");
				
				GuestVo guestVo = new GuestVo(no, name, rd, content);
				
				guestList.add(guestVo);
				
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		this.close();
		
		return guestList;
	}//
	
	public int guestDelete(GuestVo guestVo) {
		
		int count = -1;
		
		this.getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = " delete from guestbook "
							+ "where no = ? "
							+ "and password = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, guestVo.getNo());
			pstmt.setString(2, guestVo.getPw());

			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		this.close();
		
		return count;
	}
	
}
