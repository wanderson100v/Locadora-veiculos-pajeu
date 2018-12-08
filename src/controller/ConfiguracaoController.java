package controller;

import java.util.Observable;
import java.util.Observer;

import app.App;
import dao.DaoRes;
import enumeracoes.Cargo;
import excecoes.DaoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;

public class ConfiguracaoController implements Observer{

    @FXML
    private Tab dadosTab;

    @FXML
    private Button voltarBtn;

    @FXML
    void initialize() {
    	LoginController.loginController.addObserver(this);
    }
    
    @FXML
    void actionEvent(ActionEvent event) {
    	App.iniTelaMenu();
    }

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Cargo) {
			if(((Cargo) arg) != Cargo.AT) {
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
			}else 
				dadosTab.setClosable(true);
			
			
		}
		
	}

    
}
