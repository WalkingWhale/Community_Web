package com.study.jsp.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.DAO.*;
import org.json.simple.JSONObject;

import nl.captcha.Captcha;
import nl.captcha.audio.AudioCaptcha;
import nl.captcha.servlet.CaptchaServletUtil;

public class CaptchaAnswer implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String SessionAnswer = (String) request.getSession().getAttribute("captcha");
		String inputAnswer = request.getParameter("answer");
		
		
		//System.out.println(SessionAnswer);
		//System.out.println(inputAnswer);
		
		if(SessionAnswer.equals(inputAnswer)) {
			try {
				//System.out.println("재대로된 입력");				
				response.setContentType("text/html; charset=UTF-8"); 
				PrintWriter out = response.getWriter();
				out.println(1);
			} catch(Exception e) {
				
			}			
		} else {
			try {
				//System.out.println("틀린 입력");
				response.setContentType("text/html; charset=UTF-8"); 
				PrintWriter out = response.getWriter();
				out.println(0);
			} catch(Exception e) {
				
			}
		}
	}
	
}
