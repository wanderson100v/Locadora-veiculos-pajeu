package controller;

import java.util.List;

import business.BoEndereco;
import business.BoJuridico;
import business.IBoEndereco;
import business.IBoJuridico;
import entidade.Endereco;
import entidade.Entidade;
import entidade.Juridico;
import enumeracoes.Estado;
import excecoes.BoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClienteJuridicoController extends CRUDController<Juridico> {

    @FXML
    private TableColumn<Juridico,String> nomeCln;

    @FXML
    private TableColumn<Juridico,String> cnpjCln;

    @FXML
    private TextField telPreFld;

    @FXML
    private TextField telNumFld;

    @FXML
    private TextField emailFld;

    @FXML
    private TextField inscicaoEstadualFld;

    @FXML
    private TextField cnpjFld;

    @FXML
    private TextField nomeFld;

    @FXML
    private TextField ruaFld;

    @FXML
    private TextField bairroFld;

    @FXML
    private TextField cidadeFld;

    @FXML
    private ComboBox<Estado> estadoBox;

    @FXML
    private TextField numFld;

    @FXML
    private TextField cepFld;

    @FXML
    private RadioButton simAtivoRb;

    @FXML
    private RadioButton naoAtivoRb;

    @FXML
    private TextField codigoFld;
    
    @FXML
    private Button gerarBtn;
    
    private IBoEndereco boEndereco = BoEndereco.getInstance();
    
    
    private Juridico juridico;
    
    private IBoJuridico boJuridico = BoJuridico.getInstance();
    
    @FXML
    void initialize() {
    	super.initialize();
    	ToggleGroup toggleGroup = new ToggleGroup();
    	simAtivoRb.setToggleGroup(toggleGroup);
    	naoAtivoRb.setToggleGroup(toggleGroup);
    	
    	nomeCln.setCellValueFactory( new PropertyValueFactory<>("nome"));
    	cnpjCln.setCellValueFactory( new PropertyValueFactory<>("cnpj"));
    	estadoBox.getItems().setAll(Estado.values());
    }
    
    
    void crudHandle(Button btn) {
    	
    	try {	
    		if(btn == cadastrarBtn || btn == editarBtn) {
    			Juridico juridico = null;
    			if(btn == cadastrarBtn) {
    				juridico = new Juridico();
    				juridico.setEndereco(new Endereco());
    			}
    			else
    				juridico = this.juridico;
    			
	    		juridico.setNome(nomeFld.getText());
	    		juridico.setCnpj(cnpjFld.getText());
	    		juridico.setInscricaoEstadual(inscicaoEstadualFld.getText());
	    		juridico.setEmail(emailFld.getText());
	    		juridico.setTelefone(telNumFld.getText());
	    		if(simAtivoRb.isSelected())
	    			juridico.setAtivo(true);
	    		else
	    			juridico.setAtivo(false);
	    		
	    		Endereco endereco = juridico.getEndereco();
	    		endereco.setNumero(numFld.getText());
	    		endereco.setCep(cepFld.getText());
	    		endereco.setRua(ruaFld.getText());
	    		endereco.setBairro(bairroFld.getText());
	    		endereco.setCidade(cidadeFld.getText());
	    		endereco.setEstado(estadoBox.getValue());
	    		
				boJuridico.cadastrarEditar(juridico);
				alerta.imprimirMsg("Sucesso ao cadastrar","Cliente Jurídico "+((juridico.equals(this.juridico))? "editado": "cadastrado") +" com sucesso", AlertType.INFORMATION);
				
	    	}else if(btn == excluirBtn){
	    		
	    		boJuridico.excluir(juridico);
	    		alerta.imprimirMsg("Sucesso ao exluir","Cliente Jurídico exlcuido com sucesso", AlertType.INFORMATION);
	    		limparCampos();
	    	
	    	}else if(btn == limparBtn){
	    		
	    		limparCampos();
	    		
	    	}
    	} catch (BoException e) {
			alerta.imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
    }
    
    @Override
    void popularTabela(String busca) {
    	Juridico juridico = new Juridico();
		juridico.setNome(busca);
		juridico.setCodigo(busca);
		juridico.setCnpj(busca);
		juridico.setInscricaoEstadual(busca);
		juridico.setEmail(busca);
		juridico.setTelefone(busca);
		try {
			List<Juridico> juridicos = boJuridico.buscaPorBusca(juridico);
			entidadeTabela.getItems().clear();
			entidadeTabela.getItems().setAll(juridicos);
			alerta.imprimirMsg("Busca concluída","Foram econtrados "+juridicos.size()+" resultado(s)",AlertType.INFORMATION);
		} catch (BoException e) {
			alerta.imprimirMsg("Erro",e.getMessage(),AlertType.ERROR);
		}
    }
    
    void popularDescricao(Entidade entidade) {
		this.juridico = (Juridico) entidade;
		
		nomeFld.setText(juridico.getNome());
		cnpjFld.setText(juridico.getCnpj());
		inscicaoEstadualFld.setText(juridico.getInscricaoEstadual());
		emailFld.setText(juridico.getEmail());
		telNumFld.setText(juridico.getTelefone());
		codigoFld.setText(juridico.getCodigo());
		simAtivoRb.setSelected(juridico.isAtivo());
		numFld.setText(juridico.getEndereco().getNumero());
		cepFld.setText(juridico.getEndereco().getCep());
		ruaFld.setText(juridico.getEndereco().getRua());
		bairroFld.setText(juridico.getEndereco().getBairro());
		cidadeFld.setText(juridico.getEndereco().getCidade());
		estadoBox.setValue(juridico.getEndereco().getEstado());
    }
   
    @Override
   	void limparCampos() {
   		juridico = null;
   		nomeFld.setText("");
   		cnpjFld.setText("");
   		inscicaoEstadualFld.setText("");
   		emailFld.setText("");
   		telNumFld.setText("");
   		codigoFld.setText("");
   		simAtivoRb.setSelected(true);
   		
   		numFld.setText("");
   		cepFld.setText("");
   		ruaFld.setText("");
   		bairroFld.setText("");
   		cidadeFld.setText("");
   		estadoBox.setValue(null);
   		System.gc();
   	}
    
    @FXML
    void actionHandle(ActionEvent event) {
		try {
			if(event.getSource() == gerarBtn) {
				Endereco e  = boEndereco.gerarEndereco(cepFld.getText().trim());
				ruaFld.setText(e.getRua());
				bairroFld.setText(e.getBairro());
				cidadeFld.setText(e.getCidade());
				estadoBox.setValue(e.getEstado());
			}
		}catch(BoException e) {
			alerta.imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
    }
}
