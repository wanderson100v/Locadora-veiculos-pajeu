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

    private Funcionario funcionarioAuxCRUD;
    
    private Funcionario funcionarioLogado;
    
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
    protected void cadastrarEditar(Boolean cadastrar,  String opcao) throws BoException {
    	Funcionario funcionario = null;
    	if(cadastrar) {
    		funcionario = new Funcionario();
    	}else {
    		funcionario = this.funcionarioAuxCRUD;
    	}
    	
    	if(this.filial!= null)
    		funcionario.setFilial(this.filial);
    	
    	funcionario.setAtivo(simAtivoRb.isSelected());
		funcionario.setNome(nomeFld.getText().trim());
		funcionario.setCpf(cpfFld.getText().trim());
    	
    	if(cadastrar)
    		cadastrarFuncionario(funcionario);
    	else
    		editarFuncionario(funcionario);
    }
    		
    private void cadastrarFuncionario(Funcionario funcionario) throws BoException {
		String senha = senhaFld.getText().trim();
		String confirmacaoSenha = conSenhaFld.getText().trim();
		fachadaModel.cadastrarFuncionario(funcionario,senha, confirmacaoSenha, cargoBox.getValue());
		alerta.imprimirMsg("Sucesso ao cadastrar","Funcionario cadastrado com sucesso", AlertType.INFORMATION);
		entidadeTabela.getItems().add(funcionario);
    }
    
    private void editarFuncionario(Funcionario novofuncionario) throws BoException{
		fachadaModel.editarFuncionario(this.funcionarioAuxCRUD, novofuncionario);
		alerta.imprimirMsg("Sucesso ao editado","Funcionário editado com sucesso", AlertType.INFORMATION);
		entidadeTabela.getItems().remove(funcionarioAuxCRUD);
		entidadeTabela.getItems().add(novofuncionario);
		funcionarioAuxCRUD = novofuncionario;
    }
    
    protected void excluir() throws BoException{
		fachadaModel.excluirFuncionario(this.funcionarioAuxCRUD);
		alerta.imprimirMsg("Sucesso ao exluir","Funcionário exlcuido com sucesso", AlertType.INFORMATION);
		entidadeTabela.getItems().remove(funcionarioAuxCRUD);
		limparCampos();
    }

	@Override
	void popularTabela(String busca) {
		try {
			List<Funcionario> funcionarios = fachadaModel.buscarFuncionarios(busca);
			Funcionario instanciaRemover = null;
			for(Funcionario f : funcionarios)
				if(this.funcionarioLogado != null && f.getCpf().equals(this.funcionarioLogado.getCpf()))
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
		this.funcionarioAuxCRUD = (Funcionario) entidade;
		dadosAcessoPane.setVisible(false);
		resertarSenhaBtn.setVisible(true);
		simAtivoRb.setSelected(funcionarioAuxCRUD.isAtivo());
		cpfFld.setText(funcionarioAuxCRUD.getCpf());
		nomeFld.setText(funcionarioAuxCRUD.getNome());
		senhaFld.clear();
		conSenhaFld.clear();
		nivelAcessoBox.setVisible(true);
		alterarNivelAcessoBtn.setVisible(true);
		if(funcionarioAuxCRUD.getFilial()!= null)
			filialFld.setText(funcionarioAuxCRUD.getFilial().toString());
		try {
			nivelAcessoBox.setValue(fachadaModel.requisitarGralDeAcesso(funcionarioAuxCRUD));
		}catch (Exception e) {
			alerta.imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	void limparCampos() {
		this.funcionarioAuxCRUD = null;
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
				fachadaModel.resetarSenha(funcionarioAuxCRUD);
				alerta.imprimirMsg("Sucesso ao editar", "Senha editada com sucesso",AlertType.INFORMATION);
			}else if(event.getSource() == alterarNivelAcessoBtn) {
				fachadaModel.alterarGralAcesso(funcionarioAuxCRUD, fachadaModel.requisitarGralDeAcesso(funcionarioAuxCRUD),nivelAcessoBox.getValue());
				alerta.imprimirMsg("Sucesso ao editar", "Privilegios de acesso ao sistema foram alterados com sucesso",AlertType.INFORMATION);
			}
		} catch (BoException e) {
			alerta.imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void atualizar(Funcionario funcionario, Cargo cargo) {
		try {
			super.atualizar(funcionario, cargo);
			cargoBox.getItems().clear();
			if(cargo == Cargo.SU) {
				cargoBox.getItems().addAll(Cargo.values());
			}else if(cargo == Cargo.ADM) { 
				cargoBox.getItems().addAll(Cargo.ADM,Cargo.AT);
			}
			if(funcionario != null) 
				this.funcionarioLogado = funcionario;
			cargoBox.getSelectionModel().select(0);
		}catch(Exception e) {
			alerta.imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		}
	}
}
