<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.*" %>
<%@ page import="dto.*" %>
<%@ page import="java.util.*" %>
<%
	// controller : request분석 + model 호출
	int qnum = Integer.parseInt(request.getParameter("qnum"));


	// 1) questionOne
	QuestionDao questionDao = new QuestionDao();
	Question question = questionDao.selectQuestionOne(qnum);
	// 2) 1)의 itemList
	ItemDao itemDao = new ItemDao();
	ArrayList<Item> itemList = itemDao.selectItemListByQnum(qnum);
	// 3) 총투표수
	int totalCount = itemDao.selectItemCountbyQnum(qnum);

%>


<!-- View -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	<!-- nav.jsp 인클루드 -->
	<div>
		<jsp:include page="/inc/nav.jsp"></jsp:include>
	</div>

	<h1><%=qnum%>번 설문 투표결과</h1>
	<table border="1" width="80%">
		<tr>
			<td colspan="4">
				Q : <%=question.getTitle() %>
			</td>
		</tr>
		
		<tr>
			<td colspan="4">
				총 투표자 수 : <%=totalCount%>
			</td>
		</tr>
		<tr>
			<td>번호</td><td>내용</td><td>카운트(이미지)</td><td>카운트</td>
		</tr>
		
		<%
			for(Item i : itemList) {
		%>
			<tr>
				<td><%=i.getInum() %></td>
				<td><%=i.getContent() %></td>
				<td>
					<%
						int percentage = (int)(Math.round((double)i.getCount() / totalCount * 100));
					
						for(int n=1; n<=percentage; n++) {
					%>
						*
					<%		
						}
					%>
					
				</td>
				<td><%=i.getContent()%></td>
			</tr>
		<%
			}
		%>
	</table>
</body>
</html>