package controller;

import dao.DaoRes;
import excecoes.DaoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;

public class ConfiguracaoController {

    @FXML
    private Tab dadosTab;

    @FXML
    private Button voltarBtn;

    @FXML
    void initialize() {
    	
    	try {
	    	PesquisaController pesquisaController = (PesquisaController) DaoRes.getInstance().carregarControllerFXML("PesquisaPane");
			DaoRes daoRes = DaoRes.getInstance();
	
			FilialController filialController = (FilialController) daoRes.carregarControllerFXML("FilialPane");
			AcessorioController acessorioController = (AcessorioController) daoRes.carregarControllerFXML("AcessorioPane");
			CategoriaVeiculoController categoriaVeiculoController = (CategoriaVeiculoController) daoRes.carregarControllerFXML("CategoriaVeiculoPane");
			FuncionrarioController funcionrarioController = (FuncionrarioController) daoRes.carregarControllerFXML("FuncionarioPane");
		
			pesquisaController.getControladores().put("Filial", filialController);
			pesquisaController.getControladores().put("Acessório", acessorioController);
			pesquisaController.getControladores().put("Categoria Veículo", categoriaVeiculoController);
			pesquisaController.getControladores().put("Funcionario", funcionrarioController);
			
			pesquisaController.getFiltroBox().getItems().addAll(pesquisaController.getControladores().keySet());
			
			dadosTab.setContent(pesquisaController.getPesquisaPane());
    	}catch (Exception e) {
		
    	}	
    }
    
    @FXML
    void actionEvent(ActionEvent event) {

    }

    
}
