package GUI;

import java.sql.SQLException;

import Model.LoginModel;
import Model.Model;
import Model.UserTypes;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddUserController {

	@FXML
	Button	add;
	
	@FXML
	TextField IDField;
	String ID;
	
	@FXML
	TextField passwordField;
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
