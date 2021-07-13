package controller;


import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import model.enumeracoes.Estado;
import model.enumeracoes.Sexo;
import model.excecoes.BoException;
import model.vo.Endereco;
import model.vo.Entidade;
import model.vo.Fisico;
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
    
    @FXML
    private Button gerarBtn;
    
    @FXML
    private CheckBox motoristaBuscaCk;

    @FXML
    private CheckBox ativoBuscaCk;

    @FXML
    private ComboBox<String> sexoBuscaBox;

    private Fisico fisico;
    
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
    	
    	sexoBuscaBox.getItems().add("Todos");
    	sexoBuscaBox.setValue("Todos");
    	for(Sexo sexo : Sexo.values())
    		sexoBuscaBox.getItems().add(sexo.toString());
    	
    	telPreFld.setTextFormatter(Mascara.getMascaraNumericoFlutuante());
    	telNumFld.setTextFormatter(Mascara.getMascaraNumericoInteiro());
    }

	@Override
	protected void cadastrarEditar(Boolean cadastrar, String opcao) throws BoException {
		
		Fisico fisico = null;
		
		if(cadastrar) {
			fisico = new Fisico();
			fisico.setEndereco(new Endereco());
		}
		else
			fisico = this.fisico;
		
		int pre = Integer.parseInt(telPreFld.getText().trim());
		int num = Integer.parseInt(telNumFld.getText().trim());
		fisico.setNome(nomeFld.getText());
		fisico.setCpf(cpfFld.getText());
		fisico.setDataNascimento(nascimentoDate.getValue());
		fisico.setSexo(sexoBox.getValue());
		fisico.setNumeroHabilitacao(numHFld.getText());
		fisico.setIdentificacaoMotorista(numIdentFld.getText());
		fisico.setDataValidadeHabilitacao(validadeDate.getValue());
		fisico.setEmail(pre+"-"+num);
		
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
		
		fisico.setEndereco(endereco);
		fachadaModel.cadastrarEditarClienteFisico(fisico);
		
		alerta.imprimirMsg("Sucesso","Cliente físico "+opcao+" com sucesso", AlertType.INFORMATION);
		
	}

	@Override
	protected void excluir() throws BoException {
		fachadaModel.excluirClienteFisico(fisico);
		alerta.imprimirMsg("Sucesso ao exluir","Cliente físico exlcuido com sucesso", AlertType.INFORMATION);
	}
	@Override
	void popularTabela(String busca) {
		try {
			if(motoristaBuscaCk.isSelected())
				entidadeTabela.getItems().setAll(fachadaModel.buscarMotoristasValidos(LocalDate.now(),busca));
			else {
				Fisico fisico = new Fisico();
				if(!ativoBuscaCk.isIndeterminate())
					fisico.setAtivo(ativoBuscaCk.isSelected());
				if(!sexoBuscaBox.getValue().equals("Todos"))
					fisico.setSexo(Sexo.getSexo(sexoBuscaBox.getValue()));
				entidadeTabela.getItems().setAll(fachadaModel.buscarClientesFisicos(busca,fisico));
			}
			alerta.imprimirMsg("Busca concluída","Foram econtrados "+entidadeTabela.getItems().size()+" resultado(s)",AlertType.INFORMATION);
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
		if(fisico.getTelefone() != null && fisico.getTelefone().length() >0) {
			telPreFld.setText(fisico.getTelefone().substring(0,fisico.getTelefone().indexOf("-")));
			telNumFld.setText(fisico.getTelefone().substring(fisico.getTelefone().indexOf("-")+1));
		}
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
