package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.DAO.*;

import nl.captcha.Captcha;
import nl.captcha.audio.AudioCaptcha;
import nl.captcha.servlet.CaptchaServletUtil;

public class CaptchaAudio implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			String getAnswer = (String) request.getSession().getAttribute("captcha");
			AudioCaptcha ac = new AudioCaptcha.Builder()
					.addAnswer()
					.addVoice()
                    .addNoise()
                    .build();
			
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Max-Age", 0);
			HttpSession session = request.getSession();
			
			CaptchaServletUtil.writeAudio(response, ac.getChallenge());
			System.out.println(ac.getAnswer());
			session.setAttribute("captcha", ac.getAnswer());
		} catch(Exception e) {
			
		}
	}
	
}
