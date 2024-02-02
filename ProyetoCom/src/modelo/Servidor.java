package modelo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Servidor {
	private String ipServidor;
	private int puerto;
	private List<String> usuarios; //Lista con las ip de todos los usuarios
	
	public Servidor() {
		super();
	}

	public Servidor(String ipServidor, int puerto, List<String> usuarios) {
		super();
		this.ipServidor = ipServidor;
		this.puerto = puerto;
		this.usuarios = usuarios;
	}

	public String getIpServidor() {
		return ipServidor;
	}

	public void setIpServidor(String ipServidor) {
		this.ipServidor = ipServidor;
	}

	public int getPuerto() {
		return puerto;
	}

	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}

	public List<String> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<String> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public String toString() {
		return "Servidor [ipServidor=" + ipServidor + ", puerto=" + puerto + ", usuarios=" + usuarios + "]";
	}
	
	public void encender() {
		Socket socket;
		ServerSocket serverSocket;
		
		final int MAX=5000;
		byte [] mensaje = new byte[MAX];
		
		
		try {
			System.out.println("Servidor: Creando el socket");
			serverSocket = new ServerSocket();
			
			System.out.println("Servidor: Realizando el enlace al socket");
			serverSocket.bind(new InetSocketAddress("localhost", 5555));
			
			while(true) {
				InputStream is;
				OutputStream os;
				
				socket= serverSocket.accept();
				is= socket.getInputStream();
				os= socket.getOutputStream();
				
				is.read(mensaje);
				//System.out.println("Servidor: "+ new String(mensaje));
				System.out.println(new String(mensaje));
				
				//String x = "Mensaje Leido";
				//os.write(x.getBytes());
				
				is.close();
				os.close();
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	static String obtenerIP(String input) {
        // Patrón de expresión regular para encontrar una dirección IP en el formato especificado
        String patron = "(\\d+\\.\\d+\\.\\d+\\.\\d+)@";

        // Crear un objeto Pattern con el patrón
        Pattern pattern = Pattern.compile(patron);

        // Crear un objeto Matcher y buscar coincidencias en la cadena de entrada
        Matcher matcher = pattern.matcher(input);

        // Verificar si se encontró una coincidencia y devolver la IP
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            // En caso de que no se encuentre una IP, puedes manejarlo de acuerdo a tus necesidades
            return "No se encontró una dirección IP";
        }
    }
}
