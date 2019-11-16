<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.study.jsp.DAO.*" %>
<%@ page import = "com.study.jsp.DTO.*" %>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	if(session.getAttribute("ValidMem") == null){		
%>
	<jsp:forward page = "main.start" />
<%
	}
%>
<%
	if(session.getAttribute("sns") != null){		
%>
	<jsp:forward page = "modifySNS.jsp" />
<%
	}

	String name = (String)session.getAttribute("name");
	String id = (String)session.getAttribute("id");
	String nickname = (String)session.getAttribute("nickname");
		
	MemberDAO dao = MemberDAO.getInstance();
	MemberDTO dto = dao.getMember(id);
		
%>
<!DOCTYPE html>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<script src = "http://code.jquery.com/jquery-1.7.js"></script>	
    <title>login</title>
    <style>
    	.test1{
    	
    	}
    </style>
  </head>
  <body>
  	<!-- 네비게이션  -->
  	<nav class="navbar navbar-expand-lg navbar-light bg-light">
  		<a class="navbar-brand" href="main.start">JSP 게시판</a>
  			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
    			<span class="navbar-toggler-icon"></span>
 			 </button>
  		<div class="collapse navbar-collapse" id="navbarTogglerDemo02">
    		<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
      			<li>
        			<a class="nav-link" href="main.start">메인</a>
      			</li>
      			<li>
        			<a class="nav-link" href="list.nc">공지사항</a>
      			</li>
      			<li>
        			<a class="nav-link" href="list.bbs">게시판</a>
      			</li>
      			<li>
        			<a class="nav-link" href="list.data">자료실</a>
        		</li>
        		<li>
        			<a class="nav-link" href="chat.jsp">놀이터</a>
        		</li>      			
      			<% if(session.getAttribute("admin") != null) {%>
      				<li class = "dropdown nav-link">
      					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">관리<span class="caret"></span></a>
						<ul class = "dropdown-menu">
							<li><a class="nav-link" href="list.user">유저관리</a></li>
							<li><a class="nav-link" href="mnArticle.user">게시글관리</a></li>
							<li><a class="nav-link" href="mnComment.user">댓글관리</a></li>
						</ul>						
					</li>
				<% } %>
				<li>      			
        			<a class="nav-link" href="way.jsp">찾아오시는 길</a>
      			</li>
      		</ul>
      		<% if(session.getAttribute("id") == null){ %>
      		<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">접속하기<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li class="active"><a href="login.jsp">로그인</a></li>
						<li><a href="join.jsp">회원가입</a></li>
					</ul>
				</li>
			</ul> 
			<% } else { %>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">환영합니다. <%= (String) session.getAttribute("nickname") %> <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li class="active"><a href="modify.jsp">회원 정보 수정</a></li>
						<li><a href="withdraw.jsp">회원 탈퇴</a></li>
						<li><a href="logout.do">로그아웃</a></li>
					</ul>
				</li>
			</ul> 
			<% } %>		   
  		</div>
	</nav>

	<!-- 모디파이 폼 -->
	<div class="container">
		<div class="col-lg-auto justify-content-center"></div>
		<div class="col">
			<!-- 점보트론 -->
			<div class="jumbotron" style="padding-top: 20px;">
				<!-- 개인 정보를 숨기면서 post형태로 전송 -->
				<form action = "modifyOk.do" method = "post" name = "reg_frm">
					<h3 style="text-align: center;">회원정보 수정</h3>
					<div class="form-group">
						아이디 : <input type = "text" name = "id" size = "20" value = "<%= dto.getId() %>" readonly /> <br>
					</div>
					<div class="form-group">
						비밀번호 : <input type = "password" name = "pw" size = "20" value = "<%= dto.getPw() %>"><br>
					</div>
					<div class="form-group">
						비밀번호 확인 : <input type = "password" name = "pw_check" size = "20" value = "<%= dto.getPw() %>"><br>
					</div>
					<div class="form-group">
						이름 : <input type = "text" name = "name" size = "20" value = "<%= dto.getName() %>" readonly /><br>
					</div>
					<div class="form-group">
						닉네임 : <input type = "text" name = "nickname" size = "20" value = "<%= dto.getNickname() %>"><br>
					</div>
					<div class="form-group">
						메일 : <input type = "text" name = "email" size = "20" value = "<%= dto.getEmail() %>"><br>
					</div>
					<div class="form-group">
						주소 : <input type = "text" name = "address" size = "20" value = "<%= dto.getAddress() %>"><br>
					</div>
					<div class="form-group">
						<input type = "submit" class = "btn btn-primary form-control" value = "수정"> &nbsp;&nbsp;&nbsp;
						<input type = "reset" class = "btn btn-primary form-control" value = "취소" onclick = "javascript:window.location = 'main.start'">
					</div>				
				</form>
			</div>
		</div>
	</div>
	
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
  </body>
</html>