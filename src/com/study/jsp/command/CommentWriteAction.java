package com.study.jsp.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.DAO.*;
import com.study.jsp.DTO.*;

public class CommentWriteAction implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		CommentDAO dao = CommentDAO.getInstance();
        CommentDto comment = new CommentDto();
        
        // 파리미터 값을 가져온다.
        int comment_board = Integer.parseInt(request.getParameter("comment_board"));
        //System.out.println(comment_board);
        String comment_id = request.getParameter("comment_id");
        String comment_content = request.getParameter("comment_content");
        
        comment.setComment_num(dao.getSeq());    // 댓글 번호는 시퀀스값으로
        comment.setComment_board(comment_board);
        comment.setComment_id(comment_id);
        comment.setComment_content(comment_content);
        
        boolean result = dao.insertComment(comment);
 
        if(result){
        	System.out.println("댓글 작성 성공");
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out;
			try {
				out = response.getWriter();
				out.println("1");
	            out.close();
			} catch (IOException e) {				
			}            
        }            
        return;
	}	
}
