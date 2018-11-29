package view;


import javafx.scene.control.Alert;

public class Alerta extends Alert {
	private static Alerta instance;
	
	private Alerta() {
		super(AlertType.NONE);
	}
	
	public static Alerta getInstance() {
		if(instance == null)
			instance = new Alerta();
		return instance;
	}
	
	public void imprimirMsg(String title, String contentText, AlertType alertType) {
		
		setTitle(title);
		setContentText(contentText);
		setAlertType(alertType);
		showAndWait();
		
	}
	
}
