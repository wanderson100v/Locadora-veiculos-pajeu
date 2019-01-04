package controller;


import business.BoFuncionario;
import business.IBoFuncionario;
import entidade.Funcionario;
import excecoes.BoException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import view.Alerta;

public class SelecionarFuncionarioController {

    @FXML
    private DialogPane selecionarFuncionarioDialog;

    @FXML
    private TextField pesquisaFld;

    @FXML
    private CheckBox buscaRapidaChk;

    @FXML
    private TableView<Funcionario> funcionarioTbl;

    @FXML
    private TableColumn<Funcionario, String> nomeCln;

    @FXML
    private TableColumn<Funcionario, String> cpfCln;

    private IBoFuncionario boFuncionario = BoFuncionario.getInstance();
    
    private Alerta alerta = Alerta.getInstance();
    
    @FXML
    void initialize() {
    	nomeCln.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	cpfCln.setCellValueFactory(new PropertyValueFactory<>("cpf"));
    	pesquisaFld.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(buscaRapidaChk.isSelected() && pesquisaFld.getText().trim().length() > 0) {
		    		try {
						funcionarioTbl.getItems().setAll(boFuncionario.buscaPorBuscaAbrangente(pesquisaFld.getText()));
					} catch (BoException e) {
						e.printStackTrace();
					}
		    	}
			}
		});
    }
    
    @FXML
    void actionHandle(ActionEvent event) {
    	if(!buscaRapidaChk.isSelected()) {
    		try {
				funcionarioTbl.getItems().setAll(boFuncionario.buscaPorBuscaAbrangente(pesquisaFld.getText()));
				alerta.imprimirMsg("Busca concluída","Foram econtrados "+funcionarioTbl.getItems().size()+" resultado(s)",AlertType.INFORMATION);
			} catch (BoException e) {
				alerta.imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
			}
    	}
    }
    
    public DialogPane getSelecionarFuncionarioDialog() {
		return selecionarFuncionarioDialog;
	}
    
    public TableView<Funcionario> getFuncionarioTbl() {
		return funcionarioTbl;
	}

}
