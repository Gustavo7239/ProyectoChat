package control;

import java.util.ArrayList;

import modelo.Cliente;
import modelo.Servidor;

public class Principal {
	public static void main(String[] args) {
		String ServidorIp = "192.168.1.141";
		String MiIp = "192.168.1.137";
		
		ArrayList<String> ipUsuarios = new ArrayList<String>();
		ipUsuarios.add(ServidorIp);
		//ipUsuarios.add(MiIp);
		//ipUsuarios.add("10.10.0.3");
		
		//Cliente c1 = new Cliente("PC","192.168.1.137","10.10.0.1",5555);
		
		Servidor s1 = new Servidor(MiIp,5555,ipUsuarios);
		
		s1.encender();
		//c1.enviarMensaje("Hola este es un mensaje de prueba.");
	}
}
