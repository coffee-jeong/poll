package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dto.Board;
import dto.Paging;

public class BoardDao {
	// 답글 입력
	
	// 새글 입력(부모글)
	public void insertBoard(Board b) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null; // 입력직후 PK값을 반환 받기 위해
		String sql = "insert into board(name, subject, content, ref, pass, ip) values (?,?,?,?,?,?)";
		
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		conn.setAutoCommit(false); // executeUpdate()시마다 자동 커밋기능을 false
		
		stmt= conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // ref==0면 입력직후 pk을 반환받기 위해
		stmt.setString(1, b.getName());
		stmt.setString(2, b.getSubject());
		stmt.setString(3, b.getContent());
		stmt.setInt(4, b.getRef());
		stmt.setString(5, b.getPass());
		stmt.setString(6, b.getIp());
		
		int row = stmt.executeUpdate(); 
		// ref==0면 입력직후 pk을 반환 받아서 ref값을 동일하게
		rs = stmt.getGeneratedKeys();
		int pk = 0;
		if(rs.next()) {
			pk = rs.getInt(1);
		}
		System.out.println("BoardDao.insertBoard#pk: "+pk);

		PreparedStatement stmt2 = null;
		String sql2 = "update board set ref = ? where num = ?";
		
		stmt2 = conn.prepareStatement(sql2);
		// update쿼리가 실패하면 이전의 insert도 롤백 : conn.rollback();
		
		stmt2.setInt(1, pk);
		stmt2.setInt(2, pk);
		stmt2.executeUpdate();
		
		conn.commit(); // conn.setAutoCommit(false); 코드때문에 필요
		conn.close();
	}
	
	public Board selectBoard(int num) throws ClassNotFoundException, SQLException {
		Board b =null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from board where num = ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, num);
		rs = stmt.executeQuery();
		
		if(rs.next()) {
			b =new Board();
			b.setNum(rs.getInt("num"));
			b.setName(rs.getString("name"));
			b.setSubject(rs.getString("subject"));
			b.setContent(rs.getString("content"));
			b.setPos(rs.getInt("pos"));
			b.setRef(rs.getInt("ref"));
			b.setDepth(rs.getInt("depth"));
			b.setRegdate(rs.getString("regdate"));
			b.setIp(rs.getString("ip"));
			b.setCount(rs.getInt("count"));
		}
		conn.close();
		return b;
	}
	
	public ArrayList<Board> selectBoardList(Paging p) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from board order by ref desc, pos limit ?, ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, p.getBiginRow());
		stmt.setInt(2, p.getRowPerPage());
		rs = stmt.executeQuery();
		ArrayList<Board> list = new ArrayList<>();
		while(rs.next()) {
			Board b =new Board();
			b.setNum(rs.getInt("num"));
			b.setName(rs.getString("name"));
			b.setSubject(rs.getString("subject"));
			b.setPos(rs.getInt("pos"));
			b.setRef(rs.getInt("ref"));
			b.setDepth(rs.getInt("depth"));
			b.setCount(rs.getInt("count"));
			list.add(b);
		}
		conn.close();
		return list;
	}
}
