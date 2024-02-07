package control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modelo.Mensaje;

public class Servidor {
	private static final int PUERTO_ESCUCHA = 5555;
    private static final int PUERTO_EMISION = 5556;
    private static final int MAX_CONEXIONES = 10;

    private List<String> listaIPs;
    
    public Servidor(List<String> listaIPs) {
        this.listaIPs = listaIPs;
    }

	public static void main(String[] args) {
		List<String> listaIPs = new ArrayList<>();
        listaIPs.add("192.168.1.130");  // Reemplaza con tus direcciones IP
        //listaIPs.add("localhost");
        // Agrega más direcciones IP según sea necesario

        Servidor servidor = new Servidor(listaIPs);
        servidor.iniciar();
    }
	
	public void iniciar() {
        ServerSocket serverSocket = null;
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_CONEXIONES);

        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(PUERTO_ESCUCHA));

            System.out.println("Servidor iniciado. Esperando conexiones...");

            while (true) {
                Socket socket = serverSocket.accept();

                // Crea un nuevo hilo para manejar la conexión
                executorService.submit(() -> manejarConexion(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	private void manejarConexion(Socket socket) {
        try {
            InputStream inputStream = socket.getInputStream();
            byte[] mensajeBytes = new byte[1024];

            // Lee el mensaje del cliente
            int bytesLeidos = inputStream.read(mensajeBytes);
            String mensaje = new String(mensajeBytes, 0, bytesLeidos);

            Mensaje mensajeObj = new Mensaje(
                    obtenerIP(mensaje),
                    obtenerNombre(mensaje),
                    obtenerTexto(mensaje));
            
            //System.out.println(mensajeObj.msgRecibir());

            // Reenvía el mensaje a todas las IPs de la lista en el puerto de emisión
            for (String ip : listaIPs) {
                enviarMensaje(ip, PUERTO_EMISION, mensajeObj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private void enviarMensaje(String ipDestino, int puertoDestino, Mensaje mensaje) {
        Socket socket = null;
        OutputStream outputStream = null;

        try {
            socket = new Socket(ipDestino, puertoDestino);
            outputStream = socket.getOutputStream();
            byte[] mensajeBytes = mensaje.msgEnviar().getBytes();
            outputStream.write(mensajeBytes);

            //System.out.println("Mensaje enviado a " + ipDestino + ":" + puertoDestino + ": " + mensaje);
            System.out.println(mensaje.msgEnviar());
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
    public static String obtenerIP(String input) {
        String patron = "(\\d+\\.\\d+\\.\\d+\\.\\d+)@";

        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "No se encontró una dirección IP";
        }
    }
    public static String obtenerNombre(String input) {
        String patron = "\\[(.*?)\\]";
        
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return ""; 
        }
    }
    public static String obtenerTexto(String input) {
        String patron = ".*?\\: ";
        return input.replaceFirst(patron, "");
    }
}