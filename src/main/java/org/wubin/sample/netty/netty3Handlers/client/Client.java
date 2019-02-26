package org.wubin.sample.netty.netty3Handlers.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
	    Socket socket = new Socket("127.0.0.1", 10101);
		
		socket.getOutputStream().write("hello".getBytes());
		
		socket.close();
	}
}
