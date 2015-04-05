package GUI;

import java.util.ArrayList;
import java.util.List;

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

public class Model2 {
	private ObservableList<String> studentNames;
	private ObservableList<String> gradeBook;
	List<List<String>> gradeList = new ArrayList<List<String>>();
	private VBox gradeBox;
	private HBox assignmentName;
	private ScrollPane scrollpane;
	private int numGrades = 0;
	private int numStudents = 0;
	
	Model2(VBox gradeBox, HBox assign, ScrollPane scrollpane){
		studentNames = FXCollections.observableArrayList();
		gradeBook = FXCollections.observableArrayList();
		this.gradeBox = gradeBox;
		this.assignmentName = assign;
		this.scrollpane = scrollpane;
		DoubleProperty wProperty = new SimpleDoubleProperty();
		wProperty.bind(gradeBox.widthProperty());
		studentNames.add(" ");
		addGrade("Assignment1");
		System.out.println("Model set Up");
	}
	
	public ObservableList<String>	studentNames(){
		return studentNames;
		}
	public ObservableList<String>	gradeBook(){
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
		Label name = new Label(value);
		assignmentName.getChildren().add(name);
		//gradeBox.getChildren().add(name);
		this.numGrades++;
		for(int i = 0; i < this.numStudents; i++){
			
		}
		}
	}
	
	private void addLineToGrades(){
		ListView<TextField> assignment = new ListView<TextField>();
		ObservableList<TextField> assignmentGrades = FXCollections.observableArrayList();
		assignment.setOrientation(Orientation.HORIZONTAL);
		System.out.println(assignment.getOrientation());
		assignment.setItems(assignmentGrades);
		assignment.setMinWidth(500);
		assignment.setMaxWidth(500);
		assignment.setMaxHeight(30);
		assignment.setMinHeight(30);
		//gradeList.add(assignmentGrades);
		System.out.println("assignment List is: " + gradeList);
		this.gradeBox.getChildren().add(assignment);
		scrollpane.setHvalue(scrollpane.getHmax());
		for(int i = 0; i < this.numGrades; i++){
			TextField newField = new TextField();
			newField.setMaxSize(45, 20);
			newField.setMinSize(45, 20);
			assignmentGrades.add(newField);
			assignment.setFixedCellSize(30);
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

}

