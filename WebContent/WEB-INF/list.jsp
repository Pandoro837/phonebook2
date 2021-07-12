<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.List" %>
<%@ page import="com.javaex.dao.PhoneDao" %>
<%@ page import="com.javaex.vo.PersonVo" %>
    
<%
	PhoneDao phoneDao = new PhoneDao();
	List<PersonVo> personList =	(List<PersonVo>)request.getAttribute("pList"); //어트리뷰트의 자료형을 알 수 없으므로, 형변환을 통해 지정해준다
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>전화번호 리스트</h1>
	<p>입력한 정보 내역입니다.</p>

	<% for(PersonVo personInfo : personList) {%>	
		<table border = "1">
			<tr>
				<td>이름</td>
				<td><%= personInfo.getName() %></td>
			</tr>
			<tr>
				<td>핸드폰</td>
				<td><%= personInfo.getHp() %></td>
			</tr>
			<tr>
				<td>회사</td>
				<td><%= personInfo.getCompany() %></td>
			</tr>
			<tr>
				<td>
					<form action="/phonebook2/pbc" method = "get">
						<input type = "hidden" name = "action" value = "updateForm">
						<input type = "hidden" name = "personId" value = "<%= personInfo.getPersonId() %>">	<!--해당 정보만 수정할 수 있도록 personId를 hidden 타입으로 전달-->
						<%-- <% System.out.println(personInfo.getPersonId() + "list"); %> --%> <!-- personId가 전송되지 않는 에러 발생, 확인용 출력 -> 해결  -->
						<button type = "submit">
							수정
						</button>
					</form>
				</td>
				<td>
					<form action="/phonebook2/pbc" method = "get">
						<input type = "hidden" name = "action" value = "delete">
						<input type = "hidden" name = "personId" value = "<%= personInfo.getPersonId() %>"> <!--해당 정보만 삭제할 수 있도록 personId를 hidden 타입으로 전달-->
						<button type = "submit">
							삭제
						</button>
					</form>
				</td>
			</tr>
		</table>
		<br>
	<%} %>
	<a href = "/phonebook2/pbc?action=insertForm">추가번호 등록</a>
</body>
</html>