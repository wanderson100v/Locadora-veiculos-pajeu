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
    private Tab tab;

    private Pane pesquisaPane;
    
    @FXML
    void eventHandler(ActionEvent event) {
    	App.iniTelaLogin();
    }

    @FXML
    void initialize() {
    	try {
			pesquisaPane = DaoRes.getInstance().carregarPaneFXML("PesquisaPane");
			tab.setContent(pesquisaPane);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
