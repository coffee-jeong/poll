package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dto.Paging;
import dto.Question;

// Table : question CRUD담당
public class QuestionDao {
	public ArrayList<Question> selectQuestionList(Paging p) throws ClassNotFoundException, SQLException {
		 ArrayList<Question> list = new  ArrayList<>();
		 Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
		 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
			// 페이징 쿼리
			String sql = "SELECT num, title, startdate, enddate, type FROM question ORDER BY num DESC LIMIT ?, ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, p.getBiginRow());  // 시작 행 번호
			stmt.setInt(2, p.getRowPerPage()); // 한 페이지에 보여줄 행 수
			
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				Question question = new Question();
				question.setNum(rs.getInt("num"));
				question.setTitle(rs.getString("title"));
				question.setStartdate(rs.getString("startdate"));
				question.setEnddate(rs.getString("enddate"));
				question.setType(rs.getInt("type"));
				list.add(question);
			}
			conn.close();
			return list;
	}
	
	
	// 입력 후 자동으로 생성된 키값을 반환값으로 받는다
	public int insertQuestion(Question question) throws ClassNotFoundException, SQLException {
		int pk = 0;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		// 키값 반환받기 위해
		ResultSet rs = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		String sql = "insert into question(title, startdate, enddate, type) values(?,?,?,?)";
		// Statement.RETURN_GENERATED_KEYS 옵션 : insert 후 select max(pk) from...실행 
		// 제일 마지막 키를 반환해준다 
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, question.getTitle());
		stmt.setString(2, question.getStartdate());
		stmt.setString(3, question.getEnddate());
		stmt.setInt(4, question.getType());
		int row = stmt.executeUpdate();  // insert
		rs = stmt.getGeneratedKeys(); // select max(num) from question
		if(rs.next()) {
			pk = rs.getInt(1); //설렉트 절의 1번(순서를 적는다)  위의 max(num)값을 모르니 
		}
		conn.close();
		return pk;
	
	}
	// 삭제 
	public boolean deleteQuestion(int qnum) throws ClassNotFoundException, SQLException {
		boolean isDelete = false;
		int row = 0;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll", "root", "java1234");
		String sql = "SELECT * FROM question q INNER JOIN (SELECT qnum, SUM(COUNT) FROM item GROUP BY qnum) t ON q.num = t.qnum";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt = conn.prepareStatement(sql);
		System.out.println(stmt);
		ResultSet rs = stmt.executeQuery();
		
		ItemDao itemDao = new ItemDao();
		itemDao.deleteItem(qnum);
		
		sql = "DELETE FROM question WHERE num = ?";
		
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, qnum);
		
		row = stmt.executeUpdate();
		if(row == 1) {
			System.out.println("정상 삭제");
			isDelete = true;
		}
		else {
			System.out.println("비정상 삭제");
		}
		
		conn.close();
		
		return isDelete;
	}
	
	public Question selectQuestion(int qnum) throws ClassNotFoundException, SQLException {
		Question q = new Question();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		Class.forName("com.mysql.cj.jdbc.Driver");
		
		String sql = "SELECT num, title, startdate AS startDate, enddate AS endDate, createdate AS createDate, type "
					+ "FROM question WHERE num = ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, qnum);
		
		rs = stmt.executeQuery();
		
		rs.next();
		
		q.setNum(rs.getInt("num"));
		q.setTitle(rs.getString("title"));
		q.setStartdate(rs.getString("startDate"));
		q.setEnddate(rs.getString("endDate"));
		q.setCreatedate(rs.getString("createDate"));
		q.setType(rs.getInt("type"));
		
		conn.close();
		
		return q;	
	}
	
	public void updateQuestion(int qnum, String title, int type) throws ClassNotFoundException, SQLException{
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt = null;

		Class.forName("com.mysql.cj.jdbc.Driver");
		
		String sql = "UPDATE question SET title = ?, type = ? WHERE num = ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, title);
		stmt.setInt(2, type);
		stmt.setInt(3, qnum);
		
		// 디버깅
		//System.out.println(stmt);
		
		row = stmt.executeUpdate();
		
		if(row == 1) {
			System.out.println("정상 수정");
		}
		else {
			System.out.println("비정상 수정");
		}
		
		conn.close();
	}
	
	public Question selectQuestionOne(int num) throws ClassNotFoundException, SQLException {
		Question q = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from question where num = ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, num);
		rs = stmt.executeQuery();
		if(rs.next()) {
			q = new Question();
			q.setNum(num);
			q.setTitle("title");
			q.setStartdate(rs.getString("startdate"));
			q.setEnddate(rs.getString("enddate"));
			q.setStartdate(rs.getString("startdate"));
			q.setType(rs.getInt("type"));
		}
		
		
		 return q;
	}
}
