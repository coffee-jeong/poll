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
		return pk;
	
	}
}
