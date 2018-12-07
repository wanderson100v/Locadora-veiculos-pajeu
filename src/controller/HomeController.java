package controller;

import app.App;
import dao.DaoRes;
import excecoes.DaoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

public class HomeController {

    @FXML
    private Button voltarBtn;
    
    @FXML
    private Tab dadosTab;

    @FXML
    void eventHandler(ActionEvent event) {
    	App.iniTelaLogin();
    }

    @FXML
    void initialize() {
    	try {
			PesquisaController pesquisaController = (PesquisaController) DaoRes.getInstance().carregarControllerFXML("PesquisaPane");
			DaoRes daoRes = DaoRes.getInstance();
    		
    		ClienteJuridicoController clienteJuridicoController = (ClienteJuridicoController) daoRes.carregarControllerFXML("ClienteJuridicoPane");
    		ClienteFisicoController clienteFisicoController = (ClienteFisicoController) daoRes.carregarControllerFXML("ClienteFisicoPane");
    		CaminhonetaCargaController caminhonetaCargaController = (CaminhonetaCargaController) daoRes.carregarControllerFXML("CaminhonetaCargaPane");
    		AutomovelController automovelController = (AutomovelController) daoRes.carregarControllerFXML("AutomovelPane");
    		
    		pesquisaController.getControladores().put("Juridico",clienteJuridicoController);
    		pesquisaController.getControladores().put("Fisico", clienteFisicoController);
    		pesquisaController.getControladores().put("Caminhoneta Carga", caminhonetaCargaController);
    		pesquisaController.getControladores().put("Automóvel", automovelController);
			
			pesquisaController.getFiltroBox().getItems().addAll(pesquisaController.getControladores().keySet());
			
			dadosTab.setContent(pesquisaController.getPesquisaPane());
			
    	} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
