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
@WebServlet("*.bbs")
public class BBSCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BBSCon() {
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
		
		request.setCharacterEncoding("UTF-8");
		
		String viewPage = null;
		BCommand command = null;
		
		String uri = request.getRequestURI();
		//System.out.println("uri : " + uri);		
		String conPath= request.getContextPath();
		//System.out.println("conPath" + conPath);		
		String com = uri.substring(conPath.length());
		//System.out.println("command : " + command);
		HttpSession session = null;
		session = request.getSession();
		int curPage = 1;
		if(session.getAttribute("cpage") != null) {
			curPage = (int)session.getAttribute("cpage");
		}
		if(com.equals("/write_view.bbs")) {
			viewPage = "write_view.jsp";
		} else if(com.equals("/write.bbs")) {
			System.out.println("write 실행");
			command = new BWriteCommand();
			command.execute(request, response);
			viewPage = "list.bbs";
		} else if(com.equals("/list.bbs")) {
			System.out.println("list 실행");
			command = new BListCommand();
			command.execute(request, response);
			viewPage = "list.jsp";
		} else if(com.equals("/content_view.bbs")) {			
			command = new BContentCommand();
			command.execute(request, response);
			viewPage = "content_view.jsp";
		} else if(com.equals("/modify_view.bbs")) {
			
			command = new BContentCommand();
			command.execute(request, response);
			viewPage = "modify_view.jsp";
		} else if(com.equals("/modify.bbs")) {
			System.out.println("modify 실행");
			command = new BModifyCommand();
			command.execute(request, response);
			//viewPage = "content_view.jsp";
			return;
		} else if(com.equals("/delete.bbs")) {
			System.out.println("delete 실행");
			command = new BDeleteCommand();
			command.execute(request, response);
			viewPage = "list.bbs?page="+curPage;
		} else if(com.equals("/reply_view.bbs")) {
			command = new BReplyViewCommand();
			command.execute(request, response);
			viewPage = "reply_view.jsp";
		} else if(com.equals("/reply.bbs")) {
			command = new BReplyCommand();
			command.execute(request, response);
			viewPage = "list.bbs?page="+curPage;
		} else if(com.equals("/search.bbs")) {
			//System.out.println("검색 시도");
			//System.out.println(request.getParameter("sOption"));
			//System.out.println(request.getParameter("search"));
			command = new BSearchCommand();
			command.execute(request, response);
			viewPage = "search.jsp";
		} else if(com.equals("/like.bbs")) {
			//System.out.println("검색 시도");
			//System.out.println(request.getParameter("sOption"));
			//System.out.println(request.getParameter("search"));
			command = new BLikeCommand();
			command.execute(request, response);
			return;
		} else if(com.equals("/report.bbs")) {
			command = new BReportCommand();
			command.execute(request, response);
			return;
		} 
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}	
	
}
