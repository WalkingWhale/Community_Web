package com.study.jsp.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.DAO.*;
import com.study.jsp.DTO.*;

public class CommentDeleteAction implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = null;
		session = request.getSession();
		
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
		
		String id = (String)session.getAttribute("id");
        
        CommentDAO dao = CommentDAO.getInstance();
        boolean result = dao.deleteComment(comment_num, id);
        
        try {
        	 response.setContentType("text/html;charset=euc-kr");
             PrintWriter out = response.getWriter();
      
             // 정상적으로 댓글을 삭제했을경우 1을 전달한다.
             if(result) out.println("1");
             
             out.close();
        	
        } catch(Exception e) {}

	}	
}
