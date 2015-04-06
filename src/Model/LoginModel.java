package Model;

import java.sql.SQLException;

import Database.Database;

public class LoginModel {
	
	Database database;
	String filename = "jdbc:sqlite:db";
	
	public LoginModel() throws ClassNotFoundException, SQLException{
		database = new Database(filename);
		database.addUser("admin", "admin", UserTypes.PROFESSOR);
		database.addUser("Ferrer", "ILoveRobotics", UserTypes.PROFESSOR);
	}
	
	public void addUser(String ID, String password, UserTypes type) throws SQLException{
		database.addUser(ID, password, type);
	}
	
	public Database getDatabase() {
		return database;
	}
}
