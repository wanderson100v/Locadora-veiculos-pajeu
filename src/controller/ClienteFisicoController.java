package controller;


import java.time.LocalDate;
import java.util.List;

import business.BoFisico;
import business.IBoFisico;
import entidade.Endereco;
import entidade.Entidade;
import entidade.Fisico;
import enumeracoes.Estado;
import enumeracoes.Sexo;
import excecoes.BoException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import view.Mascara;

public class ClienteFisicoController extends CRUDController<Fisico> {

    @FXML
    private TableColumn<Fisico,String> nomeCln;

    @FXML
    private TableColumn<Fisico,String> cpfCln;

    @FXML
    private TableColumn<Fisico,LocalDate> nascimentoCln;

    @FXML
    private TableColumn<Fisico,String> sexoCln;

    @FXML
    private TextField telPreFld;

    @FXML
    private TextField telNumFld;

    @FXML
    private TextField emailFld;

    @FXML
    private DatePicker nascimentoDate;

    @FXML
    private ComboBox<Sexo> sexoBox;

    @FXML
    private TextField cpfFld;

    @FXML
    private TextField nomeFld;

    @FXML
    private RadioButton simAtivoRb;

    @FXML
    private RadioButton naoAtivoRb;

    @FXML
    private TextField codigoFld;

    @FXML
    private TextField numIdentFld;

    @FXML
    private TextField numHFld;

    @FXML
    private DatePicker validadeDate;

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

    private Fisico fisico;
    
    private IBoFisico boFisico = BoFisico.getInstance();
    
    @FXML
    void initialize() {
    	super.initialize();
    	ToggleGroup toggleGroup = new ToggleGroup();
    	simAtivoRb.setToggleGroup(toggleGroup);
    	naoAtivoRb.setToggleGroup(toggleGroup);
    	
    	nomeCln.setCellValueFactory( new PropertyValueFactory<>("nome"));
    	cpfCln.setCellValueFactory( new PropertyValueFactory<>("cpf"));
    	sexoCln.setCellValueFactory(new PropertyValueFactory<>("sexo"));
    	nascimentoCln.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
    	
    	sexoBox.getItems().setAll(Sexo.values());
    	estadoBox.getItems().setAll(Estado.values());
    	
    	telPreFld.setTextFormatter(Mascara.getMascaraNumericoFlutuante());
    	telNumFld.setTextFormatter(Mascara.getMascaraNumericoInteiro());
    }
    
