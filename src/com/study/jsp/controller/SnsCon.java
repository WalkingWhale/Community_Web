package com.study.jsp.controller;

import com.study.jsp.command.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FrontCon
 */
@WebServlet("*.sns")
public class SnsCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SnsCon() {
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
		
		BCommand com;
		String viewPage = "";
		if(command.equals("/login.sns")) {
			System.out.println("sns login");
			Service service = new SnsLoginOk();
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
