<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.SQLException" %>
<%@ page import = "model.*" %>


<%
	int qnum = Integer.parseInt(request.getParameter("qnum"));
	System.out.println("qnum:" + qnum);
	
	// QuestionDao 객체 생성
	QuestionDao questionDao = new QuestionDao();
	
	if (questionDao.deleteQuestion(qnum)) { // 삭제 성공
	    System.out.println("삭제 성공!");
	    response.sendRedirect("/poll/pollList.jsp"); // 삭제 후 리스트 페이지로 리다이렉트
	} else { // 삭제 실패
	    System.out.println("투표자가 있습니다. 삭제 실패!");
	    response.sendRedirect("/poll/pollList.jsp"); // 삭제 실패 시 리스트 페이지로 리다이렉트
	}

%>