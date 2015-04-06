package GUI;

import java.io.IOException;
import java.sql.SQLException;

import Model.Model;
import Model.UserTypes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	
	@FXML
	TextField idField;
	
	@FXML
	TextField passwordField;
	
	@FXML
	Button login;
	
	String ID;
	String password;
	Enum type;
	Model model;
	
	@FXML
	private void initialize() throws ClassNotFoundException, SQLException{
		model = new Model(null, null, null);
	}
	
	@FXML
	private void login() throws SQLException, IOException{
		showNewStage();
//		Enum user = checkUserType();
//		if (user == UserTypes.PROFESSOR){
//			startProfView();
//		}
//		else if (user == UserTypes.STUDENT){
//			startStudentView();
//		}
//		else{
//			sendError();
//		}
	}
	
	private void showNewStage() throws IOException {
		Parent home_page_parent = FXMLLoader.load(getClass().getResource("GUI.fxml"));
		Scene home_page_scene = new Scene(home_page_parent);
		Stage app_stage = (Stage) login.getScene().getWindow();
		app_stage.setScene(home_page_scene);
		app_stage.show();
	}
	 
	private void startProfView(){
		//TODO
	}
	
	private void startStudentView(){
		//TODO
	}
	
	private void sendError(){
		//TODO
	}
	
	private Enum checkUserType() throws SQLException{
		//TODO:
		ID = idField.getText();
		password = passwordField.getText();
		type = model.getDatabase().getUserType(ID, password);
		return type;
	}
}
