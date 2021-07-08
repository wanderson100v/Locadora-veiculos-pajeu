package controller;

import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import view.Alerta;

public class PesquisaController {

    @FXML
    private AnchorPane pesquisaPane;
    
    @FXML
    private GridPane tabelaPane;

    @FXML
    private Tab detablesTab;

    @FXML
    private ScrollPane detalhesScroll;

    @FXML
    private Button detalhesBtn;

    @FXML
    private TextField pesquisaFld;
    
    @FXML
    private TabPane detalhesTabPane;

    @FXML
    private SplitPane splitPane;
    
    @FXML
    private ComboBox<String> filtroBox;
    
    @FXML
    private ButtonBar acoesBar;
    
    private Alerta alerta = Alerta.getInstance();
    
    private HashMap<String,CRUDController<?>> controladores = new HashMap<>();
    
    
	@FXML
    void actionHandle(ActionEvent e) {
    	
		if (e.getSource() == pesquisaFld) {
    		
    		String busca = pesquisaFld.getText().trim();
			if(filtroBox.getValue() != null)
				controladores.get(filtroBox.getValue()).popularTabela(busca);
			else
				alerta.imprimirMsg("Busca invalida","Nenhum filtro selecionado",AlertType.WARNING);
		}
    	else if(e.getSource() == filtroBox ) {
    			
			tabelaPane.getChildren().setAll(controladores.get(filtroBox.getValue()).getEntidadeTabela());
			detalhesScroll.setContent(controladores.get(filtroBox.getValue()).getEntidadePane());
			acoesBar.getButtons().setAll(controladores.get(filtroBox.getValue()).getAcoesBar().getButtons());
			
    	}
    	else if(e.getSource() == detalhesBtn) {
    		if(!detalhesTabPane.getTabs().contains(detablesTab)) {
    			detalhesTabPane.getTabs().add(detablesTab);
    		}
    		splitPane.setDividerPositions(0.6);
    	}
    }
	
	
	public AnchorPane getPesquisaPane() {
		return pesquisaPane;
	}
	
	public HashMap<String, CRUDController<?>> getControladores() {
		return controladores;
	}
	
	public ComboBox<String> getFiltroBox() {
		return filtroBox;
	}
    
}
