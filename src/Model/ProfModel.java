package Model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import GUI.Main;
import GUI.ProfessorController;
import Networking.Networker;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ProfModel {
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
	private ProfessorController controller;
	private String course;
	
	public ProfModel(ProfessorController controller) throws ClassNotFoundException, SQLException{
		this.controller = controller;
		this.assignmentName = controller.assignmentNames;
		this.gradeBox = controller.gradeBox;
		this.scrollpane = controller.scrollpane;
		this.networker = new Networker();
		studentNames = FXCollections.observableArrayList();
		gradeBook = FXCollections.observableArrayList();
		assignmentName.setSpacing(30);
		assignmentName.setMaxSize(70, 20);
		assignmentName.setMinSize(70, 20);
		DoubleProperty wProperty = new SimpleDoubleProperty();
		wProperty.bind(gradeBox.widthProperty());
		Text text = new Text(" ");
		VBox box = new VBox();
		box.setMinSize(0, 30);
		box.getChildren().add(text);
		studentNames.add(box);
		System.out.println("Model set Up");
		//testLists();
		//printDatabase();
		//testingCode();
	}
	
	public void setCourse(String course){ this.course = course; }
	
	public void populateGradebook() throws SQLException, IOException{
		System.out.println("Populating gradebook");
		System.out.println("userID is: " + this.userID);
		DatabaseCommand cmd = DatabaseCommand.GET_COURSES;
		String[] args = {userID};
		ServerRequest request = new ServerRequest(cmd, args);
		ServerRequestResult result = networker.sendServerRequest(request);
		ArrayList<String> courses = (ArrayList<String>) result.getResult();
		System.out.println("Courses: " + courses);
		for (String course : courses){
//			Tab newTab = new Tab();
//			newTab.setText(course);
//			controller.tabPane.getTabs().add(newTab);
//			createNewTab(newTab);
			ArrayList<String> students = getListofStudents(userID, course);
			System.out.println("List of students: " + students);
			for(String student : students){
				System.out.println("Student: " + student);
				populateStudent(student);
			}
			ArrayList<String> assignments = getAssignments(userID, course);
			for(String assign : assignments){
				populateAssignment(assign);
			}
			
		}
	}
//	private void createNewTab(Tab currentTab) throws IOException{
//		FXMLLoader loader = new FXMLLoader();
//		loader.setLocation(Main.class.getResource("Tab.fxml"));
//		TabPane root = (TabPane) loader.load();
//		Node content = root.getTabs().get(0).getContent();
//		currentTab.setContent(content);
//	}
	
	private void populateStudent(String studentName){
		if (studentName.length() > 0){
			Text text = new Text(studentName);
			VBox box = new VBox();
			box.getChildren().add(text);
			studentNames.add(box);
			System.out.println("added student");
			this.numStudents++;
			addLineToGrades();
		}
	}
	
	private void populateAssignment(String value){
		if (value.length() > 0){
			System.out.println("Running addGrade");
			Label name = new Label(value + "     ");
			name.setMaxSize(70, 20);
			name.setMinSize(50, 20);
			assignmentName.setSpacing(10);
			assignmentName.getChildren().add(name);
			this.numGrades++;
			for(int i = 0; i < this.numStudents; i++){
				HBox newBox = new HBox();
				TextField newField = new TextField();
				newBox.getChildren().add(newField);
				newField.setMaxSize(45, 20);
				newField.setMinSize(45, 20);
				this.gradeList.get(i).add(newBox);
				newField.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent add){
						int studentIndex = gradeList.indexOf(newField.getParent());
						Text txt = (Text)studentNames.get(1).getChildren().get(0);
						String studentName = txt.getText().toString();
						Double grade = Double.parseDouble(newField.getText());
						String assignment = name.getText().toString();
					}
				});
			}
		}
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
//		Tab courseTab = controller.tabPane.getSelectionModel().getSelectedItem();
//		String courseName = courseTab.getText();
		
		addStudentToDatabase(this.userID, studentName, course);
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
//				Tab courseTab = controller.tabPane.getSelectionModel().getSelectedItem();
//				String courseName = courseTab.getText();
				addAssignmentToDatabase(userID, course, name.getText().toString());
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
		System.out.println("userID " + name);
	}
	
	public void addCourseToDatabase(String courseName, String professorName){
		professorName = controller.userID;
		System.out.println("Professor name is: " + professorName);
		System.out.println("Adding course to database: 	" + courseName + " " + professorName);
		DatabaseCommand cmd = DatabaseCommand.ADD_COURSE;
		String[] args = {courseName, professorName};
		ServerRequest request = new ServerRequest(cmd,args);
		networker.sendServerRequest(request);
		System.out.println("Successfully added course to database");
	}
	//TODO do we really want to do this vvvvvvvv
	public void removeStudentFromDatabase(String professorName, String studentName, String coursename){
//		Tab courseTab = controller.tabPane.getSelectionModel().getSelectedItem();
//		String course = courseTab.getText();
		DatabaseCommand cmd = DatabaseCommand.REMOVE_STUDENT;
		String[] args = {professorName, studentName, course};
		ServerRequest request = new ServerRequest(cmd, args);
		networker.sendServerRequest(request);
	}
	
	//database.addGrade(assignment, studentName, grade, userID, courseName);
	void addGradeToDatabase(String assignmentName, String studentName, Double grade, String professorName, String courseName){
		professorName = controller.userID;
//		Tab courseTab = controller.tabPane.getSelectionModel().getSelectedItem();
//		String course = courseTab.getText();
		DatabaseCommand cmd = DatabaseCommand.ADD_GRADE;
		String[] args = {assignmentName, studentName, grade.toString(), professorName, course};
		ServerRequest request = new ServerRequest(cmd, args);
		networker.sendServerRequest(request);
	}
	
	void addStudentToDatabase(String professorName, String studentName, String courseName){
		professorName = controller.userID;
		System.out.println("Professor name is: " + professorName);
//		Tab courseTab = controller.tabPane.getSelectionModel().getSelectedItem();
//		String course = courseTab.getText();
		System.out.println("Sending student under " + professorName + " " + studentName + " " + course );
		DatabaseCommand cmd = DatabaseCommand.ADD_STUDENT;
		String[] args = {professorName, studentName, course};
		ServerRequest request = new ServerRequest(cmd, args);
		networker.sendServerRequest(request);
		System.out.println("Student sent");
		
	}
	
	void addAssignmentToDatabase(String professorName, String courseName, String assignmentName){
		professorName = controller.userID;
//		Tab courseTab = controller.tabPane.getSelectionModel().getSelectedItem();
//		String course = courseTab.getText();
		DatabaseCommand cmd = DatabaseCommand.ADD_ASSIGNMENT;
		String[] args = {professorName, course, assignmentName};
		ServerRequest request = new ServerRequest(cmd, args);
		networker.sendServerRequest(request);
	}
	
	ArrayList<String> getListofStudents(String professorName, String courseName){
		professorName = controller.userID;
		System.out.println("Professor name is: " + professorName);
//		Tab courseTab = controller.tabPane.getSelectionModel().getSelectedItem();
//		String course = courseTab.getText();
		System.out.println("Get list of students from: " + professorName + " " + course);
		DatabaseCommand cmd = DatabaseCommand.GET_STUDENTS;
		String[] args = {professorName, course};
		ServerRequest request = new ServerRequest(cmd, args);
		ServerRequestResult result = networker.sendServerRequest(request);
		ArrayList<String> students = (ArrayList<String>) result.getResult();
		System.out.println("GetListofStudents: " + students);
		return students;
	}
	
	ArrayList<GradeInfo> getGradeInfo(String professorName, String courseName){
		professorName = controller.userID;
//		Tab courseTab = controller.tabPane.getSelectionModel().getSelectedItem();
//		String course = courseTab.getText();
		DatabaseCommand cmd = DatabaseCommand.GET_GRADE_INFO;
		String[] args = {professorName, course};
		ServerRequest request = new ServerRequest(cmd, args);
		ServerRequestResult result = networker.sendServerRequest(request);
		ArrayList<GradeInfo> gradeInfo = (ArrayList<GradeInfo>) result.getResult();
		return gradeInfo;
	}
	
	ArrayList<String> getAssignments(String professorName, String courseName){
		professorName = controller.userID;
		System.out.println("Professor name is: " + professorName);
//		Tab courseTab = controller.tabPane.getSelectionModel().getSelectedItem();
//		String course = courseTab.getText();
		DatabaseCommand cmd = DatabaseCommand.GET_ASSIGNMENTS;
		String[] args = {professorName, course};
		ServerRequest request = new ServerRequest(cmd, args);
		ServerRequestResult result = networker.sendServerRequest(request);
		ArrayList<String> assignments = (ArrayList<String>) result.getResult();
		return assignments;
	}
	
	public void setNetworker(Networker net){
		this.networker = net;
	}
}
