package Model;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Networking.Networker;
import Model.DatabaseCommand;

public class ProfessorModel {
	Networker net = new Networker();
	ObservableList<String> courseList = FXCollections.observableArrayList();
	
	public ObservableList<String> getStudents(String professorName, String courseName) {
		String[] args = {professorName, courseName};
		ServerRequestResult res = net.sendServerRequest(DatabaseCommand.GET_STUDENTS, args);
		ArrayList<String> students = (ArrayList<String>) res.getResult();
		ObservableList<String> studentList = FXCollections.observableArrayList(students);
		return studentList;	
	}
	
	public void addCourse(String courseName, String professorName){
		courseList.add(courseName);
		String[] args = {courseName, professorName};
		net.sendServerRequest(DatabaseCommand.ADD_COURSE, args);
	}
	
	public void addStudent(String professorName, String studentName, String courseName){
		String[] args = {professorName, studentName, courseName};
		net.sendServerRequest(DatabaseCommand.ADD_STUDENT, args);
	}
	
	public ObservableList<String> getCourseList(){
		return courseList;
	}
	
	public void callCourseListFromDB(String professorName) {
		DatabaseCommand cmd = DatabaseCommand.GET_COURSES;
		String[] args = {professorName};
		ServerRequest request = new ServerRequest (cmd, args);
		ServerRequestResult result = net.sendServerRequest(request);
		ArrayList<String> course = (ArrayList<String>) result.getResult();
		for (String c: course) {
			courseList.add(c);
		}
	}
}
