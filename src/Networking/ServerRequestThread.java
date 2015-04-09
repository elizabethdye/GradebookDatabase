package Networking;

import java.net.*;
import java.sql.SQLException;
import java.io.*;

import Database.Database;
import Model.DatabaseCommand;
import Model.ServerRequest;
import Model.ServerRequestResult;
import Model.UserTypes;

public class ServerRequestThread extends Thread {
    private Socket socket;
    private Database db;
    
    public ServerRequestThread(Socket socket, Database db) {
        this.socket = socket;
        this.db = db;
    }

    public void run(){
        try {
            ObjectInputStream fromClientStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream toClientStream = new ObjectOutputStream(socket.getOutputStream());
            
            try {
				ServerRequest clientRequest = (ServerRequest) fromClientStream.readObject();
				System.out.println("Got ServerRequest from client");
				ServerRequestResult result = evaluateRequest(clientRequest);
				toClientStream.writeObject(result);
				System.out.println("Sent the ServerRequestResult back to user");
				//TODO: not sure what this does or if it's needed, but copying class code structure
				toClientStream.flush();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
            socket.close();
            fromClientStream.close();
            toClientStream.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } 
    }
    
    private ServerRequestResult evaluateRequest(ServerRequest request) throws SQLException{
    	DatabaseCommand command = request.getCommand();
    	String[] args = request.getArgs();
    	ServerRequestResult result = new ServerRequestResult();
    	
    	switch (command){
    		case CREATE_TABLES:
    			result.setResult(null);
    		case ADD_COURSE:
    			db.addCourse(args[0], args[1]);
    			result.setResult(null);
    		case ADD_STUDENT:
    			db.addStudent(args[0], args[1], args[2]);
    			result.setResult(null);
    		case ADD_GRADE:
    			db.addGrade(args[0], args[1], Double.valueOf(args[2]), args[3], args[4]);
    			result.setResult(null);
    		case RETRIEVE_GRADE:
    			result.setResult(db.retrieveGrade(args[0], args[1], args[2], args[3]));
    		case RETRIEVE_OVERALL_GRADE:
    			result.setResult(db.retrieveOverallGrade(args[0], args[1], args[2]));
    		case GET_COURSES:
    			result.setResult(db.getCourses(args[0]));
    		case GET_ASSIGNMENTS:
    			result.setResult(db.getAssignments(args[0], args[1]));
    		case GET_STUDENTS:
    			result.setResult(db.getStudents(args[0], args[1]));
    		case GET_GRADE_INFO:
    			result.setResult(db.getGradeInfo(args[0], args[1]));
    		case GET_USER_TYPE:
    			result.setResult(db.getUserType(args[0], args[1]));
    		case ADD_USER:
    			db.addUser(args[0], args[1], UserTypes.fromString(args[2]));
    			result.setResult(null);
    	}
    	System.out.println("Finished evaluating database command...");
    	return result;
    }
}
