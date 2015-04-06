package GUI;

import java.io.IOException;
import java.sql.SQLException;

import Model.LoginModel;
import Model.Model;
import Model.UserTypes;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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
	LoginModel model;
	
	@FXML
	private void initialize() throws ClassNotFoundException, SQLException{
		model = new LoginModel();
		model.addUser("admin", "admin", UserTypes.PROFESSOR);
	}
	
	@FXML
	private void login() throws SQLException, IOException{
//		errorWindow();
//		showNewStage("GUI.fxml");
		Enum user = checkUserType();
		if (user == UserTypes.PROFESSOR){
			if(ID.equals("admin")){
				startAdminView();
			}
			else{
				startProfView();
			}
		}
		else if (user == UserTypes.STUDENT){
			startStudentView();
		}
		else{
			sendError();
		}
	}
	
	private void showNewStage(String FXMLFile) throws IOException {
		Parent home_page_parent = FXMLLoader.load(getClass().getResource(FXMLFile));
		Scene home_page_scene = new Scene(home_page_parent);
		Stage app_stage = (Stage) login.getScene().getWindow();
		app_stage.setScene(home_page_scene);
		app_stage.show();
	}
	
	private void startAdminView() throws IOException{
		showNewStage("AddUserUI.fxml");
	}
	
	private void startProfView() throws IOException{
		showNewStage("GUI.fxml");
//		TODO: once student view is created, rename - showNewStage("ProfGUI.fxml");
	}
	
	private void startStudentView() throws IOException{
		showNewStage("StudentGUI.fxml");
		//TODO
	}
	
	private void sendError(){
		errorWindow();
	}
	
	private Enum checkUserType() throws SQLException{
		ID = idField.getText();
		password = passwordField.getText();
		type = model.getDatabase().getUserType(ID, password);
		return type;
	}
	
	@FXML
	public void errorWindow(){
		Stage newStage = new Stage();
		VBox root = new VBox();
		Label nameField = new Label("\n     Invalid username/password.     \n ");
		root.getChildren().addAll(nameField);
		Scene stageScene = new Scene(root);
		VBox.setVgrow(root, Priority.ALWAYS);
		newStage.setScene(stageScene);
		newStage.show();
		newStage.requestFocus();
		newStage.setTitle("ERROR");
	}

}
