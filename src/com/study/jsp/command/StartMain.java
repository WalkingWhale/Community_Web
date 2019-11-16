package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.DAO.*;
import com.study.jsp.DTO.*;
import com.study.jsp.info.BPageInfo;

public class StartMain implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		int nPage = 1;
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
		}catch(Exception e) {			
		}
		
		BDao bdao = BDao.getInstance();
		NcDao ncdao = NcDao.getInstance();
		RDao rdao = RDao.getInstance();
		BPageInfo bpinfo = bdao.articlePage(nPage, "normal");
		BPageInfo ncpinfo = ncdao.articlePage(nPage);
		BPageInfo rpinfo = rdao.articlePage(nPage, "data");
		
		request.setAttribute("bpage", bpinfo);
		request.setAttribute("ncpage", bpinfo);
		request.setAttribute("rpage", bpinfo);
		
		nPage = 1;
		
		HttpSession session = null;
		session = request.getSession();
		session.setAttribute("cpage", nPage);
		session.setAttribute("firstStart", "no");
		
		ArrayList<BDto> bdtos = bdao.start(nPage);
		ArrayList<BDto> ncdtos = ncdao.start(nPage);
		ArrayList<RDto> rdtos = rdao.start(nPage);
		request.setAttribute("Nclist", ncdtos);
		request.setAttribute("Blist", bdtos);
		request.setAttribute("Rlist", rdtos);
	}
	
}
