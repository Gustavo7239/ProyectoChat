package modelo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Cliente {
	private String usuario;
	private String ipServidor;					//10.10.0.81
	private String ipUsuario;
	private int puertoServidorConexion; 		//5556
	final int MAX=5000;
	
	public Cliente() {
		super();
	}

	public Cliente(String usuario, String ipServidor, String ipUsuario, int puertoServidorConexion) {
		super();
		this.usuario = usuario;
		this.ipServidor = ipServidor;
		this.ipUsuario = ipUsuario;
		this.puertoServidorConexion = puertoServidorConexion;
	}

	public String getIpUsuario() {
		return ipUsuario;
	}

	public void setIpUsuario(String ipUsuario) {
		this.ipUsuario = ipUsuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getIpCliente() {
		return ipServidor;
	}

	public void setIpCliente(String ipCliente) {
		this.ipServidor = ipCliente;
	}

	public int getPuerto() {
		return puertoServidorConexion;
	}

	public void setPuerto(int puerto) {
		this.puertoServidorConexion = puerto;
	}

	@Override
	public String toString() {
		return "Cliente [usuario=" + usuario + ", ipCliente=" + ipServidor + ", puerto=" + puertoServidorConexion + "]";
	}
	
	public void enviarMensaje(String mensaje) {
		Socket clienteSocket;
		ServerSocket serverSocket;
		InputStream is;
		OutputStream os;
		
		try {
			clienteSocket = new Socket();
			clienteSocket.connect(new InetSocketAddress(ipServidor, puertoServidorConexion));	

			is= clienteSocket.getInputStream();
			os= clienteSocket.getOutputStream();
			
			mensaje = ipUsuario+"@["+usuario+"]: " + mensaje;
			
			System.out.print(" [<!]");
			os.write(mensaje.getBytes());
			clienteSocket.close();
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}
	
	
}
