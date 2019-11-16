package com.study.jsp.command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.DAO.*;
import com.study.jsp.DTO.*;

public class modifyOk implements Service{

	public modifyOk() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		boolean flag = false;
		
		HttpSession session = request.getSession();
					
		String id = (String)session.getAttribute("id");		
		String pw = request.getParameter("pw");
		String pw_check = request.getParameter("pw_check");
		String name = request.getParameter("name");
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		
		response.setContentType("text/html; charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		if(pw.equals(pw_check)) {
			flag = true;
		}
		
		if(flag) {
			
			System.out.println("OK");
			
			MemberDAO dao = MemberDAO.getInstance();
			MemberDTO dto = new MemberDTO();
			
			dto.setId(id);
			dto.setPw(pw);
			dto.setName(name);
			dto.setNickname(nickname);
			dto.setEmail(email);
			dto.setAddress(address);	
			
			int ri = dao.updateMember(dto);	
			
			if(ri == 1) {
				
				session.setAttribute("id", id);
				session.setAttribute("name", name);
				session.setAttribute("nickname", nickname);
				
				out.println("<html><head></head><body>");
				out.println("<script languange = \"javascript\">");
				out.println("	alert(\"회원정보 수정이 완료되었습니다.\")");
				out.println("	document.location.href=\"main.start\";");
				out.println("</script>");
				out.println("</body></html>");
			} else {
				out.println("<html><head></head><body>");
				out.println("<script languange = \"javascript\">");
				out.println("	alert(\"회원정보 수정을 실패했습니다.\")");
				out.println("	history.back();");
				out.println("</script>");
				out.println("</body></html>");
				out.close();
			}
		} else {
			out.println("<html><head></head><body>");
			out.println("<script languange = \"javascript\">");
			out.println("	alert(\"입력한 두 비밀번호가 다릅니다 확인해주십시오.\")");
			out.println("	history.back();");
			out.println("</script>");
			out.println("</body></html>");
			out.close();
		}
		
	}

}
