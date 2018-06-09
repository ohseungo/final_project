<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="./js/jquery-3.3.1.min.js"></script>

</head>
<body>
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
</body>
</html>