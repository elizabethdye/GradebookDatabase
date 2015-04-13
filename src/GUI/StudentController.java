package GUI;

import java.sql.SQLException;
import java.util.ArrayList;

import Networking.Networker;
import javafx.collections.ObservableList;
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
	
	Networker networker; 
	
	ArrayList<String, String> classes;
	
	ObservableList<String> classGrades;
	
	private String userID;
	
	@FXML
	public void initialize() throws ClassNotFoundException, SQLException{
		getClasses();
		for ( String className : classes) {
			String grade = getGrade(className);
			classGrades.add(className + "\t" + grade);
		}
	}

	public void setUser(String name) {
		this.userID = name;
	}
	
	public void setNetworker(Networker networker){
		this.networker = networker;
	}
	
	public void getClasses() {
		//TODO
		database.getClasses(userID);
	}
	
	public String getGrade(String className) {
		//TODO
		ArrayList<Double> totGrades = database.getTotalGrades(className);
		ArrayList<Double> posGrades = database.getStudentGrades(userID, className);
	}
	
	
}
