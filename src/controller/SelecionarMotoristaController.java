package controller;

import java.time.LocalDate;

import business.BoFisico;
import business.IBoFisico;
import entidade.Fisico;
import excecoes.BoException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import view.Alerta;

public class SelecionarMotoristaController {

    @FXML
    private DialogPane selecionarMotoristaDialog;

    @FXML
    private TextField pesquisaFld;

    @FXML
    private CheckBox buscaRapidaChk;

    @FXML
    private TableView<Fisico> motoristaTbl;

    @FXML
    private TableColumn<Fisico, String> nomeCln;

    @FXML
    private TableColumn<Fisico, String> codigoCln;

    @FXML
    private TableColumn<Fisico, LocalDate> validadeCln;

    @FXML
    private TableColumn<Fisico, String> identificacaoCln;

    @FXML
    private TableColumn<Fisico, String> habilitacaoCln;
    
    private IBoFisico boFisico = BoFisico.getInstance();
    private Alerta alerta = Alerta.getInstance();
	
    @FXML
    void initialize() {
    	nomeCln.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	codigoCln.setCellValueFactory(new PropertyValueFactory<>("codigo"));
    	validadeCln.setCellValueFactory(new PropertyValueFactory<>("dataValidadeHabilitacao"));
    	identificacaoCln.setCellValueFactory(new PropertyValueFactory<>("identificacaoMotorista"));
    	habilitacaoCln.setCellValueFactory(new PropertyValueFactory<>("numeroHabilitacao"));
    	/*
    	pesquisaFld.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(buscaRapidaChk.isSelected() && pesquisaFld.getText().trim().length() > 0) {
		    		try {
						motoristaTbl.getItems().setAll(boFisico.buscaPorBusca(pesquisaFld.getText()));
					} catch (BoException e) {
						e.printStackTrace();
					}
		    	}
			}
		});*/
    	
    }
    
    @FXML
    void actionHandle(ActionEvent event) {
    	/*if(!buscaRapidaChk.isSelected()) {
    		try {
				clienteTbl.getItems().setAll(boFisico.buscaPorBusca(pesquisaFld.getText()));
				alerta.imprimirMsg("Busca concluída","Foram econtrados "+clienteTbl.getItems().size()+" resultado(s)",AlertType.INFORMATION);
			} catch (BoException e) {
				alerta.imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
			}
    	}*/
    }
    
    public TableView<Fisico> getMotoristaTbl() {
    	return motoristaTbl;
    }
    
    public DialogPane getSelecionarMotoristaDialog() {
		return selecionarMotoristaDialog;
	}
	
}
