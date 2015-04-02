package Model;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import Database.Database;

public class Model {
	Database database;
	private ObservableList<String> studentNames;
	private ObservableList<HBox> gradeBook;
	
	private void Model() throws ClassNotFoundException, SQLException{
		database = new Database();
		studentNames = FXCollections.observableArrayList();
		gradeBook = FXCollections.observableArrayList();
	}
	
	public Database getDatabase(){
		return database;
	}
	
	public ObservableList<String>	getStudentNames(){
		return studentNames;
	}
	public ObservableList<HBox>	getGradebook(){
		return gradeBook;
	}
	
	public void addStudent(String value){
		if (value.length() > 0){
			studentNames.add(value);
		}
	}
	public void addGrade(String value){
		HBox newGrade = new HBox();
		gradeBook.add(newGrade);
	}
	
	public int numStudents(){
		return studentNames.size();
	}
	public int numGrades(){
		return gradeBook.size();
	}

}
