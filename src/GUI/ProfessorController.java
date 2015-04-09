package GUI;

import java.sql.SQLException;

import Model.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProfessorController {

	@FXML
	ListView<VBox> students;
	@FXML
	VBox gradeBox;
	@FXML
	HBox assignmentNames;
	@FXML
	AnchorPane constraints;
	@FXML
	ScrollPane scrollpane;
	@FXML
	Button studentAdd, studentRem, gradesAdd, gradesRem;
	@FXML
	TabPane tabPane;
	@FXML
	Tab class1, newClass;
	@FXML
	MenuItem saveGradebook;
	
	private String userID;
	private String userType = "Professor";
	
	private Model model;
	
	@FXML 
	private void initialize() throws ClassNotFoundException, SQLException{
		assignmentNames.setSpacing(10);
		scrollpane.setFitToWidth(true);
		scrollpane.setFitToHeight(true);
		scrollpane.setContent(constraints);
		scrollpane.prefViewportHeightProperty().set(constraints.getHeight());
		scrollpane.prefViewportWidthProperty().set(constraints.getWidth());
		this.model = new Model(gradeBox, assignmentNames, scrollpane, class1);
		students.setItems(model.getStudentNames());
		students.setFixedCellSize(30);
		System.out.println("Initialized");
		model.setUser(this.userID);
		System.out.println("user is " + this.userID);
	}
	
	public void setUser(String name){
		this.userID = name;
		model.setUser(name);
		System.out.println("UserID is: " + name);
	}
	/*
	@FXML
	public void newTab(){
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		newClass.setContent(selectionModel);
	}
	*/
	@FXML
	public void addStudent(){
		Stage newStage = new Stage();
		VBox root = new VBox();
		Label nameField = new Label("Enter Student Name: ");
		TextField studentName = new TextField();
		HBox selection = new HBox();
		selection.setSpacing(50);
		Button okButton = new Button("OK");
		Button closeButton = new Button("Cancel");
		studentName.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent add){
				newStage.close();
				try {
					model.addStudent(studentName.getText());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		okButton.setOnAction(new EventHandler<ActionEvent>(){
    		@Override
    		public void handle(ActionEvent close){
    			newStage.close();
    			try {
					model.addStudent(studentName.getText());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    		
    	});
		closeButton.setOnAction(new EventHandler<ActionEvent>(){
    		@Override
    		public void handle(ActionEvent close){
    			newStage.close();
			}
    		
    	});
		selection.getChildren().addAll(okButton, closeButton);
		root.getChildren().addAll(nameField, studentName, selection);

		Scene stageScene = new Scene(root);
		VBox.setVgrow(root, Priority.ALWAYS);
		newStage.setScene(stageScene);
		newStage.show();
		newStage.requestFocus();
		
	}
	
	@FXML
	public void addGrade(){
		Stage newStage = new Stage();
		VBox root = new VBox();
		Label nameField = new Label("Enter Assignment Name: ");
		TextField gradeName = new TextField();
		HBox selection = new HBox();
		selection.setSpacing(10);
		Button okButton = new Button("OK");
		Button closeButton = new Button("Cancel");
		gradeName.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent add){
				newStage.close();
				System.out.println("Model addGrade");
				try {
					model.addAssignment(gradeName.getText());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		okButton.setOnAction(new EventHandler<ActionEvent>(){
    		@Override
    		public void handle(ActionEvent close){
    			newStage.close();
    			System.out.println("Model addGrade");
				try {
					model.addAssignment(gradeName.getText());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    		
    	});
		closeButton.setOnAction(new EventHandler<ActionEvent>(){
    		@Override
    		public void handle(ActionEvent close){
    			newStage.close();
			}
    		
    	});
		selection.getChildren().addAll(okButton, closeButton);
		root.getChildren().addAll(nameField, gradeName, selection);

		Scene stageScene = new Scene(root);
		VBox.setVgrow(root, Priority.ALWAYS);
		newStage.setScene(stageScene);
		newStage.show();
		newStage.requestFocus();
		
	}
	
	public void throwError(String message){
		Stage newStage = new Stage();
		VBox root = new VBox();
		Label nameField = new Label("\n     " + message + "     \n ");
		HBox buttons = new HBox();
		buttons.setSpacing(100);
		buttons.setPadding(new Insets(10, 10, 10, 10));
		Button okButton = new Button("OK");
		Button closeButton = new Button("Cancel");
		buttons.getChildren().addAll(okButton, closeButton);
		okButton.translateXProperty().set(30);
		root.getChildren().addAll(nameField, buttons);
		Scene stageScene = new Scene(root);
		VBox.setVgrow(root, Priority.ALWAYS);
		newStage.setScene(stageScene);
		newStage.show();
		newStage.requestFocus();
		newStage.setTitle("Error");
		okButton.setOnAction(new EventHandler<ActionEvent>(){
    		@Override
    		public void handle(ActionEvent close){
    			newStage.close();
			}
    		
    	});
		closeButton.setOnAction(new EventHandler<ActionEvent>(){
    		@Override
    		public void handle(ActionEvent close){
    			newStage.close();
			}
    		
    	});
	}
	@FXML
	public void ConfirmDeleteWindow(){
		Stage newStage = new Stage();
		VBox root = new VBox();
		Label nameField = new Label("\n     Are you sure you want to delete?     \n ");
		HBox buttons = new HBox();
		buttons.setSpacing(100);
		buttons.setPadding(new Insets(10, 10, 10, 10));
		Button okButton = new Button("OK");
		Button closeButton = new Button("Cancel");
		buttons.getChildren().addAll(okButton, closeButton);
		okButton.translateXProperty().set(30);
		root.getChildren().addAll(nameField, buttons);
		Scene stageScene = new Scene(root);
		VBox.setVgrow(root, Priority.ALWAYS);
		newStage.setScene(stageScene);
		newStage.show();
		newStage.requestFocus();
		newStage.setTitle("Confirm");
		okButton.setOnAction(new EventHandler<ActionEvent>(){
    		@Override
    		public void handle(ActionEvent close){
    			String noDelete = model.getStudentNames().get(0).getChildren().get(0).toString();
    			String selected = students.getSelectionModel().getSelectedItem().getChildren().get(0).toString();
    			int index = students.getSelectionModel().getSelectedIndex();
    			newStage.close();
    			System.out.println("Deleting student");
    			if(!selected.equals(noDelete)){
    				model.getStudentNames().remove(students.getSelectionModel().getSelectedItem());
    				System.out.println(selected);
    				System.out.println(index);
    				gradeBox.getChildren().remove(index-1);
    			}
    			else{
    				throwError("Please make a valid selection");
    			}
			}
    		
    	});
		closeButton.setOnAction(new EventHandler<ActionEvent>(){
    		@Override
    		public void handle(ActionEvent close){
    			newStage.close();
			}
    		
    	});
	}
	
}
