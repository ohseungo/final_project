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
	<div class="container">
		<div class="section-title text-center center">
			<h2>상품등록</h2>
			<hr>
			<br />
			<form action="./add_product.do" method="post"
				enctype="multipart/form-data">
				<label for="productId"
					style="color: black">상품번호&nbsp;&nbsp;</label> <input
					name="productId" type="text" id="productId"
					placeholder="상품번호 입력하세요" style="width: 1000px;"> <br /> 
					<br />
				<label for="productFile" style="color: black">상품이미지&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<input name="productFile" type="file" id="productFile"
					placeholder="상품이미지를 추가하세요"> 
					<br />
					<label for="productName"
					style="color: black">상품명&nbsp;&nbsp;&nbsp;&nbsp;</label> <input
					name="productName" type="text" id="productName"
					placeholder="상품명을 입력하세요" style="width: 1000px;"> <br /> 
					<br />
					<label
					for="productContent" style="color: black">상품설명</label> <input
					name="productContent" type="text" id="productContent"
					placeholder="상품에 대한 설명을 입력하세요" style="width: 1000px;"> <br /> 
					<br />
					<label
					for="productValue" style="color: black">상품가격</label> <input
					name="productValue" type="text" id="productValue"
					placeholder="상품가격을 입력하세요" style="width: 1000px;"> <br /> <br />
				<br/> 
				<div align="right">
				<input name="submit" type="submit" class="form-control"
					id="submitBtn" style="width:150px;" value="추가하기">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
			</form>
		</div>


		<div class="section-title text-center center">
			<h2>상품목록</h2>
			<hr>
			<br />
			<table id="dataTable" align="center" class="type10">
				<thead>
					<tr style="color: black;">
						<th style="text-align: center; 
						border-bottom-left-radius: 10px;
						border-top-left-radius: 10px;">상품번호</th>
						<th style="text-align: center">이미지</th>
						<th style="text-align: center">상품명</th>
						<th style="text-align: center">종류</th>
						<th style="text-align: center">가격</th>
						<th style="text-align: center">제품삭제</th>
						<th style="text-align: center;
						border-bottom-right-radius: 10px;
						border-top-right-radius: 10px;">제품수정</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${sessionScope.compId != null}">
						<c:forEach items="${productList}" var="product">
							<tr id="proId" style="color: black; text-align: center;"
								height="150">
								
								<td width="159" style="vertical-align: middle">${product.productId}</td>
								<td width="159" style="vertical-align: middle"><img
									src="${pageContext.request.contextPath}/images/product/${product.productImage}" height="150px" width="150px"/>
								</td>
								<td width="300" style="vertical-align: middle">${product.productName}</td>
								<td width="300" style="vertical-align: middle">${product.productContent}</td>
								<td width="92" style="vertical-align: middle">${product.productValue}</td>
								<td width="80" align="center" style="vertical-align: middle">
								<input type="button" value="삭제"
									onclick="deleteBtn('${product.productId}');"
									style="width: 163px;"
									class="form-control">
								</td>
								<td width="80" style="vertical-align: middle">
								<input type="button"
									product_no="${product.productId}" name="update" id="update"
									class="form-control"
									value="제품 수정"  align="right"/>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<br />
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