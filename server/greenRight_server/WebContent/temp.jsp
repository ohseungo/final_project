<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="stylesheet" type="text/css" href="css/2bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="fonts/font-awesome/css/font-awesome.css">




<!-- Stylesheet
    ================================================== -->
<link rel="stylesheet" type="text/css"  href="css/2style.css">
<link rel="stylesheet" type="text/css" href="css/nivo-lightbox/nivo-lightbox.css">
<link rel="stylesheet" type="text/css" href="css/nivo-lightbox/default.css">
<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,700,800,600,300" rel="stylesheet" type="text/css">

<style>
body {
	background-image: url('./images/slide-3.jpg');
	background-size: 100% 120%;
}
</style>


<!-- Modernizr JS -->
<script type="text/javascript" src="./js/jquery-3.3.1.min.js"></script>
<script src="js/modernizr-2.6.2.min.js"></script>

<script type="text/javascript">
	function deleteBtn(pId) {
		if (confirm("정말 삭제하시겠습니까?") == true) {
			document.location.href = "./delete_product.do?productId=" + pId;
		} else {
			return;
		}
	}

	$(
			function() {
				$("#update").click(
						function() {
							location.href = 'updateProduct.jsp?product_no='
									+ $(this).attr("product_no");
						})
			})
</script>
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
        <li><a href="#" class="navbar-brand page-scroll" href="#page-top">상품관리</a></li>
        <li><a href="./orderList.do" class="page-scroll">주문관리</a></li>
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
<!-- About Section -->
<div id="about">
  <div class="container">
    <div class="section-title text-center center">
      <h2>상품관리화면</h2>
      <hr>
    </div>
    <div class="row">
      <div id="dataDiv">
						<h1>주문 관리</h1>
	<table border="1">
		<h3 align="center" style="color: white">주문 목록</h3>
		<thead>
			<tr style="color: black;">
				<th style="text-align: center">주문번호</th>
				<th style="text-align: center">주문자명</th>
				<th style="text-align: center">주문날짜</th>
				<th style="text-align: center">결제방법</th>
				<th style="text-align: center">주문금액</th>
				<th style="text-align: center">배송지</th>
				<th style="text-align: center">주문상태</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${sessionScope.compId != null}">
				<c:forEach items="${purchaseList}" var="purchase">
					<tr>
						<td style="text-align: center"background-color:white">${purchase.purchaseId}</td>
						<td style="text-align: center"background-color:white">${purchase.userName}</td>
						<td style="text-align: center"background-color:white">${purchase.purchaseDate}</td>
						<td style="text-align: center"background-color:white">${purchase.purchaseType}</td>
						<td style="text-align: center"background-color:white">${purchase.productValue-purchase.purchasePoint}</td>
						<td style="text-align: center"background-color:white">${purchase.userAddress}</td>
						<c:choose>
							<c:when test="${purchase.purchaseStatus==0}">
								<td style="text-align: center"background-color:white">배송완료</td>
							</c:when>
							<c:when test="${purchase.purchaseStatus==1}">
								<td style="text-align: center"background-color:white">주문확인</td>
							</c:when>
							<c:when test="${purchase.purchaseStatus==2}">
								<td style="text-align: center"background-color:white">입금확인</td>
							</c:when>
							<c:when test="${purchase.purchaseStatus==3}">
								<td style="text-align: center"background-color:white">배송중</td>
							</c:when>
							<c:otherwise>
								<td style="text-align:center" background-color:white"></td>
							</c:otherwise>
						</c:choose> 
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
      
      
  </div>
</div>
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