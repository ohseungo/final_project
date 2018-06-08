<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="./add_product.do" method="post" enctype="multipart/form-data">
          <input name="productId" type="text" class="form-control" id="productId" placeholder="상품번호를 입력하세요">
          <input name="productFile" type="file" class="form-control" id="productFile" placeholder="상품이미지를 추가하세요">
          <input name="productName" type="text" class="form-control" id="productName" placeholder="상품명을 입력하세요">
          <input name="productContent" type="text" class="form-control" id="productContent" placeholder="상품종류를 입력하세요">
          <input name="productValue" type="text" class="form-control" id="productValue" placeholder="상품가격을 입력하세요">
          <input name="submit" type="submit" class="form-control" id="submitBtn" value="추가하기">
        </form>
</body>
</html>