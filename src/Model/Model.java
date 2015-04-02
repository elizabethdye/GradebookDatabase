package Model;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Database.Database;

public class Model {
	Database database;
	private ObservableList<String> studentNames;
	private ObservableList<String> gradebook;
	
	private void Model() throws ClassNotFoundException, SQLException{
		database = new Database();
		studentNames = FXCollections.observableArrayList();
		gradebook = FXCollections.observableArrayList();
	}
	
	Database getDatabase(){
		return database;
	}
	
	ObservableList<String>	getStudentNames(){
		return studentNames;
	}
	ObservableList<String>	getGradebook(){
		return gradebook;
	}
	
	public void addStudent(String value){
		if (value.length() > 0){
			studentNames.add(value);
		}
	}
	public void addGrade(String value){
		if (value.length() > 0){
			gradebook.add(value);
		}
	}
	
	public int numStudents(){
		return studentNames.size();
	}
	public int numGrades(){
		return gradebook.size();
	}

}
