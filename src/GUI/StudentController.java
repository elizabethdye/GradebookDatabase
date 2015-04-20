package GUI;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.CourseInfo;
import Model.DatabaseCommand;
import Model.ServerRequest;
import Model.ServerRequestResult;
import Networking.Networker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StudentController {
	
	@FXML
	ListView<String> gradeReport;
	
	@FXML
	Button logout;
	
	Networker networker; 
	
	ArrayList< String > courseList;
	
	ObservableList< String > classGrades = FXCollections.observableArrayList();
	
	private String userID;
	
	@FXML
	private void initialize() {
		gradeReport.setItems(classGrades);
	}

	@FXML
	public void logout() throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginUI.fxml"));
		Parent home_page_parent = (Parent)loader.load();
		Scene home_page_scene = new Scene(home_page_parent);
		Stage app_stage = (Stage) logout.getScene().getWindow();
		app_stage.setScene(home_page_scene);
		app_stage.show();
	}
	
	public void setUser(String name) {
		this.userID = name;
		getStudentInfo();
		for ( String course : courseList) {
			classGrades.add(course);
		}
	}
	
	public void setNetworker(Networker networker){
		this.networker = networker;
	}
	
	public void getStudentInfo() {
		DatabaseCommand cmd = DatabaseCommand.GET_STUDENT_INFO;
		String[] args = {userID};
		ServerRequest rq = new ServerRequest(cmd, args);
		ServerRequestResult result = networker.sendServerRequest(rq);
		courseList = (ArrayList<String>) result.getResult();
	}
	
	public String getGrade(String professor, String course) {
		DatabaseCommand cmd = DatabaseCommand.GET_TOTAL_GRADES;
		String[] args = {professor, course};
		ServerRequest rq = new ServerRequest(cmd, args);
		ServerRequestResult result = networker.sendServerRequest(rq);
		ArrayList<Double> totGrades = (ArrayList<Double>)result.getResult();
		
		DatabaseCommand cmdStu = DatabaseCommand.GET_STUDENT_GRADES;
		String[] argsStu = {userID, professor, course};
		ServerRequest rqStu = new ServerRequest(cmdStu, argsStu);
		ServerRequestResult resultStu = networker.sendServerRequest(rq);
		ArrayList<Double> stuGrades = (ArrayList<Double>)result.getResult();
		
		Double total = 0.0;
		for ( Double grade: totGrades){
			total += grade;
		}
		Double stuTotal = 0.0;
		for ( Double grade: stuGrades){
			if ( grade >= 0){
				stuTotal += grade;
			}
		}
		Double avgGrade = stuTotal / total;
		return avgGrade.toString();
	}
	
}
