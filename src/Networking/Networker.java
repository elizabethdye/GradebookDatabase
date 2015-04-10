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
		serverHost = "Merry";
		serverPort = 8888;
		//TODO: Not sure why the argument to channel is 2 or if it matters; just following class.
		channel = new ArrayBlockingQueue<ServerRequestResult>(2);
		received = false;
	}
	
	public synchronized ServerRequestResult sendServerRequest(ServerRequest request){
		//TODO: Add a check for the ClientRequestThread to already exist and "be going" (?, trying
		//to follow class code structure).
		requestThread = new ClientRequestThread(request, serverHost, serverPort, channel);
		new Receiver(this).start();
		//TODO Once this thread finishes, the ServerRequestResults should be in channel. How do I know
		//when?
		requestThread.start();
		//TODO the hackiest thing ever, but who cares?
		while (received == false){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
