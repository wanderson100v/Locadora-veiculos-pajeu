package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.enumeracoes.Cargo;
import model.excecoes.BoException;
import model.vo.Entidade;
import model.vo.Funcionario;

public abstract class CRUDController<T extends Entidade> extends Controller {
	
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

    @FXML
    void initialize() {
    	editarBtn.setDisable(true);
    	excluirBtn.setDisable(true);
    }
    
    @FXML
    void crudHandle(ActionEvent e) {
    	Button button = (Button)e.getSource();
    	String erro = null;
    	if(button == editarBtn || button == cadastrarBtn) {
    		boolean isOpCadastro = button == cadastrarBtn;
    		try {
    			cadastrarEditar(isOpCadastro, (isOpCadastro)? "cadastrado(a)": "editado(a)");
    			cadastrarBtn.setDisable(true);
				excluirBtn.setDisable(false);
    		}catch (BoException boException) {
    			cadastrarBtn.setDisable(!isOpCadastro);
    			erro = boException.getMessage();
			}
    	}else if(button == excluirBtn) {
    		cadastrarBtn.setDisable(false);
    		editarBtn.setDisable(true);
    		try {
    			excluir();
	    		excluirBtn.setDisable(true);
	    		limparCampos();
    		}catch (BoException boException) {
    			excluirBtn.setDisable(false);
    			erro = boException.getMessage();
    		}
    	}else if(button == limparBtn) {
    		cadastrarBtn.setDisable(false);
    		excluirBtn.setDisable(true);
    		editarBtn.setDisable(true);
    		limparCampos();
    	}
    	if(erro != null)
    		alerta.imprimirMsg("Erro",erro, AlertType.ERROR);
    }
    
    protected abstract void cadastrarEditar(Boolean cadastrar, String opcao) throws BoException;
    
    protected abstract void excluir() throws BoException;
    
    abstract void limparCampos();

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
    
    abstract void popularDescricao(Entidade entidade);

    abstract void popularTabela(String busca);
    
	@Override
	public void atualizar(Funcionario funcionario, Cargo cargo) {
		limparCampos();
		entidadeTabela.getItems().clear();
	}
	
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
