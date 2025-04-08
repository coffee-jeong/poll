<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "model.*" %>
<%@ page import = "dto.*" %>
<%
	// int num = Integer.parseInt(request.getParameter("num"));
	int num = 6;
	
	BoardDao boardDao = new BoardDao();
	Board b = boardDao.selectBoard(num);
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	<h1>board one</h1>
	<table>
		<tr>
			<td>num</td>
			<td><%=b.getNum()%></td>
		</tr>
		<tr>
			<td>name</td>
			<td><%=b.getName()%></td>
		</tr>
		<tr>
			<td>subject</td>
			<td><%=b.getSubject()%></td>
		</tr>
		<tr>
			<td>content</td>
			<td><%=b.getContent()%></td>
		</tr>
		<tr>
			<td>pos</td>
			<td><%=b.getPos()%></td>
		</tr>
		
		<tr>
			<td>ref</td>
			<td><%=b.getRef()%></td>
		</tr>
		<tr>
			<td>depth</td>
			<td><%=b.getDepth()%></td>
		</tr>
		<tr>
			<td>regdate</td>
			<td><%=b.getRegdate()%></td>
		</tr>
		<tr>
			<td>ip</td>
			<td><%=b.getIp()%></td>
		</tr>
		<tr>
			<td>count</td>
			<td><%=b.getContent()%></td>
		</tr>
	</table>
	<a href="">수정</a>
	<a href="">삭제</a>
	<a href="/poll/board/insertBoardReplyForm.jsp?ref=<%=b.getRef()%>&pos=<%=b.getPos()%>&depth<%=b.getDepth()%>">답글달기</a>
</body>
</html>