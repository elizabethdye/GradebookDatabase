package Model;

import java.sql.SQLException;

import Database.Database;
import Networking.Networker;

public class LoginModel {
	
	Database database;
	String filename = "jdbc:sqlite:db";
	Networker networker;
	
	public LoginModel() throws ClassNotFoundException, SQLException{
		networker = new Networker();
		database = new Database(filename);
		System.out.println("Calling addUser from LoginModel constructor!!!");
		addUser("admin", "admin", UserTypes.ADMIN);
	}
	
	public void addUser(String ID, String password, UserTypes type) throws SQLException{
		System.out.println("CALLING addUser!!!!");
		//database.addUser(ID, password, type);
		DatabaseCommand cmd = DatabaseCommand.ADD_USER;
		String[] args = {ID, password, type.toString()};
		ServerRequest request = new ServerRequest (cmd, args);
		networker.sendServerRequest(request);
	}
	
	public Database getDatabase() {
		return database;
	}
	
	public Networker getNetworker() {
		return networker;
	}
}
