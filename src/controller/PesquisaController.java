package controller;

import java.util.List;

import business.BoJuridico;
import dao.DaoRes;
import entidade.Juridico;
import excecoes.BoException;
import excecoes.DaoException;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
    
    private ClienteJuridicoController clienteJuridicoController;
    
    private Alerta alerta = Alerta.getInstance();
    
    @FXML
    void actionHandle(ActionEvent e) {
    	if (e.getSource() == pesquisaFld) {
    		String busca = pesquisaFld.getText().trim();
    		if(!busca.isEmpty()) {
    			if(filtroBox.getValue() != null) {
	    			if(filtroBox.getValue().equals("Juridico")) {
		    			Juridico juridico = new Juridico();
			    		juridico.setNome(busca);
			    		juridico.setCnpj(busca);
			    		juridico.setInscricaoEstadual(busca);
			    		juridico.setEmail(busca);
			    		juridico.setTelefone(busca);
		    			try {
		    				List<Juridico> juridicos = BoJuridico.getInstance().buscaPorBusca(juridico);
		    				clienteJuridicoController.getEntidadeTabela().getItems().setAll(juridicos);
							clienteJuridicoController.getEntidadeTabela().refresh();
							alerta.imprimirMsg("Busca concluída","Foram econtrados "+juridicos.size()+" resultado(s)",AlertType.INFORMATION);
		    			} catch (BoException e1) {
							e1.printStackTrace();
						}
		    		}	
    			}else
    				alerta.imprimirMsg("Busca invalida","Nenhum filtro selecionado",AlertType.WARNING);
    		}else 
    			alerta.imprimirMsg("Busca invalida","O campo de busca não pode estar vazio",AlertType.WARNING);
    		
		}
    	else if(e.getSource() == filtroBox ) {
    			tabelaPane.getChildren().setAll(clienteJuridicoController.getEntidadeTabela());
				detalhesScroll.setContent(clienteJuridicoController.getEntidadePane());
				acoesBar.getButtons().setAll(clienteJuridicoController.getAcoesBar().getButtons());
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
