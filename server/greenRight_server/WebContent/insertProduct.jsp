<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
 <form action="./add_product.do" method="post">
          <input name="productId" type="text" class="form-control" id="productId" placeholder="��ǰ��ȣ�� �Է��ϼ���">
          <input name="productImage" type="text" class="form-control" id="productImage" placeholder="��ǰ�̹����� �߰��ϼ���">
          <input name="productName" type="text" class="form-control" id="productName" placeholder="��ǰ���� �Է��ϼ���">
          <input name="productContent" type="text" class="form-control" id="productContent" placeholder="��ǰ������ �Է��ϼ���">
          <input name="productValue" type="text" class="form-control" id="productValue" placeholder="��ǰ������ �Է��ϼ���">
          <input name="submit" type="submit" class="form-control" id="submitBtn" value="�߰��ϱ�">
        </form>
</body>
</html>