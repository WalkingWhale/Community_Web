<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
	<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width"/>
    <meta name="google-signin-client_id" content="22347688088-lpagjdt8vm4hgqlfm65f35rvqtskt08k.apps.googleusercontent.com">
    <script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
    
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<script src = "http://code.jquery.com/jquery-1.7.js"></script>	
    <title>login</title>
    <style>
		.btnArea .btnNaver{display:block;margin:10px 0 0;border:0;font-size:13px;color:#fff;text-align:center;height:34px;line-height:33px;padding:0 0 0 45px;background:#26c826 url(http://img.echosting.cafe24.com/skin/mobile_ko_KR/member/ico_btn_naver.png) no-repeat 13px 0;background-size:33px 33px;-moz-box-sizing:border-box;box-sizing:border-box}

		.btnArea .btnFacebook{display:block;margin:10px 0 0;border:0;font-size:13px;color:#fff;text-align:center;height:34px;line-height:33px;padding:0 0 0 45px;background:#3b5998 url(http://img.echosting.cafe24.com/skin/mobile_ko_KR/member/ico_btn_facebook.png) no-repeat 13px 0;background-size:32px 33px;-moz-box-sizing:border-box;box-sizing:border-box}

		.btnArea .btnGoogle{display:block;margin:10px 0 0;border:0;font-size:13px;color:#fff;text-align:center;height:34px;line-height:33px;padding:0 0 0 45px;background:#df4a32 url(http://img.echosting.cafe24.com/skin/mobile_ko_KR/member/ico_btn_google.png) no-repeat 12px 1px;background-size:33px 33px;-moz-box-sizing:border-box;box-sizing:border-box}

		.btnArea .btnKakao{display:block;margin:10px 0 0;border:0;font-size:13px;color:#3c1e1e;text-align:center;height:34px;line-height:33px;padding:0 0 0 45px;background:#ffeb00 url(http://img.echosting.cafe24.com/skin/mobile/member/ico_btn_kakao.png) no-repeat 11px 1px;background-size:auto 33px;-moz-box-sizing:border-box;box-sizing:border-box}			
</style>
<script>
   
   function formcheck() {
      submit();
   }
   function submit() {
      $.ajax({
         data: $('#loginProcess').serialize(),
         url: 'login.sns',
         type: 'post',
         dataType: 'text',
         success: function (json) {
            var result = JSON.parse(json);
            if(result.code == "success"){
               /* alert(result.desc); */
               window.location.replace("main.start");
            }else{
               alert(result.desc);
            }
         }
      });
   }
      function onSignIn(googleUser) {
          var profile = googleUser.getBasicProfile();
          console.log(profile);
          console.log(profile.getId());
          var pname = profile.getName();
          var pid = profile.getId();
          $.ajax({
            data: {id:pid,name:pname},
            url: 'login.sns',
            type: 'post',
            dataType: 'text',
            success: function (json) {
               var result = JSON.parse(json);
               if(result.code == "success"){
                  /* alert(result.desc); */
                  signOut1();
                  $('#my-signin2').css('display', 'none');
                  $('#logout1').css('display', 'block');
                  $('#upic1').attr('src', profile.getImageUrl());
                  $('#uname').html('[ ' +profile.getName() + ' ]');
                  window.location.replace("main.start");
               }else{            	   
            	  signOut1();
                  alert(result.desc);
               }
            }
         });
      }
      
      function onFailure(error) {
    	  
      }
      function signOut1() {
          var auth2 = gapi.auth2.getAuthInstance();
          auth2.signOut().then(function () {
             $('#my-signin2').css('display', 'block');
             $('#logout1').css('display', 'none');
             $('#upic1').attr('src', '');
             $('#uname').html('');
          });
      }
       function renderButton() {
           gapi.signin2.render('my-signin2', {
              'scope': 'profile email',
              'width': 'border-box',
              'height': 34,
              'longtitle': true,
              'theme': 'dark',
              'onsuccess': onSignIn,
              'onfailure': onFailure
           });
       }
       $(document).ready(function() {
          
       });
       
       window.fbAsyncInit = function() {
           FB.init({
             appId      : '448856675839841',
             cookie     : true,
             xfbml      : true,
             version    : 'v4.0'
           });

           FB.getLoginStatus(function(response) {
              console.log(response);
             statusChangeCallback(response);
           });
         };

         // Load the SDK asynchronously
         (function(d, s, id) {
           var js, fjs = d.getElementsByTagName(s)[0];
           if (d.getElementById(id)) return;
           js = d.createElement(s); js.id = id;
           js.src = "https://connect.facebook.net/en_US/sdk.js";
           fjs.parentNode.insertBefore(js, fjs);
         }(document, 'script', 'facebook-jssdk'));

         function statusChangeCallback(response) {
           if (response.status === 'connected') {
             getINFO();
           } else {
             $('#login2').css('display', 'block');
             $('#logout2').css('display', 'none');
             $('#upic2').attr('src', '');
             $('#uname').html('');
           }
         }
            
         function fbLogin () {
           FB.login(function(response){
             statusChangeCallback(response);
           }, {scope: 'public_profile, email'});
         }

         function fbLogout () {
           FB.logout(function(response) {
             statusChangeCallback(response);
           });
         }

         function getINFO() {
           FB.api('/me?fields=id,name,picture.width(100).height(100).as(picture_small)', function(response) {
           console.log(response);
           console.log(response.id);
          var pname = response.name;
          var pid = response.id;
          $.ajax({
            data: {id:pid,name:pname},
            url: 'login.sns',
            type: 'post',
            dataType: 'text',
            success: function (json) {
               var result = JSON.parse(json);
               if(result.code == "success"){
                  /* alert(result.desc); */
                  fbLogout();
                  window.location.replace("main.start");
               }else{
                  alert(result.desc);
               }
            }
         });
             $('#login2').css('display', 'none');
             $('#logout2').css('display', 'block');
             $('#upic2').attr('src', response.picture_small.data.url );
             $('#uname').html('[ ' + response.name + ' ]');
           });
         }
         
         Kakao.init('3476ea5dcef848b85d6b04e023bc1fa4');
         function loginWithKakao() {
           // 로그인 창을 띄웁니다.
           Kakao.Auth.login({
             success: function(authObj) {
               //alert(JSON.stringify(authObj));
               signIn(authObj);
             },
             fail: function(err) {
               alert(JSON.stringify(err));
             }
           });
         };

         function signIn(authObj) {
             //console.log(authObj);
             Kakao.API.request({
                 url: '/v2/user/me',
                 success: function(res) {
                     console.log(res);
                     console.log(res.id);
                     var pname = res.properties.nickname;
                     var pid = res.id;
                   $.ajax({
                     data: {id:pid,name:pname},
                     url: 'login.sns',
                     type: 'post',
                     dataType: 'text',
                     success: function (json) {
                        var result = JSON.parse(json);
                        if(result.code == "success"){
                           /* alert(result.desc); */
                           signOut3();
                           window.location.replace("main.start");
                        }else{
                           alert(result.desc);
                        }
                     }
                  });
                     $('#login3').css('display', 'none');
                       $('#logout3').css('display', 'block');
                     $('#upic3').attr('src', res.properties.thumbnail_image );
                       $('#uname').html('[ ' + res.properties.nickname + ' ]');
                  }
              })
        }

         function signOut3() {
            Kakao.Auth.logout(function () {
               $('#login3').css('display', 'block');
               $('#logout3').css('display', 'none');
               $('#upic3').attr('src', '');
               $('#uname').html('');
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
      		<% if(session.getAttribute("ValidMem") == null){ %>
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

	<!-- 로그인폼 -->
	<div class="container align-items-center">
		<div class="col-lg-auto justify-content-center"></div>
		<div class="col-lg-auto">
			<!-- 점보트론 -->
			<div class="jumbotron" style="padding-top: 20px;">
				<!-- 로그인 정보를 숨기면서 전송post -->
				<form method="post" action="loginOk.do">
					<h3 style="text-align: center;"> 로그인 </h3>
					<div class="form-group">
						<input type="text" class="form-control" placeholder="아이디" name="id" maxlength="20">
					</div>
					<div class="form-group">
						<input type="password" class="form-control" placeholder="비밀번호" name="pw" maxlength="20">
					</div>
					<br>
					<div>
						<input type="submit" class="btn btn-primary form-control" value="로그인">
					</div>
					<br>		
					<div>
						<input type="button" class="btn btn-primary form-control" onclick = "location.href='join.jsp'" value="회원가입">
					</div>					
				</form>
			</div>
			<br>
			<div class="btnArea type1">																	
				<div id="my-signin2"></div>
          		<div id="logout1" style="display: none;">
          			<input type="button" class="btn btn-success" onclick="signOut1();" value="로그아웃" /><br>      
         		 	<img id="upic1" src=""><br>
          		<span id="uname"></span>
          		</div>       
        		<script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>
        			 
				<div id="login2" style="display: block;">
     				<div class="btnArea type1">
          				<a href="#none" class="btnFacebook " onclick="fbLogin();"><span>Facebook 로그인</span></a>
      				</div>
      			</div>      			
				<div id="logout2" style="display: none;">
          			<input type="button" class="btn btn-success" onclick="fbLogout();" value="로그아웃" /><br>      
          			<img id="upic2" src=""><br>
         			<span id="uname"></span>
      			</div>
      			
				<div id="login3" style="display: block">
          			<div class="btnArea type1">
             			<a id="custom-login-btn" href="javascript:loginWithKakao()" class="btnKakao " onclick="MemberAction.snsLogin('kakao', '%2Findex.html')"><span>카카오계정 로그인</span></a>
          			</div>
      			</div>
      
      			<div id="logout3" style="display: none;">
          			<input type="button" class="btn btn-success" onclick="signOut3();" value="로그아웃" /><br>      
          			<img id="upic3" src=""><br>
            		<span id="uname"></span>
      			</div>
      			
      			<div id="naverIdLogin">
         			<div class="btnArea type1">
            			<a id="naverIdLogin_loginButton" href="#" role="button"class="btnNaver " onclick="MemberAction.snsLogin('naver', '%2Findex.html')"><span>네이버 로그인</span></a>
          			</div>
            		<!-- <img src="https://static.nid.naver.com/oauth/big_g.PNG" width=320></a> -->
         		</div>
			</div>
		</div>
	</div>
	<!-- /container -->
   <script src="https://code.jquery.com/jquery-1.12.1.min.js"></script>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

   <!-- (2) LoginWithNaverId Javscript SDK -->
   <script src="naveridlogin_js_sdk_2.0.0.js"></script>

   <!-- (3) LoginWithNaverId Javscript 설정 정보 및 초기화 -->
   <script>
      var naverLogin = new naver.LoginWithNaverId(
         {
            clientId: "CGgCSjtpNDI5dtat6Pqq",
            callbackUrl: "http://localhost:8081/Web_Project/login.jsp",
            isPopup: false,
            //loginButton: {color: "green", type: 3, height: 60}
         }
      );
      /* (4) 네아로 로그인 정보를 초기화하기 위하여 init을 호출 */
      naverLogin.init();
      
      /* (4-1) 임의의 링크를 설정해줄 필요가 있는 경우 */
      $("#gnbLogin").attr("href", naverLogin.generateAuthorizeUrl());

      /* (5) 현재 로그인 상태를 확인 */
      window.addEventListener('load', function () {
         naverLogin.getLoginStatus(function (status) {
            if (status) {
               /* (6) 로그인 상태가 "true" 인 경우 로그인 버튼을 없애고
                  사용자 정보를 출력합니다. */
               setLoginStatus();
            }
         });
      });

      /* (6) 로그인 상태가 "true" 인 경우 로그인 버튼을 없애고
         사용자 정보를 출력합니다. */
      function setLoginStatus() {
         console.log(naverLogin.user);
         console.log(naverLogin.user.getId());
         var pname = naverLogin.user.getName();
         var pid = naverLogin.user.getId();
          $.ajax({
            data: {id:pid,name:pname},
            url: 'login.sns',
            type: 'post',
            dataType: 'text',
            success: function (json) {
               var result = JSON.parse(json);
               if(result.code == "success"){
                  /* alert(result.desc); */
                  naverLogin.logout();
                  window.location.replace("main.start");
               }else{
                  alert(result.desc);
               }
            }
         });
         var uid = naverLogin.user.getId();
         var profileImage = naverLogin.user.getProfileImage();
         var uName = naverLogin.user.getName();
         var nickName = naverLogin.user.getNickName();
         var eMail = naverLogin.user.getEmail();
         $("#naverIdLogin_loginButton").html(
               '<br><br><img src="' + profileImage + 
               '" height=50 /> <p>' + uid + "-" + uName + '님 반갑습니다.</p>');
         $("#gnbLogin").html("Logout");
         $("#gnbLogin").attr("href", "#");
         /* (7) 로그아웃 버튼을 설정하고 동작을 정의합니다. */
         $("#gnbLogin").click(function () {
            naverLogin.logout();
            //location.reload();
            location.href="http://localhost:8081/Web_Project/main.start";
         });
      }
   	</script>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
  </body>
</html>