package controller;

import entidade.Juridico;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ClienteJuridicoController {
   
	@FXML
    private Button editarBtn;

    @FXML
    private Button excluirBtn;

    @FXML
    private TableView<Juridico> clienteJuridicoTbl;

    @FXML
    private TableColumn<Juridico,String> nomeCln;

    @FXML
    private TableColumn<Juridico,String> cnpjCln;

    @FXML
    private VBox clienteJuridicoPane;

    @FXML
    private TextField telPreFld;

    @FXML
    private TextField telNumFld;

    @FXML
    private TextField emailFld;

    @FXML
    private TextField inscicaoEstadualFld;

    @FXML
    private TextField cnpjFld;

    @FXML
    private TextField nomeFld;

    @FXML
    private TextField ruaFld;

    @FXML
    private TextField bairroFld;

    @FXML
    private TextField cidadeFld;

    @FXML
    private ComboBox<?> estadoBox;

    @FXML
    private TextField numFld;

    @FXML
    private TextField cpfFld;
    
    @FXML
    void actionHandle(ActionEvent event) {

    }

    @FXML
    void mouseClikHandle(MouseEvent event) {

    }

    @FXML
    void initialize() {
    	nomeCln.setCellValueFactory( new PropertyValueFactory<>("nome"));
    	nomeCln.setCellValueFactory( new PropertyValueFactory<>("cnpj"));
    }
    
    public VBox getClienteJuridicoPane() {
		return clienteJuridicoPane;
	}
    
    public TableView<Juridico> getClienteJuridicoTbl() {
		return clienteJuridicoTbl;
	}
}
