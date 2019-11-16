package com.study.jsp.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.DAO.*;
import com.study.jsp.DTO.*;

public class CommentUpdateFormAction implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		int comment_num = Integer.parseInt(request.getParameter("num"));
		 
        CommentDAO dao = CommentDAO.getInstance();
        CommentDto comment = dao.getComment(comment_num);
        
        // 댓글 정보를 request에 세팅한다.
        request.setAttribute("comment", comment);
	}	
}
