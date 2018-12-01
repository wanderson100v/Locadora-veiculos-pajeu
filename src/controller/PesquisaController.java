package controller;

import java.util.HashMap;
import dao.DaoRes;
import excecoes.DaoException;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import view.Alerta;

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
    
    @FXML
    private ButtonBar acoesBar;

    private TranslateTransition transition;
    
    private boolean detalhesAtivo = false;
    
    private Alerta alerta = Alerta.getInstance();
    
    private HashMap<String,CRUDController<?>> controladores = new HashMap<>();
    
    @FXML
    void initialize() {
    	try {
    		DaoRes daoRes = DaoRes.getInstance();
    		ClienteJuridicoController clienteJuridicoController = (ClienteJuridicoController) daoRes.carregarControllerFXML("ClienteJuridicoPane");
    		ClienteFisicoController clienteFisicoController = (ClienteFisicoController) daoRes.carregarControllerFXML("ClienteFisicoPane");
    		FilialController filialController = (FilialController) daoRes.carregarControllerFXML("FilialPane");
    		CaminhonetaCargaController caminhonetaCargaController = (CaminhonetaCargaController) daoRes.carregarControllerFXML("CaminhonetaCargaPane");
    		
    		controladores.put("Juridico",clienteJuridicoController);
			controladores.put("Fisico", clienteFisicoController);
			controladores.put("Filial", filialController);
			controladores.put("Caminhoneta Carga", caminhonetaCargaController);
			
			filtroBox.getItems().addAll(controladores.keySet());
    		
    		transition = new TranslateTransition();
			transition.setNode(detalhesPane);
			transition.setDuration(new Duration(1000));
			
    	} catch (DaoException e) {
			e.printStackTrace();
		}
    }
    
	@FXML
    void actionHandle(ActionEvent e) {
    	
		if (e.getSource() == pesquisaFld) {
    		
    		String busca = pesquisaFld.getText().trim();
    		
    		if(!busca.isEmpty())
    			if(filtroBox.getValue() != null)
    				controladores.get(filtroBox.getValue()).popularTabela(busca);
    			else
    				alerta.imprimirMsg("Busca invalida","Nenhum filtro selecionado",AlertType.WARNING);
    		else 
    			alerta.imprimirMsg("Busca invalida","O campo de busca não pode estar vazio",AlertType.WARNING);
    		
		}
    	else if(e.getSource() == filtroBox ) {
    			
			tabelaPane.getChildren().setAll(controladores.get(filtroBox.getValue()).getEntidadeTabela());
			detalhesScroll.setContent(controladores.get(filtroBox.getValue()).getEntidadePane());
			acoesBar.getButtons().setAll(controladores.get(filtroBox.getValue()).getAcoesBar().getButtons());
			
    	}
    	else if(e.getSource() == detalhesBtn) {
    		if(!detalhesAtivo) {
    			if(pesquisaPane.getWidth() < detalhesPane.getWidth() + tabelaPane.getWidth()) { 
    				transition.setFromX(0);
    				transition.setToX(-(detalhesPane.getWidth() + tabelaPane.getWidth() - pesquisaPane.getWidth() +30));
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
    
}
