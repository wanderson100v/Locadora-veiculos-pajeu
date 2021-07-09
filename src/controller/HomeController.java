package controller;

import app.App;
import dao.DaoRes;
import entidade.Funcionario;
import enumeracoes.Cargo;
import excecoes.DaoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import view.Alerta;

public class HomeController implements IObservadorFuncionario{

    @FXML
    private Button voltarBtn;
    
    @FXML
    private Tab dadosTab;
    
    @FXML
    private Tab reservaTab;
    
    @FXML
    private Tab locacaoTab;
    
    @FXML
    private Label descFunLbl;
    
    @FXML
    private Button configBtn;

    @FXML
    void initialize() {
    	try {
    		FuncionarioObservavel.getIntance().addObservadorFuncionario(this);
    		
			PesquisaController pesquisaController = (PesquisaController) DaoRes.getInstance().carregarControllerFXML("PesquisaPane");
			DaoRes daoRes = DaoRes.getInstance();
    		
    		ClienteJuridicoController clienteJuridicoController = (ClienteJuridicoController) daoRes.carregarControllerFXML("ClienteJuridicoPane");
    		ClienteFisicoController clienteFisicoController = (ClienteFisicoController) daoRes.carregarControllerFXML("ClienteFisicoPane");
    		CaminhonetaCargaController caminhonetaCargaController = (CaminhonetaCargaController) daoRes.carregarControllerFXML("CaminhonetaCargaPane");
    		AutomovelController automovelController = (AutomovelController) daoRes.carregarControllerFXML("AutomovelPane");
    		ManutencaoController manutencaoController = (ManutencaoController) daoRes.carregarControllerFXML("ManutencaoPane");
    		
    		pesquisaController.getControladores().put("Juridico",clienteJuridicoController);
    		pesquisaController.getControladores().put("Fisico", clienteFisicoController);
    		pesquisaController.getControladores().put("Caminhoneta Carga", caminhonetaCargaController);
    		pesquisaController.getControladores().put("Automóvel", automovelController);
    		pesquisaController.getControladores().put("Manutenção", manutencaoController);
			
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
    		App.iniTelaLogin();
    	}
    }

	@Override
	public void atualizar(Funcionario funcionario, Cargo cargo) {
		if(cargo != null) {
			if(cargo == Cargo.AT) 
				configBtn.setVisible(false);
			else 
				configBtn.setVisible(true);
			if(funcionario != null) {
				descFunLbl.setText(cargo+" "+((funcionario.getNome().contains(" "))? funcionario.getNome().substring(0,funcionario.getNome().indexOf(" ")) : funcionario.getNome())
						+ ((funcionario.getFilial() != null)? " Da Filial"+funcionario.getFilial().getNome() : " Sem Filial"));
			}
		}
			
	}
}
