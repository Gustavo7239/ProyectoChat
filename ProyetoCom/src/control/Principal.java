package control;

import java.util.ArrayList;

import modelo.Cliente;
import modelo.Servidor;

public class Principal {
	public static void main(String[] args) {
		Cliente c1 = new Cliente("PC","192.168.1.141","10.10.0.1",5555);
		
		/*
		ArrayList<String> ipUsuarios = new ArrayList<String>();
		ipUsuarios.add("10.10.0.1");
		ipUsuarios.add("10.10.0.2");
		ipUsuarios.add("10.10.0.3");
		Servidor s1 = new Servidor("localhost",5555,ipUsuarios);
		*/
		c1.enviarMensaje("Hola este es un mensaje de prueba.");
	}
}
