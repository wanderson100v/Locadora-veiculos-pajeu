package controller;

import app.App;
import dao.DaoRes;
import enumeracoes.Cargo;
import excecoes.DaoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;

public class ConfiguracaoController implements IObservadoresEntidade{

    @FXML
    private Tab dadosTab;

    @FXML
    private Button voltarBtn;

    @FXML
    void initialize() {
    	ObservadorEntidade.getIntance().getEntidadeObservadores().add(this);
    	PesquisaController pesquisaController;
		try {
			pesquisaController = (PesquisaController) DaoRes.getInstance().carregarControllerFXML("PesquisaPane");
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
			
			dadosTab.setClosable(false);
			dadosTab.setContent(pesquisaController.getPesquisaPane());
		} catch (DaoException e) {
			
		}
    }
    
    @FXML
    void actionEvent(ActionEvent event) {
    	App.iniTelaMenu();
    }


	@Override
	public void atualizar(Cargo cargo) {
		if(cargo == Cargo.AT) 
			dadosTab.setClosable(true);
			
	}

    
}
