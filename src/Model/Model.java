package Model;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.ArrayList;
import java.util.List;

import Database.Database;
import Networking.ClientRequestThread;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Model {
	Database database;
	ClientRequestThread requestThread;
	String serverHost;
	int serverPort;
	ArrayBlockingQueue<ServerRequestResult> channel;
	private ObservableList<VBox> studentNames;
	private int numGrades = 0;
	private ObservableList<String> gradeBook;
	List<List<HBox>> gradeList = new ArrayList<List<HBox>>();
	private VBox gradeBox;
	private HBox assignmentName;
	private ScrollPane scrollpane;
	private int numStudents = 0;
	private String filename = "jdbc:sqlite:db";
	private String userID;
	private String userType;
	private String courseName;
	
	public Model(VBox gradeBox, HBox assign, ScrollPane scrollpane, Tab courseName) throws ClassNotFoundException, SQLException{
		database = new Database(filename);
		studentNames = FXCollections.observableArrayList();
		gradeBook = FXCollections.observableArrayList();
		this.courseName = courseName.getText();
		this.gradeBox = gradeBox;
		this.assignmentName = assign;
		this.assignmentName.setSpacing(30);
		assignmentName.setMaxSize(70, 20);
		assignmentName.setMinSize(70, 20);
		this.scrollpane = scrollpane;
		DoubleProperty wProperty = new SimpleDoubleProperty();
		wProperty.bind(gradeBox.widthProperty());
		Text text = new Text(" ");
		VBox box = new VBox();
		box.setMinSize(0, 30);
		box.getChildren().add(text);
		studentNames.add(box);
		initiateGradebook("   Test 1     ");
		System.out.println("Model set Up");
		//testLists();
		printDatabase();
		//testingCode();
	}
	private void testLists() throws SQLException{
		for(int i = 0; i < 5; i++){
			addAssignment("TestGrade	" + i);
			System.out.println(this.numGrades);
			//TextField txtfield = (TextField)gradeList.get(i).get(0).getChildren().get(0);
			//System.out.println(txtfield.getText());
		}
		for(int i = 0; i < 5; i++){
			//TextField txtfield = (TextField)gradeList.get(i).get(0).getChildren().get(0);
			//System.out.println(txtfield.getText());
		}
		for(int i = 0; i<  5; i++){
			addStudent("Test Student " + i);
		}
		Text txt = new Text();
		System.out.println("StudentNames: ");
		for(int i = 1; i < 6; i++){
			txt = (Text)studentNames.get(i).getChildren().get(0);
			System.out.println(txt.getText().toString());
		}
		for(int i = 0; i < this.numGrades; i++){
			TextField txtfield = (TextField)gradeList.get(i).get(0).getChildren().get(0);
			txt.setText(txtfield.getText());
			System.out.println("Grade: " + txt.getText().toString());
		}
	}
	
	private void printDatabase() throws SQLException{
		System.out.println("UserID: " + this.userID);
		System.out.println("Grade info: " + database.getGradeInfo(this.userID, courseName));
		System.out.println("Get Courses: " + database.getCourses(this.userID));
		System.out.println(" Get Assignments: " + database.getAssignments(this.userID, courseName));
	}
	
	/*
	private void testingCode(){
		for(int i = 0; i < 10; i++){
			addGrade("Test");
		}
		for(int i = 0; i<  20; i++){
			addStudent("Test Student");
		}
	}
	*/
	
	private void initiateGradebook(String value){
		Label name = new Label(value);
		name.setMaxSize(70, 20);
		name.setMinSize(70, 20);
		assignmentName.getChildren().add(name);
	}
	
	public ObservableList<VBox>	getStudentNames(){
		return studentNames;
		}
	public ObservableList<String> getGradeBook(){
		return gradeBook;
		}
	
	public void addStudent(String studentName) throws SQLException{
		if (studentName.length() > 0){
			Text text = new Text(studentName);
			VBox box = new VBox();
			box.getChildren().add(text);
			studentNames.add(box);
			System.out.println("added student");
			this.numStudents++;
			addLineToGrades();
		}
		database.addStudent(userID, studentName, courseName);
		System.out.println(database.getStudents(userID, courseName));
		System.out.println("num students: " + this.numStudents);
	}
	
	public void addAssignment(String value) throws SQLException{
		if (value.length() > 0){
			System.out.println("Running addGrade");
			Label name = new Label(value + "     ");
			name.setMaxSize(70, 20);
			name.setMinSize(50, 20);
			assignmentName.setSpacing(10);
			assignmentName.getChildren().add(name);
			this.numGrades++;
			System.out.println("Num students is: " + this.numStudents);
			System.out.println("gradelist is: " + this.gradeList);
			for(int i = 0; i < this.numStudents; i++){
				HBox newBox = new HBox();
				TextField newField = new TextField();
				newBox.getChildren().add(newField);
				newField.setMaxSize(45, 20);
				newField.setMinSize(45, 20);
				this.gradeList.get(i).add(newBox);
				database.addAssignment(this.userID, courseName, name.getText().toString());
				newField.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent add){
						try {
							int studentIndex = gradeList.indexOf(newField.getParent());
							System.out.println("Student index is: " + studentIndex);
							Text txt = (Text)studentNames.get(1).getChildren().get(0);
							String studentName = txt.getText().toString();
							Double grade = Double.parseDouble(newField.getText());
							String assignment = name.getText().toString();
							System.out.println("Adding to the database: " + assignment + " " + studentName + " " + grade + " " + userID + " " + courseName);
							database.addGrade(assignment, studentName, grade, userID, courseName);
							System.out.println("Added Grade");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
			System.out.println("Grade Info: " + database.getGradeInfo(userID, courseName));
		}
	}
	
	private void addLineToGrades(){
		ListView<HBox> assignment = new ListView<HBox>();
		ObservableList<HBox> assignmentGrades = FXCollections.observableArrayList();
		TextField textfield = new TextField();
		textfield.setMaxSize(45, 20);
		textfield.setMinSize(45, 20);
		HBox box = new HBox();
		box.getChildren().add(textfield);
		assignmentGrades.add(box);
		assignment.setOrientation(Orientation.HORIZONTAL);
		System.out.println(assignment.getOrientation());
		assignment.setItems(assignmentGrades);
		setSize(assignment);
		System.out.println("assignment List is: " + gradeList);
		this.gradeBox.getChildren().add(assignment);
		this.gradeList.add(assignmentGrades);
		scrollpane.setHvalue(scrollpane.getHmax());
		for(int i = 0; i < this.numGrades; i++){
			HBox newBox = new HBox();
			TextField newField = new TextField();
			newBox.getChildren().add(newField);
			newField.setMaxSize(45, 20);
			newField.setMinSize(45, 20);
			assignmentGrades.add(newBox);
			//assignment.setFixedCellSize(30);
			assignment.scrollTo(0);
		}
	}
	private void setSize(ListView<HBox> assgn){
		assgn.setMinWidth(500);
		assgn.setMaxWidth(500);
		assgn.setMaxHeight(30);
		assgn.setMinHeight(30);
	}
	
	public int getNumStudents(){
		return studentNames.size();
	}
	public int getNumGrades(){
		return gradeBook.size();
	}
	public Database getDatabase() {
		return database;
	}
	public void setUser(String name){
		this.userID = name;
	}
	
}
