package Model;

import java.sql.SQLException;

import Database.Database;

public class LoginModel {
	
	Database database;
	
	public LoginModel() throws ClassNotFoundException, SQLException{
		database = new Database();
		database.addUser("admin", "admin", UserTypes.PROFESSOR);
	}
	
	public Database getDatabase() {
		return database;
	}
}
