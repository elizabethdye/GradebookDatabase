package Model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Networking.Networker;
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
	Networker networker;
	private ObservableList<VBox> studentNames;
	private int numGrades = 0;
	private ObservableList<String> gradeBook;
	List<List<HBox>> gradeList = new ArrayList<List<HBox>>();
	private VBox gradeBox;
	private HBox assignmentName;
	private ScrollPane scrollpane;
	private int numStudents = 0;
	private String userID;
	//private String userType;
	private String courseName;
	
	public Model(VBox gradeBox, HBox assign, ScrollPane scrollpane, Tab courseName, Networker net) throws ClassNotFoundException, SQLException{
		this.networker = net;
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
		//printDatabase();
		//testingCode();
	}
	
	public void populateGradebook(){
		DatabaseCommand cmd = DatabaseCommand.GET_COURSES;
		String[] args = {userID};
		ServerRequest request = new ServerRequest(cmd, args);
		ServerRequestResult result = networker.sendServerRequest(request);
		ArrayList<String> courses = (ArrayList<String>) result.getResult();
		System.out.println("List of courses: " + courses);
	}
	
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
		addStudentToDatabase(userID, studentName, courseName);
		//database.addStudent(userID, studentName, courseName);
//		System.out.println(database.getStudents(userID, courseName));
//		System.out.println("num students: " + this.numStudents);
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
				addAssignmentToDatabase(userID, courseName, name.getText().toString());
				//database.addAssignment(this.userID, courseName, name.getText().toString());
				newField.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent add){
						int studentIndex = gradeList.indexOf(newField.getParent());
						System.out.println("Student index is: " + studentIndex);
						Text txt = (Text)studentNames.get(1).getChildren().get(0);
						String studentName = txt.getText().toString();
						Double grade = Double.parseDouble(newField.getText());
						String assignment = name.getText().toString();
						System.out.println("Adding to the database: " + assignment + " " + studentName + " " + grade + " " + userID + " " + courseName);
						addGradeToDatabase(assignment, studentName, grade, userID, courseName);
						//database.addGrade(assignment, studentName, grade, userID, courseName);
						System.out.println("Added Grade");
					}
				});
			}
			//System.out.println("Grade Info: " + database.getGradeInfo(userID, courseName));
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
	public void setUser(String name){
		this.userID = name;
	}
	
	//database.addGrade(assignment, studentName, grade, userID, courseName);
	void addGradeToDatabase(String assignmentName, String studentName, Double grade, String profName, String courseName){
		DatabaseCommand cmd = DatabaseCommand.ADD_GRADE;
		String[] args = {assignmentName, studentName, grade.toString(), profName, courseName};
		ServerRequest request = new ServerRequest(cmd, args);
		networker.sendServerRequest(request);
	}
	
	void addStudentToDatabase(String profName, String studentName, String courseName){
		System.out.println("networker: " + networker == null);
		DatabaseCommand cmd = DatabaseCommand.ADD_STUDENT;
		String[] args = {profName, studentName, courseName};
		ServerRequest request = new ServerRequest(cmd, args);
		networker.sendServerRequest(request);
	}
	
	void addAssignmentToDatabase(String profName, String courseName, String assignmentName){
		DatabaseCommand cmd = DatabaseCommand.ADD_ASSIGNMENT;
		String[] args = {profName, courseName, assignmentName};
		ServerRequest request = new ServerRequest(cmd, args);
		networker.sendServerRequest(request);
	}
	
	public void setNetworker(Networker net){
		this.networker = net;
	}
}
