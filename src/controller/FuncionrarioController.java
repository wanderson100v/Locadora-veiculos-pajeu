package controller;

import java.util.List;
import java.util.Observer;

import business.BoFuncionario;
import business.IBoFuncionario;
import entidade.Entidade;
import entidade.Funcionario;
import enumeracoes.Cargo;
import excecoes.BoException;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class FuncionrarioController  extends CRUDController<Funcionario> implements Observer{

    @FXML
    private TableColumn<Funcionario, String> nomeCln;

    @FXML
    private TableColumn<Funcionario, String> cpfCln;

    @FXML
    private TextField cpfFld;

    @FXML
    private TextField nomeFld;

    @FXML
    private RadioButton simAtivoRb;

    @FXML
    private RadioButton naoAtivoRb;

    @FXML
    private TextField filialFld;

    @FXML
    private Button selectFilialBtn;

    @FXML
    private ComboBox<Cargo> cargoBox;

    @FXML
    private PasswordField senhaFld;

    @FXML
    private PasswordField conSenhaFld;

    @FXML
    private Button resertarSenhaBtn;

    @FXML
    private GridPane dadosAcessoPane;


    private Funcionario funcionario;
    
    private IBoFuncionario boFuncionario = BoFuncionario.getInstance();
    
    @FXML
    void initialize() {
    	super.initialize();
    	LoginController.loginController.addObserver(this);
    	
    	ToggleGroup toggleGroup = new ToggleGroup();
    	simAtivoRb.setToggleGroup(toggleGroup);
    	naoAtivoRb.setToggleGroup(toggleGroup);
    	
    	nomeCln.setCellValueFactory( new PropertyValueFactory<>("nome"));
    	cpfCln.setCellValueFactory( new PropertyValueFactory<>("cpf"));
    	
    	
    	cargoBox.getItems().addAll(Cargo.values());
    	
    	resertarSenhaBtn.setVisible(false);
    }

	@Override
	void crudHandle(Button btn) {
		try {	
    		if(btn == cadastrarBtn) {
    			Funcionario funcionario = new Funcionario();
				String senha = senhaFld.getText().trim();
				
				if(senha.equals(conSenhaFld.getText())){
	    			funcionario.setAtivo(simAtivoRb.isSelected());
	    			funcionario.setNome(nomeFld.getText());
	    			funcionario.setCpf(cpfFld.getText());
					boFuncionario.cadastrar(funcionario,senha,cargoBox.getValue());
					alerta.imprimirMsg("Sucesso ao cadastrar","Funcionario cadastrado com sucesso", AlertType.INFORMATION);
				}else {
					cadastrarBtn.setDisable(false);
					alerta.imprimirMsg("Alerta", "As senhas informadas não correspondem", AlertType.WARNING);
				}
    		}else if(btn == editarBtn) {
				Funcionario funcionario = this.funcionario;
    			String oldLogin = boFuncionario.gerarLogin(funcionario)
;				funcionario.setAtivo(simAtivoRb.isSelected());
    			funcionario.setNome(nomeFld.getText());
    			funcionario.setCpf(cpfFld.getText());
				boFuncionario.editar(funcionario, oldLogin);
				alerta.imprimirMsg("Sucesso ao editado","Funcionario editado com sucesso", AlertType.INFORMATION);
	    	}else if(btn == excluirBtn){
	    		
	    		boFuncionario.excluir(this.funcionario);
	    		alerta.imprimirMsg("Sucesso ao exluir","Automóvel exlcuido com sucesso", 
	    				AlertType.INFORMATION);
	    		limparCampos();
	    	
	    	}else if(btn == limparBtn)
	    		limparCampos();
	    		
    	} catch (BoException e) {
			alerta.imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	void popularTabela(String busca) {
		try {
			List<Funcionario> funcionarios = boFuncionario.buscarAll();
			entidadeTabela.getItems().clear();
			entidadeTabela.getItems().setAll(funcionarios);
			alerta.imprimirMsg("Busca concluída","Foram econtrados "+funcionarios.size()+" resultado(s)",AlertType.INFORMATION);
		} catch (BoException e) {
			alerta.imprimirMsg("Erro",e.getMessage(),AlertType.ERROR);
		}
	}

	@Override
	void popularDescricao(Entidade entidade) {
		this.funcionario = (Funcionario) entidade;
		dadosAcessoPane.setVisible(false);
		resertarSenhaBtn.setVisible(true);
		simAtivoRb.setSelected(funcionario.isAtivo());
		cpfFld.setText(funcionario.getCpf());
		nomeFld.setText(funcionario.getNome());
		senhaFld.clear();
		conSenhaFld.clear();
	}

	@Override
	void limparCampos() {
		this.funcionario = null;
		dadosAcessoPane.setVisible(true);
		resertarSenhaBtn.setVisible(false);
		simAtivoRb.setSelected(true);
		cpfFld.clear();
		nomeFld.clear();
		senhaFld.clear();
		conSenhaFld.clear();
		
		System.gc();
	}
	
	@FXML
    void actionHandle(ActionEvent event) {
		try {
			if(event.getSource() == selectFilialBtn) {
				
			}else if(event.getSource() == resertarSenhaBtn) {
				boFuncionario.resetarSenha(funcionario);
				alerta.imprimirMsg("Sucesso ao editar", "Senha editada com sucesso",AlertType.INFORMATION);
			}
		} catch (BoException e) {
			alerta.imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void update(java.util.Observable o, Object arg) {
		if(arg instanceof Cargo) {
			Cargo cargo = (Cargo) arg;
			if(cargo == Cargo.SU) {
				cargoBox.getItems().addAll(Cargo.values());
			}else if(cargo == Cargo.ADM) {
				cargoBox.getItems().addAll(Cargo.ADM,Cargo.AT);
			}
		}
	}
}
