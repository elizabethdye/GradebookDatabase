package GUI;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.DatabaseCommand;
import Model.ProfModel;
import Model.ServerRequest;
import Model.ServerRequestResult;
import Networking.Networker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProfessorController{

	@FXML
	public ListView<VBox> students;
	@FXML
	public VBox gradeBox;
	@FXML
	public HBox assignmentNames;
	@FXML
	public AnchorPane constraints;
	@FXML
	public ScrollPane scrollpane;
	@FXML
	public Button studentAdd, studentRem, gradesAdd, gradesRem;
	@FXML
	public MenuItem logout;
	@FXML
	public ComboBox<String> courseList;
	@FXML
	public Button addCourse;
	
	public String userID;
	public ProfModel model;
	public Networker networker;
	public ObservableList<String> courses = FXCollections.observableArrayList();
	Button studentAdd, studentRem, gradesAdd, gradesRem;
	@FXML
	MenuItem logout;
	@FXML
	MenuItem newCourse;
	@FXML
	public ComboBox courseList;
	@FXML
	public Button open;
	public Button addCourse;
	
	public String userID;
	private String userType = "Professor";
	private String course;
	
	private ProfModel model;
	
	Networker networker;
	
	
	@FXML 
	private void initialize() throws ClassNotFoundException, SQLException, IOException{
		assignmentNames.setSpacing(10);
		scrollpane.setFitToWidth(true);
		scrollpane.setFitToHeight(true);
		scrollpane.setContent(constraints);
		scrollpane.prefViewportHeightProperty().set(constraints.getHeight());
		scrollpane.prefViewportWidthProperty().set(constraints.getWidth());
		this.networker = new Networker();
		this.model = new ProfModel(this);
		System.out.println("user is " + this.userID);
		System.out.println("networker: " + this.networker == null);
		
		courseList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                try {
					switchValues();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
	}

	private void switchValues() throws SQLException, IOException {
		if (courseList.getSelectionModel().getSelectedIndex() != -1) {
			model.populateGradebook();
			students.setItems(model.getStudentNames());
			students.setFixedCellSize(30);
		}
		assignmentNames.setSpacing(10);
		scrollpane.setFitToWidth(true);
		scrollpane.setFitToHeight(true);
		scrollpane.setContent(constraints);
		scrollpane.prefViewportHeightProperty().set(constraints.getHeight());
		scrollpane.prefViewportWidthProperty().set(constraints.getWidth());
		this.networker = new Networker();
		this.model = new ProfModel(this);
		DatabaseCommand cmd = DatabaseCommand.GET_COURSES;
		String[] args = {userID};
		ServerRequest request = new ServerRequest (cmd, args);
		ServerRequestResult ret = networker.sendServerRequest(request);
		ArrayList<String> courses = (ArrayList<String>)ret.getResult();
		courseList.setItems(FXCollections.observableArrayList(courses));
		students.setItems(model.getStudentNames());
		students.setFixedCellSize(30);
		System.out.println("Initialized");
		model.setUser(this.userID);
		System.out.println("user is " + this.userID);
		System.out.println("networker: " + this.networker == null);

	}
	@FXML
	private void changeCourse(){
		course = (String) courseList.getSelectionModel().getSelectedItem();
		model.setCourse(course);
		//TODO open the correct course
	}
	@FXML
	private void addCourse(){
		changeCourse();
		model.dbAddCourse();
	}
	
	
	public void setUser(String name) throws SQLException, IOException{
		this.userID = name;
		model.setUser(name);
		DatabaseCommand cmd = DatabaseCommand.GET_COURSES;
		String[] args = {userID};
		ServerRequest request = new ServerRequest (cmd, args);
		ServerRequestResult result = networker.sendServerRequest(request);
		ArrayList<String> course = (ArrayList<String>) result.getResult();
		for (String c: course) {
			courses.add(c);
		}
		courseList.setItems(courses);
		
		
		System.out.println("UserID Has been set: " + name);
		System.out.println("UserID Has been set: " + name);
		System.out.println("UserID Has been set: " + name);
		System.out.println("UserID Has been set: " + name);
		System.out.println("UserID Has been set: " + name);
		System.out.println("UserID Has been set: " + name);
	}
	
	@FXML
	private void addCourse() {
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
			public void handle(ActionEvent close){
				model.addCourseToDatabase(courseName.getText(), userID);
				courses.add(courseName.getText());
				newStage.close();
			}
		});
		okButton.setOnAction(new EventHandler<ActionEvent>(){
    		@Override
    		public void handle(ActionEvent close){
				model.addCourseToDatabase(courseName.getText(), userID);
				courses.add(courseName.getText());
				newStage.close();
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
	
	@FXML
	private void newTab(){
		
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
	

	@FXML
	public void logout() throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginUI.fxml"));
		Parent home_page_parent = (Parent)loader.load();
		Scene home_page_scene = new Scene(home_page_parent);
		Stage app_stage = (Stage) constraints.getScene().getWindow();
		app_stage.setScene(home_page_scene);
		app_stage.show();
		System.out.println("Sent networker to LoginController...");
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
	public void removeStudent(){
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
    				model.removeStudentFromDatabase(userID, selected, courseList.getSelectionModel().getSelectedItem());
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
	
	public void setNetworker(Networker net){
		this.networker = net;
		this.model.setNetworker(net);
	}
	
}
