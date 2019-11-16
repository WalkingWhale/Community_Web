package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.DAO.*;

import nl.captcha.Captcha;
import nl.captcha.servlet.CaptchaServletUtil;

public class CaptchaImg implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			Captcha captcha = new Captcha.Builder(250, 60)
					.addText()
					.addBackground()
                    .addNoise()
                    .addBorder()
                    .build();
			
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Max-Age", 0);
			response.setContentType("image/png");
			
			
			CaptchaServletUtil.writeImage(response, captcha.getImage());
			HttpSession session = request.getSession();
			session.setAttribute("captcha", captcha.getAnswer());
			//System.out.println((String)session.getAttribute("captcha"));
		} catch(Exception e) {
			
		}
	}
	
}
