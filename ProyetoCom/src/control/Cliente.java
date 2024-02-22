package control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import modelo.Mensaje;

public class Cliente {
    private static final String IP_SERVIDOR = "localhost"; // Reemplaza con la dirección IP del servidor
    private static final int PUERTO_ESCUCHA = 5556;
    private static final int PUERTO_EMISION = 5555;

    private String usuario = "PC2";
    private String miIp = "10.10.0.186";
    
    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        cliente.iniciar();
    }

    public void iniciar() {
        // Hilo para recibir mensajes del servidor
        Thread hiloRecepcion = new Thread(() -> recibirMensajes(IP_SERVIDOR, PUERTO_ESCUCHA));
        hiloRecepcion.start();

        // Envía mensajes al servidor desde la consola
        enviarMensajes(IP_SERVIDOR, PUERTO_EMISION);

        // Espera a que el hilo de recepción termine
        try {
            hiloRecepcion.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void recibirMensajes(String ipServidor, int puerto) {
        try {
            Socket socket = new Socket(ipServidor, puerto);
            InputStream inputStream = socket.getInputStream();

            Scanner scanner = new Scanner(inputStream);

            while (true) {
                String mensajeRecibido = scanner.nextLine();
                System.out.println("Mensaje del servidor: " + mensajeRecibido);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enviarMensajes(String ipServidor, int puerto) {
        try {
            Socket socket = new Socket(ipServidor, puerto);
            OutputStream outputStream = socket.getOutputStream();
            
            Scanner scanner = new Scanner(System.in);

            while (true) {
                //System.out.print("Escribe un mensaje para enviar al servidor: ");
                System.out.print("["+usuario+"]: ");
            	String mensaje = scanner.nextLine();
                
                Mensaje mensajeObj = new Mensaje(miIp,usuario,mensaje);
                
                // Envía el mensaje al servidor
                outputStream.write(mensajeObj.msgEnviar().getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
