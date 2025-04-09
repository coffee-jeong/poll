<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "dto.*" %>
<%@ page import = "model.*" %>
<%@ page import = "java.util.*" %>
<%
	int currentPage = 1;
	if(request.getParameter("currentPage") != null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	
	ImageDao ImageDao = new ImageDao();
	Paging p = new Paging();
	p.setCurrentPage(currentPage);
	p.setRowPerPage(10);
	ArrayList<Image> list = ImageDao.selectImageList(p);

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	<%
		for(Image i : list) {
	%>
			<table border="1">
				<tr>
					<td><%=i.getMemo()%></td>
				</tr>
				<tr>
					<td>
						<img src="/poll/upload/<%=i.getFilename()%>">
					</td>
				</tr>
				<tr>
					<td>
						<a href="/poll/imageBoard/deleteImage.jsp?num=<%=i.getNum()%>&filename=<%=i.getFilename()%>">삭제</a>
					</td>
				</tr>
			</table>
			<hr>
	<%		
		}
	%>
	
</body>
</html>