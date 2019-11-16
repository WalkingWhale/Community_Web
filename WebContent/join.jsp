<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "java.lang.Math.*" %>
<%
	if(session.getAttribute("ValidMem") != null){
%>
		<jsp:forward page = "main.start" />
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
	<script src = "http://code.jquery.com/jquery-1.7.js" type="text/javascript"></script>	
    <title>Join</title>
    <style>
    	.test1{
    	
    	}
    </style>
    <script>
    	function audio(){
    		var rand = Math.random();
    		var url = 'captchaAudio.capt';
    		$.ajax({
    			url: url,
    			type: 'POST',
    			dataType: 'text',
    			data : 'rand=' + rand,
    			async: false,
    			success: function(resp){
    				var uAgent = navigator.userAgent;
    				var soundUrl = 'captchaAudio.capt?rand=' + rand;
    				if(uAgent.indexOf('Trident') > -1 || uAgent.indexOf('MSIE') > -1){
    					winPlayer(soundUrl);
    				} else if (!!document.createElement('audio').canPlayType){
    					try {
    						new Audio(SoundUrl).play();
    					} catch (e){
    						winPlayer(soundUrl);
    					}
    				}else {
    					window.open(soundUrl, '', 'width=1, height =1');
    				}
    			}
    		});
    	}
    	
    	function refreshBtn(type){
    		var rand = Math.random();
    		var url = "captchaImg.capt?rand=" + rand;
    		$('#captchaImg').attr("src", url);
    	}
    	
    	function winPlayer(objUrl){
    		$('#captchaAudio').html('<vgsound src="'+objUrl+'">');
    	}
    	
    	function chkAnswer(){
    		var url = 'Answercheck.capt';
    		$.ajax({
    			url: url,
    			type: 'POST',
    			dataType: 'text',
    			data : "answer=" + document.getElementById("captchaAnswer").value,
    			async: false,
    			success: function(data){
    				    				
    				if(data == 1){
    					alert("회원가입을 진행해 주세요");
    					document.getElementById("joinButton").disabled = false;
    				}    				
    				else {
    					alert("캡차 문자를 다시 확인해 주십시오");
    				}
    			}
    		});    		
    	}
  </script>
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

	<!-- 조인폼 -->
	<div class="container">
		<div class="col-lg-auto justify-content-center"></div>
		<div class="col-lg-auto">
			<!-- 점보트론 -->
			<div class="jumbotron" style="padding-top: 20px;">
				<!-- 개인 정보를 숨기면서 post형태로 전송 -->
				<form method="post" action="joinOk.do">
					<h3 style="text-align: center;">회원가입</h3>
					<div class="form-group">
						<input type="text" class="form-control" placeholder="아이디" name="id" maxlength="20">
					</div>

					<div class="form-group">
						<input type="password" class="form-control" placeholder="비밀번호" name="pw" maxlength="20">
					</div>

					<div class="form-group">
						<input type="text" class="form-control" placeholder="이름" name="name" maxlength="20">
					</div>
					
					<div class="form-group">
						<input type="text" class="form-control" placeholder="닉네임" name="nickname" maxlength="20">
					</div>					

					<div class="form-group">
						<input type="text" class="form-control" placeholder="이메일" name="email" maxlength="50">
					</div>
					
					<div class="form-group">
						<input type="text" class="form-control" placeholder="주소" name="address" maxlength="50">
					</div>
					
					<input id ="joinButton" type="submit" class="btn btn-primary form-control" value="회원가입" disabled>
				</form>
				<br />
				<div class = "captcha">
					<div class = "form-group">
						<img id = "captchaImg" title="캡차 이미지" src = "captchaImg.capt" alt ="캡차 이미지" />
						<div id="captchaAudio" style = "display:none;"></div>
					</div>
					<div class = "form-group">
						<a onclick = "refreshBtn()" class = "refreshBtn">
							<input type = "button" value = "새로고침" />
						</a>
						<a onclick = "audio()" class = "refreshBtn">
							<input type = "button" value = "음성듣기" />
						</a>
					</div>
					<div class = "form-group">
						<input type = "text" name = "captchaAnswer" id = "captchaAnswer" class = "from-control" placeholder = "보안문자를 입력하세요" >
					</div>
					<div>
						<a onclick = "chkAnswer()">
							<input type = "button" value = "입력" />
						</a>
					</div>					
				</div>
			</div>
		</div>
	</div>
	
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
  </body>
</html>