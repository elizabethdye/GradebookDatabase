package GUI;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.CourseInfo;
import Model.DatabaseCommand;
import Model.ServerRequest;
import Model.ServerRequestResult;
import Networking.Networker;
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
	ListView<VBox> gradeReport;
	
	@FXML
	Button logout;
	
	Networker networker; 
	
	ArrayList< CourseInfo > courseList;
	
	ObservableList< String > classGrades;
	
	private String userID;
	
	@FXML
	public void initialize() throws ClassNotFoundException, SQLException{
		networker = new Networker();
		getStudentInfo();
		for ( CourseInfo courseInfo : courseList) {
			String professor = courseInfo.getProfessor();
			String course = courseInfo.getCourse();
			String grade = getGrade(professor, course);
			classGrades.add(courseInfo.getCourse() + "     -     " + grade);
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
		DatabaseCommand cmd = DatabaseCommand.GET_STUDENT_INFO;
		String[] args = {userID};
		ServerRequest rq = new ServerRequest(cmd, args);
		ServerRequestResult result = networker.sendServerRequest(rq);
		courseList = (ArrayList<CourseInfo>)result.getResult();
	}
	
	public String getGrade(String professor, String course) {
		//TODO
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
