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
    
	<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
  	<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script> 
  	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 
  	<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote.css" rel="stylesheet">
  	<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote.js"></script>
 <!--   <script type="text/javascript" src="./naver-editor/js/service/HuskyEZCreator.js" charset="utf-8"></script>
  <script>
  	function form_check(){
  		oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
  			
  		document.modify_form.submit();	
  	}
  </script> -->
</head>
<body>
	
	<!-- 네비게이션 -->
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
  		<a class="navbar-brand" href="main.jsp">JSP 게시판</a>
  			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
    			<span class="navbar-toggler-icon"></span>
 			 </button>
  		<div class="collapse navbar-collapse" id="navbarTogglerDemo02">
    		<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
      			<li>
        			<a class="nav-link" href="main.jsp">메인</a>
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
	
	<!--  글 수정 폼 -->
	<table width = "500" cellpadding = "0" cellspacing = "0" border = "1">
		<form action ="modify.data" method = "post">
			<input type = "hidden" name = "bId" value = "${content_view.bId}">
			<tr>
				<td>번호</td>
				<td>${content_view.bId}</td>
			</tr>
			<tr>
				<td>히트</td>
				<td>${content_view.bHit}</td>	
			</tr>
			<tr>
			<td>좋아요</td>
				<td>${content_view.bLike}</td>
			</tr>
			<tr>
				<td>이름</td>
				<td><input type = "text" name = "bName" value = "${content_view.bName}" readonly></td>
			</tr>
			<tr>
				<td>제목</td>
				<td><input type = "text" name = "bTitle" value = "${content_view.bTitle}"></td>
			</tr>
			<table class = "table table-bordered">
				<thead class="thead bg-info"> 
					<tr class = "title">
						<th scope="col">첨부파일</th>
					</tr>	
				</thead>			
				<tr>				
					<td><a href ='download.data?fileName=${content_view.fileName}'>${content_view.fileName}</a></td>
				</tr>
			</table>
			<tr>
				<td>내용</td>
				<td>
					<textarea name = "bContent" id = "summernote" rows = "10" cols = "100">${content_view.bContent}</textarea>
					<!--<script type="text/javascript">
						var oEditors = [];
						nhn.husky.EZCreator.createInIFrame({
    					oAppRef: oEditors,
    					elPlaceHolder: "ir1",
    					sSkinURI: "./naver-editor/SmartEditor2Skin.html",
    					fCreator: "createSEditor2"
						});
					</script>-->
					<script>
     					$('#summernote').summernote({
     						options : {disableDragAndDrop: false},
     						lang : 'ko-KR',
        					height: 500,
        					toolbar: [
        						['style', ['style']],
        						['style', ['bold', 'italic', 'underline', 'strikethrough', 'clear']],
        						['fontface', ['fontname']],
        						['textsize', ['textsize']],
        						['color', ['color']],
        						['alignment', ['ul', 'ol', 'paragraph', 'lineheight']],
        						['height', ['height']],
        						['table', ['table']],
        						['insert', ['insert']],        						
        					]
     					 });
    				</script>
				</td>
			</tr>
			<tr>
				<td colspan = "2">
					<input class = "btn btn-primary form-control" type = "submit" value = "수정"> &nbsp;&nbsp;
					<a href = "content_view.data?bId=${content_view.bId}">취소</a> &nbsp;&nbsp;
					<a href = "list.data?page=<%= session.getAttribute("cpage") %>>">목록보기</a> &nbsp;&nbsp;
				</td>			
			</tr>
		</form>		
	</table>
	
	<!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>