package controller;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import model.enumeracoes.Estado;
import model.excecoes.BoException;
import model.vo.Endereco;
import model.vo.Entidade;
import model.vo.Juridico;
import view.Mascara;

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
    private CheckBox ativoBuscaCk;
    
    @FXML
    private Button gerarBtn;
    
    private Juridico juridico;
    
    @FXML
    void initialize() {
    	super.initialize();
    	ToggleGroup toggleGroup = new ToggleGroup();
    	simAtivoRb.setToggleGroup(toggleGroup);
    	naoAtivoRb.setToggleGroup(toggleGroup);
    	
    	nomeCln.setCellValueFactory( new PropertyValueFactory<>("nome"));
    	cnpjCln.setCellValueFactory( new PropertyValueFactory<>("cnpj"));
    	estadoBox.getItems().setAll(Estado.values());
    	
    	telPreFld.setTextFormatter(Mascara.getMascaraNumericoFlutuante());
    	telNumFld.setTextFormatter(Mascara.getMascaraNumericoInteiro());
    }
    
	@Override
	protected void cadastrarEditar(Boolean cadastrar, String opcao) throws BoException {
		
		Juridico juridico = null;
		
		if(cadastrar) {
			juridico = new Juridico();
			juridico.setEndereco(new Endereco());
		}
		else
			juridico = this.juridico;
		
		juridico.setNome(nomeFld.getText());
		juridico.setCnpj(cnpjFld.getText());
		juridico.setInscricaoEstadual(inscicaoEstadualFld.getText());
		juridico.setEmail(emailFld.getText());
		juridico.setTelefone("("+telPreFld.getText().trim()+") " + telNumFld.getText().trim());
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
		
		fachadaModel.cadastrarEditarClienteJuridico(juridico);
		alerta.imprimirMsg("Sucesso","Cliente jurídico "+opcao+" com sucesso", AlertType.INFORMATION);
		
	}

	@Override
	protected void excluir() throws BoException {
		fachadaModel.excluirClienteJuridico(juridico);
		alerta.imprimirMsg("Sucesso ao exluir","Cliente jurídico exlcuido com sucesso", AlertType.INFORMATION);
	}
    
    @Override
    void popularTabela(String busca) {
		try {
			Juridico juridico = new Juridico();
			if(!ativoBuscaCk.isIndeterminate())
				juridico.setAtivo(ativoBuscaCk.isSelected());
			
			List<Juridico> juridicos = fachadaModel.buscarClientesJuridicos(busca,juridico);
			entidadeTabela.getItems().setAll(juridicos);
			alerta.imprimirMsg("Busca conclu�da","Foram econtrados "+juridicos.size()+" resultado(s)",AlertType.INFORMATION);
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
		if(juridico.getTelefone() != null && juridico.getTelefone().length() >0) {
			telPreFld.setText(juridico.getTelefone().substring(0,juridico.getTelefone().indexOf("-")));
			telNumFld.setText(juridico.getTelefone().substring(juridico.getTelefone().indexOf("-")+1));
		}
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
				Endereco e  = fachadaModel.gerarEndereco(cepFld.getText().trim());
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
