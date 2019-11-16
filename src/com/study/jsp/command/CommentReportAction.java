package com.study.jsp.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.DAO.*;
import com.study.jsp.DTO.*;

public class CommentReportAction implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
        
        CommentDAO dao = CommentDAO.getInstance();
       
        boolean result = dao.ReportComment(comment_num);
        if(result == true) {
        	 try {
             	response.setContentType("text/html;charset=UTF-8");
                 PrintWriter out = response.getWriter();
                 
                 // 정상적으로 댓글을 신고 됬을경우 1을 전달한다.
                 if(result) out.println("4");
                 
                 out.close();     
             } catch(Exception e) {
             	
             }
        }
       
         
     

	}	
}
