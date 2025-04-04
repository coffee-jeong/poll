package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Item;

// Table : Item CRUD담당
public class ItemDao {
	public void insertItem(Item item) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		String sql = "insert into item(qnum, inum, content) value(?,?,?)";
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, item.getQnum());
		stmt.setInt(2, item.getInum());
		stmt.setString(3, item.getContent());
		int row = stmt.executeUpdate(); 
	
		if(row == 1) {
			System.out.println("ItemDao.insertItem - 입력성공");
		} else {
			System.out.println("ItemDao.insertItem - 입력실패");
		}
		conn.close();
	}
	
	// 삭제
	public void deleteItem(int qnum) throws ClassNotFoundException, SQLException{
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		Class.forName("com.mysql.cj.jdbc.Driver");
		
		String sql = "DELETE FROM item WHERE qnum = ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, qnum);
		
		row = stmt.executeUpdate();
		
		conn.close();
	}

	// 수정하기
	public ArrayList<Item> selectItem(int qnum) throws ClassNotFoundException, SQLException {
		ArrayList<Item> list = new ArrayList<Item>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		Class.forName("com.mysql.cj.jdbc.Driver");
		
		String sql = "SELECT qnum, inum, content, count FROM item WHERE qnum = ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, qnum);
		
		rs = stmt.executeQuery();
		
		while(rs.next()) {
			Item i = new Item();
			
			i.setQnum(rs.getInt("qnum"));
			i.setInum(rs.getInt("inum"));
			i.setContent(rs.getString("content"));
			i.setCount(rs.getInt("count"));
			
			list.add(i);
		}
		
		conn.close();
		
		return list;
	}
	
	// 투표하기에서 문제의 리스트 출력(updateItemFrom)  and 결과 출력(questionOneResult)
	public ArrayList<Item> selectItemListByQnum(int qnum) throws ClassNotFoundException, SQLException {
		ArrayList<Item> list = new ArrayList<Item>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * from item where qnum = ? order by inum asc";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, qnum);
		rs = stmt.executeQuery();
		// 외부 JDBC 라이브러리에 의존하는 ResultSet을 ArrayList타입으로 변경
		while(rs.next()) {
			Item i = new Item();
			i.setQnum(qnum);
			i.setInum(rs.getInt("inum"));
			i.setContent(rs.getString("content"));
			i.setCount(rs.getInt("count"));
			list.add(i);
		}
		return list;
	}
	
	public void updateItemCountPlus(int qnum, int inum) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = "update item set count = count+1 where qnum = ? and  inum = ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, qnum);
		stmt.setInt(2, inum);
		int row = stmt.executeUpdate();
		if(row == 1) {
			System.out.println("ItemDao.updateItemCountPlus#입력성공");
		} else {
			System.out.println("ItemDao.updateItemCountPlus#입력실패");
		}
	}
	
	public int selectItemCountbyQnum(int qnum) throws ClassNotFoundException, SQLException {
		int count = 0;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select sum(count) cnt from item group by qnum having qnum = ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, qnum);
		rs = stmt.executeQuery();
		if(rs.next()) {
			count = rs.getInt("cnt"); // rs.getInt(1) : 하나의 값이라서 
		}
		
		return count;
	}
	
}
