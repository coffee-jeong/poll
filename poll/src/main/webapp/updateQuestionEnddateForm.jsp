<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="dto.*" %>
<%@ page import="model.*" %>
<%
	int qnum = 0;
	if(request.getParameter("qnum") != null){
		qnum = Integer.parseInt(request.getParameter("qnum"));
	}
	System.out.println("qnum :" + qnum);

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	<h1>마감 날짜 변경</h1>
	<form action="/poll/updateQuestionEnddateAction.jsp">
		<table border="1">
			<tr>
			<th>Q </th>
				<td><input type="text" name="qnum" value="<%=qnum%>" readonly></td>		
			</tr>
			<tr>
				<th>END DATE</th>
				<td><input type="date" name="enddate"></td>
			</tr>
		</table>
		<button type="submit">[수정하기]</button>
	</form>
</body>
</html>