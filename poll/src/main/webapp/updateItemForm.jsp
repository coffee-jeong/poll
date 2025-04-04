<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "model.*" %>
<%@ page import = "dto.*" %>
<%@ page import = "java.util.*" %>
<%
	// ?번 문제와 아이템들(선택지) 출력
	// type=1이면 복수투표가 가능 아이템의 타입을 체크박스로
	// typq=0이면 체크박스를 레디오로 
	
	// controller Layer -> 요청값 분석, 모델레이어 호출
	int qnum = Integer.parseInt(request.getParameter("qnum"));
	
	// 1) questionOne
	QuestionDao questionDao = new QuestionDao();
	Question question = questionDao.selectQuestionOne(qnum);
	// 2) 1)의 itemList
	ItemDao itemDao = new ItemDao();
	ArrayList<Item> itemList = itemDao.selectItemListByQnum(qnum);


%>

<!-- View Layer -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>투표하기</title>
</head>
<body>
	<div>
		<jsp:include page="/inc/nav.jsp"></jsp:include>
	</div>
	
	<h1>투표하기</h1>
	<form action="/poll/updateItemAction.jsp" method="post">
		<input type="hidden" name="qnum" value="<%=qnum%>">
		<table border="1">
			<tr>
				<td>
					Q : <%=question.getTitle()%>
					(<%=question.getType() == 1 ? "복수투표 불가" : "복수투표 가능" %>)
				</td>
			</tr>
				<tr>
					<td>
						<%
							for(Item i : itemList) {
						%>
								<div>
									<%
										if(question.getType() == 1) { // type=radio 복수선택x로 하나의 값을 받으니
									%>	
											<input type="radio" value="<%=i.getInum() %>" name="inum">
									<%	
										} else { // type=checkbox 복수투표 가능으로 
									%>
											<input type="checkbox" value="<%=i.getInum() %>" name="inum">
									<%		
										}
									%>
									<%=i.getContent()%>
								</div>
						<%
							}
						%>
					</td>
				</tr>	
		</table>
		<button type = "submit">[투표하기]</button>
	</form>
</body>
</html>