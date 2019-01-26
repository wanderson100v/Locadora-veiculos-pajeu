package controller;

import app.App;
import business.BoFuncionario;
import business.IBoFuncionario;
import dao.DaoConfiguracaoDefault;
import entidade.ConfiguracoesDefault;
import enumeracoes.Cargo;
import excecoes.BoException;
import excecoes.DaoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    
    @FXML
    private CheckBox  lembrarLoginCk;
    
    IBoFuncionario boFuncionario = BoFuncionario.getInstance();
    
    private Alerta alerta = Alerta.getInstance();
    
    
    @FXML
    void initialize() {
    	try {
			ConfiguracoesDefault  c = DaoConfiguracaoDefault.getInstance().carregar();
			String login = c.getUserNameDefault();
			if(login!= null) {
				loginField.setText(login);
				lembrarLoginCk.setSelected(true);
				senhaField.requestFocus();
			}
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void eventHandler(ActionEvent event) {
		try {
			String login = loginField.getText();
			String senha = senhaField.getText();
    		ConnectionFactory.setUser(login,senha);
    		Cargo cargo = boFuncionario.requisitarGralDeAcesso(login);
    		ObservadorEntidade.getIntance().avisarOuvintes(cargo);
    		boFuncionario.utilizarGralAcesso(cargo);
    		if(!lembrarLoginCk.isSelected()) {
    			login = null;
    			loginField.clear();
    		}
    		senhaField.clear();
			ObservadorEntidade.getIntance().getConfiguracoesDefault().setUserNameDefault(login);
    		DaoConfiguracaoDefault.getInstance().salvar(ObservadorEntidade.getIntance().getConfiguracoesDefault());
    		App.iniTelaMenu();
		} catch (BoException | DaoException e) {
			alerta.imprimirMsg("Alerta", e.getMessage(),AlertType.WARNING);
		}
    		
    }
    
    

}
