package controller;


import model.excecoes.BoException;
import model.vo.ConfiguracoesDefault;
import model.vo.Funcionario;
import app.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import model.FuncionarioObservavel;
import model.dao.DaoConfiguracaoDefault;
import model.dao.sql.ConnectionFactory;
import model.enumeracoes.Cargo;

public class LoginController extends ControllerAdapter{

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
				Cargo cargo = fachadaModel.requisitarGralDeAcesso(login);
				fachadaModel.utilizarGralAcesso(cargo);
	    		Platform.runLater(()-> 
	    		{
	    			
					try {
						Funcionario funcionario = fachadaModel.buscaPorLogin(login);
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
