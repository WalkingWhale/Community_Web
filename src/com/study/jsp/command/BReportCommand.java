package com.study.jsp.command;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.DAO.*;
import com.study.jsp.DTO.*;

public class BReportCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = null;
		session = request.getSession();
		
		String bId = request.getParameter("bId");
		System.out.println(bId);
		BDao dao = new BDao();
		
		boolean result = dao.report(bId);	
		System.out.println(result);
		if(result == true) {
			try {
				response.setContentType("text/html; charset=UTF-8"); 
				PrintWriter out = response.getWriter();
			
				out.println("<html><head></head><body>");
				out.println("<script languange = \"javascript\">");
				out.println("	alert(\"신고하셨습니다.\")");
				out.println("	document.location.href=\"content_view.bbs?bId="+ bId +"\";");
				out.println("</script>");
				out.println("</body></html>");
				out.close();
				
			} catch(Exception e) {}
		} else if(result == false) {
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
			
	}
	
}
