package com.study.jsp.command;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.DAO.*;
import com.study.jsp.DTO.*;

public class MnDeleteCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = null;
		session = request.getSession();
		String bUser = request.getParameter("id");
		
		MnDao dao = new MnDao();
		
		boolean result = dao.delete(bUser);	
		
		//System.out.println("c : " + result);
		
		if(result == true) {
			try {
				System.out.println("강제 탈퇴 성공");
				response.setContentType("text/html; charset=UTF-8"); 
				PrintWriter out = response.getWriter();
			
				out.println("<html><head></head><body>");
				out.println("<script languange = \"javascript\">");
				out.println("	alert(\"강제 탈퇴에 성공하였습니다.\")");
				out.println("	document.location.href=\"list.user\";");
				out.println("</script>");
				out.println("</body></html>");
				out.close();
				
			} catch(Exception e) {}
		} else {
			try {
				System.out.println("강제 탈퇴 실패");
				response.setContentType("text/html; charset=UTF-8"); 
				PrintWriter out = response.getWriter();
		
				out.println("<html><head></head><body>");
				out.println("<script languange = \"javascript\">");
				out.println("	alert(\"강제 탈퇴 중 오류가 발생했습니다.\")");
				out.println("	document.location.href=\"user_view.user?id="+ bUser +"\";");
				out.println("</script>");
				out.println("</body></html>");
				out.close();
			
			} catch(Exception e) {}
		}
	}
}
