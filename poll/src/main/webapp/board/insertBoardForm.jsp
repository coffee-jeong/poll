<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="container">
	<div>
		<jsp:include page="/inc/nav.jsp"></jsp:include>
	</div>
	
	<h1>글 입력</h1>
	<form method="post" action="/poll/board/insertBoardAction.jsp">
		<table class="table">
			<tr class="table-primary">
				<td>name</td>
				<td><input type="text" name="name"></td>
			</tr>
			<tr class="table-success">
				<td>subject</td>
				<td><input type="text" name="subject"></td>
			</tr>
			<tr class="table-danger">
				<td>content</td>
				<td><textarea name="content" rows="5" cols="50"></textarea></td>
			</tr>
			<tr>
				<td>pass</td>
				<td><input type="password" name="pass"></td>
			</tr>
		</table>
		<button type="submit" class="btn btn-primary">글쓰기</button>
	</form>
</body>
</html>