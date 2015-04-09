package Model;

import java.sql.SQLException;

import Database.Database;

public class LoginModel {
	
	Database database;
	String filename = "jdbc:sqlite:db";
	
	public LoginModel() throws ClassNotFoundException, SQLException{
		database = new Database(filename);
	}
	
	public void addUser(String ID, String password, UserTypes type) throws SQLException{
		database.addUser(ID, password, type);
	}
	
	public Database getDatabase() {
		return database;
	}
}
