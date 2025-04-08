<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "model.*" %>
<%
	int num = Integer.parseInt(request.getParameter("num"));
	int ref = Integer.parseInt(request.getParameter("ref"));

	
	BoardDao boardDao = new BoardDao();
	boardDao.deleteBoard(num, ref);
	
	response.sendRedirect("/poll/board/boardList.jsp");
	
%>
