<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dto.*" %>
<%@ page import="model.*" %>
<%@ page import="java.util.*" %>
<%
	// param 받기
	String title = request.getParameter("title");
	int type = Integer.parseInt(request.getParameter("type"));
	int qnum = Integer.parseInt(request.getParameter("qnum"));
	
	// item.content 공백요소는 제외해야함
	String[] contents = request.getParameterValues("content");

	// Question 모델 호출해서 수정
	QuestionDao questionDao = new QuestionDao();
	questionDao.updateQuestion(qnum,title,type);
	
	ArrayList<Item> itemList = new ArrayList<>();
	
	// 아이템들 받기
	int inum = 1;
	for(String s : contents){
		if(s.isEmpty()){
			continue;
		}
		Item item = new Item();
		
		item.setQnum(qnum);
		item.setInum(inum);
		item.setContent(s);
		
		itemList.add(item);
		inum++;
	}
	
	
	// Item 모델 호출해서 DELETE 후 INSERT
	ItemDao itemDao = new ItemDao();
	
	// DELETE
	itemDao.deleteItem(qnum);
	
	// INSERT
	for(Item i : itemList){
		itemDao.insertItem(i);
	}
	
	// view가 필요가 없다 -> 새로운 요청 pollList.jsp
	response.sendRedirect("/poll/pollList.jsp");
%>