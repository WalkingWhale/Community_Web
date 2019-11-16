package com.study.jsp.command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.DAO.*;

public class BReplyCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = null;
		session = request.getSession();
		
		String bId = request.getParameter("bId");
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		String bGroup = request.getParameter("bGroup");
		String bStep = request.getParameter("bStep");
		String bIndent = request.getParameter("bIndent");
		String bUser = (String)session.getAttribute("id");
		
		BDao dao = new BDao();
		dao.reply(bId,bName,bTitle,bContent,bGroup,bStep,bIndent,bUser);
		
	}
	
}
