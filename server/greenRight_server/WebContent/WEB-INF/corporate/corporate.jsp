<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>관리자</title>
<link rel="stylesheet" type="text/css" href="../css/board.css">
<script type="text/javascript">

</script>
	<style>
		body{
			background-image: url('./img/field.jpg');
			background-size: 100%;
		}
	</style>
</head>
<body>
<%@ include file="/WEB-INF/corporate/corporate_admin_top.jsp" %>
	<table  id="dataTable">
        	<caption class="caption">상품 목록</caption>
        	<thead>
        		<tr>
        			<th>상품 이미지</th>
        			<th>상품이름</th>
        			<th>필요 포인트</th>
        		</tr>
        	</thead>  
        	<tbody>
				<c:forEach items = "${productList}" var="product">
					<tr>
						<td><img src="#" style="width:150px; height:150px"/></td>
						<td>${product.name}</td>
						<td>${product.price}</td>
					</tr>
				</c:forEach>
        	</tbody>
        </table>
<%@ include file="/WEB-INF/corporate/corporate_admin_bottom.jsp" %>
</body>
</html> 