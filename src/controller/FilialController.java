package controller;

import java.util.List;

import model.enumeracoes.Estado;
import model.excecoes.BoException;
import model.vo.Endereco;
import model.vo.Entidade;
import model.vo.Filial;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class FilialController extends CRUDController<Filial> {


    @FXML
    private TableColumn<Filial, String> nomeCln;

    @FXML
    private TextField nomeFld;

    @FXML
    private RadioButton simAtivoRb;

    @FXML
    private RadioButton naoAtivoRb;

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
    
    private Filial filial;
    
    @FXML
    void initialize() {
    	super.initialize();
    	ToggleGroup toggleGroup = new ToggleGroup();
    	simAtivoRb.setToggleGroup(toggleGroup);
    	naoAtivoRb.setToggleGroup(toggleGroup);
    	nomeCln.setCellValueFactory( new PropertyValueFactory<>("nome"));
    	estadoBox.getItems().setAll(Estado.values());
    }
    
	@Override
	void crudHandle(Button btn) {
		try {	
    		if(btn == cadastrarBtn) {
	    		
    			Filial filial = new Filial();
	    		filial.setNome(nomeFld.getText());
	    		if(simAtivoRb.isSelected())
	    			filial.setAtivo(true);
	    		else
	    			filial.setAtivo(false);
	    		
	    		Endereco endereco = new Endereco();
	    		endereco.setNumero(numFld.getText());
	    		endereco.setCep(cepFld.getText());
	    		endereco.setRua(ruaFld.getText());
	    		endereco.setBairro(bairroFld.getText());
	    		endereco.setCidade(cidadeFld.getText());
	    		endereco.setEstado(estadoBox.getValue());
	    		
	    		filial.setEndereco(endereco);
	    		
				fachadaModel.cadastrarEditarFilial(filial);
				alerta.imprimirMsg("Sucesso ao cadastrar","Filial cadastrada com sucesso", AlertType.INFORMATION);
				
	    	}else if(btn == editarBtn){
	    		
	    		filial.setNome(nomeFld.getText());
	    		if(simAtivoRb.isSelected())
	    			filial.setAtivo(true);
	    		else
	    			filial.setAtivo(false);
	    		
	    		Endereco endereco = filial.getEndereco();
	    		endereco.setNumero(numFld.getText());
	    		endereco.setCep(cepFld.getText());
	    		endereco.setRua(ruaFld.getText());
	    		endereco.setBairro(bairroFld.getText());
	    		endereco.setCidade(cidadeFld.getText());
	    		endereco.setEstado(estadoBox.getValue());
	    		
	    		fachadaModel.cadastrarEditarFilial(filial);
	    		alerta.imprimirMsg("Sucesso ao editar","Filial editado com sucesso", AlertType.INFORMATION);
	    		
	    	}else if(btn == excluirBtn){
	    		
	    		fachadaModel.excluirFilial(filial);
	    		alerta.imprimirMsg("Sucesso ao exluir","Filial exlcuido com sucesso", AlertType.INFORMATION);
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
		try {
			List<Filial> filiais = fachadaModel.buscarFiliais(busca);
			entidadeTabela.getItems().setAll(filiais);
			entidadeTabela.refresh();
			alerta.imprimirMsg("Busca concluída","Foram econtrados "+filiais.size()+" resultado(s)",AlertType.INFORMATION);
		} catch (BoException e) {
			alerta.imprimirMsg("Erro",e.getMessage(),AlertType.ERROR);
		}
	}

	@Override
	void popularDescricao(Entidade entidade) {
		this.filial = (Filial) entidade;
		
		nomeFld.setText(filial.getNome());
		if(filial.isAtivo())
			simAtivoRb.setSelected(true);
		else
			naoAtivoRb.setSelected(true);
		
		numFld.setText(filial.getEndereco().getNumero());
		cepFld.setText(filial.getEndereco().getCep());
		ruaFld.setText(filial.getEndereco().getRua());
		bairroFld.setText(filial.getEndereco().getBairro());
		cidadeFld.setText(filial.getEndereco().getCidade());
		estadoBox.setValue(filial.getEndereco().getEstado());
		
	}

	@Override
	void limparCampos() {
		filial = null;
		nomeFld.setText("");
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
