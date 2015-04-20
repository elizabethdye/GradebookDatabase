package Networking;

import java.util.concurrent.ArrayBlockingQueue;

import Model.ServerRequest;
import Model.ServerRequestResult;

public class Networker {
	ClientRequestThread requestThread;
	String serverHost;
	int serverPort;
	ArrayBlockingQueue<ServerRequestResult> channel;
	ServerRequestResult result;
	boolean received;
	
	public Networker(){
		serverHost = "localhost";
		serverPort = 8888;
		channel = new ArrayBlockingQueue<ServerRequestResult>(2);
		received = false;
	}
	
	public synchronized ServerRequestResult sendServerRequest(ServerRequest request){
		requestThread = new ClientRequestThread(request, serverHost, serverPort, channel);
		new Receiver(this).start();
		requestThread.start();
		while (received == false){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Got our result and will now return it from the Networker");
		received = false;
		System.out.println("Returning from sendServerRequest...");
		return result;
	}
	
	private synchronized void alert(){
		this.notifyAll();
	}
	
	public class Receiver extends Thread {
		Networker networker;
		
		public Receiver(Networker net){
			networker = net;
		}
		public void run() {
			while (requestThread.isGoing()) {
				try {
					result = channel.take();
					received = true;
					networker.alert();
					System.out.println("Got result from channel...");
					requestThread.halt();
					System.out.println(requestThread.isGoing());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
