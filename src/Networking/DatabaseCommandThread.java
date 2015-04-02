package Networking;

import java.net.*;
import java.io.*;

import Database.Database;

public class DatabaseCommandThread extends Thread {
    private Socket socket;
    private Database db;
    
    public DatabaseCommandThread(Socket socket, Database db) {
        this.socket = socket;
        this.db = db;
    }

    public void run() {
        try {
            BufferedReader responses = 
                new BufferedReader
                (new InputStreamReader(socket.getInputStream()));
            //PrintWriter writer = new PrintWriter(socket.getOutputStream());
            //writer.println("Connection open.");
            //writer.println("I will echo a single message, then close.");

            //TODO: Is StringBuilder needed/correct? Should only have one line for a db command?
            StringBuilder sb = new StringBuilder();
            while (!responses.ready()){}
            while (responses.ready()) {
                sb.append(responses.readLine() + '\n');
            }
            System.out.println("Database command from: " + socket.getInetAddress() + ": " + sb);
            
            Object ret = executeDatabaseCommand(sb.toString());
        
            //writer.print(sb);
            //writer.flush();
            socket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } 
    }
    
    private Object executeDatabaseCommand(String command){
    	
    }
}
