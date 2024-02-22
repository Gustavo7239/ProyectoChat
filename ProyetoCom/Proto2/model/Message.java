package model;

import java.io.Serializable;

public class Message implements Serializable{
	
	private String transmitterName;
	private String text;
	private String ip;
	
	public Message() {
		super();
	}
	
	public Message(String transmitterName, String text, String ip) {
		super();
		this.transmitterName = transmitterName;
		this.text = text;
		this.ip = ip;
	}

	public String getTransmitterName() {
		return transmitterName;
	}

	public void setTransmitterName(String transmitterName) {
		this.transmitterName = transmitterName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "Message [transmitterName=" + transmitterName + ", text=" + text + ", ip=" + ip + "]";
	}
		
}
