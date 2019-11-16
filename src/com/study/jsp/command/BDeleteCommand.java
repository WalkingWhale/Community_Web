package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.DAO.*;
import com.study.jsp.DTO.*;

public class BDeleteCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = null;
		session = request.getSession();
		
		String userId = (String) session.getAttribute("id");
		//System.out.println(userId + "출력");
		String bId = request.getParameter("bId");		
		BDao dao = new BDao();
		
		
		dao.delete(bId);		
		
	}
	
}
