<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
 <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
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





 <style>
      /* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
      #map {
        height: 100%;
      }
      /* Optional: Makes the sample page fill the window. */
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
    </style>





    

    
    
    
    
    
    
<!-- Modernizr JS -->
<script type="text/javascript" src="./js/jquery-3.3.1.min.js"></script>
<script src="js/modernizr-2.6.2.min.js"></script>
</head>

<body id="page-top" data-spy="scroll" data-target=".navbar-fixed-top">

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
					href="#page-top" style="color: white; font-family: Open Sans"><strong>수거함관리</strong></a></li>
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
	
	
	
	
	
 <div id="map"></div>
    <script>
      var map;
      
      function initMap() {
        map = new google.maps.Map(document.getElementById('map'), {
          zoom: 15,
          center: new google.maps.LatLng(37.501469, 127.039617),
          mapTypeId: 'roadmap'
        });

        var iconBase = 'https://maps.google.com/mapfiles/kml/shapes/';

        var features = [];
        <c:forEach items="${requestScope.recycleBoxList}" var="box">
	 	features.push({
	 		recycleBoxId : ${box.recycleBoxId},
	 		position: new google.maps.LatLng(${box.recycleBoxLat},${box.recycleBoxLong})
	 	});
		</c:forEach> 

        // Create markers.
        features.forEach(function(feature) {
          var marker = new google.maps.Marker({
            position: feature.position,
            map: map
          });
          
          marker.addListener('click', function() {
        	  var delConfirm = confirm("정말로 삭제하시겠습니까?");
        	  if(delConfirm == true){
        		  document.location.href = "./delete_recycle.do?recycleBoxId=" + feature.recycleBoxId;
        		  alert("삭제되었습니다");
        	  }else{
        	  }
        	  });
        });
        var count = 0;
        map.addListener('click', function(e) {
        	//추가하는 기능 구현!!
         	if(count != 0){
        	marker.setMap(null);
        	} 
            placeMarkerAndPanTo(e.latLng, map);
            var addConfirm = confirm("해당위치에 수거함을 설치 하시겠습니까?");
            if(addConfirm == true){
      		  document.location.href = "./add_recycle.do?recycleBoxId=" + (features.length+1)
     				  +"&recycleBoxLat=" + e.latLng.lat() + "&recycleBoxLong=" + e.latLng.lng();
      		  alert("등록되었습니다");
      	 	 }else{
      	  	}
            count++;
          });
        
      }//end initMap()
	
      
      function placeMarkerAndPanTo(latLng, map) {
    	  var marker = new google.maps.Marker({
    	    position: latLng,
    	    map: map
    	  });
    	  map.panTo(latLng);
      }
      
      
    </script>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA60f1K6JotdytU-PXtI7YBFTKU-A-adSQ&callback=initMap">
    </script>
	<br/><br/><br/>
	
	
	
	
	
	<script type="text/javascript" src="js/jquery.1.11.1.js"></script>
	<script type="text/javascript" src="js/bootstrap.js"></script>
	<!-- <script type="text/javascript" src="js/SmoothScroll.js"></script> -->
	<script type="text/javascript" src="js/jquery.counterup.js"></script>
	<!-- <script type="text/javascript" src="js/waypoints.js"></script> -->
	<script type="text/javascript" src="js/nivo-lightbox.js"></script>
	<script type="text/javascript" src="js/jquery.isotope.js"></script>
	<script type="text/javascript" src="js/jqBootstrapValidation.js"></script>
	<script type="text/javascript" src="js/contact_me.js"></script>
	<script type="text/javascript" src="js/owl.carousel.js"></script>
	<script type="text/javascript" src="js/main.js"></script>
</body>
</html>