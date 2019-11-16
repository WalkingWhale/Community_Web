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
@WebServlet("*.co")
public class CommentCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CommentCon() {
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
		if(com.equals("/write_view.co")) {
			viewPage = "write_view.jsp";
		} else if(com.equals("/CommentWriteAction.co")) {
			System.out.println("CommentWrite 실행");
			command = new CommentWriteAction();
			command.execute(request, response);
			return;
		} else if(com.equals("/CommentReplyFormAction.co")) {
			System.out.println("CommentReplyForm 실행");
			command = new CommentReplyFormAction();
			command.execute(request, response);
			viewPage = "comment_reply_view.jsp";
		} else if(com.equals("/CommentReplyAction.co")) {
			System.out.println("CommentReply 실행");
			command = new CommentReplyAction();
			command.execute(request, response);
			return;
		} else if(com.equals("/CommentUpdateFormAction.co")) {
			System.out.println("CommentUpdateForm 실행");
			command = new CommentUpdateFormAction();
			command.execute(request, response);
			viewPage = "comment_update_view.jsp";
		} else if(com.equals("/CommentUpdateAction.co")) {
			System.out.println("CommentUpdate 실행");
			command = new CommentUpdateAction();
			command.execute(request, response);
			return;
		}  else if(com.equals("/CommentDeleteAction.co")) {
			System.out.println("CommentDelete 실행");
			command = new CommentDeleteAction();
			command.execute(request, response);
			return;
		}	else if(com.equals("/CommentReportAction.co")) {
			System.out.println("CommentReport 실행");
			command = new CommentReportAction();
			command.execute(request, response);
			return;
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}	
	
}
