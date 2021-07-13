package controller;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import model.excecoes.BoException;
import model.vo.Acessorio;
import model.vo.Entidade;

public class AcessorioController extends CRUDController<Acessorio>{

    @FXML
    private TableColumn<Acessorio, String> nomeCln;

    @FXML
    private TableColumn<Acessorio, Float> valorCln;

    @FXML
    private TableColumn<Acessorio, Boolean> depreciadoCln;

    @FXML
    private RadioButton simDepreciadoRb;

    @FXML
    private RadioButton naoDepreciadoRb;

    @FXML
    private TextField valorFld;
    
    @FXML
    private TextField nomeFld;

    private Acessorio acessorio;
    
    @FXML
    void initialize() {
    	super.initialize();
    	ToggleGroup toggleGroup = new ToggleGroup();
    	simDepreciadoRb.setToggleGroup(toggleGroup);
    	naoDepreciadoRb.setToggleGroup(toggleGroup);
    	
    	nomeCln.setCellValueFactory( new PropertyValueFactory<>("nome"));
    	valorCln.setCellValueFactory( new PropertyValueFactory<>("valor"));
    	depreciadoCln.setCellValueFactory( new PropertyValueFactory<>("depreciado"));
    }

	@Override
	protected void cadastrarEditar(Boolean cadastrar, String opcao) throws BoException {
		Acessorio acessorio = (cadastrar)? new Acessorio() : this.acessorio;
		
		acessorio.setNome(nomeFld.getText().trim());
		acessorio.setValor(Float.parseFloat(valorFld.getText()));
		acessorio.setDepreciado(simDepreciadoRb.isSelected());
		
		fachadaModel.cadastrarEditarAcessorio(acessorio);
		alerta.imprimirMsg("Sucesso","Acessório "+opcao+" com sucesso", AlertType.INFORMATION);
	}

	@Override
	protected void excluir() throws BoException {
		fachadaModel.excluirAcessorio(this.acessorio);
		alerta.imprimirMsg("Sucesso ao exluir","Acessório exlcuido com sucesso", AlertType.INFORMATION);
	}
	
	@Override
	void popularTabela(String busca) {
		try {
			List<Acessorio> acessorios = fachadaModel.buscarAcessorios(busca);
			entidadeTabela.getItems().setAll(acessorios);
			alerta.imprimirMsg("Busca concluída","Foram econtrados "+acessorios.size()+" resultado(s)",AlertType.INFORMATION);
		} catch (BoException e) {
			alerta.imprimirMsg("Erro",e.getMessage(),AlertType.ERROR);
		}
	}
	
	@Override
	void popularDescricao(Entidade entidade) {
		this.acessorio = (Acessorio) entidade;
		nomeFld.setText(acessorio.getNome());
		valorFld.setText(""+acessorio.getValor());
		simDepreciadoRb.setSelected(acessorio.isDepreciado());
	}

	@Override
	void limparCampos() {
		nomeFld.setText("");
		valorFld.setText("");
		simDepreciadoRb.setSelected(false);
		System.gc();
	}

}
