<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
    	.test1{
    	
    	}
    	.site-heading{
			text-align: center;
    		vertical-align: middle;
		}
		.direction {
			margin: 0;
    		padding: 0;
    		border: none 0;
		}
		
		.direction li{
			margin: 0;
    		padding: 0;
    		border: none 0;
			width : 49%;
			
		}
		
		.fl{		
			float : left;
		}
		
		.fr{		
			float : right;
		}
		
		li .bul_dot{
			margin: 20px 0 0 20px;
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

	<!-- 웨이 폼 -->
	<div class ="contanier">
		<div class = "site-heading">
			<h1>오시는길</h1>
		</div>
				
		<div id="map" style="height:400px"></div>
		<div class = "direction">
			<ul>
				<li class ="fl" >
					<strong>대중교통으로 오시는 경우</strong>
					<ul class = "bul_dot">
						<li>
							<strong>지하철</strong>
							<p>
							6번출구 나오셔서 좌측횡단보도 건너 좌측으로 10m가시면 삼거리가 나옵니다.<br>
							삼거리에서 우측 방향으로 직진하셔서 사거리 대각선방향에 우리은행 건물 410호입니다.<br>							
							</p>
						</li>
						<br />
						<li>
							<strong>버스</strong>
							<p>
							21, 571, 652, 금천 05 [디지털3단지월드벤쳐센터] 정류장에서 하차  							
							</p>
						</li>
					</ul>
				</li>
				<li class ="fr">
					<strong>자가용으로 오시는 경우</strong>
					<ul class = "bul_dot">
						<li>							
							서부간선도로 타고 오다가 광명교를 타고 좌회전 후 첫 사거리에서 우회전
						</li>
						<li>							
							가리봉5거리에서 철산방향 수출의 다리를 넘어 첫 사거리(한진사거리)에서 우회전
						</li>
						<li>							
							남부순환도로 구로IC로 나와 좌회전
						</li>
					</ul>
				</li>
			</ul>
		</div>
		<script>
	/*
		if(!navigator.geolocation)
		alert("지원하지 않음");
		else // found() 콜백 함수 등록
		navigator.geolocation.getCurrentPosition(found);
	*/

	// 위치 파악 시 found() 호출.
	// 위치 정보 들어 있는 position 객체가 매개 변수로 넘어온다.
	function found(position) {
		var now = new Date(position.timestamp);	//37.4788962,126.8767315,17
		var lat = position.coords.latitude; // 위도
		var lon = position.coords.longitude; // 경도
		var acc = position.coords.accuracy; // 정확도

		// 위도와 경도의 소수점 이하 자리가 너무 길어 유효 숫자 6자리로 짜름
		lat = lat.toPrecision(6); lon = lon.toPrecision(6);
		
	}

	var map, infoWindow;
	function initMap() {
    	map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 37.574690, lng: 126.978142},
        zoom: 16
    });
    infoWindow = new google.maps.InfoWindow;

    // Try HTML5 geolocation.
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var pos = {
                lat: 37.478875,
                lng: 126.878931,
                acc: 17
            };

            infoWindow.setPosition(pos);
            infoWindow.setContent('KOSMO.');
            infoWindow.open(map);
            map.setCenter(pos);
            
            found(position);
        }, function() {
            handleLocationError(true, infoWindow, map.getCenter());
        });
   	} else {
        	// Browser doesn't support Geolocation
       	 handleLocationError(false, infoWindow, map.getCenter());
    	}
	}

	function handleLocationError(browserHasGeolocation, infoWindow, pos) {
    	infoWindow.setPosition(pos);
    	infoWindow.setContent(browserHasGeolocation ? 'Error: The Geolocation service failed.' : 'Error: Your browser doesn\'t support geolocation.');
    	infoWindow.open(map);
	}
	</script>
			
	</div>
	
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDBZfo_Pcq_61ZIcusersOrjTEMWyluwEc&callback=initMap" async defer></script>
    
  </body>
</html>