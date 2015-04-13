package GUI;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.CourseInfo;
import Networking.Networker;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StudentController {
	
	@FXML
	ListView<VBox> gradeReport;
	
	@FXML
	Button logout;
	
	Networker networker; 
	
	ArrayList< CourseInfo > courseList;
	
	ObservableList<String> classGrades;
	
	private String userID;
	
	@FXML
	public void initialize() throws ClassNotFoundException, SQLException{
		getStudentInfo();
		for ( CourseInfo courseInfo : courseList) {
			String professor = courseInfo.getProfessor();
			String course = courseInfo.getCourse();
			String grade = getGrade(professor, course);
			classGrades.add(courseInfo.getCourse() + "\t" + grade);
		}
	}

	@FXML
	public void logout() throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginUI.fxml"));
		Parent home_page_parent = (Parent)loader.load();
		Scene home_page_scene = new Scene(home_page_parent);
		Stage app_stage = (Stage) logout.getScene().getWindow();
		app_stage.setScene(home_page_scene);
		app_stage.show();
		System.out.println("Sent networker to LoginController...");
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
