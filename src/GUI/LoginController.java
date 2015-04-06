package GUI;

import java.io.IOException;
import java.sql.SQLException;

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
	Model model;
	
	@FXML
	private void initialize() throws ClassNotFoundException, SQLException{
//		model = new Model(null, null, null);
	}
	
	@FXML
	private void login() throws SQLException, IOException{
		errorWindow();
//		showNewStage("GUI.fxml");
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
	
	private void showNewStage(String FXMLFile) throws IOException {
		Parent home_page_parent = FXMLLoader.load(getClass().getResource(FXMLFile));
		Scene home_page_scene = new Scene(home_page_parent);
		Stage app_stage = (Stage) login.getScene().getWindow();
		app_stage.setScene(home_page_scene);
		app_stage.show();
	}
	 
	private void startProfView() throws IOException{
		showNewStage("ProfGUI.fxml");
		//TODO
	}
	
	private void startStudentView() throws IOException{
		showNewStage("StudentGUI.fxml");
		//TODO
	}
	
	private void sendError(){
		errorWindow();
		//TODO
	}
	
	private Enum checkUserType() throws SQLException{
		//TODO:
		ID = idField.getText();
		password = passwordField.getText();
		type = model.getDatabase().getUserType(ID, password);
		return type;
	}
	
	@FXML
	public void errorWindow(){
		Stage newStage = new Stage();
		VBox root = new VBox();
		Label nameField = new Label("Error: Invalid username/password.");
		HBox selection = new HBox();
		selection.setSpacing(50);
		Button okButton = new Button("OK");
//		Button closeButton = new Button("Cancel");
		okButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
    		public void handle(ActionEvent close){
    			newStage.close();
			}
    		
    	});
//		closeButton.setOnAction(new EventHandler<ActionEvent>(){
//    		@Override
//    		public void handle(ActionEvent close){
//    			newStage.close();
//			}
//    		
//    	});
		selection.getChildren().addAll(okButton);//, closeButton);
		root.getChildren().addAll(nameField, selection);

		Scene stageScene = new Scene(root);
		VBox.setVgrow(root, Priority.ALWAYS);
		newStage.setScene(stageScene);
		newStage.show();
		newStage.requestFocus();
		
	}

}
