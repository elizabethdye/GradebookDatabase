package GUI;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StudentController {
	@FXML
	VBox constraints;
	@FXML
	TabPane tabs;
	@FXML
	Tab gradeReportTab;
	@FXML
	Tab detailedReportTab;
	@FXML
	ListView<VBox> gradeReport;
	@FXML
	ListView<VBox> courses;
	@FXML
	ListView<VBox> grades;
	
	@FXML
	public void initialize() throws ClassNotFoundException, SQLException{
		
	}
	
	
	
}
