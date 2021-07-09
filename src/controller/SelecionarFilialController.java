package controller;

import model.excecoes.BoException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import mode.business.BoFilial;
import mode.business.IBoFilial;
import model.entidade.Endereco;
import model.entidade.Filial;
import view.Alerta;

public class SelecionarFilialController {

    @FXML
    private TextField pesquisaFld;

    @FXML
    private CheckBox buscaRapidaChk;

    @FXML
    private DialogPane selecionarFilialDialog;

    @FXML
    private TableView<Filial> filialTbl;

    @FXML
    private TableColumn<Filial, String> nomeCln;

    @FXML
    private TableColumn<Filial, Endereco> enderecoCln;
    
    private IBoFilial boFilial = BoFilial.getInstance();
    
    private Alerta alerta = Alerta.getInstance();
    

    @FXML
    void initialize() {
    	nomeCln.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	enderecoCln.setCellValueFactory(new PropertyValueFactory<>("endereco"));
    	
    	pesquisaFld.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(buscaRapidaChk.isSelected() && pesquisaFld.getText().trim().length() > 0) {
		    		try {
						filialTbl.getItems().setAll(boFilial.buscaPorBuscaAbrangente(pesquisaFld.getText()));
					} catch (BoException e) {
						e.printStackTrace();
					}
		    	}
			}
		});
    }
    
    @FXML
    void actionHandle(ActionEvent event) {
    	if(!buscaRapidaChk.isSelected()) {
    		try {
				filialTbl.getItems().setAll(boFilial.buscaPorBuscaAbrangente(pesquisaFld.getText()));
				alerta.imprimirMsg("Busca concluída","Foram econtrados "+filialTbl.getItems().size()+" resultado(s)",AlertType.INFORMATION);
			} catch (BoException e) {
				alerta.imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
			}
    	}
    }
    
    public DialogPane getSelecionarFilialDialog() {
		return selecionarFilialDialog;
	}
    
    public TableView<Filial> getFilialTbl() {
		return filialTbl;
	}
}
