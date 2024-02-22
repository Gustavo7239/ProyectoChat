package control;

import modelo.Cliente;

public class pruebaInterna {
	
	public static void main(String[] args) {
		String MiIp = "192.168.1.135";
		String IpDestino = "localhost";
		
		Cliente c1 = new Cliente("PC", IpDestino, MiIp, 5555);
		c1.enviarMensaje("Hola mensaje desde pc");
		System.out.println("[Mensaje enviado]");
		
		
		
	}
}
