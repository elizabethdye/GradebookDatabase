package GUI;

import java.sql.SQLException;

import Model.LoginModel;
import Model.UserTypes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AddUserController {

	@FXML
	Button	add;
	
	@FXML
	TextField IDField;
	String ID;
	
	@FXML
	PasswordField passwordField;
	String password;
	
	LoginModel model;
	
	@FXML
	public void initialize() throws ClassNotFoundException, SQLException{
		model = new LoginModel();
	}
	
	@FXML
	private void addUser() throws SQLException{
		ID = IDField.getText();
		password = passwordField.getText();
		model.addUser(ID, password, UserTypes.PROFESSOR);
	}
}
