package controller;


import app.App;
import business.BoFuncionario;
import business.IBoFuncionario;
import dao.DaoConfiguracaoDefault;
import entidade.ConfiguracoesDefault;
import enumeracoes.Cargo;
import excecoes.BoException;
import excecoes.DaoException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
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
    private FlowPane  carregandoPane;
    
    @FXML
    private CheckBox  lembrarLoginCk;
    
    IBoFuncionario boFuncionario = BoFuncionario.getInstance();
    
    private Alerta alerta = Alerta.getInstance();
    
    @FXML
    void initialize() {
    	try {
    		carregandoPane.setVisible(false);
    		ConfiguracoesDefault  c = DaoConfiguracaoDefault.getInstance().carregar();
			if(c!= null) {
				String login = c.getUserNameDefault();
				if(login!= null) {
					loginField.setText(login);
					lembrarLoginCk.setSelected(true);
					senhaField.requestFocus();
				}
			}
    	} catch (Exception e) {
    		e.printStackTrace();
		}
    }
    
    @FXML
    void eventHandler(ActionEvent event) {
		carregandoPane.setVisible(true);
		final String login = loginField.getText();
		final String senha = senhaField.getText();
		new Thread(()-> {
    		try {
				ConnectionFactory.setUser(login,senha);
				Cargo cargo = boFuncionario.requisitarGralDeAcesso(login);
	    		boFuncionario.utilizarGralAcesso(cargo);
	    		
	    		Platform.runLater(()-> 
	    		{
	    			ObservadorEntidade.getIntance().avisarOuvintes(cargo);
	    			if(!lembrarLoginCk.isSelected()) {
	        			loginField.clear();
	        		}else
	        			ObservadorEntidade.getIntance().getConfiguracoesDefault().setUserNameDefault(login);
	        		senhaField.clear();
	        		try {
						DaoConfiguracaoDefault.getInstance().salvar(ObservadorEntidade.getIntance().getConfiguracoesDefault());
						App.iniTelaMenu();
	        		} catch (DaoException e) {
	        			alerta.imprimirMsg("Alerta", e.getMessage(),AlertType.WARNING);
						e.printStackTrace();
					}
	        		carregandoPane.setVisible(false);
	    		});
    		} catch (BoException e) {
    			Platform.runLater(()->
    			{
	    			alerta.imprimirMsg("Alerta", e.getMessage(),AlertType.WARNING);
	    			carregandoPane.setVisible(false);
    			});
    			e.printStackTrace();
    		}
		}).start();
    }
    
    

}
