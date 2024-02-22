package controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import model.Message;
import view.Visual;

public class Client {
	private static String userName  = "PC1";
	private static String userIp    = "localhost";
	private static String serverIp  = "localhost";
	private static int serverPort   =  5555;

	private BlockingQueue<Message> receivedQueue = new LinkedBlockingQueue<>();
	
	private Visual v = new Visual();
	
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		
		Socket clientSocket;
		ServerSocket serverSocket;
		InputStream is;  PrintWriter pw;
		OutputStream os; BufferedReader br;
		
		try {
			System.out.println("[Cliente]: Inicializando el socket");
			clientSocket = new Socket();
			
			System.out.println("[Cliente]: Enlazando al socket");
			
			while(!clientSocket.isConnected()) {
				try {
					clientSocket.connect(new InetSocketAddress(serverIp, serverPort));
				}
				catch (Exception e) {
				}
				finally {
					System.out.print(".");
				}
				Thread.sleep(100);
			} 
			System.out.println();
			
			System.out.println("[Cliente]: Iniciando las conexiones Stream.");
			is= clientSocket.getInputStream();
			os= clientSocket.getOutputStream();
			
			pw = new PrintWriter(os,true);
			br = new BufferedReader(new InputStreamReader(is));
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void reedMessages() {
		while(!receivedQueue.isEmpty()) {
			v.printMsg(receivedQueue.poll());
		}
	}
	
	public void addReceivedQueue(Message m) {
		receivedQueue.offer(m);
	}

	
	
}
