package controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

import model.Message;

public class Processor implements Runnable{
	private Semaphore semaphore;
	private BlockingQueue<Message> messageQueue;
	
	Socket socket;
	ServerSocket serverSocket;
	
	InputStream is;  BufferedReader br;
	OutputStream os; PrintWriter pw;
	
	Server server;
	
	public Processor(Socket socket, Server server) {
		super();
		this.socket = socket;
		this.server = server;
	}

	@Override
	public void run() {
		
	}
	
	public synchronized void enQueue(Message message) {
		messageQueue.offer(message);
	}

}
