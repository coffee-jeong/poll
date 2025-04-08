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
	
	public void deleteBoard(int num, int ref) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		conn.setAutoCommit(false);  // executeUpdate()시마다 자동 커밋기능을 false
		String sql = "DELETE FROM board WHERE num = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, num);
		
		int row = stmt.executeUpdate();
		
		// 본 게시글 삭제시 답글의 subject를 본 게시글 삭제로 변경
		if(row == 1) {
		PreparedStatement stmt2 = null;
		String sql2 = "update board set subject = '상위 게시글이 삭제된 글입니다' where ref =?";
		stmt2 = conn.prepareStatement(sql2);
		stmt2.setInt(1, ref);
	    int row2 = stmt2.executeUpdate();
	    conn.commit();
		}
		
		conn.close();
	}
	
	public void updateBoard(String name, String subject, String content, int num, int pass) throws ClassNotFoundException, SQLException {
		int row = 0;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		String sql = "update board set name = ?, subject = ?, content =? where num = ? and pass = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, name);
		stmt.setString(2, subject);
		stmt.setString(3, content);
		stmt.setInt(4, num);
		stmt.setInt(5, pass);
		
		row = stmt.executeUpdate();
		
		conn.close();

	}
	
	// 답글 입력
	public void insertBoardReplay(Board b) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		// 트랜잭션(2개이상의(CUD)쿼리 한 묶음처럼 처리하고자 할때
		conn.setAutoCommit(false); // executeUpdate()시마다 자동 커밋기능을 false
		
		// ref같고 pos값이 현재글보다 크거나 같다면 pos=pos+1
		PreparedStatement stmt2 = null;
		String sql2 = "update board set pos = pos+1 where ref=? and pos >= ?";
		stmt2 = conn.prepareStatement(sql2);
		stmt2.setInt(1, b.getRef());
		stmt2.setInt(2, b.getPos());
		int row2 = stmt2.executeUpdate();
		
		// 답글입력
		PreparedStatement stmt = null;
		String sql = "insert into board(name, subject, content, ref, pos, depth, pass, ip) values (?,?,?,?,?,?,?,?)";
		stmt= conn.prepareStatement(sql); // ref==0면 입력직후 pk을 반환받기 위해
		stmt.setString(1, b.getName());
		stmt.setString(2, b.getSubject());
		stmt.setString(3, b.getContent());
		
		stmt.setInt(4, b.getRef());
		stmt.setInt(5, b.getPos());
		stmt.setInt(6, b.getDepth());
		
		stmt.setString(7, b.getPass());
		stmt.setString(8, b.getIp());
		
		int row = stmt.executeUpdate(); 
		
		
		conn.commit(); // conn.setAutoCommit(false); 코드때문에 필요
		conn.close();
	}	
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
	
	public Board selectBoardOne(int num) throws ClassNotFoundException, SQLException {
		Board b = null;
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from board where num = ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, num);
		rs = stmt.executeQuery();
		
		// rs -> board
		if(rs.next()) {
			b = new Board();
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
	
	public ArrayList<Board> selectBoardList(Paging p) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from board order by ref desc, pos asc limit ?, ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, p.getBiginRow());
		stmt.setInt(2, p.getRowPerPage());
		rs = stmt.executeQuery();
		ArrayList<Board> list = new ArrayList<>();
		// rs -> list
		while(rs.next()) {
			Board b = new Board();
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