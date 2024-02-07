package control;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class ServidorRunnable implements Runnable {
    private int puerto;
    private ServerSocket serverSocket;
    private BlockingQueue<String> mensajeQueue = new LinkedBlockingQueue<>();

    public ServidorRunnable(int puerto) {
        this.puerto = puerto;
    }

    @Override
    public void run() {
        encender();
    }

    public void encender() {
        try {
            serverSocket = new ServerSocket(puerto);
            System.out.println("Servidor iniciado en el puerto " + puerto);

            while (true) {
                // Esperar a que un cliente se conecte
                Socket socket = serverSocket.accept();

                // Leer el mensaje del cliente
                String mensajeRecibido = recibirMensaje(socket);

                // Imprimir el mensaje recibido en el servidor
                //System.out.println("Mensaje recibido en el servidor: " + mensajeRecibido);

                // Poner el mensaje en la cola para que el hilo principal lo recupere
                mensajeQueue.offer(mensajeRecibido);

                // Cerrar el socket del cliente
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String recibirMensaje(Socket socket) {
        try {
            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[1024];

            // Leer el mensaje del cliente
            int bytesRead = inputStream.read(buffer);
            return new String(buffer, 0, bytesRead);

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String recibirMensaje() throws InterruptedException {
        // Devolver el mensaje desde la cola (esto bloqueará si la cola está vacía)
        return mensajeQueue.take();
    }
}