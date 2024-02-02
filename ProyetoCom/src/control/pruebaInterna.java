package control;

import modelo.Cliente;

public class pruebaInterna {
	
	public static void main(String[] args) {
		String MiIp = "192.168.1.137";
		String IpDestino = "192.168.1.137";
		
		Cliente c1 = new Cliente("PC", IpDestino, MiIp, 5555);
		c1.enviarMensaje("Hola mensaje desde pc");
	}
}
