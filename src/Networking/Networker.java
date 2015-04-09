package Networking;

import java.util.concurrent.ArrayBlockingQueue;

import Model.ServerRequest;
import Model.ServerRequestResult;

public class Networker {
	ClientRequestThread requestThread;
	String serverHost;
	int serverPort;
	ArrayBlockingQueue<ServerRequestResult> channel;
	
	public Networker(){
		serverHost = "Bill";
		serverPort = 8888;
		//TODO: Not sure why the argument to channel is 2 or if it matters; just following class.
		channel = new ArrayBlockingQueue<ServerRequestResult>(2);
	}
	
	public void sendServerRequest(ServerRequest request){
		//TODO: Add a check for the ClientRequestThread to already exist and "be going" (?, trying
		//to follow class code structure).
		requestThread = new ClientRequestThread(request, serverHost, serverPort, channel);
		new Receiver().start();
		//TODO Once this thread finishes, the ServerRequestResults should be in channel. How do I know
		//when?
		requestThread.start();
	}
	
	public class Receiver extends Thread {
		public void run() {
			while (requestThread.isGoing()) {
				ServerRequestResult result;
				try {
					result = channel.take();
					//TODO: need to do something with the result, but what?
					//addMessage(line);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
