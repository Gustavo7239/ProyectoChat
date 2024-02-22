package controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import model.Message;

public class ClientReceiver extends Thread{
	
	private Socket clientSocket;
	private ServerSocket serverSocket;
	private InputStream is;  PrintWriter pw; ObjectInputStream ois;
	private OutputStream os; BufferedReader br; ObjectOutputStream oos;
	private Client client; 
	
	private String serverIp;
	private int serverPort;
	
	public ClientReceiver() {
		super();
	}
	
	public ClientReceiver(Client client, String serverIp, int serverPort) {
		super();
		this.client = client;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
	}

	@Override
	public void run() {
		
		try {
			//System.out.println("[Cliente]: Inicializando el socket");
			clientSocket = new Socket();
			
			//System.out.println("[Cliente]: Enlazando al socket");
			clientSocket.connect(new InetSocketAddress(serverIp, serverPort));
			
			while(true) {
				//System.out.println("[Cliente]: Iniciando las conexiones Stream.");
				is= clientSocket.getInputStream();
				os= clientSocket.getOutputStream();

				ois = new ObjectInputStream(is);
				
				Message msg;
				msg = (Message) ois.readObject();
				if(msg!=null) {
					addToReceivedQueue(msg);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addToReceivedQueue(Message m) {
		client.addReceivedQueue(m);
	}
	
}
