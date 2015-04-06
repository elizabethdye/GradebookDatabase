package Database;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class tempController {

	@FXML
	Button addButton;
	@FXML
	TextField textfield;
	
	private Database db;
	
	@FXML
	public void initialize() throws SQLException, ClassNotFoundException {
		db = new Database();
	}
	
	@FXML
	public void add() throws SQLException {
		db.addCourse("math", "carl");
		db.addStudent("carl", textfield.getText(), "math");
		db.addStudent("carl", "hey", "math");
		db.addAssignment("carl", "math", "Assignment 1");
		db.addAssignment("carl", "math", "Assignment 2");
		db.addGrade("Assignment 1", textfield.getText(), 50.0, "carl", "math");
		System.out.println(db.getAssignments("carl", "math"));
		System.out.println(db.retrieveGrade("Assignment 1", textfield.getText()));
		System.out.println(db.getStudents("carl", "math"));
	}
}