	@Override
	void crudHandle(Button btn) {
		try {	
    		if(btn == cadastrarBtn) {
	    		
    			Fisico fisico = new Fisico();
	    		fisico.setNome(nomeFld.getText());
	    		fisico.setCpf(cpfFld.getText());
	    		fisico.setDataNascimento(nascimentoDate.getValue());
	    		fisico.setSexo(sexoBox.getValue());
	    		fisico.setNumeroHabilitacao(numHFld.getText());
	    		fisico.setIdentificacaoMotorista(numIdentFld.getText());
	    		fisico.setDataValidadeHabilitacao(validadeDate.getValue());
	    		fisico.setEmail(emailFld.getText());
	    		fisico.setTelefone(telNumFld.getText());
	    		if(simAtivoRb.isSelected())
	    			fisico.setAtivo(true);
	    		else
	    			fisico.setAtivo(false);
	    		
	    		Endereco endereco = new Endereco();
	    		endereco.setNumero(numFld.getText());
	    		endereco.setCep(cepFld.getText());
	    		endereco.setRua(ruaFld.getText());
	    		endereco.setBairro(bairroFld.getText());
	    		endereco.setCidade(cidadeFld.getText());
	    		endereco.setEstado(estadoBox.getValue());
	    		
	    		fisico.setEndereco(endereco);
	    		
				boFisico.cadastrarEditar(fisico);
				alerta.imprimirMsg("Sucesso ao cadastrar","Cliente Fisico cadastrado com sucesso", AlertType.INFORMATION);
				
	    	}else if(btn == editarBtn){
	    		
	    		fisico.setNome(nomeFld.getText());
	    		fisico.setCpf(cpfFld.getText());
	    		fisico.setDataNascimento(nascimentoDate.getValue());
	    		fisico.setSexo(sexoBox.getValue());
	    		fisico.setNumeroHabilitacao(numHFld.getText());
	    		fisico.setIdentificacaoMotorista(numIdentFld.getText());
	    		fisico.setDataValidadeHabilitacao(validadeDate.getValue());
	    		fisico.setEmail(emailFld.getText());
	    		fisico.setTelefone(telNumFld.getText());
	    		if(simAtivoRb.isSelected())
	    			fisico.setAtivo(true);
	    		else
	    			fisico.setAtivo(false);
	    		
	    		Endereco endereco = fisico.getEndereco();
	    		endereco.setNumero(numFld.getText());
	    		endereco.setCep(cepFld.getText());
	    		endereco.setRua(ruaFld.getText());
	    		endereco.setBairro(bairroFld.getText());
	    		endereco.setCidade(cidadeFld.getText());
	    		endereco.setEstado(estadoBox.getValue());
	    		
	    		boFisico.cadastrarEditar(fisico);
	    		alerta.imprimirMsg("Sucesso ao editar","Cliente Fisico editado com sucesso", AlertType.INFORMATION);
	    		
	    	}else if(btn == excluirBtn){
	    		
	    		boFisico.excluir(fisico);
	    		alerta.imprimirMsg("Sucesso ao exluir","Cliente Fisico exlcuido com sucesso", AlertType.INFORMATION);
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
		Fisico fisico = new Fisico();
		fisico.setNome(busca);
		fisico.setCpf(busca);
		fisico.setCodigo(busca);
		fisico.setEmail(busca);
		fisico.setTelefone(busca);
		fisico.setIdentificacaoMotorista(busca);
		fisico.setNumeroHabilitacao(busca);
		try {
			List<Fisico> fisicos = BoFisico.getInstance().buscaPorBusca(fisico);
			entidadeTabela.getItems().setAll(fisicos);
			entidadeTabela.refresh();
			alerta.imprimirMsg("Busca concluída","Foram econtrados "+fisicos.size()+" resultado(s)",AlertType.INFORMATION);
		} catch (BoException e) {
			alerta.imprimirMsg("Erro",e.getMessage(),AlertType.ERROR);
		}
	}
	
	@Override
	void popularDescricao(Entidade entidade) {
		this.fisico = (Fisico) entidade;
		
		nomeFld.setText(fisico.getNome());
		cpfFld.setText(fisico.getCpf());
		nascimentoDate.setValue(fisico.getDataNascimento());
		sexoBox.setValue(fisico.getSexo());
		numHFld.setText(fisico.getNumeroHabilitacao());
		numIdentFld.setText(fisico.getIdentificacaoMotorista());
		validadeDate.setValue(fisico.getDataValidadeHabilitacao());
		emailFld.setText(fisico.getEmail());
		telNumFld.setText(fisico.getTelefone());
		codigoFld.setText(fisico.getCodigo());
		if(fisico.isAtivo())
			simAtivoRb.setSelected(true);
		else
			naoAtivoRb.setSelected(true);
		
		numFld.setText(fisico.getEndereco().getNumero());
		cepFld.setText(fisico.getEndereco().getCep());
		ruaFld.setText(fisico.getEndereco().getRua());
		bairroFld.setText(fisico.getEndereco().getBairro());
		cidadeFld.setText(fisico.getEndereco().getCidade());
		estadoBox.setValue(fisico.getEndereco().getEstado());
		
	}
	
	@Override
	void limparCampos() {
		fisico = null;
   		nomeFld.setText("");
   		cpfFld.setText("");
		nascimentoDate.setValue(null);
		sexoBox.setValue(null);
		numHFld.setText("");
		numIdentFld.setText("");
		validadeDate.setValue(null);
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
