package GUI;

import java.util.ArrayList;

import Model.DatabaseCommand;
import Model.ProfessorModelNew;
import Model.ServerRequest;
import Model.ServerRequestResult;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProfessorControllerNew {
	ProfessorModelNew model = new ProfessorModelNew();
	String professorID;
	String currentCourse;
	
	@FXML
	ComboBox<String> courseListComboBox;
	@FXML
	ListView<String> studentListView;
	
	@FXML
	void handleOpenCourse() {
		String selectedCourse = courseListComboBox.getSelectionModel().getSelectedItem();
		currentCourse = selectedCourse;
		populateGradebook();

	}
	
	void populateGradebook(){
		populateStudents();
		populateGrades();
	}
	
	void populateStudents(){
		ObservableList<String> studentObsList = model.getStudents(professorID, currentCourse);
		studentListView.setItems(studentObsList);
	}
	
	void populateGrades(){
		//TODO
	}
	
	@FXML
	void handleAddCourse() {
		Stage newStage = new Stage();
		VBox root = new VBox();
		Label nameField = new Label("Enter Course Name: ");
		TextField courseName = new TextField();
		HBox selection = new HBox();
		selection.setSpacing(50);
		Button okButton = new Button("OK");
		Button closeButton = new Button("Cancel");
		courseName.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent add){
				newStage.close();
				model.addCourse(courseName.getText(), professorID);
				courseListComboBox.setItems(model.getCourseList());
			}
		});
		okButton.setOnAction(new EventHandler<ActionEvent>(){
    		@Override
    		public void handle(ActionEvent close){
    			newStage.close();
    			model.addCourse(courseName.getText(), professorID);
				courseListComboBox.setItems(model.getCourseList());
			}
    		
    	});
		closeButton.setOnAction(new EventHandler<ActionEvent>(){
    		@Override
    		public void handle(ActionEvent close){
    			newStage.close();
			}
    		
    	});
		selection.getChildren().addAll(okButton, closeButton);
		root.getChildren().addAll(nameField, courseName, selection);

		Scene stageScene = new Scene(root);
		VBox.setVgrow(root, Priority.ALWAYS);
		newStage.setScene(stageScene);
		newStage.show();
		newStage.requestFocus();
		
	}
	
	@FXML
	void handleAddStudent() {
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
				model.addStudent(professorID, studentName.getText(), currentCourse);
				populateStudents();
			}
		});
		okButton.setOnAction(new EventHandler<ActionEvent>(){
    		@Override
    		public void handle(ActionEvent close){
    			newStage.close();
    			model.addStudent(professorID, studentName.getText(), currentCourse);
				populateStudents();
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
	
	public void setProfessorID(String professorID){
		this.professorID = professorID;
		model.callCourseListFromDB(professorID);
		courseList.setItems(model.getCourseList());
	}
}
