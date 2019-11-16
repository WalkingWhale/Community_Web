package com.study.jsp.command;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.DAO.*;
import com.study.jsp.DTO.*;

public class BLikeCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = null;
		session = request.getSession();
		String bId = request.getParameter("bId");
		String bUser = (String)session.getAttribute("id");
		
		BDao dao = new BDao();
		
		if(dao.WriterCheck(bId, bUser) == false) {
			int result = dao.upLike(bId, bUser);	
			System.out.println(result);
			if(result == 1) {
				try {
					response.setContentType("text/html; charset=UTF-8"); 
					PrintWriter out = response.getWriter();
				
					out.println("<html><head></head><body>");
					out.println("<script languange = \"javascript\">");
					out.println("	alert(\"좋아요를 누르셨습니다.\")");
					out.println("	document.location.href=\"content_view.bbs?bId="+ bId +"\";");
					out.println("</script>");
					out.println("</body></html>");
					out.close();
					
				} catch(Exception e) {}
			} else if(result == 2) {
				try {
					response.setContentType("text/html; charset=UTF-8"); 
					PrintWriter out = response.getWriter();
				
					out.println("<html><head></head><body>");
					out.println("<script languange = \"javascript\">");
					out.println("	alert(\"좋아요는 한번만 누를수 있습니다.\")");
					out.println("	document.location.href=\"content_view.bbs?bId="+ bId +"\";");
					out.println("</script>");
					out.println("</body></html>");
					out.close();
					
				} catch(Exception e) {}
			} else if(result == 0) {
				try {
					response.setContentType("text/html; charset=UTF-8"); 
					PrintWriter out = response.getWriter();
				
					out.println("<html><head></head><body>");
					out.println("<script languange = \"javascript\">");
					out.println("	alert(\"에러가 발생했습니다.\")");
					out.println("	document.location.href=\"content_view.bbs?bId="+ bId +"\";");
					out.println("</script>");
					out.println("</body></html>");
					out.close();
					
				} catch(Exception e) {}
			}
			
		} else {
			try {
				response.setContentType("text/html; charset=UTF-8"); 
				PrintWriter out = response.getWriter();
			
				out.println("<html><head></head><body>");
				out.println("<script languange = \"javascript\">");
				out.println("	alert(\"자신이 작성한 글은 좋아요를 누를 수 없습니다.\")");
				out.println("	document.location.href=\"content_view.bbs?bId="+ bId +"\";");
				out.println("</script>");
				out.println("</body></html>");
				out.close();
				
			} catch(Exception e) {}
		}
			
	}
	
}
