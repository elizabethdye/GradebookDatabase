package GUI;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.DatabaseCommand;
import Model.Model;
import Model.ServerRequest;
import Model.ServerRequestResult;
import Networking.Networker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProfessorController{

	@FXML
	ListView<VBox> students;
	@FXML
	public VBox gradeBox;
	@FXML
	public HBox assignmentNames;
	@FXML
	AnchorPane constraints;
	@FXML
	public ScrollPane scrollpane;
	@FXML
	Button studentAdd, studentRem, gradesAdd, gradesRem;
	@FXML
	public TabPane tabPane;
	@FXML
	public Tab class1, newClass;
	@FXML
	MenuItem logout;
	
	public String userID;
	private String userType = "Professor";
	
	private Model model;
	
	Networker networker;
	
	
	@FXML 
	private void initialize() throws ClassNotFoundException, SQLException, IOException{
		Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
		setEditable(currentTab);
		System.out.println(currentTab.getText());
		if(!currentTab.getGraphic().equals("Course Name")){
			assignmentNames.setSpacing(10);
			scrollpane.setFitToWidth(true);
			scrollpane.setFitToHeight(true);
			scrollpane.setContent(constraints);
			scrollpane.prefViewportHeightProperty().set(constraints.getHeight());
			scrollpane.prefViewportWidthProperty().set(constraints.getWidth());
			this.networker = new Networker();
			this.model = new Model(this);
			students.setItems(model.getStudentNames());
			students.setFixedCellSize(30);
			System.out.println("Initialized");
			model.setUser(this.userID);
			System.out.println("user is " + this.userID);
			System.out.println("networker: " + this.networker == null);
		}
		setEditable(currentTab);
	}
	
	private void setEditable(Tab tab){
		tab.setText("");
		Label label = new Label();
		label.setText("Course Name");
		tab.setGraphic(label);
		label.setOnMouseClicked(new EventHandler<MouseEvent>() {  
			  @Override  
			  public void handle(MouseEvent event) {
				  if (event.getClickCount()==2){
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
							  label.setText(courseName.getText());
							  newStage.close();
						  }
					  });
					okButton.setOnAction(new EventHandler<ActionEvent>(){
			    		@Override
			    		public void handle(ActionEvent close){
			    			label.setText(courseName.getText());
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

					Scene stageScene = new Scene(root);
					VBox.setVgrow(root, Priority.ALWAYS);
					newStage.setScene(stageScene);
					newStage.show();
					newStage.requestFocus();
				  }
			  }
		});
	}
	
	public void setUser(String name) throws SQLException, IOException{
		this.userID = name;
		model.setUser(name);
		System.out.println("UserID Has been set: " + name);
		System.out.println("UserID Has been set: " + name);
		System.out.println("UserID Has been set: " + name);
		System.out.println("UserID Has been set: " + name);
		System.out.println("UserID Has been set: " + name);
		System.out.println("UserID Has been set: " + name);
		model.populateGradebook();
	}
	
	@FXML
	private void newTab(){
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
    			Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
    			try {
    				FXMLLoader loader = new FXMLLoader();
        			loader.setLocation(Main.class.getResource("Tab.fxml"));
					TabPane root = (TabPane) loader.load();
					Node content = root.getTabs().get(0).getContent();
					currentTab.setContent(content);
					Tab newTab = new Tab();
					currentTab.setText(courseName.getText());
					System.out.println("Attempting to add course to database");
					model.addCourseToDatabase(courseName.getText(), userID);
					newTab.setText("new");
					tabPane.getTabs().add(newTab);
					newTab.setOnSelectionChanged(new EventHandler<Event>() {
					    @Override
					    public void handle(Event t) {
					       newTab();
					    }
					});
					setEditable(newTab);
					newStage.close();
					currentTab.setOnSelectionChanged(new EventHandler<Event>() {
					    @Override
					    public void handle(Event t) {
					    }
					});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		okButton.setOnAction(new EventHandler<ActionEvent>(){
    		@Override
    		public void handle(ActionEvent close){
    			Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
    			try {
    				FXMLLoader loader = new FXMLLoader();
        			loader.setLocation(Main.class.getResource("Tab.fxml"));
					TabPane root = (TabPane) loader.load();
					Node content = root.getTabs().get(0).getContent();
					currentTab.setContent(content);
					Tab newTab = new Tab();
					currentTab.setText(courseName.getText());
					model.addCourseToDatabase(courseName.getText(), userID);
					newTab.setText("new");
					tabPane.getTabs().add(newTab);
					newTab.setOnSelectionChanged(new EventHandler<Event>() {
					    @Override
					    public void handle(Event t) {
					       newTab();
					    }
					});
					setEditable(newTab);
					newStage.close();
					currentTab.setOnSelectionChanged(new EventHandler<Event>() {
					    @Override
					    public void handle(Event t) {
					    }
					});
				} catch (IOException e) {
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
		root.getChildren().addAll(nameField, courseName, selection);

		Scene stageScene = new Scene(root);
		VBox.setVgrow(root, Priority.ALWAYS);
		newStage.setScene(stageScene);
		newStage.show();
		newStage.requestFocus();		
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
    				Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
    				model.getStudentNames().remove(students.getSelectionModel().getSelectedItem());
    				model.removeStudentFromDatabase(userID, selected, currentTab.getText());
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
