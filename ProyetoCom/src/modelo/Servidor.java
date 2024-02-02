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
			serverSocket.bind(new InetSocketAddress(ipServidor, puerto));
			
			System.out.println("---------------------------------------------------------");
			
			while(true) {
				InputStream is;
				OutputStream os;
				
				socket= serverSocket.accept();
				is= socket.getInputStream();
				os= socket.getOutputStream();
				
				is.read(mensaje);
				//System.out.println("Servidor: "+ new String(mensaje));
				String strMensaje = new String(mensaje);
				String IpSender = obtenerIP(strMensaje);
				//System.out.println("Ip del sender: "+IpSender);
				
				//System.out.println("Enviando el mensaje a: ");
				for(String ip : usuarios) {
					System.out.println("Ip: "+ip);
					
					if(!ip.equals(IpSender) && !ip.equals(ipServidor)) {
						//System.out.println(ip);
						Cliente c =new Cliente(obtenerNombre(strMensaje), ip, ip,puertoOpuesto());
						c.enviarMensaje(obtenerTexto(strMensaje));
					}
				}
				System.out.println(limpliarCadena(strMensaje));
				
				//String x = "Mensaje Leido";
				//os.write(x.getBytes());
				
				is.close();
				os.close();
				mensaje = new byte[MAX];
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public int puertoOpuesto() {
		int valor = 0;
		
		if(puerto == 5555) valor = 5556;
		else if (puerto == 5556) valor = 5555;
		//else valor = puerto+1;
		
		return valor;
	}
}
