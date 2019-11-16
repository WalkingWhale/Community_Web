package com.study.jsp.command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.DAO.*;
import com.study.jsp.DTO.*;

public class withdrawOk implements Service{

	public withdrawOk() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		boolean flag = false;
		
		HttpSession session = request.getSession();
					
		String id_1 = (String)session.getAttribute("id");	
		String id_2 = request.getParameter("id");	
		String pw = request.getParameter("pw");
		int ri = 1;
		response.setContentType("text/html; charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		if(id_1.equals(id_2)) {
			flag = true;
		}
		
		if(flag) {
			
			System.out.println("OK");
			
			MemberDAO dao = MemberDAO.getInstance();
			MemberDTO dto = dao.getMember(id_1);
			
			if(pw.equals(dto.getPw())) {
				ri = dao.withdrawMember(dto.getId());
				if(ri == 1) {
					out.println("<html><head></head><body>");
					out.println("<script languange = \"javascript\">");
					out.println("	alert(\"회원에서 탈퇴되셨습니다.\")");
					out.println("	document.location.href=\"logout.do\";");
					out.println("</script>");
					out.println("</body></html>");
					out.close();
				} else {
					out.println("<html><head></head><body>");
					out.println("<script languange = \"javascript\">");
					out.println("	alert(\"이미 회원이 아니거나 오류가 발생했습니다 관리자에게 문의 바랍니다.\")");
					out.println("	history.back();");
					out.println("</script>");
					out.println("</body></html>");
					out.close();
				}
			} else {
				out.println("<html><head></head><body>");
				out.println("<script languange = \"javascript\">");
				out.println("	alert(\"비밀번호가 달라 회원탈퇴에 요청에 실패했습니다.\")");
				out.println("	history.back();");
				out.println("</script>");
				out.println("</body></html>");
				out.close();
			}		
			
			
			
		} else {
			out.println("<html><head></head><body>");
			out.println("<script languange = \"javascript\">");
			out.println("	alert(\"로그인된 계정만 탈퇴요청 할 수 있습니다.\")");
			out.println("	history.back();");
			out.println("</script>");
			out.println("</body></html>");
			out.close();
		}
		
	}

}
