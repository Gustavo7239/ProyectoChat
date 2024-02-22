package controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import model.Message;

public class Server {
	private String serverIp = "localhost";
	private int serverPort  = 5555;
	private BlockingQueue<Message> broadcastQueue;
	
	public void main(String[] args) {
		Socket socket;
		ServerSocket serverSocket;
		
		InputStream is;  BufferedReader br;
		OutputStream os; PrintWriter pw;
		
		broadcastQueue = new LinkedBlockingDeque<>();
		
		try {

			System.out.println("[Servidor]: Inicializando el socket");
			serverSocket = new ServerSocket();

			System.out.println("[Servidor]: Enlazando al socket");
			serverSocket.bind(new InetSocketAddress(serverIp, serverPort));

			System.out.println("[Servidor]: Escuchando peticiones...");

			while (true) {
				socket = serverSocket.accept();
				
				System.out.println("[Servidor]: Peticion recibida.");
				new Thread(new Processor(socket, this)).start();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Servidor: FIN");
	}

	public BlockingQueue<Message> getBroadcastQueue() {
		return broadcastQueue;
	}

	public void setBroadcastQueue(BlockingQueue<Message> broadcastQueue) {
		this.broadcastQueue = broadcastQueue;
	}

	
	
	
}
