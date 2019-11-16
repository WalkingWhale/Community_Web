package com.study.jsp.DAO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.sql.*;
import com.study.jsp.DTO.*;

public class MemberDAO {
	public static final int MEMBER_NONEXISTENT = 0;
	public static final int MEMBER_EXISTENT = 1;
	public static final int MEMBER_JOIN_FAIL = 0;
	public static final int MEMBER_JOIN_SUCCESS = 1;
	public static final int MEMBER_LOGIN_PW_NO_GOOD = 0;
	public static final int MEMBER_LOGIN_SUCCESS = 1;
	public static final int MEMBER_LOGIN_IS_NOT = -1;
	public static final int MANAGER_LOGIN_SUCCESS = 5;
	public static final int MEMBER_LOGIN_BANNED = -2;
	
	private static MemberDAO instance = new MemberDAO();
	
	private MemberDAO() {		
	}
	
	public static MemberDAO getInstance() {
		return instance;
	}
	
	private Connection getConnection() {
		Context context = null;
		DataSource dataSource =null;
		Connection con = null;
		
		try {
			context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
			con = dataSource.getConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	public int memberInsert(MemberDTO dto){		
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String query = "insert into members (id, pw, name, email, rdate, address, nickname) values(?, ?, ?, ?, ?, ?, ?)";
		int result = 0;
		
		try {
			
			con = getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPw());
			pstmt.setString(3, dto.getName());
			pstmt.setString(4, dto.getEmail());
			pstmt.setTimestamp(5, dto.getrDate());
			pstmt.setString(6, dto.getAddress());
			pstmt.setString(7, dto.getNickname());
			pstmt.executeUpdate();
			result = MemberDAO.MEMBER_JOIN_SUCCESS;			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int confirmId(String id){
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select id from members where id = ?";
		int result = 0;
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = MemberDAO.MEMBER_EXISTENT;
			} else {
				result = MemberDAO.MEMBER_NONEXISTENT;
			}					
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean snsUserCheck(String id) {
		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select count(*) as num from members where id = ?";
				
		try {
			con = getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();	
			
			if(rs.next()) {				
				if(rs.getInt("num") >= 1) {
					System.out.println("dddddd");
					flag = true;
				}				
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	public boolean snsUserBan(String id) {
		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select ban from members where id = ?";		
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();	
			
			if(rs.next()) {				
				if(rs.getString("ban").equals("1")) {
					flag = true;
				}				
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	public int userCheck(String id, String pw){
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select pw, admin, ban from members where id = ?";
		int result = 0;
		String dbPw;
		
//		System.out.println("aa:"+ id +":"+ pw);
		try {
			con = getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

//			System.out.println("bb:");
			
			if(rs.next()) {
//				System.out.println(rs.getString("pw"));
				dbPw = rs.getString("pw");
				if(dbPw.equals(pw)) {
					if(rs.getString("ban").equals("1")) {
						result = MemberDAO.MEMBER_LOGIN_BANNED;
					} else {
						System.out.println("Login ok");
						if(rs.getString("admin").equals("1")) {
							result = MemberDAO.MANAGER_LOGIN_SUCCESS;	// 관리자 로그인 ok
						} else {
							result = MemberDAO.MEMBER_LOGIN_SUCCESS;	// 일반 회원 로그인 ok
						}
					}					
					
				} else {
					System.out.println("Login fail");
					result = MemberDAO.MEMBER_LOGIN_PW_NO_GOOD;	// 비밀번호 x
				}
				
			} else {
				System.out.println("Login fail");
				result = MemberDAO.MEMBER_LOGIN_IS_NOT;		// 아이디 x
			}					
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public MemberDTO getMember(String id){		
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select * from members where id = ?";
		MemberDTO dto = null;
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setPw(rs.getString("pw"));
				dto.setName(rs.getString("name"));
				dto.setNickname(rs.getString("nickname"));
				dto.setEmail(rs.getString("email"));
				dto.setrDate(rs.getTimestamp("rDate"));
				dto.setAddress(rs.getString("address"));
				
			} 			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}
	
	public int updateMember(MemberDTO dto){		
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String query = "update members set pw = ?, nickname = ?, email = ?, address = ? where id = ?";
		int result = 0;
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, dto.getPw());
			pstmt.setString(2, dto.getNickname());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getAddress());
			pstmt.setString(5, dto.getId());
			result = pstmt.executeUpdate();				
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				
				pstmt.close();
				con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int withdrawMember(String id) {
		Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		int result = 1;
		String query_1 = "delete from members where id = ?";
		String query_2 = "select count(*) from members where id = ?";
		
		try {
			con = getConnection();
			pstmt1 = con.prepareStatement(query_1);
			pstmt1.setString(1, id);
			int temp = pstmt1.executeUpdate();
			
			pstmt2 = con.prepareStatement(query_2);
			pstmt2.setString(1, id);
			result = pstmt2.executeUpdate();
						
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				
				pstmt1.close();
				pstmt2.close();
				con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
}
