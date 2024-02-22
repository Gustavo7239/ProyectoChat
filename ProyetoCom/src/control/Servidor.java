package control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
        List<String> listaIPs = List.of("192.168.1.130", "localhost");
        Servidor servidor = new Servidor(listaIPs);
        servidor.iniciar();
    }

    public void iniciar() {
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_CONEXIONES);

        try (ServerSocket serverSocket = new ServerSocket(PUERTO_ESCUCHA)) {
            System.out.println("Servidor iniciado. Esperando conexiones...");

            while (true) {
                Socket socket = serverSocket.accept();
                executorService.submit(() -> manejarConexion(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void manejarConexion(Socket socket) {
        try (InputStream inputStream = socket.getInputStream()) {
            byte[] mensajeBytes = inputStream.readAllBytes();
            String mensaje = new String(mensajeBytes);

            String ipRemota = obtenerIP(socket);
            String nombreCliente = obtenerNombre(mensaje);
            String textoMensaje = obtenerTexto(mensaje);

            Mensaje mensajeObj = new Mensaje(ipRemota, nombreCliente, textoMensaje);

            for (String ipDestino : listaIPs) {
                enviarMensaje(ipDestino, PUERTO_EMISION, mensajeObj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enviarMensaje(String ipDestino, int puertoDestino, Mensaje mensaje) {
        try (Socket socket = new Socket(ipDestino, puertoDestino);
             OutputStream outputStream = socket.getOutputStream()) {
            byte[] mensajeBytes = mensaje.toString().getBytes();
            outputStream.write(mensajeBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String obtenerIP(Socket socket) {
        return socket.getInetAddress().getHostAddress();
    }

    private String obtenerNombre(String input) {
        String patron = "\\[(.*?)\\]";
        Matcher matcher = Pattern.compile(patron).matcher(input);
        return matcher.find() ? matcher.group(1) : "";
    }

    private String obtenerTexto(String input) {
        String patron = ".*?\\: ";
        return input.replaceFirst(patron, "");
    }
}
