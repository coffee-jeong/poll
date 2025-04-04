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
    
    // 객체 생성
    QuestionDao questionDao = new QuestionDao();
    ItemDao itemDao = new ItemDao();
    
    Question question = questionDao.selectQuestion(qnum);
    ArrayList<Item> itemList = itemDao.selectItem(qnum);
    int listSize = itemList.size();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>insertPollForm</title>
</head>
<body>
	<div>
		<jsp:include page="/inc/nav.jsp"></jsp:include>
	</div>
	
    <h1>투표프로그램</h1>
    <hr>
    <h2>설문수정</h2>
    <form method="post" action="/poll/UpdatePollAction.jsp">
        <input type="hidden" name="qnum" value="<%=question.getNum()%>">
        <table border="1">
            <tr>
                <td>질문</td>
                <td colspan="2">
                    <input type="text" name="title" value="<%=question.getTitle()%>">
                </td>
            </tr>
            <tr>
                <td rowspan="8">항목</td>
                <td>1) <input type="text" name="content" value="<%=listSize > 0 ? itemList.get(0).getContent() : ""%>"></td>
                <td>2) <input type="text" name="content" value="<%=listSize > 1 ? itemList.get(1).getContent() : ""%>"></td>
            </tr>
            <tr>
                <td>3) <input type="text" name="content" value="<%=listSize > 2 ? itemList.get(2).getContent() : ""%>"></td>
                <td>4) <input type="text" name="content" value="<%=listSize > 3 ? itemList.get(3).getContent() : ""%>"></td>
            </tr>
            <tr>
                <td>5) <input type="text" name="content" value="<%=listSize > 4 ? itemList.get(4).getContent() : ""%>"></td>
                <td>6) <input type="text" name="content" value="<%=listSize > 5 ? itemList.get(5).getContent() : ""%>"></td>
            </tr>
            <tr>
                <td>7) <input type="text" name="content" value="<%=listSize > 6 ? itemList.get(6).getContent() : ""%>"></td>
                <td>8) <input type="text" name="content" value="<%=listSize > 7 ? itemList.get(7).getContent() : ""%>"></td>
            </tr>
            <tr>
                <td>시작일</td>
                <td><input type="date" name="startdate" value="<%=question.getStartdate()%>" readonly></td>
            </tr>
            <tr>
                <td>종료일</td>
                <td><input type="date" name="enddate" value="<%=question.getEnddate()%>" readonly><a href="/poll/updateQuestionEnddateForm.jsp?qnum=<%=qnum%>">[종료일자수정]</a></td>
            </tr>
            <tr>
                <td>복수투표</td>
                <td>
                    <input type="radio" name="type" checked value="1">yes
                    <input type="radio" name="type" <%=question.getType() == 0 ? "checked" : "" %> value="0">no
                </td>
            </tr>
        </table>
        <button type="submit">수정하기</button>
        <button type="reset">다시쓰기</button>
        <a href="/poll/pollList.jsp">리스트</a>
    </form>
</body>
</html>
