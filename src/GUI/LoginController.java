package GUI;

import java.sql.SQLException;

import Model.Model;
import Model.UserTypes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
	private void initialize(){
		model = new Model();
	}
	
	@FXML
	private void login(){
		String user = checkUserType();
		if ( user == UserTypes.PROFESSOR. ) {
	}
	
	private UserTypes checkUserType(){
		//TODO:
		ID = idField.getText();
		password = passwordField.getText();
		type = model.getDatabase().getUserType(ID, password);
		return type;
	}
}
