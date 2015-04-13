package Database;

import java.sql.*;
import java.util.ArrayList;

import Model.CourseInfo;
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
			stat.execute("CREATE TABLE CourseParticipantTable (Professor TEXT, Course TEXT, Student TEXT, OverallGrade REAL)");
			stat.execute("CREATE TABLE AssignmentTable (Professor TEXT, Course TEXT, Assignment TEXT, TotalPossible REAL)");
			stat.execute("CREATE TABLE GradeTable (Student TEXT, Professor TEXT, Course TEXT, Assignment TEXT, Grade REAL)");
			stat.execute("CREATE TABLE LoginTable (Person TEXT, Type TEXT, Password TEXT)");
			stat.execute("CREATE TABLE CourseTable (Professor TEXT, Course TEXT)");
		}
		catch (SQLException sq){
			return;
		}
	}
	
	public void deleteTables() throws SQLException {
		stat.execute("DROP TABLE CourseParticipantTable");
		stat.execute("DROP TABLE AssignmentTable");
		stat.execute("DROP TABLE GradeTable");
		stat.execute("DROP TABLE LoginTable");
		stat.execute("DROP TABLE CourseTable");
	}
	
	public void addCourse(String courseName, String professorName) throws SQLException {
		//TODO: do I need to check if it is already present?
		stat.execute("INSERT INTO CourseTable VALUES ('" + professorName + "', '" + courseName + "')");
	}
	
	public void removeCourse(String courseName, String professorName) throws SQLException {
		stat.execute("DELETE FROM CourseTable WHERE Professor = '" + professorName + "' AND Course = '" + courseName + "'");
	}
	
	public void addAssignment(String professorName, String courseName, String assignmentName) throws SQLException {
		stat.execute("INSERT INTO AssignmentTable VALUES ('" + professorName + "', '" + courseName + "', '" + assignmentName + "', -1)");
		for (String student: getStudents(professorName, courseName)) {
			stat.execute("INSERT INTO GradeTable VALUES ('" + student + "', '" + professorName + "', '" + courseName + "', '" + assignmentName + "', -1)");
		}
	}
	
	public void setTotalPossible(String professorName, String courseName, String assignmentName, Double totalPoints) throws SQLException {
		stat.execute("UPDATE AssignmentTable SET TotalPossible = " + totalPoints.toString() + " WHERE Professor = '" + 
				professorName + "' AND Course = '" + courseName + "' AND Assignment = '" + assignmentName + "'");
	}
	
	public double getTotalPossible(String professorName, String courseName, String assignmentName) throws SQLException {
		stat.execute("SELECT * FROM AssignmentTable WHERE Professor = '" + professorName + "' AND Course = '" + courseName + 
				"' AND Assignment = '" + assignmentName + "'");
		ResultSet results = stat.getResultSet();
		double val = results.getDouble("TotalPossible");
		results.close();
		return val;
	}
	
	public ArrayList<Double> getTotalGrades(String professorName, String courseName) throws SQLException {
		stat.execute("SELECT * FROM AssignmentTable WHERE Professor = '" + professorName + "' AND Course = '" + courseName + "'");
		ResultSet results = stat.getResultSet();
		ArrayList<Double> grades = new ArrayList<Double>();
		while (results.next()) {
			grades.add(results.getDouble("TotalPossible"));
		}
		results.close();
		return grades;
	}
	
	public ArrayList<Double> getStudentGrades(String studentName, String professorName, String courseName) throws SQLException {
		stat.execute("SELECT * FROM GradeTable WHERE Course = '" + courseName + "' AND Professor = '" + professorName + "' AND Student = '" + studentName + "'");
		ResultSet results = stat.getResultSet();
		ArrayList<Double> grades = new ArrayList<Double>();
		while (results.next()) {
			grades.add(results.getDouble("Grade"));
		}
		results.close();
		return grades;
	}
	
	public void removeAssignment(String professorName, String courseName, String assignmentName) throws SQLException {
		stat.execute("DELETE FROM AssignmentTable WHERE Professor = '" + professorName + "' AND Course = '" + courseName + "' AND Assignment = '" + assignmentName + "'");
		stat.execute("DELETE FROM GradeTable WHERE Professor = '" + professorName + "' AND Course = '" + courseName + "' AND Assignment = '" + assignmentName + "'");
	}
	
	public void addStudent(String professorName, String studentName, String courseName) throws SQLException {
		stat.execute("INSERT INTO CourseParticipantTable VALUES ('" + professorName + "', '" + courseName + "', '" + studentName + "', -1)");
		ArrayList<String> assignments = getAssignments(professorName, courseName);
		for (String assignment:assignments) {
			stat.execute("INSERT INTO GradeTable VALUES ('" + studentName + "', '" + professorName + "', '" + courseName + "', '" + assignment + "', -1)"); 
		}
	}
	
	public void removeStudent(String professorName, String studentName, String courseName) throws SQLException {
		stat.execute("DELETE FROM CourseParticipantTable WHERE Professor = '" + professorName + "' AND Course = '" + courseName + "' AND Student = '" + studentName + "'");
		stat.execute("DELETE FROM GradeTable WHERE Professor = '" + professorName + "' AND Course = '" + courseName + "' AND Student = '" + studentName + "'");
	}
	
	public ArrayList<CourseInfo> getStudentInfo(String studentName) throws SQLException {
		stat.execute("SELECT * FROM CourseParticipantTable WHERE Student = '" + studentName + "'");
		ResultSet results = stat.getResultSet();
		ArrayList<CourseInfo> courseInfo = new ArrayList<CourseInfo>();
		while (results.next()) {
			courseInfo.add(new CourseInfo(results.getString("Course"), results.getString("Professor")));
		}
		results.close();
		return courseInfo;
	}
	
	public void addGrade(String assignmentName, String studentName, Double grade, String professorName, String courseName) throws SQLException {
		//TODO: when updating do I need to update all fields?
		stat.execute("UPDATE GradeTable SET Grade = " + grade.toString() + " WHERE Student = '" + studentName + "' AND Professor = '" 
		+ professorName + "' AND Course = '" + courseName + "' AND Assignment = '" + assignmentName + "'");
		//TODO: I would recommend that whatever calls this value also uses it to compute the grade and updates that subsequently
	}
	
	public double retrieveGrade(String assignmentName, String studentName, String courseName, String profName) throws SQLException {
		stat.execute("SELECT Grade FROM GradeTable WHERE Assignment = '" + assignmentName + 
				"' AND Student = '" + studentName + "' AND Professor = '" + profName + 
				"' AND Course = '" + courseName + "'");
		ResultSet results = stat.getResultSet();
		Double val = results.getDouble(1);
		results.close();
		return val;
		//TODO: do I need this method? I don't think so.
	}
	
	public double retrieveOverallGrade(String professorName, String courseName, String studentName) throws SQLException {
		stat.execute("SELECT * FROM CourseParticipantTable WHERE Professor = '" + professorName + "' AND Course = '" + courseName + "' AND Student = '" + studentName + "'");
		ResultSet results = stat.getResultSet();
		Double val = results.getDouble("OverallGrade");
		results.close();
		return val;
	}
	
	public void setOverallGrade(String professorName, String courseName, String studentName, Double grade) throws SQLException {
		stat.execute("UPDATE CourseParticipantTable SET OverallGrade = " + grade.toString() + " WHERE Professor = '" + 
				professorName + "' AND Course = '" + courseName + "' AND Student = '" + studentName + "'");
	}
	
	public ArrayList<String> getCourses(String professorName) throws SQLException {
		stat.execute("SELECT * FROM CourseTable WHERE Professor = '" + professorName + "'");
		ResultSet results = stat.getResultSet();
		ArrayList<String> courses = new ArrayList<String>();
		while (results.next()) {
			courses.add(results.getString("Course"));
		}
		results.close();
		return courses;
	}
	
	public ArrayList<String> getAssignments(String professorName, String courseName) throws SQLException {
		stat.execute("SELECT * FROM AssignmentTable WHERE Professor = '" + professorName + "' AND Course = '" + courseName + "'");
		ResultSet results = stat.getResultSet();
		ArrayList<String> assignments = new ArrayList<String>();
		while (results.next()) {
			assignments.add(results.getString("Assignment"));
		}
		results.close();
		return assignments;
	}
	
	public ArrayList<String> getStudents(String professorName, String courseName) throws SQLException {
		stat.execute("SELECT * FROM CourseParticipantTable WHERE Professor = '" + professorName + "' AND Course = '" + courseName + "'");
		ResultSet results = stat.getResultSet();
		ArrayList<String> students = new ArrayList<String>();
		while (results.next()) {
			students.add(results.getString("Student"));
		}
		results.close();
		return students;
	}
	
	public ArrayList<Model.GradeInfo> getGradeInfo(String professorName, String courseName) throws SQLException {
		stat.execute("SELECT * FROM GradeTable WHERE Professor = '" + professorName + "' AND Course = '" + courseName + "'");
		ResultSet results = stat.getResultSet();
		ArrayList<Model.GradeInfo> gradeInfo = new ArrayList<Model.GradeInfo>(); 
		while (results.next()) {
			Model.GradeInfo gradeSet = new Model.GradeInfo(results.getString("Student"), results.getString("Assignment"), results.getDouble("Grade"));
			gradeInfo.add(gradeSet);
		}
		results.close();
		return gradeInfo;
	}
	
	public UserTypes getUserType(String ID, String password) throws SQLException{
		stat.execute("SELECT * FROM LoginTable WHERE Person = '" + ID + "' AND Password = '" + password + "'");
		ResultSet results = stat.getResultSet();
		if (results.next()) {
			UserTypes type = UserTypes.fromString(results.getString("Type"));
			results.close();
			return type;
		}
		else {
			UserTypes type = UserTypes.fromString("INVALID");
			results.close();
			return type;
		}
	}
	
	public void addUser(String ID, String password, UserTypes type) throws SQLException {
		stat.execute("INSERT INTO LoginTable VALUES ('" + ID + "', '" + type.toString() + "', '" + password + "')");
	}
}