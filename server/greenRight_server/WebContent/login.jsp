<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="./login1.do" style="width:500px; margin:0 auto 0 auto;">
		<table border="1" id=totaltable style="background-color: LightGray; width:300px">
			<tr>
				<td align="center" colspan="2"
					style="background-color: MediumSeaGreen">�α���</td>
			</tr>
			<tr>
				<td align="right">���̵�</td>
				<td><input type="text" id="userId" name="userId"></td>
			</tr>
			<tr>
				<td align="right">��й�ȣ</td>
				<td><input type="password" id="password" name="password"></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit"
					name="submit" value="�α���" id="loginBtn"> <input
					type="reset" name="reset" value="���"></td>
			</tr>
		</table>
	</form>
</body>
</html>