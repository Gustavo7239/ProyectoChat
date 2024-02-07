package modelo;

public class Mensaje {
	private String ip;
	private String usuario;
	private String mensaje;
	
	public Mensaje() {
		super();
	}

	public Mensaje(String ip, String usuario, String mensaje) {
		super();
		this.ip = ip;
		this.usuario = usuario;
		this.mensaje = mensaje;
	}

	
	
	public String getIp() {
		return ip;
	}

	
	public void setIp(String ip) {
		this.ip = ip;
	}
	

	public String getUsuario() {
		return usuario;
	}
	

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	
	public String getMensaje() {
		return mensaje;
	}

	
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String msgEnviar() {
		return "[" + usuario + "]: " + mensaje;
	}
	
	public String msgRecibir() {
		return ip+"@["+usuario+"]: " + mensaje;
	}
	
	public void modMensaje(Mensaje mensaje) {
		this.ip = mensaje.ip;
		this.usuario = mensaje.usuario;
		this.mensaje = mensaje.mensaje;
	}
}
