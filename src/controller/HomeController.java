package controller;

import model.excecoes.DaoException;
import model.vo.Funcionario;
import app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import model.dao.DaoRes;
import model.enumeracoes.Cargo;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import view.Alerta;

public class HomeController extends Controller{

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
    		DaoRes daoRes = DaoRes.getInstance();
    		PesquisaController pesquisaController = (PesquisaController) DaoRes.getInstance().carregarControllerFXML("PesquisaPane");
    		dadosTab.setContent(pesquisaController.getPesquisaPane());
			reservaTab.setContent(daoRes.carregarPaneFXML("ReservaTabPane"));
			locacaoTab.setContent(daoRes.carregarPaneFXML("LocacaoTabPane"));
			
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
