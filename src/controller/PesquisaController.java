package controller;

import java.util.HashMap;
import java.util.List;

import business.BoFisico;
import business.BoJuridico;
import dao.DaoRes;
import entidade.Entidade;
import entidade.Fisico;
import entidade.Juridico;
import excecoes.BoException;
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
    
    private CRUDController<Juridico> clienteJuridicoController;
    
    private CRUDController<Fisico> clienteFisicoController;
    
    private Alerta alerta = Alerta.getInstance();
    
    private HashMap<String,CRUDController<?>> mapa = new HashMap<>();
    
    @FXML
    void initialize() {
    	try {
    		clienteJuridicoController = (ClienteJuridicoController) DaoRes.getInstance().carregarControllerFXML("ClienteJuridicoPane");
    		clienteFisicoController = (ClienteFisicoController) DaoRes.getInstance().carregarControllerFXML("ClienteFisicoPane");
    		mapa.put("Juridico", clienteJuridicoController);
			mapa.put("Fisico", clienteFisicoController);
			filtroBox.getItems().addAll(mapa.keySet());
    		
    		transition = new TranslateTransition();
			transition.setNode(detalhesPane);
			transition.setDuration(new Duration(1000));
			
    	} catch (DaoException e) {
			e.printStackTrace();
		}
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
    void actionHandle(ActionEvent e) {
    	if (e.getSource() == pesquisaFld) {
    		String busca = pesquisaFld.getText().trim();
    		if(!busca.isEmpty()) {
    			if(filtroBox.getValue() != null) {
    				List entidades = null;
    				try {
	    				if(filtroBox.getValue().equals("Juridico")) {
			    			Juridico juridico = new Juridico();
				    		juridico.setNome(busca);
				    		juridico.setCodigo(busca);
				    		juridico.setCnpj(busca);
				    		juridico.setInscricaoEstadual(busca);
				    		juridico.setEmail(busca);
				    		juridico.setTelefone(busca);
				    		entidades  = BoJuridico.getInstance().buscaPorBusca(juridico);
			    		}else if(filtroBox.getValue().equals("Fisico")) {
			    			Fisico fisico = new Fisico();
			    			fisico.setNome(busca);
			    			fisico.setCpf(busca);
			    			fisico.setCodigo(busca);
			    			fisico.setEmail(busca);
			    			fisico.setTelefone(busca);
			    			fisico.setIdentificacaoMotorista(busca);
			    			fisico.setNumeroHabilitacao(busca);
			    			entidades  = BoFisico.getInstance().buscaPorBusca(fisico);
			    		}
	    				mapa.get(filtroBox.getValue()).getEntidadeTabela().getItems().setAll(entidades);
	    				mapa.get(filtroBox.getValue()).getEntidadeTabela().refresh();
						alerta.imprimirMsg("Busca concluída","Foram econtrados "+entidades.size()+" resultado(s)",AlertType.INFORMATION);
	    			} catch (BoException e1) {
						e1.printStackTrace();
					}
    			}else
    				alerta.imprimirMsg("Busca invalida","Nenhum filtro selecionado",AlertType.WARNING);
    		}else 
    			alerta.imprimirMsg("Busca invalida","O campo de busca não pode estar vazio",AlertType.WARNING);
    		
		}
    	else if(e.getSource() == filtroBox ) {
    			
			tabelaPane.getChildren().setAll(mapa.get(filtroBox.getValue()).getEntidadeTabela());
			detalhesScroll.setContent(mapa.get(filtroBox.getValue()).getEntidadePane());
			acoesBar.getButtons().setAll(mapa.get(filtroBox.getValue()).getAcoesBar().getButtons());
			
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
