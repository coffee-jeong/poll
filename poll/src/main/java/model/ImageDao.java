package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Image;
import dto.Paging;

public class ImageDao {
	public void deleteImage(int num) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = "delete from image where num = ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");

		stmt= conn.prepareStatement(sql); 
		stmt.setInt(1, num);
		int row = stmt.executeUpdate();
		
		conn.close();			
		
	}
	
	public ArrayList<Image> selectImageList(Paging p) throws ClassNotFoundException, SQLException {
		ArrayList<Image> list = new ArrayList<Image>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		String sql = "select * from image order by num desc limit ?, ?";
		
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, p.getBiginRow());
		stmt.setInt(2, p.getRowPerPage());
		
		rs = stmt.executeQuery();
		while(rs.next()) {
			Image img = new Image();
			img.setNum(rs.getInt("num"));
			img.setMemo(rs.getString("memo"));
			img.setFilename(rs.getString("filename"));
			img.setCreatedate(rs.getString("createdate"));
			list.add(img);
		}
		return list;
		
	}
	
	public void insertImage(Image img) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt2 = null;
		String sql2 = "update board set pos = pos+1 where ref=? and pos >= ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		
		// 답글입력
		PreparedStatement stmt = null;
		String sql = "insert into image(memo,filename) values(?,?)";
		stmt= conn.prepareStatement(sql); // 
		stmt.setString(1, img.getMemo());
		stmt.setString(2, img.getFilename());
		int row = stmt.executeUpdate();
		
		conn.close();		
	}
}