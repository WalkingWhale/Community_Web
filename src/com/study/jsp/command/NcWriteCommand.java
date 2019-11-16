package com.study.jsp.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.DAO.*;

public class NcWriteCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();	
		
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		String bUser = (String) session.getAttribute("id");
		
		NcDao dao = new NcDao();
		if(dao.managerCheck(bUser)) {
			dao.write(bName,bTitle,bContent,bUser);
			
			try {
				response.setContentType("text/html; charset=UTF-8"); 
				PrintWriter out = response.getWriter();
			
				out.println("<html><head></head><body>");
				out.println("<script languange = \"javascript\">");
				out.println("	alert(\"공지사항을 작성하였습니다.\")");
				out.println("	document.location.href=\"list.nc\";");
				out.println("</script>");
				out.println("</body></html>");
				out.close();
				
			} catch(Exception e) {}	
		} else {
			try {
				response.setContentType("text/html; charset=UTF-8"); 
				PrintWriter out = response.getWriter();
			
				out.println("<html><head></head><body>");
				out.println("<script languange = \"javascript\">");
				out.println("	alert(\"관리자만 공지사항을 작성할 수 있습니다.\")");
				out.println("	document.location.href=\"list.nc\";");
				out.println("</script>");
				out.println("</body></html>");
				out.close();
				
			} catch(Exception e) {}	
		}
		
	}
	
}
