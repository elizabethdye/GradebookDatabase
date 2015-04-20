package Database;

import java.sql.*;
import java.util.ArrayList;

import Model.CourseInfo;
import Model.GradeInfo;
import Model.UserTypes;

public class Database {
	
	Statement stat;
	
	public Database(String filename) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection(filename);
        stat = con.createStatement();
        createTables();
	}
	
	public void createTables() {
		try {
			stat.executeUpdate("CREATE TABLE CourseParticipantTable (Professor TEXT, Course TEXT, Student TEXT)");
			stat.executeUpdate("CREATE TABLE AssignmentTable (Professor TEXT, Course TEXT, Assignment TEXT, TotalPossible REAL)");
			stat.executeUpdate("CREATE TABLE GradeTable (Student TEXT, Professor TEXT, Course TEXT, Assignment TEXT, Grade REAL)");
			stat.executeUpdate("CREATE TABLE LoginTable (Person TEXT, Type TEXT, Password TEXT)");
			stat.executeUpdate("CREATE TABLE CourseTable (Professor TEXT, Course TEXT)");
		}
		catch (SQLException sq){
			return;
		}
	}
	
	public void deleteTables() throws SQLException {
		stat.executeUpdate("DROP TABLE CourseParticipantTable");
		stat.executeUpdate("DROP TABLE AssignmentTable");
		stat.executeUpdate("DROP TABLE GradeTable");
		stat.executeUpdate("DROP TABLE LoginTable");
		stat.executeUpdate("DROP TABLE CourseTable");
	}
	
	public void addCourse(String courseName, String professorName) throws SQLException {
		//TODO: do I need to check if it is already present?
		stat.executeUpdate("INSERT INTO CourseTable VALUES ('" + professorName + "', '" + courseName + "')");
	}
	
	public void removeCourse(String courseName, String professorName) throws SQLException {
		stat.executeUpdate("DELETE FROM CourseTable WHERE Professor = '" + professorName + "' AND Course = '" + courseName + "'");
	}
	
	public void addAssignment(String professorName, String courseName, String assignmentName) throws SQLException {
		stat.executeUpdate("INSERT INTO AssignmentTable VALUES ('" + professorName + "', '" + courseName + "', '" + assignmentName 
				+ "', -1)");
		for (String student: getStudents(professorName, courseName)) {
			stat.executeUpdate("INSERT INTO GradeTable VALUES ('" + student + "', '" + professorName + "', '" + courseName + 
					"', '" + assignmentName + "', -1)");
		}
	}
	
	public void setTotalPossible(String professorName, String courseName, String assignmentName, Double totalPoints) throws SQLException {
		stat.executeUpdate("UPDATE AssignmentTable SET TotalPossible = " + totalPoints.toString() + " WHERE Professor = '" + 
				professorName + "' AND Course = '" + courseName + "' AND Assignment = '" + assignmentName + "'");
	}
	
	public double getTotalPossible(String professorName, String courseName, String assignmentName) throws SQLException {
		ResultSet results = stat.executeQuery("SELECT * FROM AssignmentTable WHERE Professor = '" + professorName + 
				"' AND Course = '" + courseName + "' AND Assignment = '" + assignmentName + "'");
		double val = results.getDouble("TotalPossible");
		results.close();
		return val;
	}
	
	public ArrayList<Double> getTotalGrades(String professorName, String courseName) throws SQLException {
		ResultSet results = stat.executeQuery("SELECT * FROM AssignmentTable WHERE Professor = '" + professorName + 
				"' AND Course = '" + courseName + "'");
		ArrayList<Double> grades = new ArrayList<Double>();
		while (results.next()) {
			grades.add(results.getDouble("TotalPossible"));
		}
		results.close();
		return grades;
	}
	
	public ArrayList<Double> getStudentGrades(String studentName, String professorName, String courseName) throws SQLException {
		ResultSet results = stat.executeQuery("SELECT * FROM GradeTable WHERE Course = '" + courseName + "' AND Professor = '" 
				+ professorName + "' AND Student = '" + studentName + "'");
		ArrayList<Double> grades = new ArrayList<Double>();
		while (results.next()) {
			grades.add(results.getDouble("Grade"));
		}
		results.close();
		return grades;
	}
	
	public void removeAssignment(String professorName, String courseName, String assignmentName) throws SQLException {
		stat.executeUpdate("DELETE FROM AssignmentTable WHERE Professor = '" + professorName + "' AND Course = '" + courseName + 
				"' AND Assignment = '" + assignmentName + "'");
		stat.executeUpdate("DELETE FROM GradeTable WHERE Professor = '" + professorName + "' AND Course = '" + courseName + 
				"' AND Assignment = '" + assignmentName + "'");
	}
	
	public void addStudent(String professorName, String studentName, String courseName) throws SQLException {
		stat.executeUpdate("INSERT INTO CourseParticipantTable VALUES ('" + professorName + "', '" + courseName + "', '" + 
				studentName + "')");
		ArrayList<String> assignments = getAssignments(professorName, courseName);
		for (String assignment:assignments) {
			stat.executeUpdate("INSERT INTO GradeTable VALUES ('" + studentName + "', '" + professorName + "', '" + courseName + 
					"', '" + assignment + "', -1)"); 
		}
	}
	
	public void removeStudent(String professorName, String studentName, String courseName) throws SQLException {
		stat.executeUpdate("DELETE FROM CourseParticipantTable WHERE Professor = '" + professorName + "' AND Course = '" + 
				courseName + "' AND Student = '" + studentName + "'");
		stat.executeUpdate("DELETE FROM GradeTable WHERE Professor = '" + professorName + "' AND Course = '" + courseName + 
				"' AND Student = '" + studentName + "'");
	}
	
	public ArrayList<String> getStudentInfo(String studentName) throws SQLException {
		ResultSet results = stat.executeQuery("SELECT * FROM CourseParticipantTable WHERE Student = '" + studentName + "'");
		ArrayList<String> courses = new ArrayList<String>();
		while (results.next()) {
			courses.add(results.getString("Course"));
		}
		results.close();
		return courses;
	}
	
	public void addGrade(String assignmentName, String studentName, Double grade, String professorName, String courseName) throws SQLException {
		stat.executeUpdate("UPDATE GradeTable SET Grade = " + grade.toString() + " WHERE Student = '" + studentName + 
				"' AND Professor = '" + professorName + "' AND Course = '" + courseName + "' AND Assignment = '" + assignmentName 
				+ "'");
	}
	
	public double retrieveGrade(String assignmentName, String studentName, String courseName, String profName) throws SQLException {
		ResultSet results = stat.executeQuery("SELECT Grade FROM GradeTable WHERE Assignment = '" + assignmentName + 
				"' AND Student = '" + studentName + "' AND Professor = '" + profName + "' AND Course = '" + courseName + "'");
		Double val = results.getDouble(1);
		results.close();
		return val;
	}
	
	public ArrayList<String> getCourses(String professorName) throws SQLException {
		ResultSet results = stat.executeQuery("SELECT * FROM CourseTable WHERE Professor = '" + professorName + "'");
		ArrayList<String> courses = new ArrayList<String>();
		while (results.next()) {
			courses.add(results.getString("Course"));
		}
		results.close();
		return courses;
	}
	
	public ArrayList<String> getAssignments(String professorName, String courseName) throws SQLException {
		ResultSet results = stat.executeQuery("SELECT * FROM AssignmentTable WHERE Professor = '" + professorName + 
				"' AND Course = '" + courseName + "'");
		ArrayList<String> assignments = new ArrayList<String>();
		while (results.next()) {
			assignments.add(results.getString("Assignment"));
		}
		results.close();
		return assignments;
	}
	
	public ArrayList<String> getStudents(String professorName, String courseName) throws SQLException {
		ResultSet results = stat.executeQuery("SELECT * FROM CourseParticipantTable WHERE Professor = '" + professorName + 
				"' AND Course = '" + courseName + "'");
		ArrayList<String> students = new ArrayList<String>();
		while (results.next()) {
			students.add(results.getString("Student"));
		}
		results.close();
		return students;
	}
	
	public ArrayList<Model.GradeInfo> getGradeInfo(String professorName, String courseName) throws SQLException {
		ResultSet results = stat.executeQuery("SELECT * FROM GradeTable WHERE Professor = '" + professorName + "' AND Course = '" 
				+ courseName + "'");
		ArrayList<Model.GradeInfo> gradeInfo = new ArrayList<Model.GradeInfo>(); 
		while (results.next()) {
			GradeInfo gradeSet = new GradeInfo(results.getString("Student"), results.getString("Assignment"), results.getDouble("Grade"));
			gradeInfo.add(gradeSet);
		}
		results.close();
		return gradeInfo;
	}
	
	public UserTypes getUserType(String ID, String password) throws SQLException{
		ResultSet results = stat.executeQuery("SELECT * FROM LoginTable WHERE Person = '" + ID + "' AND Password = '" + password 
				+ "'");
		UserTypes type;
		if (results.next()) {
			type = UserTypes.fromString(results.getString("Type"));
		}
		else {
			type = UserTypes.fromString("INVALID");
		}
		results.close();
		return type;
	}
	
	public void addUser(String ID, String password, UserTypes type) throws SQLException {
		stat.executeUpdate("INSERT INTO LoginTable VALUES ('" + ID + "', '" + type.toString() + "', '" + password + "')");
	}
}