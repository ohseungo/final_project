<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="./login.do" style="width:500px; margin:0 auto 0 auto;">
		<table border="1" id=totaltable style="background-color: LightGray; width:300px">
			<tr>
				<td align="center" colspan="2"
					style="background-color: MediumSeaGreen">로그인</td>
			</tr>
			<tr>
				<td align="right">아이디</td>
				<td><input type="text" id="userId" name="userId"></td>
			</tr>
			<tr>
				<td align="right">비밀번호</td>
				<td><input type="password" id="password" name="password"></td>
			</tr>
			<tr>
				<td align="right">이름</td>
				<td><input type="text" id="userName" name="userName"></td>
			</tr>
			<tr>
				<td align="right">전화번호</td>
				<td><input type="text" id="userPhone" name="userPhone"></td>
			</tr>
			<tr>
				<td align="right">이메일</td>
				<td><input type="text" id="userEmail" name="userEmail"></td>
			</tr>
			<tr>
				<td align="right">차량번호</td>
				<td><input type="text" id="userCar" name="userCar"></td>
			</tr>
			<tr>
				<td align="right">회원구분</td>
				<td><input type="text" id="userType" name="userType"></td>
			</tr>
			<tr>
				<td align="right">업체고유번호</td>
				<td><input type="text" id="id" name="id"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
				<input type="submit" name="submit" value="회원가입" id="loginBtn"> 
				<input type="reset" name="reset" value="취소"></td>
			</tr>
		</table>
	</form>
</body>
</html>