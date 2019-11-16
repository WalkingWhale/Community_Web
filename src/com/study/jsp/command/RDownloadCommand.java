package com.study.jsp.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.DAO.*;
import com.study.jsp.DTO.*;

public class RDownloadCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = null;
		session = request.getSession();
		
		// 다운로드할 파일명을 가져온다.
        int bId = Integer.parseInt(request.getParameter("bId"));
        
        
        RDao dao = new RDao();        
        String sysName = dao.getSysname(bId);
        String fileName = dao.getOriname(sysName);
        System.out.println("오리지널 이름 : " + fileName);
        System.out.println("서버에 저장된 이름" + sysName);
        // 파일이 있는 절대경로를 가져온다.
        // 현재 업로드된 파일은 UploadFolder 폴더에 있다.
        String folder = request.getServletContext().getRealPath("UploadFolder");
        // 파일의 절대경로를 만든다.
        String filePath = folder + "/" + sysName;
        System.out.println("경로" + filePath);
        try {
            File file = new File(filePath);
            byte b[] = new byte[(int) file.length()];
            System.out.println("크기" + b.length );
            // page의 ContentType등을 동적으로 바꾸기 위해 초기화시킴
            response.reset();
            response.setContentType("application/octet-stream");
            
            // 한글 인코딩
            String encoding = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            
            // 파일 링크를 클릭했을 때 다운로드 저장 화면이 출력되게 처리하는 부분
            response.setHeader("Content-Disposition", "attachment;filename="+ encoding);
            response.setHeader("Content-Length", String.valueOf(file.length()));
            
            if (file.isFile()) // 파일이 있을경우
            {	
            	System.out.println("파일 발견");
                FileInputStream fileInputStream = new FileInputStream(file);
                ServletOutputStream servletOutputStream = response.getOutputStream();
                
                //  파일을 읽어서 클라이언트쪽으로 저장한다.
                int readNum = 0;
                while ( (readNum = fileInputStream.read(b)) != -1) {
                    servletOutputStream.write(b, 0, readNum);
                }
                
                servletOutputStream.close();
                fileInputStream.close();
            }
            
        } catch (Exception e) {
            System.out.println("Download Exception : " + e.getMessage());
        }
    }

}
