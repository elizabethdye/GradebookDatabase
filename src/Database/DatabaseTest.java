package Database;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

public class DatabaseTest {

	private Database db;
	
	private void createDatabase() throws ClassNotFoundException, SQLException {
		db = new Database("jdbc:sqlite:testdb");
		db.deleteTables();
	}
	
	@Test
	public void testAddingCourse() throws ClassNotFoundException, SQLException {
		createDatabase();
		db.addCourse("Carl", "Math 150");
		db.addCourse("Carl", "Math 160");
		ArrayList<String> classList = new ArrayList<String>();
		classList.add("Math 150");
		classList.add("Math 160");
		assertEquals(classList, db.getCourses("Carl"));
	}

}
