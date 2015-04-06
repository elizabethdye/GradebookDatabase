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
		db.addStudent("carl", textfield.getText(), "math");
		System.out.println(db.getCourses("carl"));
	}
}
