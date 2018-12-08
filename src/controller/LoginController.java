package controller;

import java.util.Observable;

import app.App;
import business.BoFuncionario;
import business.IBoFuncionario;
import excecoes.BoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sql.ConnectionFactory;
import view.Alerta;

public class LoginController extends Observable{

	
	public static LoginController loginController;
    
	@FXML
	private TextField loginField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private Button loginBtn;
    
    IBoFuncionario boFuncionario = BoFuncionario.getInstance();
    
    private Alerta alerta = Alerta.getInstance();
    
    
    @FXML
    void initialize() {
    	loginController = this;
    }
    
    @FXML
    void eventHandler(ActionEvent event) {
    	if(event.getSource() == loginBtn) {
    		try {
    			String login = loginField.getText();
    			String senha = senhaField.getText();
	    		ConnectionFactory.setUser(login,senha);
	    		setChanged();
				notifyObservers(boFuncionario.requisitarGralDeAcesso());
				App.iniTelaMenu();
    		} catch (BoException e) {
    			alerta.imprimirMsg("Alerta", "Usuario não autorizado",AlertType.WARNING);
    		}
    		
    	}
    }
    
    

}
