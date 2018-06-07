<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>GreenRight</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="Free HTML5 Website Template by FreeHTML5.co" />
	<meta name="keywords" content="free website templates, free html5, free template, free bootstrap, free website template, html5, css3, mobile first, responsive" />
	<meta name="author" content="FreeHTML5.co" />

  	<!-- Facebook and Twitter integration -->
	<meta property="og:title" content=""/>
	<meta property="og:image" content=""/>
	<meta property="og:url" content=""/>
	<meta property="og:site_name" content=""/>
	<meta property="og:description" content=""/>
	<meta name="twitter:title" content="" />
	<meta name="twitter:image" content="" />
	<meta name="twitter:url" content="" />
	<meta name="twitter:card" content="" />

	<!-- <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet"> -->
	
	<!-- Animate.css -->
	<link rel="stylesheet" href="css/1animate.css">
	<!-- Icomoon Icon Fonts-->
	<link rel="stylesheet" href="css/1icomoon.css">
	<!-- Bootstrap  -->
	<link rel="stylesheet" href="css/1bootstrap.css">
	<!-- Theme style  -->
	<link rel="stylesheet" href="css/1style.css">

	<!-- Modernizr JS -->
	<script src="js/modernizr-2.6.2.min.js"></script>

</head>
	
    
<body>
	<div class="fh5co-loader"></div>
	<div id="page">
	<nav class="fh5co-nav" role="navigation">
		<div class="container">
			<div class="fh5co-top-logo">
				<div id="fh5co-logo"><a href="index.html">Home</a></div>
			</div>
			<div class="fh5co-top-menu menu-1 text-left">
				<ul>
					<li class="has-dropdown">
						<a href="#">상품관리</a>
<!-- 						<ul class="dropdown">
							<li><a href="#">Add Product</a></li>
							<li><a href="#">Edit Product</a></li>
							<li><a href="#">Delete Product</a></li>
						</ul> -->
					</li>
					<li class="has-dropdown">
						<a href="#">주문관리</a>
					</li>
					
				</ul>
			</div>
			<div class="fh5co-top-social menu-1 text-right">
				<ul class="fh5co-social">
					<li><a href="./logout.do">로그아웃</a></li>
				</ul>
			</div>
		</div>
	</nav>
      
     <div id="result" ></div>
        <div id="dataDiv">
        	
        	<table  id="dataTable">
        	<caption class="caption">판매 상품 목록</caption>
        	<thead>
        		<tr>
        			<th>상품번호</th>
        			<th>가격</th>
        			<th>상품명</th>
        			<th>종류</th>
        		</tr>
        	</thead>  
        	<tbody>
				<c:forEach items = "${productList}" var="product">
					<tr>
						<td>${product.productId}</td>
						<td>${product.productValue}</td>
						<td>${product.productName}</td>
						<td>${product.productContent}</td>
					</tr>
				</c:forEach>
        	</tbody>
        	</table>
        </div>
	


	<footer id="fh5co-footer" role="contentinfo">
		<div class="container">
			<div class="row copyright">
				<div class="col-md-12 text-center">
					<p>
						<small class="block">&copy; 2016 Free HTML5. All Rights Reserved.</small> 
						<small class="block">Designed by <a href="http://freehtml5.co/" target="_blank">FreeHTML5.co</a> Demo Images: <a href="http://unsplash.co/" target="_blank">Unsplash</a> &amp; <a href="http://blog.gessato.com/" target="_blank">Gessato</a></small>
					</p>
					
					<ul class="fh5co-social-icons">
						<li><a href="#"><i class="icon-twitter"></i></a></li>
						<li><a href="#"><i class="icon-facebook"></i></a></li>
						<li><a href="#"><i class="icon-linkedin"></i></a></li>
						<li><a href="#"><i class="icon-dribbble"></i></a></li>
					</ul>
					
				</div>
			</div>

		</div>
	</footer>
	</div>

	<div class="gototop js-top">
		<a href="#" class="js-gotop"><i class="icon-arrow-up"></i></a>
	</div>
	
	<!-- jQuery -->
	<script src="js/jquery.min.js"></script>
	<!-- jQuery Easing -->
	<script src="js/jquery.easing.1.3.js"></script>
	<!-- Bootstrap -->
	<script src="js/bootstrap.min.js"></script>
	<!-- Waypoints -->
	<script src="js/jquery.waypoints.min.js"></script>
	<!-- Main -->
	<script src="js/main.js"></script>
      
    </body>
</html>

