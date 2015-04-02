package GUI;

import Model.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller {

	@FXML
	ListView<String> students;
	@FXML
	ListView<HBox> grades;
	@FXML
	Button studentAdd, studentRem, gradesAdd, gradesRem;
	@FXML
	Tab class1, newClass;
	
	private Model model = new Model();
	
	@FXML
	private void initialize(){
		students.setItems(model.studentNames());
		grades.setItems(model.gradeBook());
	}
	
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
				model.addStudent(studentName.getText());
			}
		});
		okButton.setOnAction(new EventHandler<ActionEvent>(){
    		@Override
    		public void handle(ActionEvent close){
    			newStage.close();
    			model.addStudent(studentName.getText());
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
		selection.setSpacing(50);
		Button okButton = new Button("OK");
		Button closeButton = new Button("Cancel");
		gradeName.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent add){
				newStage.close();
				System.out.println("Model addGrade");
				model.addGrade(gradeName.getText());
			}
		});
		okButton.setOnAction(new EventHandler<ActionEvent>(){
    		@Override
    		public void handle(ActionEvent close){
    			newStage.close();
    			System.out.println("Model addGrade");
				model.addGrade(gradeName.getText());
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
	
}
