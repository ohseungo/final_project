<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
 <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<title>GreenRight</title>
<meta name="description" content="">
<meta name="author" content="">
<meta http-equiv="X-UA-Compatible" content="ie=edge">

<!-- Favicons
    ================================================== -->
<!-- Bootstrap -->
<link rel="stylesheet" type="text/css" href="css/2bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="fonts/font-awesome/css/font-awesome.css">




<!-- Stylesheet
    ================================================== -->
<link rel="stylesheet" type="text/css" href="css/2style.css">
<link rel="stylesheet" type="text/css"
	href="css/nivo-lightbox/nivo-lightbox.css">
<link rel="stylesheet" type="text/css"
	href="css/nivo-lightbox/default.css">
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,700,800,600,300"
	rel="stylesheet" type="text/css">

<style type="text/css">
body{font-family: NanumSquare}
</style>





 <script src="https://api2.sktelecom.com/tmap/js?version=1&format=javascript&appKey=db476347-088f-49e1-bc96-2853a2a54b00"></script>
        <script type="text/javascript">
        var map, marker, markerLayer;
			function initTmap(){
				map = new Tmap.Map({
					div:'map_div',
					width : "80%",
					height : "600px",
				});
				map.setCenter(new Tmap.LonLat("127.0396597", "37.5013068").transform("EPSG:4326", "EPSG:3857"), 15);
				markerLayer = new Tmap.Layer.Markers();//마커 레이어 생성
				map.addLayer(markerLayer);//map에 마커 레이어 추가
				map.events.register("click", map, onClickMap);//map 클릭 이벤트를 등록합니다.
				
				var lonlat = new Tmap.LonLat(126.984895, 37.566369).transform("EPSG:4326", "EPSG:3857");//좌표 지정
				marker = new Tmap.Marker(lonlat, icon);//마커 생성
				markerLayer.addMarker(marker);//레이어에 마커 추가 
				
				var size = new Tmap.Size(24, 38);//아이콘 크기
				var offset = new Tmap.Pixel(-(size.w / 2), -(size.h));//아이콘 중심점
				var positions = [];
				 <c:forEach items="${requestScope.recycleBoxList}" var="box">
				 	positions.push({
				 		"lonlat": new Tmap.LonLat(${box.recycleBoxLong},${box.recycleBoxLat}).transform("EPSG:4326", "EPSG:3857")
				 	});
				 </c:forEach>				
				 
				for (var i = 0; i< positions.length; i++){//for문을 통하여 배열 안에 있는 값을 마커 생성
					var icon = new Tmap.Icon('http://tmapapis.sktelecom.com/upload/tmap/marker/pin_b_m_a.png',size, offset);//아이콘 설정
					var lonlat = positions[i].lonlat;//좌표값
					marker = new Tmap.Marker(lonlat, icon);//마커 생성
					markerLayer.addMarker(marker); //마커 레이어에 마커 추가
				}
			} 
			
			var count = 0;
			function onClickMap(e){
				if(count != 0){
				markerLayer.removeMarker(marker); // 기존 마커 삭제
				}
				var lonlat = map.getLonLatFromViewPortPx(e.xy).transform("EPSG:3857", "EPSG:4326");//클릭 부분의 ViewPortPx를 LonLat 좌표로 변환합니다.
				var result ='클릭한 위치의 좌표는'+lonlat+'입니다.'; 
				var resultDiv = document.getElementById("result");
				resultDiv.innerHTML = result;
				
				var size = new Tmap.Size(24, 38);//아이콘 사이즈 설정
				var offset = new Tmap.Pixel(-(size.w/2), -(size.h));//아이콘 중심점 설정
				var icon = new Tmap.Icon('http://tmapapis.sktelecom.com/upload/tmap/marker/pin_b_m_a.png',size, offset);//마커 아이콘 설정
				marker = new Tmap.Marker(lonlat.transform("EPSG:4326", "EPSG:3857"), icon);//마커 생성
				markerLayer.addMarker(marker);//마커 레이어에 마커 추가
				count++;
				}
			
			initTmap();
		</script>





    

    
    
    
    
    
    
<!-- Modernizr JS -->
<script type="text/javascript" src="./js/jquery-3.3.1.min.js"></script>
<script src="js/modernizr-2.6.2.min.js"></script>
</head>

<body id="page-top" data-spy="scroll" data-target=".navbar-fixed-top" onload="initTmap()">

	<!-- Navigation
    ==========================================-->
	
	<!-- Header -->
	<header id="header">
	<div class="intro">
		<div class="overlay">
		
		
		<nav id="menu" class="navbar navbar-default navbar">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand page-scroll" href="#page-top" style="color: white; font-family: Open Sans" ><img src="./img/worldwide1.png" width="150px" height="30px"/></a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="#" class="navbar-brand page-scroll"
					href="#page-top" style="color: white; font-family: Open Sans"><strong>상품관리</strong></a></li>
				<li><a href="./orderList.do" class="page-scroll" style="color: white; font-family: Open Sans"><strong>주문관리</strong></a></li>
				<li><a href="./logout.do" class="page-scroll" style="color: white; font-family: Open Sans"><strong>로그아웃</strong></a></li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid --> </nav>
		
		
			<div class="container">
				<div class="row">
					<div class="intro-text">
						<span style="font-family: Open Sans">Welcome to</span>
						<h1 style="font-family: Open Sans">GREENRIGHT</h1>
						<h3 style="font-family: Open Sans">&nbsp;Less pollution, the best solution</h3>
					</div>
				</div>
			</div>
		</div>
	</div>
	</header>
	<!-- About Section -->
	<br />
	<br />
	
	
	
	
	
<div id="map_div"></div>      

<p id="result"></p>
  
	<br/><br/><br/>
	
	
	
	
	
	<script type="text/javascript" src="js/jquery.1.11.1.js"></script>
	<script type="text/javascript" src="js/bootstrap.js"></script>
	<script type="text/javascript" src="js/SmoothScroll.js"></script>
	<script type="text/javascript" src="js/jquery.counterup.js"></script>
	<script type="text/javascript" src="js/waypoints.js"></script>
	<script type="text/javascript" src="js/nivo-lightbox.js"></script>
	<script type="text/javascript" src="js/jquery.isotope.js"></script>
	<script type="text/javascript" src="js/jqBootstrapValidation.js"></script>
	<script type="text/javascript" src="js/contact_me.js"></script>
	<script type="text/javascript" src="js/owl.carousel.js"></script>
	<script type="text/javascript" src="js/main.js"></script>
</body>
</html>