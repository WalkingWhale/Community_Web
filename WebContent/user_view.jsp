<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>    
<%
	if(session.getAttribute("ValidMem") != null){
		String name = (String)session.getAttribute("name");
		String id = (String)session.getAttribute("id");
		String nickname = (String)session.getAttribute("nickname");
	} else {
	%>
		<jsp:forward page = "login.jsp" />
	<%		
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
    	h2 {
			text-align: center;
    		vertical-align: middle;
		}
		
		.arSearch{
			align: center;
			vertical-align: middle;
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
	
	<!--  유저 상세 폼 -->
	<h2>유저 상세 정보</h2>
	<table class = "table table-bordered table-hover">
		<thead class="thead bg-info"> 
			<tr>
				<th scope="col">아이디</th>
				<th scope="col">이름</th>				
				<th scope="col">닉네임</th>
				<th scope="col">이메일</th>
				<th scope="col">블락여부</th>
				<th scope="col">가입일</th>
				<th scope="col">주소</th>
				<th scope="col">총 개시글</th>
				<th scope="col">총 댓글</th>
			</tr>	
		</thead>		
		<tr>
			<td>${user_view.id}</td>
			<td>${user_view.name}</td>
			<td>${user_view.nickname}</td>
			<td>${user_view.email}</td>
			<td>
				<c:choose>
					<c:when test = "${user_view.ban eq '1'}">
						블락 계정
						<button class="btn btn-suceess my-2 my-sm-0" onclick = "location.href='banUser.user?banid=${user_view.id}'">밴 해제</button>
					</c:when>
					<c:when test = "${user_view.ban eq '0'}">
						일반 계정
						<button class="btn btn-outline-success my-2 my-sm-0" onclick = "location.href='banUser.user?banid=${user_view.id}'">밴</button>
					</c:when>
				</c:choose>
				<button class = "btn btn-outline-success my-2 my-sm-0" onclick = "location.href='deleteUser.user?id=${user_view.id}'">강제 탈퇴</button>
			</td>
			<td>${user_view.rDate}</td>
			<td>${user_view.address}</td>
			<td>${user_view.nContent}</td>
			<td>${user_view.nReply}</td>
		</tr>	
	</table>
	
	<!-- 유저 검색 -->
	<div class = "arSearch container align-items-center">
		<form class = "form-inline align-item-center" method="post" action="user_search.user">
			<div class="form-group col-lg-autor">
				<select name = "sOption" class="form-control">
    				<option value="nickname" selected>닉네임</option>
    				<option value="id">id</option>
				</select>
				<input type = "serach" name = "search" class = "form-control mr-sm-2" placeholder = "Search" aria-label="Search">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">검색</button>
			</div>
		</form>
	</div>
	
	<h2>작성 게시글</h2>
	<!--  유저 게시글 폼 -->
	<table class = "table table-bordered table-hover">
		<thead class="thead bg-info"> 
			<tr>
				<th scope="col">번호</th>
				<th scope="col">이름</th>
				<th scope="col">제목</th>
				<th scope="col">날짜</th>
				<th scope="col">히트</th>
				<th scope="col">좋아요</th>
			</tr>	
		</thead>
		
		<c:forEach items = "${list}" var = "dto">
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
			<td>${dto.bLike}</td>
		</tr>
		</c:forEach>
		<!-- <tr>		
			<td colspan = "5"><a href = "write_view.do">글작성</a></td>
		</tr> -->		
		<tr>			
			<td colspan = "6">
			<nav>
				<ul class="pagination pg-blue justify-content-center">
					<!-- 처음 -->
					<c:choose>
						<c:when test="${(page.curPage -1) < 1}">
							<li class="page-item disabled"><a class="page-link disabled" href="#">First</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link" href="user_view.user?id=${user_view.id}&page=1">First</a></li>
						</c:otherwise>
					</c:choose>
					<!-- 이전 -->
					<c:choose>
						<c:when test="${(page.curPage -1) < 1}">
							<li class="page-item disabled"><a class="page-link disabled" href="user_view.user?id=${user_view.id}&page=${page.curPage -1}">Previous</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link" href="user_view.user?id=${user_view.id}&page=${page.curPage -1}" >Previous</a></li>			
						</c:otherwise>
					</c:choose>
			
					<!-- 개별페이지 -->
					<c:forEach var= "fEach" begin="${page.startPage}" end= "${page.endPage}" step="1">
					<c:choose>
						<c:when test="${page.curPage == fEach}">
							<li class="page-item active"><a class="page-link" href="#">${fEach}</a></li> &nbsp; &nbsp;
						</c:when>
					<c:otherwise>
						<li class="page-item"><a class="page-link" href="user_view.user?id=${user_view.id}&page=${fEach}">${fEach}</a></li> &nbsp;						
					</c:otherwise>
					</c:choose>
					</c:forEach>
			
					<!-- 다음 -->
					<c:choose>
						<c:when test='${(page.curPage + 1) > page.totalPage}'>
							<li class="page-item disabled"><a class="page-link disabled" href="user_view.user?id=${user_view.id}&page=${page.curPage +1}">Next</a></li>
						</c:when>
						<c:otherwise>	
							<li class="page-item"><a class="page-link" href="user_view.user?id=${user_view.id}&page=${page.curPage + 1}">Next</a></li>
						</c:otherwise>
					</c:choose>
			
					<!-- 끝 -->
					<c:choose>
						<c:when test='${page.curPage == page.totalPage}'>
							<li class="page-item disabled"><a class="page-link disabled" href="#" onclick = "prev();">Last</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link" href="user_view.user?id=${user_view.id}&page=${page.totalPage}">Last</a></li>
						</c:otherwise>
					</c:choose>
				</ul>				
			</nav>
			</td>		
		</tr>
	</table>
	
	<!-- 유저 작성글 검색 -->
	<div class = "arSearch container align-items-center">
		<form class = "form-inline align-item-center" method="post" action="user_content_search.user">
			<div class="form-group col-lg-autor">
				<select name = "sOption" class="form-control">
    				<option value="title" selected>제목</option>
    				<option value="content">내용</option>
				</select>
				<input type = "serach" name = "search" class = "form-control mr-sm-2" placeholder = "Search" aria-label="Search">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">검색</button>
			</div>
		</form>
	</div>
	
	<!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>