package com.study.jsp.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.DAO.*;
import com.study.jsp.DTO.*;

public class SnsLoginOk implements Service{

	public SnsLoginOk() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String nickname = request.getParameter("name") + "(" + id + ")";
		String pw = "somethingspecial";
		String email = "your@email.com";
		
		System.out.println(id);
		System.out.println(name);
		
		MemberDAO dao = MemberDAO.getInstance();
		
		if(dao.snsUserCheck(id)) {			
			// 이미 DB상에 존재 블락 체크 후 로그인 처리
			if(dao.snsUserBan(id)) {
				String json_data = "";
				
				response.setContentType("application/json; charset=UTF-8");
				PrintWriter out = response.getWriter();
				json_data= json_data = "{\"code\":\"ban\", \"desc\":\"블락당한 계정입니다 관리자에게 문의해주세요.\"}";
				out.println(json_data);
			} else {
				MemberDTO dto2 = dao.getMember(id);
				
				HttpSession session = request.getSession();
				session.setAttribute("id", id);
				session.setAttribute("name", name);
				session.setAttribute("nickname", dto2.getNickname());
				session.setAttribute("ValidMem", "yes");
				session.setAttribute("sns", "yes");
				
				String json_data = "";
				
				response.setContentType("application/json; charset=UTF-8");
				PrintWriter out = response.getWriter();
				json_data= json_data = "{\"code\":\"success\", \"desc\":\"로그인에 성공했습니다.\"}";
				out.println(json_data);
			}
			
			
		} else {
			// DB등록
			MemberDTO dto = new MemberDTO();
			dto.setId(id);
			dto.setPw(pw);
			dto.setName(name);
			dto.setNickname(nickname);
			dto.setEmail(email);
			dto.setrDate(new Timestamp(System.currentTimeMillis()));
			
			int ri =dao.memberInsert(dto);
			
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
			session.setAttribute("name", name);
			session.setAttribute("nickname", nickname);
			session.setAttribute("ValidMem", "yes");
			session.setAttribute("sns", "yes");
			
			String json_data = "";
			
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			json_data= json_data = "{\"code\":\"success\", \"desc\":\"로그인에 성공했습니다.\"}";
			out.println(json_data);
			
		}		
		
	}
}
