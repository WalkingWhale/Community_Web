package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.DAO.*;
import com.study.jsp.DTO.*;
import com.study.jsp.info.BPageInfo;

public class MnSearchCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		int nPage = 1;
		String sOption = "";
		String search = "";
		
		try {
			String sPage = request.getParameter("page");
			sOption = request.getParameter("sOption");
			search = request.getParameter("search");			
			nPage = Integer.parseInt(sPage);
		} 
		catch(Exception e) {			
		}
		
		MnDao dao = MnDao.getInstance();
		BPageInfo pinfo = dao.SearchPage(nPage, sOption, search);
		request.setAttribute("page", pinfo);
		
		nPage = pinfo.getCurPage();
		
		HttpSession session = null;
		session = request.getSession();	
		session.setAttribute("cpage", nPage);
		session.setAttribute("sOption", sOption);
		session.setAttribute("search", search);
		
		ArrayList<MnDTO> dtos = dao.Search(nPage, sOption, search);
		request.setAttribute("list", dtos);
	}
	
}
