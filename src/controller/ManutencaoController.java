package controller;

import java.time.LocalDateTime;
import java.util.List;

import model.enumeracoes.EstadoManutencao;
import model.enumeracoes.TipoManutencao;
import model.excecoes.BoException;
import model.vo.Entidade;
import model.vo.Manutencao;
import model.vo.Veiculo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class ManutencaoController extends CRUDController<Manutencao>{

    @FXML
    private TableColumn<Manutencao, TipoManutencao> tipoCln;

    @FXML
    private TableColumn<Manutencao, Float> custoCln;

    @FXML
    private TableColumn<Manutencao, EstadoManutencao> estadoCln;

    @FXML
    private TableColumn<Manutencao, LocalDateTime> inicioDateCln;
    
    @FXML
    private TableColumn<Manutencao, Veiculo> veiculoCln;

    @FXML
    private VBox entidadePane;

    @FXML
    private TextField custoFld;

    @FXML
    private ComboBox<TipoManutencao> tipoBox;

    @FXML
    private ComboBox<EstadoManutencao> estadoManutencaoBox;

    @FXML
    private DatePicker inicioDate;

    @FXML
    private ComboBox<Integer> horaBox;
    
    @FXML
    private ComboBox<Integer> horaCustoBox;

    @FXML
    private TextField dadosVeiculoFld;

    @FXML
    private Button selectVeiculoBtn;

    @FXML
    private ButtonBar acoesBar;

    @FXML
    private Button limparBtn;

    @FXML
    private Button cadastrarBtn;

    @FXML
    private Button editarBtn;

    @FXML
    private Button excluirBtn;
  
    @FXML
    private CheckBox veiculoBuscaCk;

    @FXML
    private FlowPane veiculoBuscaPae;

    @FXML
    private TextField veiculoBuscaFFld;

    @FXML
    private Button selectVeiculoBuscaBtn;
    
    private Manutencao manutencao = new Manutencao();
    private Veiculo veiculo, veiculoBusca;
    
    @FXML
    void initialize() {
    	super.initialize();
    	for(int i =0 ; i <24 ; i++)
    		horaBox.getItems().add(i);
    	horaCustoBox.setItems(horaBox.getItems());
    	horaCustoBox.setValue(0);
    	tipoBox.getItems().addAll(TipoManutencao.values());
     	estadoManutencaoBox.getItems().addAll(EstadoManutencao.values());
     	tipoCln.setCellValueFactory(new PropertyValueFactory<>("tipoManuntencao"));
     	custoCln.setCellValueFactory(new PropertyValueFactory<>("custo"));
     	estadoCln.setCellValueFactory(new PropertyValueFactory<>("estadoManutencao"));
     	inicioDateCln.setCellValueFactory(new PropertyValueFactory<>("dataHoraInicio"));
     	veiculoCln.setCellValueFactory(new PropertyValueFactory<>("veiculo"));
    }
    
	@Override
	protected void cadastrarEditar(Boolean cadastrar, String opcao) throws BoException {
		Manutencao manutencao = (cadastrar)? new Manutencao(): this.manutencao;
		
		manutencao.setCusto(Float.parseFloat(custoFld.getText()));
		manutencao.setDataHoraInicio(Util.gerarHorario(inicioDate, horaBox));
		manutencao.setEstadoManutencao(estadoManutencaoBox.getValue());
		manutencao.setTipoManuntencao(tipoBox.getValue());
		
		fachadaModel.cadastrarEditarManutencao(manutencao);
		alerta.imprimirMsg("Sucesso","Manutenção "+opcao+" com sucesso", AlertType.INFORMATION);
	}

	@Override
	protected void excluir() throws BoException {
		fachadaModel.excluirManutencao(manutencao);
		alerta.imprimirMsg("Sucesso ao exluir", "Manutenção exlcuido com sucesso", AlertType.INFORMATION);
	}
	
	@Override
	void popularTabela(String busca) {
		try {
			Manutencao manutencao = new Manutencao();
			manutencao.setVeiculo(veiculoBusca);
			List<Manutencao> manutencoes = fachadaModel.buscarManutencoes(busca,manutencao);
			entidadeTabela.getItems().setAll(manutencoes);
			alerta.imprimirMsg("Busca concluída","Foram econtrados "+manutencoes.size()+" resultado(s)",AlertType.INFORMATION);
		} catch (BoException e) {
			alerta.imprimirMsg("Erro",e.getMessage(),AlertType.ERROR);
		}
		
	}

	@Override
	void popularDescricao(Entidade entidade) {
		this.manutencao = (Manutencao) entidade;
		
		custoFld.setText(manutencao.getCusto()+"");
		if(manutencao.getDataHoraInicio()!= null) {
			inicioDate.setValue(manutencao.getDataHoraInicio().toLocalDate());
			horaBox.setValue(manutencao.getDataHoraInicio().getHour());
		}
		estadoManutencaoBox.setValue(manutencao.getEstadoManutencao());
		tipoBox.setValue(manutencao.getTipoManuntencao());
		dadosVeiculoFld.setText(manutencao.getVeiculo().toString());
	}

	@Override
	void limparCampos() {
		manutencao = null;
		veiculo = null;
		custoFld.clear();
		inicioDate.setValue(null);
		horaBox.setValue(null);
		estadoManutencaoBox.setValue(null);
		tipoBox.setValue(null);
		dadosVeiculoFld.clear();
		System.gc();
	}
	
	@FXML
    void actionHandle(ActionEvent event) {
    	if(event.getSource() == selectVeiculoBtn) {
    		veiculo = Util.selecionarVeiculoEmDialogo(null);
    		dadosVeiculoFld.setText(veiculo.toString());
    	}
    	else if(event.getSource() == selectVeiculoBuscaBtn) {
    		veiculoBusca = Util.selecionarVeiculoEmDialogo(null);
    		if(veiculoBusca != null)
    			veiculoBuscaFFld.setText(veiculoBusca.toString());
    	}
    	else if(event.getSource() == veiculoBuscaCk) {
    		veiculoBuscaPae.setDisable(!veiculoBuscaCk.isSelected());
    		if(!veiculoBuscaCk.isSelected()) {
    			veiculoBusca = null;
    			veiculoBuscaFFld.clear();
    		}
    	}
    	
    }
}
