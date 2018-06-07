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
	
	<style>
		body{
			background-image: url('./images/slide-3.PNG');
			background-size: 100% 300%;
		}
	</style>
	
</head>
	
    
<body>
	<div class="fh5co-loader"></div>
	<div id="page">
	<nav class="fh5co-nav" role="navigation">
		<div class="container">
			<div class="fh5co-top-logo">
				<div id="fh5co-logo"><a href="redirect:/corporate.do">Home</a></div>
			</div>
			<div class="fh5co-top-menu menu-1 text-left">
				<ul>
					<li class="has-dropdown">
						<a href="redirect:/corporate.do">상품관리</a>
 						<!-- <ul class="dropdown">
							<li><a href="addProduct.jsp">Add Product</a></li>
							<li><a href="#">Edit Product</a></li>
							<li><a href="#">Delete Product</a></li>
						</ul>  -->
					</li>
					<li class="has-dropdown">
						<a href="addProduct.jsp">주문관리</a>
						<!-- <ul class="dropdown">
							<li><a href="#">View Orders</a></li>
						</ul> -->
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
        	<table id="dataTable" style="margin-left: auto; margin-right: auto; background: rgba(255,255,255,0.1)" 
        	border="1px" bordercolor="white" align="center">
        	<br/><br/>
        	<h3 align="center" style="color: white">판매 상품 목록</h3>
        	<thead>
        		<tr style="color: white;">
        			<th style="text-align: center">상품번호</th>
        			<th style="text-align: center">상품명</th>
        			<th style="text-align: center">종류</th>
        			<th style="text-align: center">가격</th>
        		</tr>
        	</thead>  
        	<tbody>
        		<c:if test="${sessionScope.compId != null}">
					<c:forEach items = "${productList}" var="product">
						<tr style="color: white; text-align: center;" height="90">
							<td width="159">${product.productId}</td>
							<td width="374">${product.productName}</td>
							<td width="374">${product.productContent}</td>
							<td width="92">${product.productValue}</td>
						</tr>
					</c:forEach>
        		</c:if>
        	</tbody>
        	</table>
        </div>
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

