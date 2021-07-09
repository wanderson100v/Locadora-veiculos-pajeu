package controller;

import model.excecoes.BoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import mode.business.BoAcessorio;
import mode.business.IBoAcessorio;
import model.entidade.Acessorio;

public class SelecionarAcessoriosController {

    @FXML
    private DialogPane selecionarAcessoriosDialog;

    @FXML
    private ListView<Acessorio> todosListView;

    @FXML
    private Button addBtn;

    @FXML
    private ListView<Acessorio> meusListView;

    @FXML
    private Button remBtn;
    
    private IBoAcessorio boAcessorio = BoAcessorio.getInstance();
    
    @FXML
    void initialize() {
    	try {
    		todosListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    		meusListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    		todosListView.getItems().addAll(boAcessorio.buscarAll());
		} catch (BoException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void actionHandle(ActionEvent event) {
    	if(event.getSource() == addBtn) {
    		for(Acessorio acessorio : todosListView.getSelectionModel().getSelectedItems()) {
    			boolean existe = false;
    			for(Acessorio meuAcessorio : meusListView.getItems())
    				if(meuAcessorio.getNome().equals(acessorio.getNome()))
    					existe = true;
    			if(!existe)
    				meusListView.getItems().add(acessorio);
    		}
    	}else if(event.getSource() == remBtn)
    		meusListView.getItems().removeAll(meusListView.getSelectionModel().getSelectedItems());
    }
    
    public ListView<Acessorio> getMeusListView() {
		return meusListView;
	}
    
    public DialogPane getSelecionarAcessoriosDialog() {
		return selecionarAcessoriosDialog;
	}
    
    
   
}
