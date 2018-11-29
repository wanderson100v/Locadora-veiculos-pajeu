package controller;

import business.BoJuridico;
import business.IBoJuridico;
import business.IBussines;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import view.Alerta;

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
    		if(btn == cadastrarBtn) {
	    		
    			Juridico juridico = new Juridico();
	    		juridico.setNome(nomeFld.getText());
	    		juridico.setCnpj(cnpjFld.getText());
	    		juridico.setInscricaoEstadual(inscicaoEstadualFld.getText());
	    		juridico.setEmail(emailFld.getText());
	    		juridico.setTelefone(telNumFld.getText());
	    		if(simAtivoRb.isSelected())
	    			juridico.setAtivo(true);
	    		else
	    			juridico.setAtivo(false);
	    		
	    		Endereco endereco = new Endereco();
	    		endereco.setNumero(numFld.getText());
	    		endereco.setCep(cepFld.getText());
	    		endereco.setRua(ruaFld.getText());
	    		endereco.setBairro(bairroFld.getText());
	    		endereco.setCidade(cidadeFld.getText());
	    		endereco.setEstado(estadoBox.getValue());
	    		
	    		juridico.setEndereco(endereco);
	    		
				boJuridico.cadastrarEditar(juridico);
				alerta.imprimirMsg("Sucesso ao cadastrar","Cliente Jurídico cadastrado com sucesso", AlertType.INFORMATION);
				
	    	}else if(btn == editarBtn){
	    		
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
	    		alerta.imprimirMsg("Sucesso ao editar","Cliente Jurídico editado com sucesso", AlertType.INFORMATION);
	    		
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
    
    void popularTabela(Entidade entidade) {
		this.juridico = (Juridico) entidade;
		
		nomeFld.setText(juridico.getNome());
		cnpjFld.setText(juridico.getCnpj());
		inscicaoEstadualFld.setText(juridico.getInscricaoEstadual());
		emailFld.setText(juridico.getEmail());
		telNumFld.setText(juridico.getTelefone());
		codigoFld.setText(juridico.getCodigo());
		if(juridico.isAtivo())
			simAtivoRb.setSelected(true);
		else
			naoAtivoRb.setSelected(true);
		
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
}
