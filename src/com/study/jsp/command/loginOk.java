package com.study.jsp.command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.DAO.*;
import com.study.jsp.DTO.*;

public class loginOk implements Service{

	public loginOk() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = "";
		String email = "";
		String address = "";
		String nickName = "";
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberDTO dto = new MemberDTO();
		
		dto.setId(id);
		dto.setPw(pw);
		
//		String query = "select * from member where id = ? and pw = ?";
		response.setContentType("text/html; charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		
		
		int ri = dao.userCheck(dto.getId(), dto.getPw());
		
		if(ri == MemberDAO.MEMBER_LOGIN_PW_NO_GOOD) {			
			out.println("<html><head></head><body>");
			out.println("<script languange = \"javascript\">");
			out.println("	alert(\"아이디 혹은 비밀번호가 다릅니다.\")");
			out.println("	history.back();");
			out.println("</script>");
			out.println("</body></html>");
			out.close();
		} else if(ri == MemberDAO.MEMBER_LOGIN_IS_NOT) {
			out.println("<html><head></head><body>");
			out.println("<script languange = \"javascript\">");
			out.println("	alert(\"아이디가 존재하지 않습니다 회원가입을 해주십시오.\")");
			out.println("	history.back();");
			out.println("</script>");
			out.println("</body></html>");
			out.close();
		} else if(ri == MemberDAO.MEMBER_LOGIN_BANNED) {
			out.println("<html><head></head><body>");
			out.println("<script languange = \"javascript\">");
			out.println("	alert(\"블락된 계정입니다 관리자에게 문의해 주십시오.\")");
			out.println("	history.back();");
			out.println("</script>");
			out.println("</body></html>");
			out.close();
		} else if (ri == MemberDAO.MEMBER_JOIN_SUCCESS) {
			System.out.println("일반 멤버 로그인");
			MemberDTO dto2 = dao.getMember(id);
			
			name = dto2.getName();
			nickName = dto2.getNickname();
					
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
			session.setAttribute("name", name);
			session.setAttribute("nickname", nickName);
			session.setAttribute("ValidMem", "yes");
			
			out.println("<html><head></head><body>");
			out.println("<script languange = \"javascript\">");
			out.println("	alert(\"로그인 하셨습니다.\")");
			out.println("	document.location.href=\"main.start\";");
			out.println("</script>");
			out.println("</body></html>");
			out.close();
						
		} else if (ri == MemberDAO.MANAGER_LOGIN_SUCCESS) {
			System.out.println("관리자 로그인");
			MemberDTO dto2 = dao.getMember(id);
			
			name = dto2.getName();
			nickName = dto2.getNickname();
					
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
			session.setAttribute("name", name);
			session.setAttribute("nickname", nickName);
			session.setAttribute("admin", "yes");
			session.setAttribute("ValidMem", "yes");
			
			out.println("<html><head></head><body>");
			out.println("<script languange = \"javascript\">");
			out.println("	alert(\"로그인 하셨습니다.\")");
			out.println("	document.location.href=\"main.start\";");
			out.println("</script>");
			out.println("</body></html>");
			out.close();
		}
		
	}
}
