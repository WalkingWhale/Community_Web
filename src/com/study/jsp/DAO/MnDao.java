package com.study.jsp.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.naming.*;
import javax.sql.DataSource;

import com.study.jsp.DTO.*;
import com.study.jsp.info.BPageInfo;

public class MnDao {
	DataSource dataSource;
	private static MnDao instance = new MnDao();
	
	int listCount = 10;
	int pageCount = 10;
	
	public MnDao() {
		try {
			Context context= new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static MnDao getInstance() {
		return instance;
	}	
	
	public ArrayList<MnDTO> list(int curPage){
		
		ArrayList<MnDTO> dtos = new ArrayList<MnDTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		
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
					   	   "				from Members ) A "+
					   	   "	where rownum <= ?) B " +
					       "where B.num >= ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, nEnd);
			pstmt.setInt(2, nStart);
			
			String query2 = "";
			String query3 = "";
			
			int nContent = 0;
			int nReply = 0;
						
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				String id = rs.getString("id");
				String pw = rs.getString("pw");
				String name = rs.getString("name");
				String nickname = rs.getString("nickname");
				String email = rs.getString("email");
				Timestamp rDate = rs.getTimestamp("rDate");
				String address = rs.getString("address");
				String ban = rs.getString("ban");
				
				query2 = "select count(*) as total from mvc_board where bUser = ?";
				pstmt2 = con.prepareStatement(query2);
				pstmt2.setString(1, id);				
				
				query3 = "select count(*) as total from board_comment where comment_id = ?";
				pstmt3 = con.prepareStatement(query3);
				pstmt3.setString(1, id);
				
				rs2 = pstmt2.executeQuery() ;	// 쓴 게시글 갯수
				if(rs2.next()) {
					nContent = rs2.getInt("total");
				}
				//System.out.println(nContent);
				rs3 = pstmt3.executeQuery() ;	// 쓴 댓글 갯수
				if(rs3.next()) {
					nReply = rs3.getInt("total");
				}
				//System.out.println(nReply);
				
				MnDTO dto = new MnDTO(id, pw, name, nickname, email, rDate, address, ban, nContent, nReply);			
				
				dtos.add(dto);
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {		
				if(rs != null) rs.close();
				if(rs2 != null) rs2.close();
				if(pstmt != null) pstmt.close();
				if(pstmt2 != null) pstmt2.close();
				if(pstmt3 != null) pstmt3.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return dtos;
	}
	
	public ArrayList<BDto> Articlelist(int curPage, String id){
		
	ArrayList<BDto> dtos = new ArrayList<BDto>();
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
				   	   "				from mvc_board where bUser = ? and btype != ? "+
				       "			order by bGroup desc, bStep asc ) A "+
				   	   "	where rownum <= ? ) B " +
				       "where B.num >= ?";
		pstmt = con.prepareStatement(query);
		pstmt.setString(1, id);
		pstmt.setString(2, "reply");
		pstmt.setInt(3, nEnd);
		pstmt.setInt(4, nStart);
		
					
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
			
			BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bindent, bUser, bType, bLike);
			
			if((diff / (1000 * 60 * 60)) < 1) {
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
	
	public ArrayList<BDto> AllArticlelist(int curPage){
		
		ArrayList<BDto> dtos = new ArrayList<BDto>();
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
					   	   "				from mvc_board where bType != ?"+
					       "			order by bGroup desc, bStep asc ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "notification");
			pstmt.setInt(2, nEnd);
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
				int report = rs.getInt("report");
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bindent, bUser, bType, bLike);
				
				if((diff / (1000 * 60 * 60)) < 1) {
					//System.out.println((diff / (1000 * 60 * 60)) + " 시간 전 작성");
					dto.setbNew(true);
				} 
				
				if(report > 0) {
					dto.setReport(true);
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
	
	public ArrayList<CommentDto> AllCommentlist(int curPage){
		
		ArrayList<CommentDto> dtos = new ArrayList<CommentDto>();
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		
		int nStart = (curPage - 1) * listCount + 1;
		int nEnd = (curPage - 1) * listCount + listCount;
		
		try {			
			con = dataSource.getConnection();
			/*String query = "select * " +
						   "from mvc_board "+
						   "order by bGroup desc, bStep asc";*/
			String query = "select max(level) as max_level, comment_num from board_comment CONNECT BY prior comment_num = comment_parent group by comment_num";
			String query2 = "select * " +
					   	   "	from ( "+
					   	   "		select rownum num, A.* " +
					   	   "			from ( " +
					   	   "				select * " +
					   	   "				from board_comment where comment_num = ? ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";
			String query3 = "select bTitle from mvc_board where bId = ?";
			
			
			pstmt = con.prepareStatement(query);
			
			
						
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
               	
               	pstmt2 = con.prepareStatement(query2);
               	pstmt2.setString(1, rs.getString("comment_num"));
    			pstmt2.setInt(2, nEnd);
    			pstmt2.setInt(3, nStart);
               
               	
               	rs2 = pstmt2.executeQuery();
               	
               	while(rs2.next()) {
               		CommentDto comment = new CommentDto();
    				comment.setComment_level(rs.getInt("max_level"));
    				comment.setComment_num(rs2.getInt("COMMENT_NUM"));
    				comment.setComment_board(rs2.getInt("COMMENT_BOARD"));
    				comment.setComment_id(rs2.getString("COMMENT_ID"));
                   	comment.setComment_date(rs2.getTimestamp("COMMENT_DATE"));
                   	comment.setComment_parent(rs2.getInt("COMMENT_PARENT"));
                   	comment.setComment_content(rs2.getString("COMMENT_CONTENT"));
                   	if(rs2.getInt("REPORT") > 0) {
                   		comment.setReport(true);
                   	}
                   	
                   	pstmt3 = con.prepareStatement(query3);
                	pstmt3.setInt(1, rs2.getInt("COMMENT_BOARD"));
                	
                	rs3 = pstmt3.executeQuery();
                	if(rs3.next()) {
                		comment.setParent_title(rs3.getString("bTitle"));
                	}
                	
                	dtos.add(comment);
               	}       
			}
		} catch(Exception e) {
				e.printStackTrace();
		} finally {
			try {		
				if(rs != null) rs.close();
				if(rs2 != null) rs2.close();
				if(rs3 != null) rs3.close();
				if(pstmt != null) pstmt.close();
				if(pstmt2 != null) pstmt2.close();
				if(pstmt3 != null) pstmt3.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return dtos;
	}
	
	public ArrayList<CommentDto> Commentlist(int curPage, String id){
		
		ArrayList<CommentDto> dtos = new ArrayList<CommentDto>();
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
					   	   "				from board_comment where comment_id = ? ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setInt(3, nEnd);
			pstmt.setInt(4, nStart);
			
						
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CommentDto comment = new CommentDto();
                comment.setComment_level(rs.getInt("LEVEL"));
                comment.setComment_num(rs.getInt("COMMENT_NUM"));
                comment.setComment_board(rs.getInt("COMMENT_BOARD"));
                comment.setComment_id(rs.getString("COMMENT_ID"));
                comment.setComment_date(rs.getTimestamp("COMMENT_DATE"));
                comment.setComment_parent(rs.getInt("COMMENT_PARENT"));
                comment.setComment_content(rs.getString("COMMENT_CONTENT"));
                dtos.add(comment);
					
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
	
	public ArrayList<PeriodAsearchDto> SearchPeriodUser(String pOption) {
		
		ArrayList<PeriodAsearchDto> dtos = new ArrayList<PeriodAsearchDto>();
		PeriodAsearchDto dto = new PeriodAsearchDto();
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String id = "";
		String query = "";
		
		try {			
			con = dataSource.getConnection();
			if(pOption.equals("1day")) {
				query = "select * from ( select count(*) as total, bUser from mvc_board where bdate > sysdate-2 group by bUser order by total desc) where rownum < 4";
			} else if(pOption.equals("1week")) {
				query = "select * from ( select count(*) as total, bUser from mvc_board where bdate > sysdate-8 group by bUser order by total desc) where rownum < 4";
			} else if(pOption.equals("1month")) {
				query = "select * from ( select count(*) as total, bUser from mvc_board where bdate > sysdate-32 group by bUser order by total desc) where rownum < 4";
			} else if(pOption.equals("3month")) {
				query = "select * from ( select count(*) as total, bUser from mvc_board where bdate > sysdate-64 group by bUser order by total desc) where rownum < 4";
			} else if(pOption.equals("6month")) {
				query = "select * from ( select count(*) as total, bUser from mvc_board where bdate > sysdate-187 group by bUser order by total desc) where rownum < 4";
			} 
			
			
			//System.out.println("1차 쿼리 : " + query);
			pstmt = con.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				id = rs.getString("buser");
				//System.out.println("bbbbb : " + id);
				
				String query2 = "select * from members where id = ?";
				pstmt2 = con.prepareStatement(query2);
				pstmt2.setString(1, id);
				
				rs2 = pstmt2.executeQuery();
				
				while(rs2.next()) {
					//System.out.println("cccc : " + rs2.getString("id"));
					
					dto = new PeriodAsearchDto(rs2.getString("id"),rs2.getString("name"),rs2.getString("nickname"),rs2.getString("ban"),rs.getInt("total"));
					
					dtos.add(dto);
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
		
		return dtos;
	}
	
	public ArrayList<PeriodCsearchDto> SearchPeriodUserComment(String pOption) {
		
		ArrayList<PeriodCsearchDto> dtos = new ArrayList<PeriodCsearchDto>();
		PeriodCsearchDto dto = new PeriodCsearchDto();
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String id = "";
		String query = "";
		
		try {			
			con = dataSource.getConnection();
			if(pOption.equals("1day")) {
				query = "select * from ( select count(*) as total, comment_id from board_comment where comment_date > sysdate-2 group by comment_id order by total desc) where rownum < 4";
			} else if(pOption.equals("1week")) {
				query = "select * from ( select count(*) as total, comment_id from board_comment where comment_date > sysdate-8 group by comment_id order by total desc) where rownum < 4";
			} else if(pOption.equals("1month")) {
				query = "select * from ( select count(*) as total, comment_id from board_comment where comment_date > sysdate-32 group by comment_id order by total desc) where rownum < 4";
			} else if(pOption.equals("3month")) {
				query = "select * from ( select count(*) as total, comment_id from board_comment where comment_date > sysdate-64 group by comment_id order by total desc) where rownum < 4";
			} else if(pOption.equals("6month")) {
				query = "select * from ( select count(*) as total, comment_id from board_comment where comment_date > sysdate-187 group by comment_id order by total desc) where rownum < 4";
			} 
			
			
			//System.out.println("1차 쿼리 : " + query);
			pstmt = con.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				id = rs.getString("comment_id");
				//System.out.println("bbbbb : " + id);
				
				String query2 = "select * from members where id = ?";
				pstmt2 = con.prepareStatement(query2);
				pstmt2.setString(1, id);
				
				rs2 = pstmt2.executeQuery();
				
				while(rs2.next()) {
					//System.out.println("cccc : " + rs2.getString("id"));
					
					dto = new PeriodCsearchDto(rs2.getString("id"),rs2.getString("name"),rs2.getString("nickname"),rs2.getString("ban"),rs.getInt("total"));
					
					dtos.add(dto);
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
		
		return dtos;
	}
	
	public MnDTO userView(String userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		
		MnDTO dto = null;
		
		try {			
			con = dataSource.getConnection();
			/*String query = "select * " +
						   "from mvc_board "+
						   "order by bGroup desc, bStep asc";*/
			String query = "select * from members where id = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, userId);
			
			String query2 = "";
			String query3 = "";
			
			int nContent = 0;
			int nReply = 0;
						
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				String id = rs.getString("id");
				String pw = rs.getString("pw");
				String name = rs.getString("name");
				String nickname = rs.getString("nickname");
				String email = rs.getString("email");
				Timestamp rDate = rs.getTimestamp("rDate");
				String address = rs.getString("address");
				String ban = rs.getString("ban");
				
				query2 = "select count(*) as total from mvc_board where bUser = ?";
				pstmt2 = con.prepareStatement(query2);
				pstmt2.setString(1, id);		
				
				query3 = "select count(*) as total from board_comment where comment_id = ?";
				pstmt3 = con.prepareStatement(query3);
				pstmt3.setString(1, id);
				
				rs2 = pstmt2.executeQuery() ;	// 쓴 개시글 갯수
				if(rs2.next()) {
					nContent = rs2.getInt("total");
				}
				//System.out.println(nContent);
				rs3 = pstmt3.executeQuery() ;	// 쓴 댓글 갯수
				if(rs3.next()) {
					nReply = rs3.getInt("total");
				}
				//System.out.println(nReply);
				
				dto = new MnDTO(id, pw, name, nickname, email, rDate, address, ban, nContent, nReply);			
								
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {		
				if(rs != null) rs.close();
				if(rs2 != null) rs2.close();
				if(pstmt != null) pstmt.close();
				if(pstmt2 != null) pstmt2.close();
				if(pstmt3 != null) pstmt3.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}	
	
	public ArrayList<MnDTO> Search(int curPage, String sOption, String search){
		
		ArrayList<MnDTO> dtos = new ArrayList<MnDTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		String query = "";
		String query2 = "";
		String query3 = "";
		String temp = "";
		int nContent = 0;
		int nReply = 0;
		
		int nStart = (curPage - 1) * listCount + 1;
		int nEnd = (curPage - 1) * listCount + listCount;
				
		try {			
			con = dataSource.getConnection();
			/*String query = "select * " +
						   "from mvc_board "+
						   "order by bGroup desc, bStep asc";*/
			
			if(sOption.equals("id")) {
				query = "select * " +
					   	   "	from ( "+
					   	   "		select rownum num, A.* " +
					   	   "			from ( " +
					   	   "				select * " +
					   	   "				from members where id = ? ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";
			} else if(sOption.equals("nickname")) {
				query = "select * " +
					   	   "	from ( "+
					   	   "		select rownum num, A.* " +
					   	   "			from ( " +
					   	   "				select * " +
					   	   "				from members where nickname = ? ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";			
			}
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, search);
			pstmt.setInt(2, nEnd);
			pstmt.setInt(3, nStart);
			
						
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString("id");
				String pw = rs.getString("pw");
				String name = rs.getString("name");
				String nickname = rs.getString("nickname");
				String email = rs.getString("email");
				Timestamp rDate = rs.getTimestamp("rDate");
				String address = rs.getString("address");
				String ban = rs.getString("ban");
				
				query2 = "select count(*) as total from mvc_board where bUser = ?";
				pstmt2 = con.prepareStatement(query2);
				pstmt2.setString(1, id);		
				
				query3 = "select count(*) as total from board_comment where comment_id = ?";
				pstmt3 = con.prepareStatement(query3);
				pstmt3.setString(1, id);
				
				rs2 = pstmt2.executeQuery() ;	// 쓴 개시글 갯수
				if(rs2.next()) {
					nContent = rs2.getInt("total");
				}
				//System.out.println(nContent);
				rs3 = pstmt3.executeQuery() ;	// 쓴 댓글 갯수
				if(rs3.next()) {
					nReply = rs3.getInt("total");
				}
				//System.out.println(nReply);
				
				MnDTO dto = new MnDTO(id, pw, name, nickname, email, rDate, address, ban, nContent, nReply);
				
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
	
	public ArrayList<BDto> SearchArticle(int curPage, String sOption, String search){
		
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
					   	   "				from mvc_board where btitle like ?"+
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
					   	   "				from mvc_board where bname = ?"+
					       "			order by bGroup desc, bStep asc ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";
			} else if(sOption.equals("content")) {
				query = "select * " +
					   	   "	from ( "+
					   	   "		select rownum num, A.* " +
					   	   "			from ( " +
					   	   "				select * " +
					   	   "				from mvc_board where bcontent like ?"+
					       "			order by bGroup desc, bStep asc ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";
				temp = search;
				search = "%"+temp+"%";
			}
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, search);
			pstmt.setInt(2, nEnd);
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
	
	public ArrayList<CommentDto> SearchComment(int curPage, String sOption, String search){
		
		ArrayList<CommentDto> dtos = new ArrayList<CommentDto>();
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		String query = "";
		String query2 = "";
		String query3 = "";
		String temp = "";
		
		int nStart = (curPage - 1) * listCount + 1;
		int nEnd = (curPage - 1) * listCount + listCount;
		
		try {			
			con = dataSource.getConnection();
			/*String query = "select * " +
						   "from mvc_board "+
						   "order by bGroup desc, bStep asc";*/
			query = "select max(level) as max_level, comment_num from board_comment CONNECT BY prior comment_num = comment_parent group by comment_num";
			
			query3 = "select bTitle from mvc_board where bId = ?";
			
			if(sOption.equals("writer")) {
				query2 = "select * " +
					   	   "	from ( "+
					   	   "		select rownum num, A.* " +
					   	   "			from ( " +
					   	   "				select * " +
					   	   "				from board_comment where comment_id = ? and comment_num = ? ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";
			} else if(sOption.equals("content")) {
				query2 = "select * " +
					   	   "	from ( "+
					   	   "		select rownum num, A.* " +
					   	   "			from ( " +
					   	   "				select * " +
					   	   "				from board_comment where comment_content like ? and comment_num = ? ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";
				temp = search;
				search = "%"+temp+"%";
			}
			
			pstmt = con.prepareStatement(query);
			pstmt2 = con.prepareStatement(query2);
			pstmt3 = con.prepareStatement(query3);
			
			
					
						
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				pstmt2.setString(1, search);
				pstmt2.setString(2, rs.getString("comment_num"));
				pstmt2.setInt(3, nEnd);
				pstmt2.setInt(4, nStart);
				
				rs2 = pstmt2.executeQuery();
				
				while(rs2.next()) {
					pstmt3.setString(1, rs2.getString("comment_board"));
					
					rs3 = pstmt3.executeQuery();
					
					CommentDto comment = new CommentDto();
    				comment.setComment_level(rs.getInt("max_level"));
    				comment.setComment_num(rs2.getInt("COMMENT_NUM"));
    				comment.setComment_board(rs2.getInt("COMMENT_BOARD"));
    				comment.setComment_id(rs2.getString("COMMENT_ID"));
                   	comment.setComment_date(rs2.getTimestamp("COMMENT_DATE"));
                   	comment.setComment_parent(rs2.getInt("COMMENT_PARENT"));
                   	comment.setComment_content(rs2.getString("COMMENT_CONTENT"));                   	
                   	
                	pstmt3.setInt(1, rs2.getInt("COMMENT_BOARD"));
                	
                	if(rs3.next()) {
                		comment.setParent_title(rs3.getString("bTitle"));
                	}
                	
                	dtos.add(comment);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {		
				if(rs != null) rs.close();
				if(rs2 != null) rs2.close();
				if(rs3 != null) rs3.close();
				if(pstmt != null) pstmt.close();
				if(pstmt2 != null) pstmt2.close();
				if(pstmt3 != null) pstmt3.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return dtos;
	}
	
	public ArrayList<BDto> SearchPeriod(int curPage, String pOption){
		
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
			
			if(pOption.equals("1day")) {
				query = "select * " +
					   	   "	from ( "+
					   	   "		select rownum num, A.* " +
					   	   "			from ( " +
					   	   "				select * " +
					   	   "				from mvc_board where bDate >= sysdate-2"+
					       "			order by bGroup desc, bStep asc ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";
			} else if(pOption.equals("1week")) {
				query = "select * " +
					   	   "	from ( "+
					   	   "		select rownum num, A.* " +
					   	   "			from ( " +
					   	   "				select * " +
					   	   "				from mvc_board where bDate > sysdate-8"+
					       "			order by bGroup desc, bStep asc ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";
			} else if(pOption.equals("1month")) {
				query = "select * " +
					   	   "	from ( "+
					   	   "		select rownum num, A.* " +
					   	   "			from ( " +
					   	   "				select * " +
					   	   "				from mvc_board where bDate > sysdate-32"+
					       "			order by bGroup desc, bStep asc ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";
			} else if(pOption.equals("3month")) {
				query = "select * " +
					   	   "	from ( "+
					   	   "		select rownum num, A.* " +
					   	   "			from ( " +
					   	   "				select * " +
					   	   "				from mvc_board where bDate > sysdate-94"+
					       "			order by bGroup desc, bStep asc ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";
			} else if(pOption.equals("6month")) {
				System.out.println("aaaa : 187");
				query = "select * " +
					   	   "	from ( "+
					   	   "		select rownum num, A.* " +
					   	   "			from ( " +
					   	   "				select * " +
					   	   "				from mvc_board where bDate > sysdate-187"+
					       "			order by bGroup desc, bStep asc ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";
			}
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, nEnd);
			pstmt.setInt(2, nStart);
			
						
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
	
	public ArrayList<CommentDto> SearchCommentListPeriod(int curPage, String pOption){
		
		ArrayList<CommentDto> dtos = new ArrayList<CommentDto>();
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		String query = "select max(level) as max_level, comment_num from board_comment CONNECT BY prior comment_num = comment_parent group by comment_num";
		String query2 = "";
		String query3 = "select bTitle from mvc_board where bId = ?";
		String temp = "";
		
		int nStart = (curPage - 1) * listCount + 1;
		int nEnd = (curPage - 1) * listCount + listCount;
		
		try {			
			con = dataSource.getConnection();
			/*String query = "select * " +
						   "from mvc_board "+
						   "order by bGroup desc, bStep asc";*/
			
			if(pOption.equals("1day")) {
				query2 = "select * " +
					   	   "	from ( "+
					   	   "		select rownum num, A.* " +
					   	   "			from ( " +
					   	   "				select * " +
					   	   "				from board_comment where comment_date > sysdate-2 and comment_num = ? ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";
			} else if(pOption.equals("1week")) {
				query2 = "select * " +
					   	   "	from ( "+
					   	   "		select rownum num, A.* " +
					   	   "			from ( " +
					   	   "				select * " +
					   	   "				from board_comment where comment_date > sysdate-7 and comment_num = ? ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";
			} else if(pOption.equals("1month")) {
				query2 = "select * " +
					   	   "	from ( "+
					   	   "		select rownum num, A.* " +
					   	   "			from ( " +
					   	   "				select * " +
					   	   "				from board_comment where comment_date > sysdate-32 and comment_num = ? ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";
			} else if(pOption.equals("3month")) {
				query2 = "select * " +
					   	   "	from ( "+
					   	   "		select rownum num, A.* " +
					   	   "			from ( " +
					   	   "				select * " +
					   	   "				from board_comment where comment_date > sysdate-64 and comment_num = ? ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";
			} else if(pOption.equals("6month")) {
				query2 = "select * " +
					   	   "	from ( "+
					   	   "		select rownum num, A.* " +
					   	   "			from ( " +
					   	   "				select * " +
					   	   "				from board_comment where comment_date > sysdate-187 and comment_num = ? ) A "+
					   	   "	where rownum <= ? ) B " +
					       "where B.num >= ?";
			}
			
			pstmt = con.prepareStatement(query);
			pstmt2 = con.prepareStatement(query2);
			pstmt3 = con.prepareStatement(query3);	
			
					
						
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				pstmt2.setString(1, rs.getString("comment_num"));
				pstmt2.setInt(2, nEnd);
				pstmt2.setInt(3, nStart);
				
				rs2 = pstmt2.executeQuery();
				
				while(rs2.next()) {
					pstmt3.setString(1, rs2.getString("comment_board"));
					
					rs3 = pstmt3.executeQuery();
					
					CommentDto comment = new CommentDto();
    				comment.setComment_level(rs.getInt("max_level"));
    				comment.setComment_num(rs2.getInt("COMMENT_NUM"));
    				comment.setComment_board(rs2.getInt("COMMENT_BOARD"));
    				comment.setComment_id(rs2.getString("COMMENT_ID"));
                   	comment.setComment_date(rs2.getTimestamp("COMMENT_DATE"));
                   	comment.setComment_parent(rs2.getInt("COMMENT_PARENT"));
                   	comment.setComment_content(rs2.getString("COMMENT_CONTENT"));                   	
                   	
                	pstmt3.setInt(1, rs2.getInt("COMMENT_BOARD"));
                	
                	if(rs3.next()) {
                		comment.setParent_title(rs3.getString("bTitle"));
                	}
                	
                	dtos.add(comment);
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
		return dtos;
	}
	
	public BDto contentView(String strID) {
		
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
				int bLike = rs.getInt("bLike");
				
				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bindent, bUser, bType, bLike);
											
			}
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
	
	public int ban(String id) {				
		int result = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		String query_update = "";
		//System.out.println("a : " + id);
		try {			
			con = dataSource.getConnection();
			String query = "select ban from members where id = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				//System.out.println("b : " + rs.getString("ban"));
				if(rs.getString("ban").equals("0")) {
					query_update = "update members set ban = ? where id = ?";
					pstmt2 = con.prepareStatement(query_update);
					
					pstmt2.setString(1, "1");
					pstmt2.setString(2, id);
					
					int temp = pstmt2.executeUpdate();
					result = 1;
				} else {
					query_update = "update members set ban = ? where id = ?";
					pstmt2 = con.prepareStatement(query_update);
					
					pstmt2.setString(1, "0");
					pstmt2.setString(2, id);
					
					int temp = pstmt2.executeUpdate();
					result = 2;
				}								
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null)	rs.close();
				if(pstmt != null) pstmt.close();
				if(pstmt2 != null) pstmt2.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
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
			pstmt.setString(7, "normal");
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
	
	public boolean delete(String id) {
		boolean result = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		ResultSet rs = null;
		
		String query1 = "select bId from mvc_board where bUser = ?";		
		String query2 = "delete from mvc_board where bid = ?";
		String query3 = "delete from like_board where bid = ?";
		String query4 = "delete from file_board where bid = ?";
		String query5 = "delete from board_comment where comment_id = ?";
		String query6 = "delete from members where id = ?"; 
		String query7 = "select count(*) as total from members where id = ?";
		
		try {			
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(query1);
			pstmt.setString(1, id);
			pstmt2 = con.prepareStatement(query2);
			pstmt3 = con.prepareStatement(query3);		
			pstmt4 = con.prepareStatement(query4);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				pstmt2.setString(1, rs.getString("bId"));
				pstmt3.setString(1, rs.getString("bId"));
				pstmt4.setString(1, rs.getString("bId"));
				
				int temp1 = pstmt2.executeUpdate();
				int temp2 = pstmt3.executeUpdate();
				int temp3 = pstmt4.executeUpdate();
			}
			
			pstmt = con.prepareStatement(query5);
			pstmt.setString(1, id);
			
			int temp1 = pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(query6);
			pstmt.setString(1, id);
			
			int temp2 = pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(query7);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt("total") == 0) {
					System.out.println("탈퇴처리 완료");
					result = true;
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(pstmt2 != null) pstmt2.close();
				if(pstmt3 != null) pstmt3.close();
				if(pstmt4 != null) pstmt4.close();
				if(con != null) con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public boolean deleteComment(int comment_num) {
		boolean result = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		 
        try {
            con = dataSource.getConnection();
            con.setAutoCommit(false); // 자동 커밋을 false로 한다.
 
            StringBuffer sql = new StringBuffer();
            sql.append("DELETE FROM BOARD_COMMENT");
            sql.append(" WHERE COMMENT_NUM IN");
            sql.append(" (SELECT COMMENT_NUM");
            sql.append(" FROM BOARD_COMMENT");
            sql.append(" START WITH COMMENT_NUM = ?");
            sql.append(" CONNECT BY PRIOR COMMENT_NUM = COMMENT_PARENT) ");
            
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setInt(1, comment_num);
            
            int flag = pstmt.executeUpdate();
            if(flag > 0){
                result = true;
                con.commit(); // 완료시 커밋
            }    
            
        } catch (Exception e) {
            try {
                con.rollback(); // 오류시 롤백
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
            throw new RuntimeException(e.getMessage());
        }	finally {
			try {
				if(pstmt != null) pstmt.close();
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
	
	public BPageInfo ArticlePage(int curPage) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int listCount = 10;
		int pageCount = 10;
		
		// 총 게시물의 갯수
		int totalCount = 0;
		
		try {			
			con = dataSource.getConnection();
			
			String query = "select count(*) as total from mvc_board";
			pstmt = con.prepareStatement(query);
			
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
	
	public BPageInfo CommentPage(int curPage) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int listCount = 10;
		int pageCount = 10;
		
		// 총 게시물의 갯수
		int totalCount = 0;
		
		try {			
			con = dataSource.getConnection();
			
			String query = "select count(*) as total from board_comment";
			pstmt = con.prepareStatement(query);
			
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
	
	public BPageInfo userPage(int curPage) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int listCount = 10;
		int pageCount = 10;
		
		// 총 게시물의 갯수
		int totalCount = 0;
		
		try {			
			con = dataSource.getConnection();
			
			String query = "select count(*) as total from members";
			pstmt = con.prepareStatement(query);
			
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
	
	public BPageInfo userArticle(int curPage, String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int listCount = 10;
		int pageCount = 10;
		
		// 총 게시물의 갯수
		int totalCount = 0;
		
		try {			
			con = dataSource.getConnection();
			
			String query = "select count(*) as total from mvc_board where bUser = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			
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
	
	public BPageInfo userComment(int curPage, String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int listCount = 10;
		int pageCount = 10;
		
		// 총 게시물의 갯수
		int totalCount = 0;
		
		try {			
			con = dataSource.getConnection();
			
			String query = "select count(*) as total from board_comment where comment_user = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			
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
	
	public BPageInfo SearchPageArticle(int curPage, String sOption, String search) {
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
				query = "select count(*) as total from mvc_board where btitle like ?";
				temp = search;
				search = "%"+temp+"%";
			} else if(sOption.equals("writer")) {
				query = "select count(*) as total from mvc_board where bname = ?";
			} else if(sOption.equals("content")) {
				query = "select count(*) as total from mvc_board where bcontent like ?";
				temp = search;
				search = "%"+temp+"%";
			}
			
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, search);
			
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
	
	public BPageInfo SearchPageComment(int curPage, String sOption, String search) {
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
			
			if(sOption.equals("writer")) {
				query = "select count(*) as total from board_comment where comment_id = ?";
			} else if(sOption.equals("content")) {
				query = "select count(*) as total from board_comment where comment_content like ?";
				temp = search;
				search = "%"+temp+"%";
			}
			
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, search);
			
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
			
			if(sOption.equals("nickname")) {
				query = "select count(*) as total from members where nickname = ?";
			} else if(sOption.equals("id")) {
				query = "select count(*) as total from members where id = ?";
			}
			
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, search);
			
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
	
	public BPageInfo SearchPagePeriod(int curPage, String pOption) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int listCount = 10;
		int pageCount = 10;
		
		// 총 게시물의 갯수
		int totalCount = 0;
		
		String query = "";
		System.out.println(pOption);
		
		try {			
			con = dataSource.getConnection();
			
			if(pOption.equals("1day")) {
				query = "select count(*) as total from mvc_board where bDate > sysdate-2";
			} else if(pOption.equals("1week")) {
				query = "select count(*) as total from mvc_board where bDate > sysdate-8";
			} else if(pOption.equals("1month")) {
				query = "select count(*) as total from mvc_board where bDate > sysdate-32";
			} else if(pOption.equals("3month")) {
				query = "select count(*) as total from mvc_board where bDate > sysdate-94";
			} else if(pOption.equals("6month")) {
				query = "select count(*) as total from mvc_board where bDate > sysdate-187";
			}
			
			
			pstmt = con.prepareStatement(query);
			
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
	
	public BPageInfo SearchCommentPeriod(int curPage, String pOption) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int listCount = 10;
		int pageCount = 10;
		
		// 총 게시물의 갯수
		int totalCount = 0;
		
		String query = "";
		
		try {			
			con = dataSource.getConnection();
			
			if(pOption.equals("1day")) {
				query = "select count(*) as total from board_comment where comment_date > sysdate-2";
			} else if(pOption.equals("1week")) {
				query = "select count(*) as total from board_comment where comment_date > sysdate-8";
			} else if(pOption.equals("1month")) {
				query = "select count(*) as total from board_comment where comment_date > sysdate-32";
			} else if(pOption.equals("3month")) {
				query = "select count(*) as total from board_comment where comment_date > sysdate-94";
			} else if(pOption.equals("6month")) {
				query = "select count(*) as total from board_comment where comment_date > sysdate-187";
			}
			
			
			pstmt = con.prepareStatement(query);
			
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
