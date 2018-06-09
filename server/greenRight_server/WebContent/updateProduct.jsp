<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="./js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
	
</script>
</head>
<body>
	<form action="<%=request.getContextPath()%>/update_product.do" method="post">
		<table border="1">
			<tr>
				<td>제품 번호</td>
				<td><input name="productId" type="text" class="form-control"
					id="productId" value="<%=request.getParameter("product_no")%>"></td>
			</tr>
			<tr>
				<td>상품이미지</td>
				<td><input name="productImage" type="text" class="form-control"
					id="productImage" placeholder="상품이미지를 추가하세요"></td>
			</tr>
			<tr>
				<td>상품명</td>
				<td><input name="productName" type="text" class="form-control"
					id="productName" placeholder="상품명을 입력하세요"></td>
			</tr>
			<tr>
				<td>상품종류</td>
				<td><input name="productContent" type="text"
					class="form-control" id="productContent" placeholder="상품종류를 입력하세요">
				</td>
			</tr>
			<tr>
				<td>상품가격</td>
				<td><input name="productValue" type="text" class="form-control"
					id="productValue" placeholder="상품가격을 입력하세요"></td>
			</tr>
		
		</table>
		<input type="submit" value="상품수정" /> <input type="reset" value="취소" />
	</form>
	
</body>
</html>