package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
		stat.execute("CREATE TABLE CourseParticipantTable (Professor TEXT, Course TEXT, Student TEXT, OverallGrade REAL)");
		stat.execute("CREATE TABLE AssignmentTable (Professor TEXT, Course TEXT, Assignment TEXT, TotalPossible REAL)");
		stat.execute("CREATE TABLE GradeTable (Student TEXT, Professor TEXT, Course TEXT, Assignment TEXT, Grade REAL)");
		stat.execute("CREATE TABLE LoginTable (Person TEXT, Type TEXT, Password TEXT)");
		stat.execute("CREATE TABLE CourseTable (Professor TEXT, Course TEXT)");
	}
	
	public void addCourse(String courseName, String professorName) throws SQLException {
		//TODO: do I need to check if it is already present?
		stat.execute("INSERT INTO CourseTable VALUES (" + professorName + ", " + courseName + ")");
	}
	
	public void addStudent(String professorName, String studentName, String courseName) throws SQLException {
		stat.execute("INSERT INTO CourseParticipantTable VALUES (" + professorName + ", " + courseName + ", " + studentName + ", '')");
		//TODO: for each assignment, add a thing.
		stat.execute("SELECT FROM AssignmentTable WHERE Course = " + courseName + " AND Professor = " + professorName);
		ResultSet results = stat.getResultSet();
		while (results.next()) {
			stat.execute("INSERT INTO GradeTable VALUES (" + studentName + ", " + professorName + ", " + courseName + ", " + results.getString(3) + ", '')"); 
		//TODO: not sure if the results.getString() part is done correctly
		}
	}
	
	public void addGrade(String assignmentName, String studentName, Double grade, String professorName, String courseName) throws SQLException {
		//TODO: when updating do I need to update all fields?
		stat.execute("UPDATE GradeTable SET Grade = " + grade.toString() + " WHERE Student = " + studentName + " AND Professor = " 
		+ professorName + " AND Course = " + courseName + "AND Assignment = " + assignmentName);
		//TODO: I would recommend that whatever calls this value also uses it to compute the grade and updates that subsequently
	}
	
	public double retrieveGrade(String assignmentName, String studentName) throws SQLException {
		stat.execute("SELECT FROM AssignmentGradeTable WHERE Assignment = " + assignmentName + " AND Student = " + studentName);
		ResultSet results = stat.getResultSet();
		return results.getDouble("Grade");
		//TODO: do I need this method? I don't think so.
	}
	
	public double retrieveOverallGrade(String professorName, String courseName, String studentName) throws SQLException {
		stat.execute("SELECT FROM CourseParticipantTable WHERE Professor = " + professorName + "AND Course = " + courseName + " AND Student = " + studentName);
		ResultSet results = stat.getResultSet();
		return results.getDouble("OverallGrade");
	}
	
	public ArrayList<String> getCourses(String professorName) throws SQLException {
		stat.execute("SELECT FROM CourseTable WHERE Professor = " + professorName);
		ResultSet results = stat.getResultSet();
		ArrayList<String> courses = new ArrayList<String>();
		while (results.next()) {
			courses.add(results.getString("Course"));
		}
		return courses;
	}
	
	public ArrayList<String> getAssignments(String professorName, String courseName) throws SQLException {
		stat.execute("SELECT FROM AssignmentTable WHERE Professor = " + professorName + " AND Course = " + courseName);
		ResultSet results = stat.getResultSet();
		ArrayList<String> assignments = new ArrayList<String>();
		while (results.next()) {
			assignments.add("Assignment");
		}
		return assignments;
	}
	
	public ArrayList<String> getStudents(String professorName, String courseName) throws SQLException {
		stat.execute("SELECT FROM CourseParticipants WHERE Professor = " + professorName + " AND Course = " + courseName);
		ResultSet results = stat.getResultSet();
		ArrayList<String> students = new ArrayList<String>();
		while (results.next()) {
			students.add("Student");
		}
		return students;
	}
	
	public ArrayList<ArrayList>
	//TODO: what happens if grade is not entered? is ''?
}
