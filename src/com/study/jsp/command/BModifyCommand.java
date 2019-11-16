package com.study.jsp.command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.DAO.*;

public class BModifyCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)  {
		HttpSession session = request.getSession();
		String bId = request.getParameter("bId");
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");	
		String bUser = (String) session.getAttribute("id");		
		
		BDao dao = new BDao();
		
		
		
		if(dao.checkUser(bId, bUser)) {
			dao.modify(bId,bName,bTitle,bContent);
			
			try {
				response.setContentType("text/html; charset=UTF-8"); 
				PrintWriter out = response.getWriter();
			
				out.println("<html><head></head><body>");
				out.println("<script languange = \"javascript\">");
				out.println("	alert(\"수정하였습니다.\")");
				out.println("	document.location.href=\"list.bbs\";");
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
				out.println("	alert(\"글의 작성자만 수정할 수 있습니다.\")");
				out.println("	document.location.href=\"list.bbs\";");
				out.println("</script>");
				out.println("</body></html>");
				out.close();
				
			} catch(Exception e) {}			
		}			
	}	
}
