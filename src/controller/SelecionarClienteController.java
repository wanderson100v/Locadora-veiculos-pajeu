package controller;

import model.excecoes.BoException;
import model.vo.Cliente;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import view.Alerta;

public class SelecionarClienteController extends ControllerAdapter{

    @FXML
    private DialogPane selecionarClienteDialog;

    @FXML
    private TextField pesquisaFld;

    @FXML
    private CheckBox buscaRapidaChk;
    
    @FXML
    private ComboBox<String> tipoClienteBox;
    
    @FXML
    private TableView<Cliente> clienteTbl;

    @FXML
    private TableColumn<Cliente, String> nomeCln;

    @FXML
    private TableColumn<Cliente, String> codigoCln;

    @FXML
    private TableColumn<Cliente, String> emailCln;
    		
    @FXML
    void initialize() {
    	nomeCln.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	codigoCln.setCellValueFactory(new PropertyValueFactory<>("codigo"));
    	emailCln.setCellValueFactory(new PropertyValueFactory<>("email"));
    	
    	tipoClienteBox.getItems().addAll("F�sico","Jur�dico");
    	tipoClienteBox.setValue("F�sico");
    	
    	pesquisaFld.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(buscaRapidaChk.isSelected() && pesquisaFld.getText().trim().length() > 0) {
		    		buscar(false);
		    	}
			}
		});
    	
    }
    
    @FXML
    void actionHandle(ActionEvent event) {
    	if(!buscaRapidaChk.isSelected()) {
    		buscar(true);
    	}
    }
    
    private void buscar(Boolean mostrarMsg)  {
    	try {
			if(tipoClienteBox.getValue().equals("Físico"))
				clienteTbl.getItems().setAll(fachadaModel.buscarClientesFisicos(pesquisaFld.getText()));
			else 
				clienteTbl.getItems().setAll(fachadaModel.buscarClientesJuridicos(pesquisaFld.getText()));
			if(mostrarMsg)
				 Alerta.getInstance().imprimirMsg("Busca concluída","Foram econtrados "+clienteTbl.getItems().size()+" resultado(s)",AlertType.INFORMATION);
		} catch (BoException e) {
			 Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
    }
    
    public TableView<Cliente> getClienteTbl() {
		return clienteTbl;
	}
    
    public DialogPane getSelecionarClienteDialog() {
		return selecionarClienteDialog;
	}
}
