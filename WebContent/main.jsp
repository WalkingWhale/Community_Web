<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%
	if(session.getAttribute("ValidMem") != null){
		String name = (String)session.getAttribute("name");
		String id = (String)session.getAttribute("id");
		String nickname = (String)session.getAttribute("nickname");
	}
%>

<!DOCTYPE html>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel = "stylesheet" href = "css/custom.css">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<script src = "http://code.jquery.com/jquery-1.7.js"></script>	
    <title>JSP 게시판</title>
    <script type="text/javascript">
   	$(function() {
		   			
	});
    </script>
    <style>
    	@import url('https://fonts.googleapis.com/css?family=Nanum+Gothic&display=swap');
		@import url('https://fonts.googleapis.com/css?family=Do+Hyeon&display=swap');
		h2{
			font-family: 'Do Hyeon';
			color : #3399FF;
		}
		.ncLink {
			text-align : left;
		}
		.ncA {
			color : #ffffff;
		}
		
		.mainList{
			float : left;
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
      		<% if(session.getAttribute("id") == null) { %>
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

	<!-- 메인폼 -->
	<div class ="container">
		<div id="myCarousel" class="carousel slide" data-ride="carousel">
  			<ol class="carousel-indicators">
    			<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
    			<li data-target="#myCarousel" data-slide-to="1"></li>
    			<li data-target="#myCarousel" data-slide-to="2"></li>
 			</ol>
  			<div class="carousel-inner">
    			<div class="carousel-item active" data-interval="10000">
      				<img src="images/1.jpg" class="d-block w-100" alt="...">
      				<div class="ncLink carousel-caption d-none d-md-block">
          				<h2>공지사항 1</h2>
          				<a href = "content_view.nc?bId=48" class = "ncA">공지사항 1로 이동합니다.</a>
        			</div>
    			</div>
    			<div class="carousel-item" data-interval="10000">
     				<img src="images/2.jpg" class="d-block w-100" alt="...">
     				<div class="ncLink carousel-caption d-none d-md-block">
          				<h2>공지사항 2</h2>
          				<a href = "content_view.nc?bId=49" class = "ncA">공지사항 2로 이동합니다.</a>
        			</div>     				
    			</div>
    			<div class="carousel-item" data-interval="10000">
      				<img src="images/3.jpg" class="d-block w-100" alt="...">
      				<div class="ncLink carousel-caption d-none d-md-block">
          				<h2>공지사항 3</h2>
          				<a href = "content_view.nc?bId=50" class = "ncA">공지사항 3으로 이동합니다.</a>
        			</div>      				
    			</div>
  			</div>	
  			<a class="carousel-control-prev" href="#myCarousel" role="button" data-slide="prev">
    			<span class="carousel-control-prev-icon" aria-hidden="true"></span>
    			<span class="sr-only">Previous</span>
  			</a>
  			<a class="carousel-control-next" href="#myCarousel" role="button" data-slide="next">
    			<span class="carousel-control-next-icon" aria-hidden="true"></span>
    			<span class="sr-only">Next</span>
  			</a>
		</div>
	</div>
	<div style = "margin :auto;">
	<div style="border: 1px solid #BEE5EB; float: left; width: 32%; padding:10px;">	
		<table class = "mainList table table-bordered table-hover">
			<thead class="thead bg-info"><th scople = "col" colspan = "6"><a href = "list.nc"><span style="color: black;">공지사항</span></a></th></thead>
			<thead class="thead bg-info"> 
				<tr>
					<th scope="col">번호</th>
					<th scope="col">이름</th>
					<th scope="col">제목</th>
					<th scope="col">날짜</th>
					<th scope="col">조회수</th>
				</tr>	
			</thead>
		
			<c:forEach items = "${Nclist}" var = "dto">
			<tr>
				<td>${dto.bId}</td>
				<td>${dto.bName}</td>
				<td>
					<c:forEach begin = "1" end = "${dto.bIndent}">-</c:forEach>
					<a href = "content_view.nc?bId=${dto.bId}">${dto.bTitle}</a>
					<c:if test = "${dto.bNew eq true}">
						<span style = "color:red">- new</span>
					</c:if>
				</td>
				<td>${dto.bDate}</td>
				<td>${dto.bHit}</td>
			</tr>
			</c:forEach>	
		</table>
	</div>
	<div style="border: 1px solid #BEE5EB; float: left; width: 32%; padding:10px;">		
		<table class = "mainList table table-bordered table-hover">
			<thead class="thead bg-info"><th scople = "col" colspan = "6"><a href = "list.bbs"><span style="color: black;">게시판</span></a></th></thead>
			<thead class="thead bg-info"> 
				<tr>
					<th scope="col">번호</th>
					<th scope="col">작성자</th>
					<th scope="col">제목</th>
					<th scope="col">날짜</th>
					<th scope="col">조회수</th>
				</tr>	
			</thead>
		
			<c:forEach items = "${Blist}" var = "dto">
			<tr>
				<td>${dto.bId}</td>
				<td>${dto.bName}</td>
				<td>
					<c:forEach begin = "1" end = "${dto.bIndent}">-</c:forEach>
					<a href = "content_view.bbs?bId=${dto.bId}">${dto.bTitle}</a>
					<c:if test = "${dto.bNew eq true}">
						<span style = "color:red">- new</span>
					</c:if>		
					</td>
					<td>${dto.bDate}</td>
					<td>${dto.bHit}</td>
				</tr>
			</c:forEach>
		</table>
	</div>	
	<div style="border: 1px solid #BEE5EB; float: left; width: 32%; padding:10px;">	
		<table class = "mainList table table-bordered table-hover">
			<thead class="thead bg-info"><th scople = "col" colspan = "6"><a href = "list.data"><span style="color: black;">자료실</span></a></th></thead>
			<thead class="thead bg-info"> 
				<tr>
					<th scope="col">번호</th>
					<th scope="col">이름</th>
					<th scope="col">제목</th>
					<th scope="col">날짜</th>
					<th scope="col">조회수</th>
				</tr>	
			</thead>
		
			<c:forEach items = "${Rlist}" var = "dto">
			<tr>
				<td>${dto.bId}</td>
				<td>${dto.bName}</td>
				<td>
					<c:forEach begin = "1" end = "${dto.bIndent}">-</c:forEach>
					<a href = "content_view.data?bId=${dto.bId}">${dto.bTitle}</a>
					<c:if test = "${dto.bNew eq true}">
						<span style = "color:red">- new</span>
					</c:if>		
				</td>
				<td>${dto.bDate}</td>
				<td>${dto.bHit}</td>
			</tr>
			</c:forEach>
		</table>
	</div>
	</div>
	
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
  </body>
</html>