package controller;

import java.net.URL;
import java.util.ResourceBundle;

import business.BoCliente;
import business.IBoCliente;
import entidade.Cliente;
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

public class SelecionarClienteController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DialogPane selecionarClienteDialog;

    @FXML
    private TextField pesquisaFld;

    @FXML
    private CheckBox buscaRapidaChk;

    @FXML
    private TableView<Cliente> clienteTbl;

    @FXML
    private TableColumn<Cliente, String> nomeCln;

    @FXML
    private TableColumn<Cliente, String> codigoCln;

    @FXML
    private TableColumn<Cliente, String> emailCln;
    
    private IBoCliente boCliente = BoCliente.getInstance();

    private Alerta alerta = Alerta.getInstance();

    		
    @FXML
    void initialize() {
    	nomeCln.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	codigoCln.setCellValueFactory(new PropertyValueFactory<>("codigo"));
    	emailCln.setCellValueFactory(new PropertyValueFactory<>("email"));
    	
    	pesquisaFld.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(buscaRapidaChk.isSelected() && pesquisaFld.getText().trim().length() > 0) {
		    		try {
						clienteTbl.getItems().setAll(boCliente.buscaPorBusca(pesquisaFld.getText()));
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
				clienteTbl.getItems().setAll(boCliente.buscaPorBusca(pesquisaFld.getText()));
				alerta.imprimirMsg("Busca concluída","Foram econtrados "+clienteTbl.getItems().size()+" resultado(s)",AlertType.INFORMATION);
			} catch (BoException e) {
				alerta.imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
			}
    	}
    }
    
    public TableView<Cliente> getClienteTbl() {
		return clienteTbl;
	}
    
    public DialogPane getSelecionarClienteDialog() {
		return selecionarClienteDialog;
	}
}
