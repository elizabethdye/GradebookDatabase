package Database;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Model.GradeInfo;
import Model.UserTypes;

public class DatabaseTest {

	private Database db;
	ArrayList<String> courseList = new ArrayList<String>(
			Arrays.asList("Course1", "Course2"));
	ArrayList<String> studentList = new ArrayList<String>(
		    Arrays.asList("Stu1", "Stu2"));
	ArrayList<String> assignmentList = new ArrayList<String>(
		    Arrays.asList("Assign1", "Test1"));
	
	
	@Before
	public void createDatabase() throws ClassNotFoundException, SQLException {
		db = new Database("jdbc:sqlite:testdb");
	}
	
	@After
	public void deleteDatabase() throws SQLException {
		db.deleteTables();
	}
	
	@Test
	public void testAddingCourse() throws ClassNotFoundException, SQLException {
		addTwoCourses();
		assertEquals(courseList, db.getCourses("Prof1"));
	}
	
	@Test
	public void testCreatingAfterCreated() throws SQLException {
		addTwoCourses();
		db.createTables();
		assertEquals(courseList, db.getCourses("Prof1"));
	}
	
	@Test
	public void testAddingStudents() throws SQLException {
		addTwoCourses();
		addTwoStudents();
		assertEquals(studentList, db.getStudents("Prof1", "Course1"));
	}
	
	@Test
	public void testAddingStudentsAfterAssignments() throws SQLException {
		addTwoCourses();
		addTwoAssignments();
		addTwoStudents();
		assertEquals(assignmentList, db.getAssignments("Prof1", "Course1"));
		assertEquals(-1, db.retrieveGrade("Assign1", "Stu1", "Course1", "Prof1"), 0.01);
		assertEquals(-1, db.retrieveGrade("Assign1", "Stu2", "Course1", "Prof1"), 0.01);
		assertEquals(-1, db.retrieveGrade("Test1", "Stu1", "Course1", "Prof1"), 0.01);
		assertEquals(-1, db.retrieveGrade("Test1", "Stu2", "Course1", "Prof1"), 0.01);
	}
		
	@Test
	public void testAddingAssignments() throws SQLException {
		addTwoCourses();
		addTwoAssignments();
		assertEquals(assignmentList, db.getAssignments("Prof1", "Course1"));	
	}
	
	@Test
	public void testAddingGrades() throws SQLException {
		addTwoCourses();
		addTwoStudents();
		addTwoAssignments();
		addGrades();
		ArrayList<GradeInfo> gradeList = createGradeArrayList();
		ArrayList<GradeInfo> databaseGrades = db.getGradeInfo("Prof1", "Course1");
		assertTrue(gradeList.get(0).isEqual(databaseGrades.get(0)));
		assertTrue(gradeList.get(1).isEqual(databaseGrades.get(1)));
		assertTrue(gradeList.get(2).isEqual(databaseGrades.get(2)));
		assertTrue(gradeList.get(3).isEqual(databaseGrades.get(3)));
	}
	
	@Test
	public void testRetrievingGrade() throws SQLException {
		addTwoCourses();
		addTwoStudents();
		addTwoAssignments();
		addGrades();
		assertEquals(70.0, db.retrieveGrade("Test1", "Stu1", "Course1", "Prof1"), 0.01);
	}
	
	@Test
	public void testAddingUsers() throws SQLException {
		db.addUser("Prof1", "Password1", UserTypes.PROFESSOR);
		assertEquals(UserTypes.PROFESSOR, db.getUserType("Prof1", "Password1"));
	}
	
	@Test
	public void testFailedLogin() throws SQLException {
		db.addUser("Prof1", "Password1", UserTypes.PROFESSOR);
		assertEquals(UserTypes.INVALID, db.getUserType("Prof1", "Password2"));
	}
	
	public void addGrades() throws SQLException {
		db.addGrade("Assign1", "Stu1", 90.0, "Prof1", "Course1");
		db.addGrade("Assign1", "Stu2", 80.0, "Prof1", "Course1");
		db.addGrade("Test1", "Stu1", 70.0, "Prof1", "Course1");
		db.addGrade("Test1", "Stu2", 60.0, "Prof1", "Course1");
	}
	
	public void addTwoCourses() throws SQLException {
		db.addCourse("Course1", "Prof1");
		db.addCourse("Course2", "Prof1");
	}
	
	public void addTwoStudents() throws SQLException {
		db.addStudent("Prof1", "Stu1", "Course1");
		db.addStudent("Prof1", "Stu2", "Course1");
	}
	
	public void addTwoAssignments() throws SQLException {
		db.addAssignment("Prof1", "Course1", "Assign1");
		db.addAssignment("Prof1", "Course1", "Test1");
	}
	
	public ArrayList<GradeInfo> createGradeArrayList() {
		ArrayList<GradeInfo> gradeList = new ArrayList<GradeInfo>();
		gradeList.add(new GradeInfo("Stu1", "Assign1", 90.0));
		gradeList.add(new GradeInfo("Stu2", "Assign1", 80.0));
		gradeList.add(new GradeInfo("Stu1", "Test1", 70.0));
		gradeList.add(new GradeInfo("Stu2", "Test1", 60.0));
		return gradeList;
	}

}
