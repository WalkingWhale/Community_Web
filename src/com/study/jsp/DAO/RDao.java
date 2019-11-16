package com.study.jsp.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.naming.*;
import javax.sql.DataSource;

import com.study.jsp.DTO.*;
import com.study.jsp.info.BPageInfo;

public class RDao {
	DataSource dataSource;
	private static RDao instance = new RDao();
	
	int listCount = 10;
	int pageCount = 10;
	
	public RDao() {
		try {
			Context context= new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static RDao getInstance() {
		return instance;
	}
	
	public void write(RDto dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {			
			con = dataSource.getConnection();
			String query = "insert into mvc_board" +
						   "(bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent, bType, bUser, bLike, fileName) "+
						   "values " +
						   "(mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0, ?, ?, 0, ?)";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, dto.getbName());
			pstmt.setString(2, dto.getbTitle());
			pstmt.setString(3, dto.getbContent());
			pstmt.setString(4, "data");
			pstmt.setString(5, dto.getbUser());
			pstmt.setString(6, dto.getFileName());
			
			int rm = pstmt.executeUpdate();
			
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
	}
	
	public void FileDB(String fileName_ori,String fileName_sys, String bId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {			
			con = dataSource.getConnection();
			String query = "insert into file_board " +
						   "values " +
						   "(?, ?, ?)";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, fileName_ori);
			pstmt.setString(2, fileName_sys);
			pstmt.setString(3, bId);
			
			int rm = pstmt.executeUpdate();
			
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
	}
	
	public ArrayList<RDto> list(int curPage){
		
		ArrayList<RDto> dtos = new ArrayList<RDto>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int nStart = (curPage - 1) * listCount + 1;
		int nEnd = (curPage - 1) * listCount + listCount;
		
		try {			
			con = dataSource.getConnection();
			/*String query = "select * " +
						   "from mvc_board "+
						   "order by bGroup desc, bStep asc";*/
			String query = "select * " +
					   	   "	from ( "+
					   	   "		select rownum num, A.* " +
					   	   "			from ( " +
					   	   "				select * " +
					   	   "				from mvc_board "+
					       "			order by bGroup desc, bStep asc ) A "+
					   	   "	where rownum <= ? and btype = ?) B " +
					       "where B.num >= ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, nEnd);
			pstmt.setString(2, "data");
			pstmt.setInt(3, nStart);
			
						
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd kk:mm:ss");
				Calendar cal = Calendar.getInstance();
				String today = null;
				today = formatter.format(cal.getTime());
				Timestamp ts = Timestamp.valueOf(today);				
				long diff = ts.getTime() - bDate.getTime();
				
				
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bindent = rs.getInt("bindent");				
				String bUser = rs.getString("bUser");
				String bType = rs.getString("bType");
				int bLike = rs.getInt("bLike");
				String fileName = rs.getString("fileName");
				
				RDto dto = new RDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bindent, bUser, bType, bLike, fileName);
				
				if((diff / (1000 * 60 * 60)) < 1) {
					System.out.println((diff / (1000 * 60 * 60)) + " 시간 전 작성");
					dto.setbNew(true);
				} 
				dtos.add(dto);
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {		
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return dtos;
	}
	
	public ArrayList<RDto> start(int curPage){
		
		ArrayList<RDto> dtos = new ArrayList<RDto>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int nStart = (curPage - 1) * 5 + 1;
		int nEnd = (curPage - 1) * 5 + 5;
		
		try {			
			con = dataSource.getConnection();
			/*String query = "select * " +
						   "from mvc_board "+
						   "order by bGroup desc, bStep asc";*/
			String query = "select * " +
					   	   "	from ( "+
					   	   "		select rownum num, A.* " +
					   	   "			from ( " +
					   	   "				select * " +
					   	   "				from mvc_board "+
					       "			order by bGroup desc, bStep asc ) A "+
					   	   "	where rownum <= ? and btype = ?) B " +
					       "where B.num >= ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, nEnd);
			pstmt.setString(2, "data");
			pstmt.setInt(3, nStart);
			
						
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd kk:mm:ss");
				Calendar cal = Calendar.getInstance();
				String today = null;
				today = formatter.format(cal.getTime());
				Timestamp ts = Timestamp.valueOf(today);				
				long diff = ts.getTime() - bDate.getTime();
				
				
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bindent = rs.getInt("bindent");				
				String bUser = rs.getString("bUser");
				String bType = rs.getString("bType");
				int bLike = rs.getInt("bLike");
				String fileName = rs.getString("fileName");
				
				RDto dto = new RDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bindent, bUser, bType, bLike, fileName);
				
				if((diff / (1000 * 60 * 60)) < 1) {
					System.out.println((diff / (1000 * 60 * 60)) + " 시간 전 작성");
					dto.setbNew(true);
				} 
				dtos.add(dto);
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {		
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return dtos;
	}
	
	public ArrayList<BDto> Search(int curPage, String sOption, String search){
		
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		String temp = "";
		
		int nStart = (curPage - 1) * listCount + 1;
		int nEnd = (curPage - 1) * listCount + listCount;
		
		try {			
			con = dataSource.getConnection();
			/*String query = "select * " +
						   "from mvc_board "+
						   "order by bGroup desc, bStep asc";*/
			
			if(sOption.equals("title")) {
				query = "select * " +
					   	   "	from ( "+
					   	   "		select rownum num, A.* " +
					   	   "			from ( " +
					   	   "				select * " +
					   	   "				from mvc_board where btype = ? and btitle like ?"+
					       "			order by bGroup desc, bStep asc ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";
				temp = search;
				search = "%"+temp+"%";
			} else if(sOption.equals("writer")) {
				query = "select * " +
					   	   "	from ( "+
					   	   "		select rownum num, A.* " +
					   	   "			from ( " +
					   	   "				select * " +
					   	   "				from mvc_board where btype = ? and bname = ?"+
					       "			order by bGroup desc, bStep asc ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";
			} else if(sOption.equals("content")) {
				query = "select * " +
					   	   "	from ( "+
					   	   "		select rownum num, A.* " +
					   	   "			from ( " +
					   	   "				select * " +
					   	   "				from mvc_board where btype = ? and bcontent like ?"+
					       "			order by bGroup desc, bStep asc ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";
				temp = search;
				search = "%"+temp+"%";
			}
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "data");
			pstmt.setString(2, search);
			pstmt.setInt(3, nEnd);
			pstmt.setInt(4, nStart);
			
						
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
				Calendar cal = Calendar.getInstance();
				String today = null;
				today = formatter.format(cal.getTime());
				Timestamp ts = Timestamp.valueOf(today);				
				long diff = ts.getTime() - bDate.getTime();
				
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bindent = rs.getInt("bindent");				
				String bUser = rs.getString("bUser");
				String bType = rs.getString("bType");
				
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bindent, bUser, bType);
				
				if((diff /(1000 * 60 * 60)) < 1) {
					//System.out.println((diff / (1000 * 60 * 60)) + " 시간 전 작성");
					dto.setbNew(true);
				}
				
				dtos.add(dto);
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {		
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return dtos;
	}
	
	public RDto contentView(String strID) {
		upHit(strID);
		
		RDto dto = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		String fileName_ori ="";
		try {			
			con = dataSource.getConnection();
			String query = "select * from mvc_board where bId = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(strID));
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bindent = rs.getInt("bindent");
				String bUser = rs.getString("bUser");
				String bType = rs.getString("bType");
				int bLike = rs.getInt("bLike");
				String fileName =rs.getString("fileName");
				String query2 = "select filename_ori from file_board where filename_sys = ?";
				pstmt2 = con.prepareStatement(query2);
				pstmt2.setString(1, rs.getString("fileName"));
				
				rs2 = pstmt2.executeQuery();
				
				if(rs2.next()) {
					fileName_ori = rs2.getString("filename_ori");
				}
				dto = new RDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bindent, bUser, bType, bLike, fileName_ori);
											
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {				
				if(pstmt != null) pstmt.close();
				if(pstmt2 != null) pstmt2.close();
				if(rs != null) rs.close();
				if(rs2 != null) rs2.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return dto;
	}
	
	public void modify(String bId, String bName, String bTitle, String bContent) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String query = "update mvc_board" +
					   " set bName = ?, bTitle = ?, bContent = ? where bId = ?";		
		try {			
			con = dataSource.getConnection();
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setString(4, bId);
			
			int rm = pstmt.executeUpdate();
			
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
	}
	
	public boolean modifyCheck(String bId, String userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean flag = false;
		
		String query = "select bUser from mvc_board where bId = ?";
		
		try {			
			con = dataSource.getConnection();
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(userId.equals(rs.getString("bUser"))) {
					System.out.println("글 작성자 동일");
					flag = true;
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return flag;
	}
	
	public BDto reply_view(String strID) {
				
		BDto dto = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {			
			con = dataSource.getConnection();
			String query = "select * from mvc_board where bId = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(strID));
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bindent = rs.getInt("bindent");
				String bUser = rs.getString("bUser");
				String bType = rs.getString("bType");
				
				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bindent, bUser, bType);
											
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null)	rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return dto;
	}
	
	public void reply(String bId, String bName, String bTitle, String bContent, String bGroup, String bStep, String bIndent, String bUser) {

		replyShape(bGroup, bStep);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String query = "insert into mvc_board" +
				   	   "(bId, bName, bTitle, bContent, bGroup, bStep, bIndent, bType, bUser) "+
				       "values " +
				       "(mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?)";		
		try {			
			con = dataSource.getConnection();
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bGroup));
			pstmt.setInt(5, Integer.parseInt(bStep) + 1);
			pstmt.setInt(6, Integer.parseInt(bIndent) + 1);
			pstmt.setString(7, "data");
			pstmt.setString(8, bUser);
			
			
			int rm = pstmt.executeUpdate();
			
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
	}
	
	public void replyShape(String strGroup, String strStep) {		
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String query = "update mvc_board" +
					   " set bStep = bStep + 1 where bGroup = ? and bStep > ?";		
		try {			
			con = dataSource.getConnection();
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(strGroup));
			pstmt.setInt(2, Integer.parseInt(strStep));
			
			int rm = pstmt.executeUpdate();
			
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
	}
	
	public boolean checkUser(String bId, String userId) {
		Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		boolean flag = false;
		
		String query_1 = "select bUser from mvc_board where bId = ?";
		String query_2 = "select admin from members where id = ?";
		
		try {			
			con = dataSource.getConnection();
			
			pstmt1 = con.prepareStatement(query_1);
			pstmt1.setString(1, bId);
			
			rs1 = pstmt1.executeQuery();
			
			pstmt2 = con.prepareStatement(query_2);
			pstmt2.setString(1, userId);
			
			rs2 = pstmt2.executeQuery();
			
			if(rs1.next()) {
				if(userId.equals(rs1.getString("bUser"))) {
					System.out.println("글 작성자 동일");
					flag = true;
				}
			}
			
			if(rs2.next()) {
				if(rs2.getString("admin").equals("1")) {
					System.out.println("관리자 승인");
					flag = true;
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs1 != null) rs1.close();
				if(rs2 != null) rs2.close();
				if(pstmt2 != null) pstmt2.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return flag;
	}
	
	public boolean WriterCheck(String bId, String userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean flag = false;
		
		String query = "select bUser from mvc_board where bId = ?";
		
		try {			
			con = dataSource.getConnection();
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(userId.equals(rs.getString("bUser"))) {
					System.out.println("글 작성자 동일");
					flag = true;
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return flag;
	}
	
	public void delete(String bId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		
		String query = "delete from mvc_board where bid = ?";
		String query2 = "delete from like_board where bid = ?";
		String query3 = "delete from file_board where bid = ?";
		
		try {			
			con = dataSource.getConnection();
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bId);
			
			pstmt2 = con.prepareStatement(query2);
			pstmt2.setString(1, bId);
			
			pstmt3 = con.prepareStatement(query3);
			pstmt3.setString(1, bId);
			
			int rm = pstmt.executeUpdate();
			int rm2 = pstmt2.executeUpdate();
			int rm3 = pstmt3.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {				
				if(pstmt != null) pstmt.close();
				if(pstmt2 != null) pstmt2.close();
				if(pstmt3 != null) pstmt3.close();				
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void upHit(String bId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String query = "update mvc_board set bHit = bHit + 1 where bid = ?";
		
		try {			
			con = dataSource.getConnection();
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bId);
			
			int rm = pstmt.executeUpdate();
			
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
	}
	
	public int upLike(String bId, String bUser) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;	
		PreparedStatement pstmt3 = null;
		int result = 0;
		
		
		if(LikeCheck(bId, bUser)) {
			return 2;
		}
		
		String query = "update mvc_board set bLike = bLike + 1 where bid = ?";
		String query2 = "insert into like_board values(?, ?)";
		String query3 = "update mvc_board set bHit = bHit - 1 where bid = ?";
		try {			
			con = dataSource.getConnection();
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bId);
			
			pstmt2 = con.prepareStatement(query2);
			pstmt2.setString(1, bId);
			pstmt2.setString(2, bUser);
			
			pstmt3 = con.prepareStatement(query3);
			pstmt3.setString(1, bId);
			
			int rm = pstmt.executeUpdate();
			int rm2 = pstmt2.executeUpdate();
			int rm3 = pstmt3.executeUpdate();
			
			result = 1;
					
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {				
				if(pstmt != null) pstmt.close();
				if(pstmt2 != null) pstmt2.close();
				if(pstmt3 != null) pstmt3.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public boolean report(String bId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;	
		boolean result = false;
		
		String query = "update mvc_board set report = report + 1 where bid = ?";
		String query2 = "update mvc_board set bHit = bHit - 1 where bid = ?";
		try {			
			con = dataSource.getConnection();
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bId);
			
			pstmt2 = con.prepareStatement(query2);
			pstmt2.setString(1, bId);
			
			int rm = pstmt.executeUpdate();
			int rm2 = pstmt2.executeUpdate();
			
			result = true;
					
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {				
				if(pstmt != null) pstmt.close();
				if(pstmt2 != null) pstmt2.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public boolean LikeCheck(String bId, String bUser) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		boolean flag = false;
		String query = "select bUser from like_board where bid = ?";
		
		try {			
			con = dataSource.getConnection();
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(rs.getString("bUser").equals(bUser)) {
					flag = true;
				}
			}
					
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {				
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return flag;
	}
	
	public String getSysname(int bId) {
		String Sysname = "";
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		String query = "select fileName from mvc_board where bId = ?";
		
		try {			
			con = dataSource.getConnection();
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, bId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Sysname = rs.getString("fileName");
			}
					
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {				
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return Sysname;
	}
	
	public String getOriname(String sysName) {
		String Oriname = "";
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		String query = "select fileName_ori from file_board where fileName_sys = ?";
		
		try {			
			con = dataSource.getConnection();
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, sysName);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Oriname = rs.getString("filename_ori");
			}
					
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {				
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return Oriname;
	}
	
	public BPageInfo articlePage(int curPage, String bType) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int listCount = 10;
		int pageCount = 10;
		
		// 총 게시물의 갯수
		int totalCount = 0;
		
		try {			
			con = dataSource.getConnection();
			
			String query = "select count(*) as total from mvc_board where bType = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bType);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				totalCount = rs.getInt("total");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {			
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		int totalPage = totalCount / listCount;
		if((totalCount % listCount) > 0) {
			totalPage ++;
		}
		
		int myCurPage = curPage;
		if(myCurPage > totalPage) {
			myCurPage = totalPage;
		}
		if(myCurPage < 1) {
			myCurPage = 1;
		}
		
		// 시작 페이지
		int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;
		
		// 끝 페이지
		int endPage = startPage + pageCount - 1;
		if(endPage > totalPage) {
			endPage = totalPage;
		}
		
		BPageInfo pinfo = new BPageInfo();
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(curPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		return pinfo;
	}
	
	public BPageInfo SearchPage(int curPage, String sOption, String search) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int listCount = 10;
		int pageCount = 10;
		
		// 총 게시물의 갯수
		int totalCount = 0;
		
		String query = "";
		String temp;
		
		try {			
			con = dataSource.getConnection();
			
			if(sOption.equals("title")) {
				query = "select count(*) as total from mvc_board where btype = ? and btitle like ?";
				temp = search;
				search = "%"+temp+"%";
			} else if(sOption.equals("writer")) {
				query = "select count(*) as total from mvc_board where btype = ? and bname = ?";
			} else if(sOption.equals("content")) {
				query = "select count(*) as total from mvc_board where btype = ? and bcontent like ?";
				temp = search;
				search = "%"+temp+"%";
			}
			
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "data");
			pstmt.setString(2, search);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				totalCount = rs.getInt("total");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {			
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		int totalPage = totalCount / listCount;
		if((totalCount % listCount) > 0) {
			totalPage ++;
		}
		
		int myCurPage = curPage;
		if(myCurPage > totalPage) {
			myCurPage = totalPage;
		}
		if(myCurPage < 1) {
			myCurPage = 1;
		}
		
		// 시작 페이지
		int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;
		
		// 끝 페이지
		int endPage = startPage + pageCount - 1;
		if(endPage > totalPage) {
			endPage = totalPage;
		}
		
		BPageInfo pinfo = new BPageInfo();
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(curPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		
		return pinfo;
	}
}
