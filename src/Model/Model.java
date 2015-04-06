package Model;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.ArrayList;
import java.util.List;

import Database.Database;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
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
	
	public Model(VBox gradeBox, HBox assign, ScrollPane scrollpane) throws ClassNotFoundException, SQLException{
		database = new Database(filename);
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
		Text text = new Text(" ");
		VBox box = new VBox();
		box.setMinSize(0, 30);
		box.getChildren().add(text);
		studentNames.add(box);
		initiateGradebook("   Test 1     ");
		System.out.println("Model set Up");
		//testingCode();
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
	public ObservableList<String>	getGradeBook(){
		return gradeBook;
		}
	
	public void addStudent(String value){
		if (value.length() > 0){
			Text text = new Text(value);
			VBox box = new VBox();
			box.getChildren().add(text);
			studentNames.add(box);
			System.out.println("added student");
			this.numStudents++;
			addLineToGrades();
		}
	}
	
	public void addGrade(String value){
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
	/*
	private void populateGradebook(){
<<<<<<< HEAD
		System.out.println("Populating gradebook");
		for(int i = 0; i < numStudents(); i++){
=======
		System.out.println("Populating gradbook");
		for(int i = 0; i < getNumStudents(); i++){
>>>>>>> 09653dc415688631d1ee8326a63758e5f013d6f9
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
	
	public void sendServerRequest(ServerRequest request){
		//TODO: Add a check for the ClientRequestThread to already exist and "be going" (?, trying
		//to follow class code structure).
		//TODO: Not sure why the argument to channel is 2 or if it matters; just following class.
		channel = new ArrayBlockingQueue<ServerRequestResult>(2);
		requestThread = new ClientRequestThread(request, serverHost, serverPort, channel);
		new Receiver().start();
		//TODO Once this thread finishes, the ServerRequestResults should be in channel. How do I know
		//when?
		requestThread.start();
	}
	
	public class Receiver extends Thread {
		public void run() {
			while (requestThread.isGoing()) {
				ServerRequestResult result;
				try {
					result = channel.take();
					//TODO: need to do something with the result, but what?
					//addMessage(line);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
