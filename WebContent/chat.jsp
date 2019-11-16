<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<meta charset="UTF-8">
<title>JSP 게시판</title>
 <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<script src = "http://code.jquery.com/jquery-1.7.js"></script>
	<style>
		.container{
			height : 310px;
			width : 100%;
			margin : none;
			padding : none;
		}
		.chatfield {
			height : 300px;
			width : 90%;
			overflow: auto; /* div 영역의 값에 따라 자동 스크롤. */
			float : left;
			border : 1px solid gray;
		}
		.inputArea{
			height : 100;
			width : 80%;
			overflow: none;
			margin:auto;
			text-align : center;
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
	<br>
	<div class = "container">
		<!-- Server responses get wriiten here -->
		<div class = "chatfield" id = "messages"></div>
	</div>
	<br>
	<div class = "inputArea">
		<%= (String)session.getAttribute("nickname") %> : <input type = "text" id ="messageinput" /> <button type = "button" onclick="send();">Send</button>
	</div>
	
	<!-- Script to utilise the WebSocket -->
	<script type="text/javascript">
		var webSocket;
		var messages = document.getElementById("messages");
		
		$(function() {
			   openSocket();			
		});
		
		function openSocket(){
			// Ensures only one connection is open at a time
			if(webSocket != undefined && webSocket.readyState != WebSocket.CLOSED){
				writeResponse("WebSocket is already opened");
				return;				
			}
			
			// Create a new instance of the websocket
			// webSocket = new WebSocket("ws://localhost:8081/ *ProjectName*/echo");
			webSocket = new WebSocket("ws://localhost:8081/Web_Project/websocketendpoint?");
			// Binds functions to the listeners for the websocket.
			webSocket.onopen = function(event){
				// For reasons I can't determine, onopen gets called twice
				// and the first time event.data is undefined.
				// Leave a commnet if you know the answer.
				if(event.date == undefined){
					return;
				}
				writeResponse(event.data);
			};
			
			webSocket.onmessage = function(event){
				writeResponse(event.data);
			};
			
			webSocket.onclose = function(event){
				writeResponse("Connection closed");
			};
		}
		
		function send() {
			var id = "<%= (String)session.getAttribute("nickname") %>";
			var text = document.getElementById("messageinput").value;
			webSocket.send(id + " : " + text);
		}
		
		function closeSocket(){
			webSocket.close();
		}
		
		function writeResponse(text){
			var strArray = text.split('/');
			for(var i=0; i<strArray.length; i++){
				console.log('str['+i+']: ' + strArray[i]);
			}
			if(strArray[0] == "msg"){
				messages.innerHTML += (strArray[1] + "<br />");
			}
			
		}
		
	</script>
</body>
</html>