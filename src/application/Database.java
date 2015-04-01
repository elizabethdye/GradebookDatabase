package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	
	Statement stat;
	
	public Database() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:db");
        stat = con.createStatement();
        //TODO: somehow check to see if the database has the appropriate tables or not
        createTables();
	}
	
	private void createTables() throws SQLException {
		stat.execute("CREATE TABLE StudentGradeTable (Course TEXT, Student TEXT, OverallGrade REAL)");
		stat.execute("CREATE TABLE AssignmentTable (Course TEXT, Assignment TEXT)");
		stat.execute("CREATE TABLE AssignmentGradeTable (Assignment TEXT, Student TEXT, Grade REAL)");
		stat.execute("CREATE TABLE LoginTable (Person TEXT, Type TEXT, Password TEXT)");
	}
	
	public void addCourse(String courseName) throws SQLException {
		//TODO: do I need to check if it is already present?
		stat.execute("INSERT INTO StudentGradeTable VALUES (" + courseName + "'', '')");
		stat.execute("INSERT INTO AssignmentTable VALUES (" + courseName + "'','')");
	}
	
	public void addStudent(String studentName, String courseName) throws SQLException {
		stat.execute("INSERT INTO StudentGradeTable VALUES (" + courseName + "," + studentName + ", '')");
		stat.execute("SELECT FROM AssignmentTable WHERE courseName = " + courseName);
		ResultSet results = stat.getResultSet();
		while (results.next()) {
			stat.execute("INSERT INTO AssignmentGradeTable VALUES (" + results.getString("Assignment") + ", " + studentName + ", '')"); 
		//TODO: not sure if the results.getString() part is done correctly
		}
	}
	
	public void addGrade(String assignmentName, String studentName, Double grade) throws SQLException {
		//TODO: what to do if two courses have assignments of the same name?
		//TODO: when updating do I need to update all fields?
		stat.execute("UPDATE AssignmentGradeTable SET Assignment = " + assignmentName + ", Student = " + studentName + 
				", Grade = " + grade.toString() + " WHERE Assignment = " + assignmentName + " AND Student = " + studentName);
		//TODO: I would recommend that whatever calls this value also uses it to compute the grade and updates that subsequently
	}
	
	public double retrieveGrade(String assignmentName, String studentName) throws SQLException {
		stat.execute("SELECT FROM AssignmentGradeTable WHERE Assignment = " + assignmentName + " AND Student = " + studentName);
		ResultSet results = stat.getResultSet();
		return results.getDouble("Grade");
	}
	
	public double retrieveOverallGrade(String courseName, String studentName) throws SQLException {
		stat.execute("SELECT FROM StudentGradeTable WHERE Course = " + courseName + " AND Student = " + studentName);
		ResultSet results = stat.getResultSet();
		return results.getDouble("OverallGrade");
	}
}
