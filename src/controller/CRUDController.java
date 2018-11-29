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
    private TableView<T> entidadeTabela;

    
    protected Alerta alerta = Alerta.getInstance();
    
    @FXML
    void initialize() {
    	editarBtn.setDisable(true);
    	excluirBtn.setDisable(true);
    }
    
    @FXML
    void crudHandle(ActionEvent e) {
    	Button button = (Button)e.getSource();
    	if(button == editarBtn) {
    		cadastrarBtn.setDisable(true);
    	}else if(button == excluirBtn) {
    		cadastrarBtn.setDisable(false);
    		editarBtn.setDisable(true);
    	}else if(button == limparBtn) {
    		cadastrarBtn.setDisable(false);
    		excluirBtn.setDisable(true);
    		editarBtn.setDisable(true);
		}else if(button == cadastrarBtn) {
			cadastrarBtn.setDisable(true);
			excluirBtn.setDisable(true);
			editarBtn.setDisable(true);
		}
    	crudHandle(button);
    }

    @FXML
    void mouseClikHandle(MouseEvent e) {
    	Entidade entidade = entidadeTabela.getSelectionModel().getSelectedItem();
    	if(entidade != null) {
    		cadastrarBtn.setDisable(true);
    		editarBtn.setDisable(false);
    		excluirBtn.setDisable(false);
    		popularTabela(entidade);
    	}
    }
    
    abstract void crudHandle(Button btn);

    abstract void limparCampos();
    
    abstract void popularTabela(Entidade entidade);
    
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
