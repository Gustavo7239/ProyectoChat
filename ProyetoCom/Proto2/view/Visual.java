package view;

import model.Message;

public class Visual {
	public void printMsg(Message m) {
		System.out.println("["+m.getTransmitterName()+"]: "+m.getText());
	}
}
