package Model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Database.Database;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Model {
	Database database;
	private ObservableList<String> studentNames;
	private ObservableList<String> gradeBook;
	List<List<String>> gradeList = new ArrayList<List<String>>();
	private VBox gradeBox;
	private HBox assignmentName;
	private ScrollPane scrollpane;
	private int numGrades = 0;
	private int numStudents = 0;
	
	public Model(VBox gradeBox, HBox assign, ScrollPane scrollpane) throws ClassNotFoundException, SQLException{
		database = new Database();
		studentNames = FXCollections.observableArrayList();
		gradeBook = FXCollections.observableArrayList();
		this.gradeBox = gradeBox;
		this.assignmentName = assign;
		this.assignmentName.setSpacing(30);
		assignmentName.setMaxSize(70, 20);
		assignmentName.setMinSize(70, 20);
		this.scrollpane = scrollpane;
		DoubleProperty wProperty = new SimpleDoubleProperty();
		wProperty.bind(gradeBox.widthProperty());
		studentNames.add(" ");
		initiateGradebook("   Test 1     ");
		System.out.println("Model set Up");
	}
	
	private void initiateGradebook(String value){
		Label name = new Label(value);
		name.setMaxSize(70, 20);
		name.setMinSize(50, 20);
		assignmentName.getChildren().add(name);
	}
	
	public ObservableList<String>	getStudentNames(){
		return studentNames;
		}
	public ObservableList<String>	getGradeBook(){
		return gradeBook;
		}
	
	public void addStudent(String value){
		if (value.length() > 0){
			studentNames.add(value);
			System.out.println("added student");
			this.numStudents++;
			addLineToGrades();
		}
	}
	
	public void addGrade(String value){
		if (value.length() > 0){
		System.out.println("Running addGrade");
		Label name = new Label(value + "           ");
		name.setMaxSize(70, 20);
		name.setMinSize(50, 20);
		assignmentName.setSpacing(10);
		assignmentName.getChildren().add(name);
		this.numGrades++;
		for(int i = 0; i < this.numStudents; i++){
			
		}
		}
	}
	
	private void addLineToGrades(){
		ListView<HBox> assignment = new ListView<HBox>();
		ObservableList<HBox> assignmentGrades = FXCollections.observableArrayList();
		TextField textfield = new TextField();
		textfield.setMaxSize(45, 20);
		textfield.setMinSize(45, 20);
		HBox box = new HBox();
		box.setSpacing(30);
		box.getChildren().add(textfield);
		assignmentGrades.add(box);
		assignment.setOrientation(Orientation.HORIZONTAL);
		System.out.println(assignment.getOrientation());
		assignment.setItems(assignmentGrades);
		assignment.setMinWidth(500);
		assignment.setMaxWidth(500);
		assignment.setMaxHeight(30);
		assignment.setMinHeight(30);
		System.out.println("assignment List is: " + gradeList);
		this.gradeBox.getChildren().add(assignment);
		scrollpane.setHvalue(scrollpane.getHmax());
		for(int i = 0; i < this.numGrades; i++){
			HBox newBox = new HBox();
			newBox.setSpacing(10);
			TextField newField = new TextField();
			newBox.getChildren().add(newField);
			newField.setMaxSize(45, 20);
			newField.setMinSize(45, 20);
			assignmentGrades.add(newBox);
			//assignment.setFixedCellSize(30);
			assignment.scrollTo(0);
		}
	}
	/*
	private void populateGradebook(){
		System.out.println("Populating gradbook");
		for(int i = 0; i < getNumStudents(); i++){
			HBox studentGrades = new HBox();
			studentGrades.setSpacing(20);
			gradeBook.add(studentGrades);
			for(int j = 0; j < this.numGrades; i++){
				HBox assignGrade = new HBox();
				studentGrades.getChildren().add(assignGrade);
			}
		}
	}
	*/
	
	public int getNumStudents(){
		return studentNames.size();
	}
	public int getNumGrades(){
		return gradeBook.size();
	}
	

	public Database getDatabase() {
		return database;
	}

}
