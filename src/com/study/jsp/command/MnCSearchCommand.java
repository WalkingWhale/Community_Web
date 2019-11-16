package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.DAO.*;
import com.study.jsp.DTO.*;
import com.study.jsp.info.BPageInfo;

public class MnCSearchCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		int nPage = 1;
		String sOption = "";
		String pOption = "";
		String search = "";
		BPageInfo pinfo = null;
		ArrayList<CommentDto> dtos = null;
		ArrayList<PeriodCsearchDto> dto = null;
		
		try {
			String sPage = request.getParameter("page");
			sOption = request.getParameter("sOption");
			pOption = request.getParameter("pOption");
			search = request.getParameter("search");		
			nPage = Integer.parseInt(sPage);
		} 
		catch(Exception e) {			
		}
		
		MnDao dao = MnDao.getInstance();
		
		if(sOption.equals("period")) {
			//System.out.println("기간검색");
			//System.out.println(nPage + " + " + sOption + " + " + pOption);
			pinfo = dao.SearchCommentPeriod(nPage, pOption);
			request.setAttribute("page", pinfo);
			search = "nn";
		} else {
			//System.out.println("일반검색");
			pinfo = dao.SearchPageComment(nPage, sOption, search);
			request.setAttribute("page", pinfo);
		}
		
		
		
		nPage = pinfo.getCurPage();
		
		HttpSession session = null;
		session = request.getSession();	
		session.setAttribute("cpage", nPage);
		session.setAttribute("sOption", sOption);
		session.setAttribute("pOption", pOption);
		session.setAttribute("search", search);
		
		if(sOption.equals("period")) {	
			dtos = dao.SearchCommentListPeriod(nPage, pOption);
			dto = dao.SearchPeriodUserComment(pOption);
		} else {
			dtos = dao.SearchComment(nPage, sOption, search);
		}
		
		request.setAttribute("list", dtos);
		request.setAttribute("listUser", dto);
	}
	
}
