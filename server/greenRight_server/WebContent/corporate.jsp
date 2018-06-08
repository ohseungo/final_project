<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>GreenRight</title>
<meta name="description" content="">
<meta name="author" content="">

<!-- Favicons
    ================================================== -->
<!-- Bootstrap -->
<link rel="stylesheet" type="text/css"  href="css/2bootstrap.css">
<link rel="stylesheet" type="text/css" href="fonts/font-awesome/css/font-awesome.css">

<!-- Slider
    ================================================== -->
<link href="css/2owl.carousel.css" rel="stylesheet" media="screen">
<link href="css/2owl.theme.css" rel="stylesheet" media="screen">

<!-- Stylesheet
    ================================================== -->
<link rel="stylesheet" type="text/css"  href="css/2style.css">
<link rel="stylesheet" type="text/css" href="css/nivo-lightbox/nivo-lightbox.css">
<link rel="stylesheet" type="text/css" href="css/nivo-lightbox/default.css">
<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,700,800,600,300" rel="stylesheet" type="text/css">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
	
    
<body id="page-top" data-spy="scroll" data-target=".navbar-fixed-top">
<!-- Navigation
    ==========================================-->
<nav id="menu" class="navbar navbar-default navbar">
  <div class="container"> 
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"> <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
      <a class="navbar-brand page-scroll" href="#page-top">GreenRight</a> </div>
    
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav navbar-right">
        <li><a href="#" class="page-scroll">상품관리</a></li>
        <li><a href="#" class="page-scroll">주문관리</a></li>
        <li><a href="./logout.do" class="page-scroll">로그아웃</a></li>
      </ul>
    </div>
    <!-- /.navbar-collapse --> 
  </div>
  <!-- /.container-fluid --> 
</nav>
<!-- Header -->
<header id="header">
  <div class="intro">
    <div class="overlay">
      <div class="container">
        <div class="row">
          <div class="intro-text"> <span>Welcome to</span>
            <h1>GREENRIGHT</h1>
            <h3>&nbsp;Less pollution, the best solution</h3>
        </div>
      </div>
    </div>
  </div>
</header>

<div id="about">
  <div class="container">
    <div class="section-title text-center center">
      <h2>판매상품목록</h2>
      <hr>
    </div>
    <div class="row">
           <div id="result" ></div>
        <div id="dataDiv">
        	<table id="dataTable" style="margin-left: auto; margin-right: auto; background: rgba(255,255,255,0.8)" 
        	border="1px" bordercolor="white" align="center">
        	<thead>
        		<tr style="color: black;">
        			<th style="text-align: center">상품번호</th>
        			<th style="text-align: center">이미지</th>
        			<th style="text-align: center">상품명</th>
        			<th style="text-align: center">종류</th>
        			<th style="text-align: center">가격</th>
        			<th style="text-align: center">제품삭제</th>
        		</tr>
        	</thead>  
        	<tbody>
        		<c:if test="${sessionScope.compId != null}">
					<c:forEach items = "${productList}" var="product">
						<tr id="proId" style="color: black; text-align: center;" height="150">
							<td width="159"><a href="<%=request.getContextPath()%>/subject_view.do?subject_code=${Subject.subject_code}">${Subject.subject_title}</a>${product.productId}</td>
							<td width="159">${product.productImage}</td>
							<td width="300">${product.productName}</td>
							<td width="300">${product.productContent}</td>
							<td width="92">${product.productValue}</td>
							<td width="80">
							<input type="button" value="삭제" onclick="deleteBtn('${product.productId}');">
							</td>
						</tr>
					</c:forEach>
        		</c:if>
        	</tbody>
        	</table>
        	<br/>
        	<div align="right">
        	<a href="#" data-toggle="modal" data-target="#modal1" class="btn btn-lg btn-default smoothScroll wow fadeInUp hidden-xs" data-wow-delay="0.8s">상품 등록</a>
        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        	</div>
        </div>
          
        </div>
      </div>
    </div>
  </div>
</div>
      
    
	</div>
	
<!-- modal -->
<div class="modal fade" id="modal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
      <div class="modal-content modal-popup">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h2 class="modal-title" style="color: white">상품등록</h2>
        </div>
        <form action="./add_product.do" method="post">
          <input name="productId" type="text" class="form-control" id="productId" placeholder="상품번호를 입력하세요">
          <input name="productImage" type="text" class="form-control" id="productImage" placeholder="상품이미지를 추가하세요">
          <input name="productName" type="text" class="form-control" id="productName" placeholder="상품명을 입력하세요">
          <input name="productContent" type="text" class="form-control" id="productContent" placeholder="상품종류를 입력하세요">
          <input name="productValue" type="text" class="form-control" id="productValue" placeholder="상품가격을 입력하세요">
          <input name="submit" type="submit" class="form-control" id="submitBtn" value="추가하기">
        </form>
      </div>
  </div>
</div>

<!-- modal2 -->
<div class="modal fade" id="modal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
      <div class="modal-content modal-popup">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h2 class="modal-title" style="color: white">상품등록</h2>
        </div>
        <form action="./update_product.do" method="post">
          <input name="productId" type="text" class="form-control" id="productId" placeholder="z">
          <input name="productImage" type="text" class="form-control" id="productImage" placeholder="z">
          <input name="productName" type="text" class="form-control" id="productName" placeholder=z>
          <input name="productContent" type="text" class="form-control" id="productContent" placeholder="z">
          <input name="productValue" type="text" class="form-control" id="productValue" placeholder="z">
          <input name="submit" type="submit" class="form-control" id="submitBtn" value="수정 완료">
        </form>
      </div>
  </div>
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

