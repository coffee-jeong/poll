<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "dto.*" %>
<%@ page import = "model.*" %>
<%@ page import = "java.util.*"%>
<%
	String enddate = request.getParameter("enddate");
	int qnum = Integer.parseInt(request.getParameter("qnum"));
	
	System.out.println("qnum: " + qnum + ", enddate: " + enddate);
	
	// updateEnddate 메서드 호출, enddate와 qnum을 인자로 전달
	QuestionDao questionDao = new QuestionDao();
	questionDao.updateEnddate(enddate, qnum);  // 수정된 메서드 호출
	
	// 페이지 리다이렉션
	response.sendRedirect("/poll/pollList.jsp");


%>