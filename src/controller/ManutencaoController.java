package controller;

import java.time.LocalDateTime;
import java.util.List;

import business.BoManutencao;
import entidade.Entidade;
import entidade.Manutencao;
import entidade.Veiculo;
import enumeracoes.EstadoManutencao;
import enumeracoes.TipoManutencao;
import excecoes.BoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import view.Alerta;

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

    private Manutencao manutencao;
    private Veiculo veiculo;
    
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
	void crudHandle(Button btn) {
		try {	
			try {
				if(btn == cadastrarBtn) {
					BoManutencao.getInstance().cadastrarEditar(new Manutencao(Util.gerarHorario(inicioDate, horaBox)
							, tipoBox.getValue(), estadoManutencaoBox.getValue(), Float.parseFloat(custoFld.getText()), 
							horaCustoBox.getValue(),veiculo));
					alerta.imprimirMsg("Sucesso ao cadastrar","Manutenção cadastrada com sucesso", AlertType.INFORMATION);
					
		    	}else if(btn == editarBtn){
		    		
		    		manutencao.setCusto(Float.parseFloat(custoFld.getText()));
		    		manutencao.setDataHoraInicio(Util.gerarHorario(inicioDate, horaBox));
		    		manutencao.setEstadoManutencao(estadoManutencaoBox.getValue());
		    		manutencao.setTipoManuntencao(tipoBox.getValue());
		    		BoManutencao.getInstance().cadastrarEditar(manutencao);
		    		alerta.imprimirMsg("Sucesso ao editar","Manutenção editado com sucesso", AlertType.INFORMATION);
		    	}
		    }catch (Exception e) {
	    		Alerta.getInstance().imprimirMsg("Alerta", "Há um ou mais campos com entradas invalidas", AlertType.WARNING);
	    	}	
	    	 if(btn == excluirBtn){
	    		BoManutencao.getInstance().excluir(manutencao);
	    		alerta.imprimirMsg("Sucesso ao exluir","Manutenção exlcuido com sucesso", AlertType.INFORMATION);
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
			List<Manutencao> manutencoes = BoManutencao.getInstance().buscaPorBuscaAbrangente(busca);
			entidadeTabela.getItems().setAll(manutencoes);
			entidadeTabela.refresh();
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
    		manutencao.setVeiculo(Util.selecionarVeiculoEmDialogo(null));
    		dadosVeiculoFld.setText(veiculo.toString());
    	}
    }
}
