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
	private void initialize() throws ClassNotFoundException, SQLException{
		model = new Model(null, null, null);
	}
	
	@FXML
	private void login() throws SQLException{
		Enum user = checkUserType();
		if (user == UserTypes.PROFESSOR){
			startProfView();
		}
		else if (user == UserTypes.STUDENT){
			startStudentView();
		}
		else{
			sendError();
		}
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
