package controller;

import java.util.HashMap;

import javafx.application.Platform;
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
import javafx.scene.layout.GridPane;
import model.dao.DaoRes;
import model.enumeracoes.Cargo;
import model.excecoes.DaoException;
import model.vo.Funcionario;

public class PesquisaController extends Controller{

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
    private Button pesquisaBtn; 
    
    @FXML
    private TabPane detalhesTabPane;

    @FXML
    private SplitPane splitPanePesquisa;
    
    @FXML
    private ComboBox<String> filtroBox;
    
    @FXML
    private ButtonBar acoesBar;
    
    private HashMap<String,CRUDController<?>> controladores = new HashMap<>();
    
	@FXML
    void actionHandle(ActionEvent e) {
    	String chaveControlador = filtroBox.getValue();
		if (e.getSource() == pesquisaFld || e.getSource() == pesquisaBtn) {
    		String busca = pesquisaFld.getText().trim();
    		if(chaveControlador != null)
				controladores.get(chaveControlador).popularTabela(busca);
			else
				alerta.imprimirMsg("Busca invalida","Nenhum filtro selecionado",AlertType.WARNING);
		}
    	else if(e.getSource() == filtroBox ) {
    			
			tabelaPane.getChildren().setAll(controladores.get(chaveControlador).getEntidadeTabela());
			detalhesScroll.setContent(controladores.get(chaveControlador).getEntidadePane());
			acoesBar.getButtons().setAll(controladores.get(chaveControlador).getAcoesBar().getButtons());
			
    	}
    	else if(e.getSource() == detalhesBtn) {
    		if(!detalhesTabPane.getTabs().contains(detablesTab)) {
    			detalhesTabPane.getTabs().add(detablesTab);
    		}
    		splitPanePesquisa.setDividerPositions(0.6);
    	}
    }
	
	
	public SplitPane getPesquisaPane() {
		return splitPanePesquisa;
	}

	@Override
	public void atualizar(Funcionario funcionario, Cargo cargo) {
		addControladores(cargo);
	}
	
	
	public void addControladores(Cargo cargo) {
		Platform.runLater(()->{
			try {
				DaoRes daoRes = DaoRes.getInstance();
				controladores.clear();
				
				ClienteJuridicoController clienteJuridicoController = (ClienteJuridicoController) daoRes.carregarControllerFXML("ClienteJuridicoPane");
				ClienteFisicoController clienteFisicoController = (ClienteFisicoController) daoRes.carregarControllerFXML("ClienteFisicoPane");
				CaminhonetaCargaController caminhonetaCargaController = (CaminhonetaCargaController) daoRes.carregarControllerFXML("CaminhonetaCargaPane");
				AutomovelController automovelController = (AutomovelController) daoRes.carregarControllerFXML("AutomovelPane");
				ManutencaoController manutencaoController = (ManutencaoController) daoRes.carregarControllerFXML("ManutencaoPane");
				
				controladores.put("Clientes j�ridicos",clienteJuridicoController);
				controladores.put("Clientes f�sicos", clienteFisicoController);
				controladores.put("Caminhoneta Carga", caminhonetaCargaController);
				controladores.put("Autom�vel", automovelController);
				controladores.put("Manuten��o", manutencaoController);
				
				if(cargo != Cargo.AT) {
					FilialController filialController = (FilialController) daoRes.carregarControllerFXML("FilialPane");
					AcessorioController acessorioController = (AcessorioController) daoRes.carregarControllerFXML("AcessorioPane");
					CategoriaVeiculoController categoriaVeiculoController = (CategoriaVeiculoController) daoRes.carregarControllerFXML("CategoriaVeiculoPane");
					FuncionarioController funcionrarioController = (FuncionarioController) daoRes.carregarControllerFXML("FuncionarioPane");
					controladores.put("Filiais", filialController);
					controladores.put("Acess�rios", acessorioController);
					controladores.put("Categ�ria de V�iculos", categoriaVeiculoController);
					controladores.put("Funcion�rios", funcionrarioController);
				}
				filtroBox.getItems().addAll(controladores.keySet());
			} catch (DaoException e) {
				e.printStackTrace();
			}
		});
	}
	
	
	public MementoBusca createMementoBusca() {
		String busca = pesquisaFld.getText();
		String filtroSelecionado = filtroBox.getSelectionModel().getSelectedItem();
		return new MementoBusca(busca,filtroSelecionado);
	}
	
	public void setMemento(MementoBusca memento) {
		String busca = memento.getBusca();
		String filtroSelecionado = memento.getFiltroSelecionado();
		pesquisaFld.setText(busca);
		filtroBox.getSelectionModel().select(filtroSelecionado);
	}
	
	
	public class MementoBusca {
		
		private final String busca;
	    
		final String  filtroSelecionado;
		
		public MementoBusca(String busca, String filtroSelecionado) {
			this.busca = busca;
			this.filtroSelecionado = filtroSelecionado;
		}
		
		private String getBusca() {
			return busca;
		}
		
		private String getFiltroSelecionado() {
			return filtroSelecionado;
		}

	}
	
}
