package control;

import java.util.ArrayList;

import modelo.Cliente;
import modelo.Mensaje;
import modelo.Recibir;
import modelo.Servidor;

public class Principal {
	public static void main(String[] args) {
		String ServidorIp = "192.168.1.130";
		String MiIp = "192.168.1.135";
		/*
		ArrayList<String> ipUsuarios = new ArrayList<String>();
		ipUsuarios.add(ServidorIp);
		ipUsuarios.add(MiIp);
		//ipUsuarios.add("10.10.0.3");
		
		//Cliente c1 = new Cliente("PC","192.168.1.137","10.10.0.1",5555);
		
		Servidor s1 = new Servidor(MiIp,5555,ipUsuarios);
		
		s1.encender();
		//c1.enviarMensaje("Hola este es un mensaje de prueba.");
		
		*/
		Mensaje mensaje = new Mensaje();
		Recibir recibir = new Recibir("192.168.1.135",5555,mensaje);
		
		Thread thread = new Thread(recibir);
        
        thread.start();
       
        while(true) {
        	
        	Mensaje mensajeRecibido = mensaje;

        // Verifica si se ha recibido un mensaje y, en caso afirmativo, imprímelo
        	if (mensajeRecibido != null && mensajeRecibido.getIp() != null) {
        		System.out.println("Mensaje recibido: " +mensajeRecibido.msgEnviar());
        	} else {
        		//System.out.println("No se recibió ningún mensaje");
        	}
        	
        }
 	}
}
