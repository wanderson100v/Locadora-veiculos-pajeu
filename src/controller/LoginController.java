package controller;

import app.App;
import business.BoFuncionario;
import business.IBoFuncionario;
import enumeracoes.Cargo;
import excecoes.BoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sql.ConnectionFactory;
import view.Alerta;

public class LoginController{

	
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
    }
    
    @FXML
    void eventHandler(ActionEvent event) {
    	if(event.getSource() == loginBtn) {
    		try {
    			String login = loginField.getText();
    			String senha = senhaField.getText();
	    		ConnectionFactory.setUser(login,senha);
	    		Cargo cargo = boFuncionario.requisitarGralDeAcesso(login);
	    		FuncionarioObservavel.getIntance().avisarOuvintes(cargo);
	    		boFuncionario.utilizarGralAcesso(cargo);
				App.iniTelaMenu();
    		} catch (BoException e) {
    			alerta.imprimirMsg("Alerta", "Usuario não autorizado",AlertType.WARNING);
    		}
    		
    	}
    }
    
    

}
