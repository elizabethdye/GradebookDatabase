package Networking;

import java.net.*;
import java.io.*;

import Database.Database;

public class Server {
	private ServerSocket accepter;
	private Database db;

	public Server(int port) throws IOException{
		accepter = new ServerSocket(port);
		System.out.println("Server IP address: " + accepter.getInetAddress());
	}

	public void listen() throws IOException {
		for (;;) {
			Socket s = accepter.accept();
			ServerRequestThread requestThread = new ServerRequestThread(s, db);
			System.out.println("Connection accepted from " + s.getInetAddress());
			requestThread.start();
		}
	}

	public static void main(String[] args) throws IOException {
		Server s = new Server(Integer.parseInt(args[0]));
		s.listen();
	}
}
