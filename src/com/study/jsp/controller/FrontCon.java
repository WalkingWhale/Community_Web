package com.study.jsp.controller;

import com.study.jsp.command.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FrontCon
 */
@WebServlet("*.do")
public class FrontCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FrontCon() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
		actionDo(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		actionDo(request, response);
	}
	
	protected void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("actionDo");
		
		String uri = request.getRequestURI();
		System.out.println("uri : " + uri);
		
		String conPath= request.getContextPath();
		System.out.println("conPath" + conPath);
		
		String command = uri.substring(conPath.length());
		System.out.println("command : " + command);
		
		if(command.equals("/joinOk.do")) {
			System.out.println("join");
			Service service = new joinOk();
			service.execute(request, response);
			System.out.println("------------------");
		} else if(command.equals("/loginOk.do")) {
			System.out.println("login");
			Service service = new loginOk();
			service.execute(request, response);
			System.out.println("------------------");
		} else if(command.equals("/modifyOk.do")) {
			System.out.println("modify");
			Service service = new modifyOk();
			service.execute(request, response);
			System.out.println("------------------");
		} else if(command.equals("/logout.do")) {
			System.out.println("logout");
			logout(request, response);
			System.out.println("------------------");
		} else if(command.equals("/withdrawOk.do")) {
			System.out.println("withdraw");
			Service service = new withdrawOk();
			service.execute(request, response);
			System.out.println("------------------");
		} 
	}	
	
	
	protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();		
		session.invalidate();
		
		response.setContentType("text/html; charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		
		// html 출력
		out.println("<html><head></head><body>");
		out.println("<script languange = \"javascript\">");
		out.println("	alert(\"로그아웃 하셨습니다.\")");
		out.println("	document.location.href=\"main.start\";");
		out.println("</script>");
		out.println("</body></html>");
		out.close();
	}
	
	
}
