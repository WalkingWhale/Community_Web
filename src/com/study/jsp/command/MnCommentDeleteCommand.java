package com.study.jsp.command;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.DAO.*;
import com.study.jsp.DTO.*;

public class MnCommentDeleteCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = null;
		session = request.getSession();
		
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
		
		String id = (String)session.getAttribute("id");
        
        MnDao dao = MnDao.getInstance();
        boolean result = dao.deleteComment(comment_num);

	}		
	
}
