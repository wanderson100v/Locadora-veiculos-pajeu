package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import adapter.ReservaDisponibilidade;
import business.BoReserva;
import excecoes.BoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SelecionarReservaDispoController {

    @FXML
    private DialogPane selectReservaDispoDialog;

    @FXML
    private CheckBox superiorCk;

    @FXML
    private CheckBox locavelCk;

    @FXML
    private TableView<ReservaDisponibilidade> reservaDispoTbl;

    @FXML
    private TableColumn<ReservaDisponibilidade, String> categoriaCln;

    @FXML
    private TableColumn<ReservaDisponibilidade, Integer> reservadoCln;

    @FXML
    private TableColumn<ReservaDisponibilidade, Integer> aReceberCln;

    @FXML
    private TableColumn<ReservaDisponibilidade, Integer> emEstoqueCln;

    @FXML
    private TableColumn<ReservaDisponibilidade, Integer> disponivelCln;

    @FXML
    private TableColumn<ReservaDisponibilidade, Float> valorCln;

    @FXML
    private TableColumn<ReservaDisponibilidade, String> descricaoCln;
    
    private List<ReservaDisponibilidade> reservasSuperiores;
    
    private Long filialId;
    
    @FXML
    void initialize() {
    	categoriaCln.setCellValueFactory(new PropertyValueFactory<>("tipoCategoria"));
    	reservadoCln.setCellValueFactory(new PropertyValueFactory<>("reservado"));
    	aReceberCln.setCellValueFactory(new PropertyValueFactory<>("receber"));
    	emEstoqueCln.setCellValueFactory(new PropertyValueFactory<>("emEstoque"));
    	disponivelCln.setCellValueFactory(new PropertyValueFactory<>("disponivel"));
    	valorCln.setCellValueFactory(new PropertyValueFactory<>("valorDiariaCategoria"));
    	descricaoCln.setCellValueFactory(new PropertyValueFactory<>("descricaoCategoria"));
    }
    

    @FXML
    void checkHandle(ActionEvent event) {
    	CheckBox fonte = (CheckBox) event.getSource();
    	if(reservasSuperiores == null) {
    		reservasSuperiores = new ArrayList<>();
    		reservasSuperiores.addAll(reservaDispoTbl.getItems());
    	}
    	if(fonte == superiorCk) {
    		if(superiorCk.isSelected()) {
    			reservaDispoTbl.getItems().setAll(reservasSuperiores);
    		}else {
    			try {
    				reservaDispoTbl.getItems().setAll(BoReserva.getInstance().buscarReservaDisponibilidade(filialId, LocalDateTime.now()));
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
		return reservaDispoTbl;
	}
    
    public DialogPane getSelectReservaDispoDialog() {
		return selectReservaDispoDialog;
	}
    
    public void setFilialId(Long filialId) {
		this.filialId = filialId;
	}
}
