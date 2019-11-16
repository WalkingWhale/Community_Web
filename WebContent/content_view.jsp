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
    <script>
    
    var httpRequest = null;
    
    // httpRequest 객체 생성
    function getXMLHttpRequest(){
        var httpRequest = null;
    
        if(window.ActiveXObject){
            try{
                httpRequest = new ActiveXObject("Msxml2.XMLHTTP");    
            } catch(e) {
                try{
                    httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
                } catch (e2) { httpRequest = null; }
            }
        }
        else if(window.XMLHttpRequest){
            httpRequest = new window.XMLHttpRequest();
        }
        return httpRequest;    
    }

    
 	// 댓글 등록
	function writeCmt()
	{
		var form = document.getElementById("writeCommentForm");
	
		var board = form.comment_board.value
		var id = form.comment_id.value
		var content = form.comment_content.value;
		
		if(!content)
		{
			alert("내용을 입력하세요.");
			return false;
		}
		else
		{	
			var param="comment_board="+board+"&comment_id="+id+"&comment_content="+content;
		
			httpRequest = getXMLHttpRequest();
			httpRequest.onreadystatechange = checkFunc;
			httpRequest.open("POST", "CommentWriteAction.co", true);	
			httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;charset=UTF-8'); 
			httpRequest.send(param);
		}
	}
	
	function checkFunc(){
		if(httpRequest.readyState == 4){
			// 결과값을 가져온다.
			var resultText = httpRequest.responseText;
			if(resultText == 1){ 
				document.location.reload(); // 상세보기 창 새로고침
			} else if(resultText == 4){
				alert("신고하였습니다.");
				document.location.reload(); // 신고 후 상세보기 창 새로고침
			}
		}
	}
	
	function cmReplyOpen(comment_num)
    {
        var userId = '${sessionScope.id}';
        
        if(userId == "" || userId == null){
            alert("로그인후 사용가능합니다.");
            return false;
        }
        else{
            // 댓글 답변창 open
            window.name = "parentForm";
            window.open("CommentReplyFormAction.co?num="+comment_num, "replyForm", "width=570, height=350, resizable = no, scrollbars = no");
        }
    }
	
	// 댓글 삭제 확인
	function cmDeleteOpen(comment_num){
        var msg = confirm("댓글을 삭제합니다.");    
        if(msg == true){ // 확인을 누를경우
            deleteCmt(comment_num);
        }
        else{
            return false; // 삭제취소
        }
    }
	
	// 댓글 삭제
    function deleteCmt(comment_num)
    {
        var param="comment_num="+comment_num;
        
        httpRequest = getXMLHttpRequest();
        httpRequest.onreadystatechange = checkFunc;
        httpRequest.open("POST", "CommentDeleteAction.co", true);    
        httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;charset=UTF-8'); 
        httpRequest.send(param);
    }
	
 	// 댓글 신고
    function cmReport(comment_num)
    {
        var param="comment_num="+comment_num;
        
        httpRequest = getXMLHttpRequest();
        httpRequest.onreadystatechange = checkFunc;
        httpRequest.open("POST", "CommentReportAction.co", true);    
        httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;charset=UTF-8'); 
        httpRequest.send(param);
    }
	
	 // 댓글 수정창
    function cmUpdateOpen(comment_num){
        window.name = "parentForm";
        window.open("CommentUpdateFormAction.co?num="+comment_num,
                    "updateForm", "width=570, height=350, resizable = no, scrollbars = no");
    }
	
	</script>
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
				<th scope="col">좋아요</th>
			</tr>	
		</thead>		
		
		<tr>
			<td>${content_view.bId}</td>
			<td>${content_view.bName}</td>
			<td>${content_view.bDate}</td>
			<td>${content_view.bHit}</td>
			<td>${content_view.bLike}</td>
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
	
	<!-- 댓글 부분 -->	
	<div class="comment" id = "comment">
		<center>
		<table border="1" bordercolor="lightgray">
			<!-- 댓글 목록 -->	
			<c:forEach var="comment" items="${commentList}">		
			<tr>
				<!-- 아이디, 작성날짜 -->
				<td width="150">
					<div>
						<c:if test="${comment.comment_level > 1}">
                            &nbsp;&nbsp;&nbsp;&nbsp; <!-- 답변글일경우 아이디 앞에 공백을 준다. -->
                            <img src="img/reply_icon.gif">
                        </c:if>
                        
						${comment.comment_id}<br>
						<font size="2" color="lightgray">${comment.comment_date}</font>
					</div>
				</td>
				<!-- 본문내용 -->
				<td width="550">
					<div class="text_wrapper">
						${comment.comment_content}
					</div>
				</td>
				<!-- 버튼 -->
				<td width="100">
					<div id="btn" style="text-align:center;">
						<a href="#" onclick="cmReplyOpen(${comment.comment_num})">[답변]</a><br>
						<a href="#" onclick= "cmReport(${comment.comment_num})">[신고]</a><br>
					<!-- 댓글 작성자만 수정, 삭제 가능하도록 - 삭제는 admin도 가능 -->
					<c:if test="${comment.comment_id == sessionScope.id}">
					<a href="#" onclick="cmUpdateOpen(${comment.comment_num})">[수정]</a><br>
					</c:if>		
					<c:if test="${comment.comment_id == sessionScope.id||sessionScope.admin eq 'yes'}">
						<a href="#" onclick="cmDeleteOpen(${comment.comment_num})">[삭제]</a>
					</c:if>		
					</div>
				</td>
			</tr>			
			</c:forEach>
			
			<!-- 로그인 했을 경우만 댓글 작성가능 -->
			<% if(session.getAttribute("id") != null){ %>
			<tr bgcolor="#F5F5F5">
			<form id="writeCommentForm">
				<input type="hidden" name="comment_board" value="${content_view.bId}">
				<input type="hidden" name="comment_id" value="${sessionScope.id}">
				<!-- 아이디-->
				<td width="150">
					<div style="text-align:center;">
						<%= session.getAttribute("id") %>
					</div>
				</td>
				<!-- 본문 작성-->
				<td width="550">
					<div>
						<textarea name="comment_content" rows="4" cols="70" ></textarea>
					</div>
				</td>
				<!-- 댓글 등록 버튼 -->
					<td width="100">
						<div id="btn" style="text-align:center;">
							<p><a href="#" onclick="writeCmt()">[댓글등록]</a></p>	
						</div>
					</td>
			</form>
			</tr>
			<% } %>	
		</table>
		</center>
	</div>
	
	
	<div class = "container">
		<center>
		<ul class="horizontal-menu">
    		<li>
       			<a href = "list.bbs?page=<%= session.getAttribute("cpage") %>">목록보기</a>
    		</li>
	   		<% if(session.getAttribute("id") != null) {%>
    		<li>
        		<a href = "reply_view.bbs?bId=${content_view.bId}">답변</a>
	    	</li>
			<li>      			
        		<a href = "like.bbs?bId=${content_view.bId}">좋아요</a>
	    	</li>
	    	<li>
	    		<a href = "report.bbs?bId=${content_view.bId }">신고</a>
	    	</li>
    		<% } %>
    		<c:if test="${content_view.bUser == sessionScope.id}">
    		<li>
        		<a href = "modify_view.bbs?bId=${content_view.bId}">수정</a>	
	    	</li>
	    	</c:if>
	    	<c:if test="${content_view.bUser == sessionScope.id || sessionScope.admin != null}">
    		<li>
        		<a href = "delete.bbs?bId=${content_view.bId}">삭제</a>
	    	</li>
	    	</c:if>
	    	
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