<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "dto.*" %>
<%@ page import = "model.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.util.Date" %>
<%@ page import = "java.text.SimpleDateFormat" %>
<%
	// question 테이블 리스트 -> 페이징 -> title 링크(startdate <= 오늘날짜 <= enddate) -> 투표프로그램
	// QuestionDao.selectQuestionList(Paging)
	int currentPage = 1;
	if(request.getParameter("currentPage") != null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	int rowPerPage = 10;
	Paging paging = new Paging();
	paging.setCurrentPage(currentPage);
	paging.setRowPerPage(rowPerPage);
	
	QuestionDao questionDao = new QuestionDao();
	ArrayList<Question> list = questionDao.selectQuestionList(paging);
    
	// 오늘 날짜 가져오기
    Calendar today = Calendar.getInstance();
    today.set(Calendar.HOUR_OF_DAY, 0);
    today.set(Calendar.MINUTE, 0);
    today.set(Calendar.SECOND, 0);
    today.set(Calendar.MILLISECOND, 0);
    
    Date todayDate = today.getTime();
    
%>
<!DOCTYPE html>
<html> 
<head>
<meta charset="UTF-8">
<title>pollList</title>
</head>
<body>
	<h1>설문리스트</h1>
	<!-- foreach문 ArrayList<Question> list 출력 title 
	링크(startdate <= 오늘날짜 <= enddate) 투표시작전, 투표종료, 투표하기 -->
	
	<table border ="1">
		<tr>
			<th>NO</th>
			<th>Title</th>
			<th>Start Date</th>
			<th>End Date</th>
			<th>Type</th>
			<th>Vote</th>
		</tr>
		<%
			for(Question q : list) {
		%>
		<tr>
			<td><%=q.getNum()%></td>
			<td><%=q.getTitle()%></td>
			<td><%=q.getStartdate()%></td>
			<td><%=q.getEnddate()%></td>
			<td><%=q.getType()%></td>
			<td>
				<%
					SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd");
				    Date startDate = now.parse(q.getStartdate());
				    Date endDate = now.parse(q.getEnddate());

			        // 날짜 비교
			        if (todayDate.before(startDate)) {
			        	%>투표시작전<%
			        } else if (todayDate.after(endDate)) {
			        	%>투표종료<%
			        } else {
			        	%><a href="">[투표하기]</a><%
			        }
				%>
			</td>
			</tr>
		<%
			}
		%>
	</table>
	
	<% int prevPage = (currentPage > 1) ? currentPage - 1 : 1; %>
    <% int nextPage = currentPage + 1; %>
    <a href="?currentPage=<%=prevPage%>">이전</a>
    <a href="?currentPage=<%=nextPage%>">다음</a>
	
</body>
</html>
