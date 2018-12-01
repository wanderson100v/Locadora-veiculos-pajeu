package controller;

import entidade.Entidade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import view.Alerta;

public abstract class CRUDController<T extends Entidade> {
	
	@FXML
    protected Button editarBtn;

    @FXML
    protected Button excluirBtn;
   
    @FXML
    protected Button limparBtn;

    @FXML
    protected Button cadastrarBtn;
    
    @FXML
    private ButtonBar acoesBar;
    
    @FXML
    private VBox entidadePane;
    
    @FXML
    protected TableView<T> entidadeTabela;

    
    protected Alerta alerta = Alerta.getInstance();
    
    @FXML
    void initialize() {
    	editarBtn.setDisable(true);
    	excluirBtn.setDisable(true);
    }
    
    @FXML
    void crudHandle(ActionEvent e) {
    	Button button = (Button)e.getSource();
    	crudHandle(button);
    	if(button == editarBtn) {
    		cadastrarBtn.setDisable(true);
    	}else if(button == excluirBtn) {
    		cadastrarBtn.setDisable(false);
    		editarBtn.setDisable(true);
    		excluirBtn.setDisable(true);
    	}else if(button == limparBtn) {
    		cadastrarBtn.setDisable(false);
    		excluirBtn.setDisable(true);
    		editarBtn.setDisable(true);
		}else if(button == cadastrarBtn) {
			cadastrarBtn.setDisable(true);
			excluirBtn.setDisable(true);
			editarBtn.setDisable(true);
		}
    	
    }

    @FXML
    void mouseClikHandle(MouseEvent e) {
    	Entidade entidade = entidadeTabela.getSelectionModel().getSelectedItem();
    	if(entidade != null) {
    		cadastrarBtn.setDisable(true);
    		popularDescricao(entidade);
    		editarBtn.setDisable(false);
    		excluirBtn.setDisable(false);
    	}
    }
    
    abstract void crudHandle(Button btn);

    abstract void popularTabela(String busca);
    
    abstract void popularDescricao(Entidade entidade);
    
    abstract void limparCampos();
    
    public ButtonBar getAcoesBar() {
		return acoesBar;
	}
    
    public TableView<T> getEntidadeTabela() {
		return entidadeTabela;
	}
    
	public VBox getEntidadePane() {
		return entidadePane;
	}
}
