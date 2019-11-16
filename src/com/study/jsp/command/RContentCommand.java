package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.DAO.*;
import com.study.jsp.DTO.*;

public class RContentCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String bId = request.getParameter("bId");
		RDao dao = new RDao();
		RDto dto = dao.contentView(bId); 
		
		request.setAttribute("content_view", dto);
		
		CommentDAO commentDAO = CommentDAO.getInstance();
	    ArrayList<CommentDto> commentList = commentDAO.getCommentList(Integer.parseInt(bId));
	        
	    // 댓글이 1개라도 있다면 request에 commentList를 세팅한다.
	    if(commentList.size() > 0) {
	    	System.out.println("발견");
	    	request.setAttribute("commentList", commentList);
	    }   
	}
	
}
