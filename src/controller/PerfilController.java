package controller;

import java.net.URL;
import java.util.ResourceBundle;

import model.excecoes.BoException;
import model.vo.Funcionario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.dao.sql.ConnectionFactory;
import model.enumeracoes.Cargo;

public class PerfilController extends Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pesquisaPane;

    @FXML
    private TextField cpfFld;

    @FXML
    private TextField nomeFld;

    @FXML
    private Button alterarPerfilBtn;

    @FXML
    private GridPane dadosAcessoPane;

    @FXML
    private PasswordField senhaFld;

    @FXML
    private PasswordField conSenhaFld;
    
    @FXML
    private PasswordField novaSenhaFld;

    @FXML
    private Button alterarSenhaBtn;
    
    private Funcionario funcionario;

    @FXML
    void actionHandle(ActionEvent event) {
    	if(funcionario!= null) {
    		Button btn = (Button) event.getSource();
	    	try {	
		    	if(btn == alterarSenhaBtn) {
		    		String senha = senhaFld.getText().trim();
		    		if(senha.equals(conSenhaFld.getText())){
						if(ConnectionFactory.getUser()[1].equals(senha)) {
							fachadaModel.editaSenha(funcionario,novaSenhaFld.getText().trim());
							novaSenhaFld.setText("");
							conSenhaFld.setText("");
							senhaFld.setText("");
							alerta.imprimirMsg("Sucesso ao editar","Senha editada com sucesso", AlertType.INFORMATION);
						}else
							alerta.imprimirMsg("Alerta", "Senha incorreta", AlertType.WARNING);
					}else 
						alerta.imprimirMsg("Alerta", "As senhas informadas não correspondem", AlertType.WARNING);
		    	}
	    	} catch (BoException e) {
				alerta.imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
			}
    	}
    }

	public void atualizar(Funcionario funcionario, Cargo cargo) {
		this.funcionario = funcionario;
		if(funcionario != null) {
			nomeFld.setText(funcionario.getNome());
			cpfFld.setText(funcionario.getCpf());
		}
	}
}
