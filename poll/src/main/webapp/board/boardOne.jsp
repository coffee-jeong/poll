<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "model.*"%>
<%@ page import = "dto.*"%>
<%
	int num = Integer.parseInt(request.getParameter("num"));
	
	BoardDao boardDao = new BoardDao();
	Board b = boardDao.selectBoardOne(num);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
	<!-- Latest compiled and minified CSS -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Latest compiled JavaScript -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class = "container">
	<div>
		<jsp:include page="/inc/nav.jsp"></jsp:include>
	</div>
	<div class="container mt-3">
		<h1>board one</h1>
		<table class = "table">
			<tr>
				<td>num</td>
				<td><%=b.getNum()%></td>
			</tr>
			<tr class="table-primary">
				<td>name</td>
				<td><%=b.getName()%></td>
			</tr>
			<tr class="table-success">
				<td>subject</td>
				<td><%=b.getSubject()%></td>
			</tr>
			<tr class="table-danger">
				<td>content</td>
				<td><%=b.getContent()%></td>
			</tr>
			<tr class="table-info">
				<td>pos</td>
				<td><%=b.getPos()%></td>
			</tr>
			<tr class="table-warning">
				<td>ref</td>
				<td><%=b.getRef()%></td>
			</tr>
			<tr class="table-active">
				<td>depth</td>
				<td><%=b.getDepth()%></td>
			</tr>
			<tr class="table-secondary">
				<td>regdate</td>
				<td><%=b.getRegdate()%></td>
			</tr>
			<tr class="table-light">
				<td>ip</td>
				<td><%=b.getIp()%></td>
			</tr>
			<tr class="table-dark">
				<td>count</td>
				<td><%=b.getCount()%></td>
			</tr>
		</table>
		<a href="/poll/board/updateBoardForm.jsp?num=<%=b.getNum()%>">수정</a>
		<a href="/poll/board/deleteBoardAction.jsp?num=<%=b.getNum()%>">삭제</a>
		<a href="/poll/board/insertBoardReplyForm.jsp?ref=<%=b.getRef()%>&pos=<%=b.getPos()%>&depth=<%=b.getDepth()%>">답글달기</a>
	</div>
</body>
</html>