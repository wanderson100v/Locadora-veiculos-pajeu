package controller;

import business.BoCategoriaVeiculo;
import business.IBoCategoriaVeiculo;
import entidade.CategoriaVeiculo;
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

public class SelecionarCategoriaVeiculoController {

    @FXML
    private DialogPane selecionarCategoriaVeiculoDialog;

    @FXML
    private TextField pesquisaFld;

    @FXML
    private CheckBox buscaRapidaChk;

    @FXML
    private TableView<CategoriaVeiculo> categoriaVeiculoTbl;

    @FXML
    private TableColumn<CategoriaVeiculo, String> tipoCln;

    @FXML
    private TableColumn<CategoriaVeiculo, Float> valorCln;

    @FXML
    private TableColumn<CategoriaVeiculo, String> descricaoCln;

    private IBoCategoriaVeiculo boCategoriaVeiculo = BoCategoriaVeiculo.getInstance();
    
    private Alerta alerta = Alerta.getInstance();
    
    @FXML
    void initialize() {
    	tipoCln.setCellValueFactory( new PropertyValueFactory<>("tipo"));
    	valorCln.setCellValueFactory( new PropertyValueFactory<>("valorDiaria"));
    	descricaoCln.setCellValueFactory( new PropertyValueFactory<>("descricao"));
    	
    	pesquisaFld.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(buscaRapidaChk.isSelected() && pesquisaFld.getText().trim().length() > 0) {
		    		try {
						categoriaVeiculoTbl.getItems().setAll(boCategoriaVeiculo.buscaPorBuscaAbrangente(pesquisaFld.getText()));
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
				categoriaVeiculoTbl.getItems().setAll(boCategoriaVeiculo.buscaPorBuscaAbrangente(pesquisaFld.getText()));
				alerta.imprimirMsg("Busca concluída","Foram econtrados "+categoriaVeiculoTbl.getItems().size()+" resultado(s)",AlertType.INFORMATION);
			} catch (BoException e) {
				alerta.imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
			}
    	}
    }
    
    public TableView<CategoriaVeiculo> getCategoriaVeiculoTbl() {
		return categoriaVeiculoTbl;
	}
    
    public DialogPane getSelecionarCategoriaVeiculoDialog() {
		return selecionarCategoriaVeiculoDialog;
	}
}
