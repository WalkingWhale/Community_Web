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
@WebServlet("*.nc")
public class NotificationCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public NotificationCon() {
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
		if(com.equals("/write_view.nc")) {
			viewPage = "notification_write_view.jsp";
		} else if(com.equals("/write.nc")) {
			System.out.println("write 실행");
			command = new NcWriteCommand();
			command.execute(request, response);
			return;
		} else if(com.equals("/list.nc")) {
			System.out.println("list 실행");
			command = new NcListCommand();
			command.execute(request, response);
			viewPage = "notification.jsp";
		} else if(com.equals("/content_view.nc")) {			
			command = new NcContentCommand();
			command.execute(request, response);
			viewPage = "notification_content_view.jsp";
		} else if(com.equals("/modify_view.nc")) {			
			command = new NcContentCommand();
			command.execute(request, response);
			viewPage = "notification_modify_view.jsp";
		} else if(com.equals("/modify.nc")) {
			System.out.println("modify 실행");
			System.out.println("modify 실행");
			command = new NcModifyCommand();
			command.execute(request, response);
			//viewPage = "content_view.jsp";
			return;
		} else if(com.equals("/delete.nc")) {
			System.out.println("delete 실행");
			command = new NcDeleteCommand();
			command.execute(request, response);
			viewPage = "list.nc?page="+curPage;
		} else if(com.equals("/search.nc")) {
			System.out.println("검색 시도");
			System.out.println(request.getParameter("sOption"));
			System.out.println(request.getParameter("search"));
			command = new NcSearchCommand();
			command.execute(request, response);
			viewPage = "notification_search.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}	
	
}
