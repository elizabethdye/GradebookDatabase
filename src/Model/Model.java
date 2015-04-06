package Model;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import Database.Database;

public class Model {
	Database database;
	ClientRequestThread requestThread;
	String serverHost;
	int serverPort;
	ArrayBlockingQueue<ServerRequestResult> channel;
	private ObservableList<String> studentNames;
	private ObservableList<HBox> gradeBook;
	private HBox gradeBox;
	private int numGrades = 0;
	
	private void Model() throws ClassNotFoundException, SQLException{
		database = new Database();
		studentNames = FXCollections.observableArrayList();
		gradeBook = FXCollections.observableArrayList();
		studentNames.add(" ");
		this.gradeBox = new HBox();
		gradeBox.setSpacing(20);
		gradeBook.add(this.gradeBox);
	}
	
	public Database getDatabase(){
		return database;
	}
	
	public ObservableList<String>	studentNames(){
		return studentNames;
		}
	public ObservableList<HBox>	gradeBook(){
		return gradeBook;
		}
	
	public void addStudent(String value){
		if (value.length() > 0){
			studentNames.add(value);
			HBox studentGrades = new HBox();
			studentGrades.setSpacing(20);
			gradeBook.add(studentGrades);
			System.out.println("added student");
			for(int i = 0; i < this.numGrades; i++){
				HBox assignGrade = new HBox();
				studentGrades.getChildren().add(assignGrade);
			}
		}
	}
	
	public void addGrade(String value){
		System.out.println("Running addGrade");
		/*Text text = new Text();
		text.setText(value);
		this.gradeBox.getChildren().add(text);*/
		Button button = new Button(value);
		this.gradeBox.getChildren().add(button);
		System.out.println("GradeBook size is: " + gradeBook.size());
		for( int i = 0; i < gradeBook.size(); i++){
			System.out.println("GradeBook is: " + gradeBook().get(i).toString());
		}
		this.numGrades++;
	}
	
	private void populateGradebook(){
		System.out.println("Populating gradebook");
		for(int i = 0; i < numStudents(); i++){
			HBox studentGrades = new HBox();
			studentGrades.setSpacing(20);
			gradeBook.add(studentGrades);
			for(int j = 0; j < this.numGrades; i++){
				HBox assignGrade = new HBox();
				studentGrades.getChildren().add(assignGrade);
			}
		}
	}
	
	public int numStudents(){
		return studentNames.size();
	}
	public int numGrades(){
		return gradeBook.size();
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
