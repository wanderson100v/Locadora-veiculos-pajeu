package controller;


import app.App;
import model.excecoes.BoException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import mode.business.BoFuncionario;
import mode.business.IBoFuncionario;
import mode.enumeracoes.Cargo;
import model.dao.DaoConfiguracaoDefault;
import model.entidade.ConfiguracoesDefault;
import model.entidade.Funcionario;
import model.sql.ConnectionFactory;
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
	    			
					try {
						Funcionario funcionario = BoFuncionario.getInstance().buscaPorLogin(login);
						if(funcionario != null) {
							FuncionarioObservavel.getIntance().avisarOuvintes(funcionario, cargo);
							if(!lembrarLoginCk.isSelected())
			        			loginField.clear();
			        		senhaField.clear();
							App.iniTelaMenu();
						}else {
							alerta.imprimirMsg("Alerta", "USUÁRIO NÃO EXISTENTE",AlertType.WARNING);
						}
					} catch (BoException e) {
						e.printStackTrace();
					}
	    		});
    		} catch (BoException e) {
    			Platform.runLater(()->
    			{
	    			alerta.imprimirMsg("Alerta", e.getMessage(),AlertType.WARNING);
	    			carregandoPane.setVisible(false);
    			});
    			e.printStackTrace();
    		}
    		carregandoPane.setVisible(false);
		}).start();
    }
    
    

}
