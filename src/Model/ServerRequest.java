package Model;

public class ServerRequest {
	DatabaseCommand command;
	String[] args;
	
	public DatabaseCommand getCommand(){
		return command;
	}
	
	public String[] getArgs(){
		return args;
	}
}
