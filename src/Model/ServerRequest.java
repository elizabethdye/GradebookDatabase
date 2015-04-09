package Model;

public class ServerRequest {
	DatabaseCommand command;
	String[] args;
	
	public ServerRequest (DatabaseCommand cmd, String[] args){
		this.command = cmd;
		this.args = args;
	}
	
	public DatabaseCommand getCommand(){
		return command;
	}
	
	public String[] getArgs(){
		return args;
	}
}
