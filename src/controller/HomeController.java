package controller;

import app.App;
import dao.DaoRes;
import enumeracoes.Cargo;
import excecoes.DaoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import view.Alerta;

public class HomeController implements IFuncionarioObservadores{

    @FXML
    private Button voltarBtn;
    
    @FXML
    private Tab dadosTab;
    
    @FXML
    private Tab reservaTab;
    
    @FXML
    private Tab locacaoTab;

    @FXML
    private Button configBtn;

    @FXML
    void initialize() {
    	try {
    		FuncionarioObservavel.getIntance().getFuncionarioObservadores().add(this);
    		
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
			reservaTab.setContent(daoRes.carregarPaneFXML("ReservaTabPane"));
			locacaoTab.setContent(daoRes.carregarPaneFXML("LocacaoTabPane"
					+ ""));
			
    	} catch (DaoException e) {
			e.printStackTrace();
			Alerta.getInstance().imprimirMsg("Erro",e.getMessage(),AlertType.ERROR);
			System.exit(0);
    	}
    }
    
    @FXML
    void actionHandle(ActionEvent event) {
    	if(event.getSource() == configBtn)
    		App.iniTelaConfig();
    	else if(event.getSource() == voltarBtn) {
    		FuncionarioObservavel.getIntance().avisarOuvintes(null);
    		App.iniTelaLogin();
    	}
    }

	@Override
	public void atualizar(Cargo cargo) {
		if(cargo != null)
			if(cargo == Cargo.AT) 
				configBtn.setVisible(false);
			else 
				configBtn.setVisible(true);
			
	}
}
