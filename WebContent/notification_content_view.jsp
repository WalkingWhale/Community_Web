<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if(session.getAttribute("ValidMem") != null){
		String name = (String)session.getAttribute("name");
		String id = (String)session.getAttribute("id");
		String nickname = (String)session.getAttribute("nickname");
	} else {
		
	}
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
    <title>JSP 게시판</title>
    <style>
    body{
    	width : 100%;
    	height : 100%;
    }
    h2{    	
		text-align: center;
    	vertical-align: middle;		
    }
    
    li{
    	list-style-type: none;
    }
    
    .horizontal-menu{
    	width : 70%;
		display: inline-block;
		overflow: hidden;
		margin : auto;
	}
		
	.wrapper {
        text-align: center;
    }
    
	.horizontal-menu li {
		float: left;
		margin : auto;
	}
	
	.horizontal-menu a {
		display: block;
		height: 50px;
		line-height: 50px;
		background-color: #3ea3ba;
		color: #ddd;
		padding: 0 35px;
		border-right: 1px solid #358da1;
		
	}
	
	.title{
		text-align:center;
	}
	
	.comment{
		margin:auto;
	}
    </style>
</head>
<body>
	<!-- 네비게이션 -->
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
	
	<!--  글내용 폼 -->
	<h2>${content_view.bTitle }</h2>
	
	<table class = "table table-bordered">
		<thead class="thead bg-info"> 
			<tr>
				<th scope="col">번호</th>
				<th scope="col">작성자</th>
				<th scope="col">날짜</th>
				<th scope="col">조회수</th>
			</tr>	
		</thead>		
		
		<tr>
			<td>${content_view.bId}</td>
			<td>${content_view.bName}</td>
			<td>${content_view.bDate}</td>
			<td>${content_view.bHit}</td>
		</tr>
		
	</table>
	<table class = "table table-bordered" height = "500">
		<thead class="thead bg-info"> 
			<tr class = "title">
				<th scope="col">게시글</th>
			</tr>	
		</thead>			
		<tr>			
			<td>${content_view.bContent}</td>
		</tr>		
	</table>	
	
	<div class = "container">
	<center>
		<ul class="horizontal-menu">
    		<li>
       			<a href = "list.nc?page=<%= session.getAttribute("cpage") %>" class = "btn btn-primary">목록보기</a>
    		</li>
	   		<% if(session.getAttribute("admin") != null) {%>
    		<li>
        		<a href = "modify_view.nc?bId=${content_view.bId}" class = "btn btn-primary">수정</a>
	    	</li>
    		<li>
        		<a href = "delete.nc?bId=${content_view.bId}" class = "btn btn-primary">삭제</a>
	    	</li>    		
    		<% } %>
     	</ul>
    </center>
	</div>
	
	<!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>