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
	
	ArrayList< CourseInfo > courseList;
	
	ObservableList<String> classGrades;
	
	private String userID;
	
	@FXML
	public void initialize() throws ClassNotFoundException, SQLException{
		getStudentInfo();
		for ( CourseInfo courseInfo : courseList) {
			
			String grade = getGrade(courseInfo.getProfessor(), courseInfo.getCourse());
			classGrades.add(className + "\t" + grade);
		}
	}

	public void setUser(String name) {
		this.userID = name;
	}
	
	public void setNetworker(Networker networker){
		this.networker = networker;
	}
	
	public void getStudentInfo() {
		//TODO
		database.getStudentInfo(userID);
	}
	
	public String getGrade(String professor, String course) {
		//TODO
		ArrayList<Double> totGrades = database.getTotalGrades(professor, course);
		ArrayList<Double> stuGrades = database.getStudentGrades(userID, professor, course);
		Double total;
		for ( Double grade: totGrades){
			total += grade;
		}
		Double stuTotal;
		for ( Double grade: stuGrades){
			if ( grade >= 0){
				stuTotal += grade;
			}
		}
		Double avgGrade = stuTotal / total;
		return avgGrade.toString();
	}
	
	
}
