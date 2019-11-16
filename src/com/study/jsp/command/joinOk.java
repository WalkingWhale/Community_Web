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

public class joinOk implements Service{

	public joinOk() {		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("joinOk");
		
		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		
		MemberDTO dto = new MemberDTO();
		dto.setId(id);
		dto.setPw(pw);
		dto.setName(name);
		dto.setNickname(nickname);
		dto.setEmail(email);
		dto.setAddress(address);
		dto.setrDate(new Timestamp(System.currentTimeMillis()));
		
		response.setContentType("text/html; charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		
		MemberDAO dao = MemberDAO.getInstance();
		if(dao.confirmId(dto.getId()) == MemberDAO.MEMBER_EXISTENT) {
			out.println("<html><head></head><body>");
			out.println("<script languange = \"javascript\">");
			out.println("	alert(\"아이디가 이미 존재 합니다.\")");
			out.println("	history.back();");
			out.println("</script>");
			out.println("</body></html>");
			out.close();
		} else {
			int ri =dao.memberInsert(dto);
			if(ri == MemberDAO.MEMBER_JOIN_SUCCESS) {
				HttpSession session = request.getSession();
				session.setAttribute("id", dto.getId());
				
				// html 출력
				out.println("<html><head></head><body>");
				out.println("<script languange = \"javascript\">");
				out.println("	alert(\"회원가입을 축하 합니다.\")");
				out.println("	document.location.href=\"login.jsp\";");
				out.println("</script>");
				out.println("</body></html>");
				out.close();
			} else {
				// html 출력
				out.println("<html><head></head><body>");
				out.println("<script languange = \"javascript\">");
				out.println("	alert(\"회원가입에 실패했습니다.\")");
				out.println("	document.location.href=\"login.jsp\";");
				out.println("</script>");
				out.println("</body></html>");
				out.close();
			}
		}
		
	}
	
	
}
