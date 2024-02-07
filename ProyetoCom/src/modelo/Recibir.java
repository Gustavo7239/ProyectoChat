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

public class Recibir implements Runnable{
	private String ipServidor;
	private int puerto;
	private Mensaje mensaje;
	
	public Recibir() {
		super();
	}

	public Recibir(String ipServidor, int puerto, Mensaje mensaje) {
		super();
		this.ipServidor = ipServidor;
		this.puerto = puerto;
		this.mensaje = mensaje;
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

	public Mensaje getMensaje() {
		return mensaje;
	}

	public void setMensaje(Mensaje mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public void run() {
		Socket socket;
		ServerSocket serverSocket;
		
		final int MAX=5000;
		byte [] mensaje = new byte[MAX];
		
		
		try {
			//System.out.println("Servidor: Creando el socket");
			serverSocket = new ServerSocket();
			System.out.print(".");
			
			//System.out.println("Servidor: Realizando el enlace al socket");
			serverSocket.bind(new InetSocketAddress(ipServidor, puerto));
			System.out.println(".");
			
			while(true) {
				InputStream is;

	            socket = serverSocket.accept();
	            is = socket.getInputStream();
	            is.read(mensaje);
	            
	            String strMensaje = new String(mensaje);
	            
	            Mensaje mensajeObj = new Mensaje(
	                    obtenerIP(strMensaje),
	                    obtenerNombre(strMensaje),
	                    obtenerTexto(strMensaje));

	            is.close();
	            mensaje = new byte[MAX];
	            
	            System.out.println(mensajeObj.msgRecibir());
	            
	            this.mensaje.modMensaje(mensajeObj);
	            
	            System.out.print("-");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void encender() {
		
	}
	
	public String obtenerIP(String input) {
        String patron = "(\\d+\\.\\d+\\.\\d+\\.\\d+)@";

        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "No se encontró una dirección IP";
        }
    }
	
	public String limpliarCadena(String input) {
        String patron = ".*?\\@";
        return input.replaceFirst(patron, "");
    }
	
	public String obtenerTexto(String input) {
        String patron = ".*?\\: ";
        return input.replaceFirst(patron, "");
    }
	
	public String obtenerNombre(String input) {
        String patron = "\\[(.*?)\\]";
        
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return ""; 
        }
    }

	public int puertoOpuesto(int puerto) {
		int valor = 0;
		
		if(puerto == 5555) valor = 5556;
		else if (puerto == 5556) valor = 5555;
		//else valor = puerto+1;
		
		return valor;
	}

}
