package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.DAO.*;
import com.study.jsp.DTO.*;
import com.study.jsp.info.BPageInfo;

public class MnContentCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		MnDao dao = new MnDao();
		MnDTO dto = dao.userView(id);
		
		int nPage = 1;
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
		}catch(Exception e) {			
		}
		
		BPageInfo pinfo = dao.userArticle(nPage, id);
		request.setAttribute("page", pinfo);
		
		nPage = pinfo.getCurPage();
		
		HttpSession session = null;
		session = request.getSession();
		session.setAttribute("cpage", nPage);
		
		ArrayList<BDto> dtos = dao.Articlelist(nPage, id);
		request.setAttribute("list", dtos);
		
		request.setAttribute("user_view", dto);
	}
	
}
