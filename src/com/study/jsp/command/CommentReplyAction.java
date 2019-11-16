package com.study.jsp.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.DAO.*;
import com.study.jsp.DTO.*;

public class CommentReplyAction implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// 파라미터를 가져온다.
        int comment_num = Integer.parseInt(request.getParameter("comment_num"));
        int comment_board = Integer.parseInt(request.getParameter("comment_board"));
        String comment_id = request.getParameter("comment_id");
        String comment_content = request.getParameter("comment_content");
 
        CommentDAO dao = CommentDAO.getInstance();
        
        CommentDto comment = new CommentDto();    
        comment.setComment_num(dao.getSeq());    // 시퀀스를 가져와 세팅한다
        comment.setComment_board(comment_board);
        comment.setComment_id(comment_id);
        comment.setComment_content(comment_content);
        comment.setComment_parent(comment_num);  // 부모댓글의 글번호를 세팅
        
        boolean result = dao.insertComment(comment);
        
        try {
        	 response.setContentType("text/html;charset=UTF-8");
             PrintWriter out = response.getWriter();
             
             // 정상적으로 댓글을 저장했을경우 1을 전달한다.
             if(result) out.println("1");
             else out.println("0");
             out.close();
             
        }catch(Exception e) {}       
        
     

	}	
}
