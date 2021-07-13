package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import model.dao.DaoRes;
import model.excecoes.DaoException;
import view.Alerta;

public class DadosController{

    @FXML
    private Button voltarPesquisaBtn;

    @FXML
    private Button salvarPesquisaBtn;

    @FXML
    private Button avancarPequisaBtn;

	private CareTakerPesquisa careTakerPesquisa;
	
	@FXML
	private SplitPane splitPanePesquisa;
	
    @FXML
    void initialize() {
    	try {
    		PesquisaController pesquisaController = (PesquisaController) DaoRes.getInstance().carregarControllerFXML("PesquisaPane");
			this.careTakerPesquisa = new CareTakerPesquisa(pesquisaController);
			this.splitPanePesquisa.getItems().setAll(pesquisaController.getPesquisaPane().getItems());
    	} catch (DaoException e) {
			e.printStackTrace();
			Alerta.getInstance().imprimirMsg("Erro",e.getMessage(),AlertType.ERROR);
			System.exit(0);
    	}
    }
    
    @FXML
    void actionHandler(ActionEvent event) {
    	if(event.getSource() == salvarPesquisaBtn) {
    		careTakerPesquisa.salvarEstadoPesquisa();
    	}else if(event.getSource() == voltarPesquisaBtn) {
    		careTakerPesquisa.voltarEstadoBuscaAnterior();
    	}else if(event.getSource() == avancarPequisaBtn) {
    		careTakerPesquisa.irParaProximaEstadoPesquisa();
    	}
    }
    
	@FXML
    void keyHandler(KeyEvent event) {
		 if(event.getCode() == new KeyCodeCombination(KeyCode.S, KeyCodeCombination.CONTROL_ANY).getCode()) {
			 careTakerPesquisa.salvarEstadoPesquisa();
		 }
		 else if(event.getCode() == new KeyCodeCombination(KeyCode.Z, KeyCodeCombination.CONTROL_ANY).getCode()) {
			 careTakerPesquisa.voltarEstadoBuscaAnterior();
		 }
		 else if(event.getCode() == new KeyCodeCombination(KeyCode.Y, KeyCodeCombination.CONTROL_ANY).getCode()) {
			 careTakerPesquisa.irParaProximaEstadoPesquisa();
		 }
	}
	
	public class CareTakerPesquisa{
		private List<PesquisaController.MementoBusca> mementos;
		private PesquisaController originator;
		private int buscaCorrente;
		
		public CareTakerPesquisa(PesquisaController originator) {
			this.originator = originator;
			this.mementos = new ArrayList<>();
		}
		
		public void salvarEstadoPesquisa(){
			PesquisaController.MementoBusca memento = originator.createMementoBusca();
			this.mementos.add(memento);
			this.buscaCorrente = mementos.indexOf(memento);
			if(mementos.size() > 20)
				mementos.remove(0);
		}
		
		public void irParaProximaEstadoPesquisa() {
			if(buscaCorrente < mementos.size()) {
				buscaCorrente++;
				originator.setMemento(mementos.get(buscaCorrente));
			}
		}
		
		public void voltarEstadoBuscaAnterior() {
			if(buscaCorrente > 0) {
				buscaCorrente--;
				originator.setMemento(mementos.get(buscaCorrente));
			}
		}
		
	}
}
