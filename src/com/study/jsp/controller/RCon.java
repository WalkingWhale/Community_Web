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
@WebServlet("*.data")
public class RCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RCon() {
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
		if(com.equals("/write_view.data")) {
			viewPage = "reference_write_view.jsp";
		} else if(com.equals("/write.data")) {
			System.out.println("write 실행");
			command = new RWriteCommand();
			command.execute(request, response);
			viewPage = "list.data";
		} else if(com.equals("/list.data")) {
			System.out.println("list 실행");
			command = new RListCommand();
			command.execute(request, response);
			viewPage = "reference_list.jsp";
		} else if(com.equals("/content_view.data")) {			
			command = new RContentCommand();
			command.execute(request, response);
			viewPage = "reference_content_view.jsp";
		} else if(com.equals("/modify_view.data")) {
			
			command = new RContentCommand();
			command.execute(request, response);
			viewPage = "reference_modify_view.jsp";
		} else if(com.equals("/modify.data")) {
			System.out.println("modify 실행");
			command = new RModifyCommand();
			command.execute(request, response);
			viewPage = "content_view.jsp";
			return;
		} else if(com.equals("/delete.data")) {
			System.out.println("delete 실행");
			command = new RDeleteCommand();
			command.execute(request, response);
			viewPage = "list.data?page="+curPage;
		} else if(com.equals("/search.data")) {
			//System.out.println("검색 시도");
			//System.out.println(request.getParameter("sOption"));
			//System.out.println(request.getParameter("search"));
			command = new BSearchCommand();
			command.execute(request, response);
			viewPage = "search.jsp";
		} else if(com.equals("/like.data")) {
			//System.out.println("검색 시도");
			//System.out.println(request.getParameter("sOption"));
			//System.out.println(request.getParameter("search"));
			command = new RLikeCommand();
			command.execute(request, response);
			return;
		} else if(com.equals("/download.data")) {
			//System.out.println("검색 시도");
			//System.out.println(request.getParameter("sOption"));
			//System.out.println(request.getParameter("search"));
			command = new RDownloadCommand();
			command.execute(request, response);
			return;
		} else if(com.equals("/report.data")) {
			command = new RReportCommand();
			command.execute(request, response);
			return;
		} 
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}	
	
}
