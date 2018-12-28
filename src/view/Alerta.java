package view;


import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

public class Alerta extends Alert {
	private static Alerta instance;
	
	private Alerta() {
		super(AlertType.NONE);
		getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
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
	
	public boolean imprimirMsgConfirmacao(String contentText) {
		
		setTitle("Confirmação");
		setContentText(contentText);
		setAlertType(AlertType.CONFIRMATION);
		Optional<ButtonType> btn = showAndWait();
		if(btn.isPresent())
			if(btn.get() == ButtonType.OK)
				return true;
		return false;
	}
	
	public void imprimir() {
		setAlertType(AlertType.NONE);
		getButtonTypes().add(ButtonType.NEXT);
	}
	public static void main(String[] args) {
		Alerta.getInstance().imprimir();
	}
}
