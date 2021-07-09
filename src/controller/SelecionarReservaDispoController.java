package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.adapter.ReservaDisponibilidade;
import model.excecoes.BoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mode.business.BoReserva;

public class SelecionarReservaDispoController {

    @FXML
    private DialogPane selectReservaDispoDialog;

    @FXML
    private CheckBox superiorCk;

    @FXML
    private CheckBox locavelCk;

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
    private List<ReservaDisponibilidade> reservasSuperiores;
    
    private Long filialId;
    
    @FXML
    void initialize() {
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
    

    @FXML
    void checkHandle(ActionEvent event) {
    	CheckBox fonte = (CheckBox) event.getSource();
    	if(reservasSuperiores == null) {
    		reservasSuperiores = new ArrayList<>();
    		reservasSuperiores.addAll(disponiTbl.getItems());
    	}
    	if(fonte == superiorCk) {
    		if(superiorCk.isSelected()) {
    			disponiTbl.getItems().setAll(reservasSuperiores);
    		}else {
    			try {
    				disponiTbl.getItems().setAll(BoReserva.getInstance().buscarReservaDisponibilidade(filialId, LocalDateTime.now()));
				} catch (BoException e) {
				}
    		}
    	}else if(fonte == locavelCk) {
    		if(locavelCk.isSelected()) {
    			
    		}else {
    			
    		}
    	}
    	
    }

    public TableView<ReservaDisponibilidade> getReservaDispoTbl() {
		return disponiTbl;
	}
    
    public DialogPane getSelectReservaDispoDialog() {
		return selectReservaDispoDialog;
	}
    
    public void setFilialId(Long filialId) {
		this.filialId = filialId;
	}
}
