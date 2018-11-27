package controller;

import dao.DaoRes;
import entidade.Juridico;
import excecoes.DaoException;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class PesquisaController {
	

    @FXML
    private AnchorPane pesquisaPane;
    
    @FXML
    private AnchorPane tabelaPane;

    @FXML
    private BorderPane detalhesPane;

    @FXML
    private ScrollPane detalhesScroll;

    @FXML
    private Button detalhesBtn;

    @FXML
    private TextField pesquisaFld;

    @FXML
    private ComboBox<String> filtroBox;
    
    private TranslateTransition transition;
    
    private boolean detalhesAtivo = false;
    
    private ClienteJuridicoController clienteJuridicoController;
    
    
    @FXML
    void actionHandle(ActionEvent e) {
    	if(e.getSource() == filtroBox ) {
    			tabelaPane.getChildren().setAll(clienteJuridicoController.getClienteJuridicoTbl());
				detalhesScroll.setContent(clienteJuridicoController.getClienteJuridicoPane());
    	}
    	if(e.getSource() == detalhesBtn) {
    		if(!detalhesAtivo) {
    			if(pesquisaPane.getWidth() < detalhesPane.getWidth() + tabelaPane.getWidth()) { // 
    				transition.setFromX(0);
    				transition.setToX(-(detalhesPane.getWidth() + tabelaPane.getWidth() - pesquisaPane.getWidth()));
	    			detalhesAtivo = true;
	    			transition.play();
    			}
    		}else {
    			transition.setFromX(transition.getToX());
    			transition.setToX(0);
    			detalhesAtivo = false;
    			transition.play();
    		}
    	}
    }
    
    @FXML
    void initialize() {
    	try {
    		clienteJuridicoController = (ClienteJuridicoController) DaoRes.getInstance().carregarControllerFXML("ClienteJuridicoPane");
    		transition = new TranslateTransition();
			transition.setNode(detalhesPane);
			transition.setDuration(new Duration(1000));
			filtroBox.getItems().add("Juridico");
    	} catch (DaoException e) {
			e.printStackTrace();
		}
    }

}
