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
@WebServlet("*.user")
public class ManagementCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ManagementCon() {
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
		if(com.equals("/list.user")) {
			System.out.println("list 유저 실행");
			command = new MnListCommand();
			command.execute(request, response);
			viewPage = "Userlist.jsp";
		} else if(com.equals("/user_view.user")) {			
			command = new MnContentCommand();
			command.execute(request, response);
			viewPage = "user_view.jsp";
		} else if(com.equals("/banUser.user")) {
			System.out.println("유저 밴 실행");
			command = new MnBanCommand();
			command.execute(request, response);
			return;
		} else if(com.equals("/deleteUser.user")) {
			System.out.println("delete 실행");
			command = new MnDeleteCommand();
			command.execute(request, response);
			return;
		} else if(com.equals("/search.user")) {
			System.out.println("검색 시도");
			//System.out.println(request.getParameter("sOption"));
			//System.out.println(request.getParameter("search"));
			command = new MnSearchCommand();
			command.execute(request, response);
			viewPage = "userSearch.jsp";
		} else if(com.equals("/Asearch.user")) {
			//System.out.println("여기까지 실행");
			command = new MnASearchCommand();
			command.execute(request, response);
			viewPage = "Mn_ArticleSearch.jsp";
		} else if(com.equals("/mnArticle.user")) {
			command = new MnArticlesListCommand();
			command.execute(request, response);
			viewPage = "Mn_Articlelist.jsp";
		} else if(com.equals("/Asearch.user")) {
			//System.out.println("여기까지 실행");
			command = new MnASearchCommand();
			command.execute(request, response);
			viewPage = "Mn_ArticleSearch.jsp";
		} else if(com.equals("/mnComment.user")) {
			command = new MnCommentListCommand();
			command.execute(request, response);
			viewPage = "Mn_Commentlist.jsp";
		} else if(com.equals("/Csearch.user")) {
			//System.out.println("여기까지 실행");
			command = new MnCSearchCommand();
			command.execute(request, response);
			viewPage = "Mn_CommentSearch.jsp"; 
		} else if(com.equals("/deleteArticle.user")) {
			//System.out.println("여기까지 실행");
			command = new BDeleteCommand();
			command.execute(request, response);
			viewPage = "mnArticle.user"; 
		} else if(com.equals("/deleteComment.user")) {
			//System.out.println("여기까지 실행");
			command = new MnCommentDeleteCommand();
			command.execute(request, response);
			viewPage = "mnComment.user"; 
		}
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response); 
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
