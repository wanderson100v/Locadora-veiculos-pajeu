package controller;

import java.util.List;

import model.enumeracoes.Cargo;
import model.excecoes.BoException;
import model.vo.Entidade;
import model.vo.Filial;
import model.vo.Funcionario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class FuncionarioController  extends CRUDController<Funcionario> {

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
    private ComboBox<Cargo> nivelAcessoBox;

    @FXML
    private PasswordField senhaFld;

    @FXML
    private PasswordField conSenhaFld;
    
    @FXML
    private Button resertarSenhaBtn;
    
    @FXML
    private Button alterarNivelAcessoBtn;

    @FXML
    private GridPane dadosAcessoPane;

    private Funcionario funcionario;
    private Filial filial;
    
    @FXML
    void initialize() {
    	super.initialize();
    	ToggleGroup toggleGroup = new ToggleGroup();
    	simAtivoRb.setToggleGroup(toggleGroup);
    	naoAtivoRb.setToggleGroup(toggleGroup);
    	
    	nomeCln.setCellValueFactory( new PropertyValueFactory<>("nome"));
    	cpfCln.setCellValueFactory( new PropertyValueFactory<>("cpf"));
    	
    	nivelAcessoBox.getItems().addAll(Cargo.values());
    	
    	resertarSenhaBtn.setVisible(false);
		nivelAcessoBox.setVisible(false);
		alterarNivelAcessoBtn.setVisible(false);
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
	    			if(filial!= null) {
	    				funcionario.setFilial(filial);
	    				this.filial = null;
	    			}
					fachadaModel.cadastrarFuncionario(funcionario,senha,cargoBox.getValue());
					alerta.imprimirMsg("Sucesso ao cadastrar","Funcionario cadastrado com sucesso", AlertType.INFORMATION);
				}else {
					cadastrarBtn.setDisable(false);
					alerta.imprimirMsg("Alerta", "As senhas informadas não correspondem", AlertType.WARNING);
				}
    		}else if(btn == editarBtn) {
				Funcionario novofuncionario = this.funcionario;
				novofuncionario.setAtivo(simAtivoRb.isSelected());
				novofuncionario.setNome(nomeFld.getText());
				novofuncionario.setCpf(cpfFld.getText());
    			fachadaModel.editarFuncionario( this.funcionario, novofuncionario);
				alerta.imprimirMsg("Sucesso ao editado","Funcionário editado com sucesso", AlertType.INFORMATION);
	    	}else if(btn == excluirBtn){
	    		fachadaModel.excluirFuncionario(this.funcionario);
	    		alerta.imprimirMsg("Sucesso ao exluir","Funcionário exlcuido com sucesso", 
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
			List<Funcionario> funcionarios = fachadaModel.buscarFuncionarios(busca);
			Funcionario instanciaRemover = null;
			for(Funcionario f : funcionarios)
				if(this.funcionario != null && f.getCpf().equals(this.funcionario.getCpf()))
					instanciaRemover = f;
			funcionarios.remove(instanciaRemover);
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
		nivelAcessoBox.setVisible(true);
		alterarNivelAcessoBtn.setVisible(true);
		if(funcionario.getFilial()!= null)
			filialFld.setText(funcionario.getFilial().toString());
		try {
			nivelAcessoBox.setValue(fachadaModel.requisitarGralDeAcesso(funcionario));
		}catch (Exception e) {
			alerta.imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	void limparCampos() {
		this.funcionario = null;
		this.filial = null;
		filialFld.clear();
		dadosAcessoPane.setVisible(true);
		resertarSenhaBtn.setVisible(false);
		simAtivoRb.setSelected(true);
		cpfFld.clear();
		nomeFld.clear();
		senhaFld.clear();
		conSenhaFld.clear();
		cargoBox.setValue(null);
		
		nivelAcessoBox.setValue(null);
		nivelAcessoBox.setVisible(false);
		alterarNivelAcessoBtn.setVisible(false);
	}
	
	@FXML
    void actionHandle(ActionEvent event) {
		try {
			if(event.getSource() == selectFilialBtn) {
				Filial filial = Util.selecionarFilialEmDialogo();
				if(filial!= null) {
					filialFld.setText(filial.toString());
					this.filial = filial;
				}
			}else if(event.getSource() == resertarSenhaBtn) {
				fachadaModel.resetarSenha(funcionario);
				alerta.imprimirMsg("Sucesso ao editar", "Senha editada com sucesso",AlertType.INFORMATION);
			}else if(event.getSource() == alterarNivelAcessoBtn) {
				fachadaModel.alterarGralAcesso(funcionario, fachadaModel.requisitarGralDeAcesso(funcionario),nivelAcessoBox.getValue());
				alerta.imprimirMsg("Sucesso ao editar", "Privilegios de acaesso alterados com sucesso",AlertType.INFORMATION);
			}
		} catch (BoException e) {
			alerta.imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void atualizar(Funcionario funcionario, Cargo cargo) {
		try {
			cargoBox.getItems().clear();
			if(cargo == Cargo.SU) {
				System.out.println("Adicionado como super user todos os cargos");
				cargoBox.getItems().addAll(Cargo.values());
			}else if(cargo == Cargo.ADM) { 
				cargoBox.getItems().addAll(Cargo.ADM,Cargo.AT);
			}
			if(funcionario != null) 
				this.funcionario = funcionario;
		}catch(Exception e) {
			alerta.imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		}
	}
}
