package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import model.excecoes.BoException;
import model.vo.Filial;
import model.vo.Funcionario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import model.FachadaModel;
import model.adapter.ReservaDisponibilidade;
import model.enumeracoes.Cargo;
import view.Alerta;

public class DisponibilidadeReservaController implements IObservadorFuncionario{


    @FXML
    private TableView<ReservaDisponibilidade> disponiTbl;

    @FXML
    private TableColumn<ReservaDisponibilidade, String> tipoCatCln;

    @FXML
    private TableColumn<ReservaDisponibilidade, Float> valorDiariaCatCln;

    @FXML
    private TableColumn<ReservaDisponibilidade, String> descricaoCatCln;

    @FXML
    private TableColumn<ReservaDisponibilidade, Integer> prevLocaCln;

    @FXML
    private TableColumn<ReservaDisponibilidade, Integer> prevManuCln;

    @FXML
    private TableColumn<ReservaDisponibilidade, Integer> prevReseCln;

    @FXML
    private TableColumn<ReservaDisponibilidade, Integer> totalLocaCln;

    @FXML
    private TableColumn<ReservaDisponibilidade, Integer> totalManuCln;

    @FXML
    private TableColumn<ReservaDisponibilidade, Integer> totalReseCln;

    @FXML
    private TableColumn<ReservaDisponibilidade, Integer> totalVeiculoCln;

    @FXML
    private TableColumn<ReservaDisponibilidade, Integer> disponivelCln;

    @FXML
    private Button atualizarTabelaBtn;

    @FXML
    private FlowPane horarioPane;

    @FXML
    private DatePicker dataDate;

    @FXML
    private ComboBox<Integer> horaBox;

    @FXML
    private RadioButton minhaFilialRb;

    @FXML
    private RadioButton outraFilialRb;

    @FXML
    private CheckBox horarioAtualCk;

    @FXML
    private TextField dadosFilialFld;

    @FXML
    private Button selecionarFilialBtn;
    
    private Funcionario funcionario;
    private Filial outraFilial;
    private ToggleGroup toggleGroup;
    
    private FachadaModel fachadaModel;
    
    @FXML
    void initialize() {
    	this.fachadaModel = FachadaModel.getInstance();
    	FuncionarioObservavel.getIntance().addObservadorFuncionario(this);
    
    }
    
    @FXML
    void actionHandle(ActionEvent event) {
    	try {
	    	Object fonte  = event.getSource();
	    	if(fonte == atualizarTabelaBtn) {
	    		if(toggleGroup.getSelectedToggle()!= null) {
	    			LocalDateTime horario = 
	    			(horarioAtualCk.isSelected())?
	    				LocalDateTime.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue(),
	    						LocalDate.now().getDayOfMonth(),LocalDateTime.now().getHour(),0)
	    				: LocalDateTime.of(dataDate.getValue().getYear(),dataDate.getValue().getMonthValue(),
	    						dataDate.getValue().getDayOfMonth(),horaBox.getValue(),0);
    				Filial filial = (minhaFilialRb.isSelected()) ? funcionario.getFilial(): outraFilial;
	    			disponiTbl.getItems().setAll(fachadaModel.buscarReservaDisponibilidade(filial.getId(),horario));
		    		Alerta.getInstance().imprimirMsg("Busca Concluida", disponiTbl.getItems().size()+" resultados",AlertType.INFORMATION);
	    		}else
	    			Alerta.getInstance().imprimirMsg("Alerta", "É necessário selecionar opção de busca para filial",AlertType.WARNING);
	    	
	    	}else if(fonte == selecionarFilialBtn) {
	    		Filial filial  = Util.selecionarFilialEmDialogo();
	    		if(filial!= null) {
	    			outraFilial = filial;
	    			dadosFilialFld.setText(outraFilial.toString());
	    		}
	    	}else if(fonte == outraFilialRb) {
	    		outraFilial = Util.selecionarFilialEmDialogo();
	    		if(outraFilial!= null) {
	    			dadosFilialFld.setText(outraFilial.toString());
	    			selecionarFilialBtn.setDisable(false);
	    		}else {
	    			outraFilialRb.setSelected(false);
	    			dadosFilialFld.clear();
	    		}
	    	}else if(fonte == minhaFilialRb) {
	    		if(funcionario.getFilial() != null) {
	    			dadosFilialFld.setText(funcionario.getFilial().toString());
	    			selecionarFilialBtn.setDisable(true);
	    		}
	    		else {
	    			dadosFilialFld.clear();
	    			minhaFilialRb.setSelected(false);
	    			Alerta.getInstance().imprimirMsg("Alerta", "Funcionário não esta relacionado a nenhuma filial",AlertType.WARNING);
	    		}
	    	}
    	}catch (BoException e) {
    		Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
    	}
    }
    

    @FXML
    void checkHandle(ActionEvent event) {
		horarioPane.setDisable(horarioAtualCk.isSelected());
    }
    

	public void atualizar(Funcionario funcionario, Cargo cargo) {
		try {
			if(tipoCatCln != null && tipoCatCln.getCellValueFactory() == null)
	    		fazerLigacao();
			if(funcionario != null) {
				this.funcionario = funcionario;
				if(funcionario.getFilial()!= null) {
					minhaFilialRb.setSelected(true);
					dadosFilialFld.setText(funcionario.getFilial().toString());
					disponiTbl.getItems().setAll(fachadaModel.buscarReservaDisponibilidade(funcionario.getFilial().getId(),LocalDateTime.now()));
				}
			}
		} catch (BoException e) {
			Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
	}
    
    private void fazerLigacao() {
    	for(int i =0; i <24; i++) 
    		horaBox.getItems().add(i);
    	
    	dataDate.setValue(LocalDate.now());
    	horaBox.setValue(LocalDateTime.now().getHour());

    	toggleGroup = new ToggleGroup();
    	minhaFilialRb.setToggleGroup(toggleGroup);
    	outraFilialRb.setToggleGroup(toggleGroup);
    	
    	tipoCatCln.setCellValueFactory(new PropertyValueFactory<>("tipoCategoria"));
    	valorDiariaCatCln.setCellValueFactory(new PropertyValueFactory<>("valorDiariaCategoria"));
    	descricaoCatCln.setCellValueFactory(new PropertyValueFactory<>("descricaoCategoria"));
    	prevLocaCln.setCellValueFactory(new PropertyValueFactory<>("previsaoLocacaoAcumulada"));
    	prevManuCln.setCellValueFactory(new PropertyValueFactory<>("previsaoManutencaoAcumulada"));
    	prevReseCln.setCellValueFactory(new PropertyValueFactory<>("previsaoReservaAcumulada"));
    	totalLocaCln.setCellValueFactory(new PropertyValueFactory<>("totalLocado"));
    	totalManuCln.setCellValueFactory(new PropertyValueFactory<>("totalManter"));
    	totalReseCln.setCellValueFactory(new PropertyValueFactory<>("totalReserva"));
    	totalVeiculoCln.setCellValueFactory(new PropertyValueFactory<>("totalVeiculo"));
    	disponivelCln.setCellValueFactory(new PropertyValueFactory<>("disponivel"));
    }
}
