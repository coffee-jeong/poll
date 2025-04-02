package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
		int row = stmt.executeUpdate();  // insert
	
		if(row == 1) {
			System.out.println("ItemDao.insertItem - 입력성공");
		} else {
			System.out.println("ItemDao.insertItem - 입력실패");
		}
		conn.close();
	}
}
