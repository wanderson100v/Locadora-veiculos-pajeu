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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.dao.DaoRes;
import model.enumeracoes.Cargo;
import model.excecoes.DaoException;
import model.vo.Funcionario;

public class PesquisaController extends Controller{

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
    
    private HashMap<String,CRUDController<?>> controladores = new HashMap<>();
   
	@FXML
    void actionHandle(ActionEvent e) {
    	String chaveControlador = filtroBox.getValue();
		if (e.getSource() == pesquisaFld) {
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
    		splitPane.setDividerPositions(0.6);
    	}
    }
	
	
	public AnchorPane getPesquisaPane() {
		return pesquisaPane;
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
				
				controladores.put("Clientes júridicos",clienteJuridicoController);
				controladores.put("Clientes físicos", clienteFisicoController);
				controladores.put("Caminhoneta Carga", caminhonetaCargaController);
				controladores.put("Automóvel", automovelController);
				controladores.put("Manutenção", manutencaoController);
				
				if(cargo != Cargo.AT) {
					FilialController filialController = (FilialController) daoRes.carregarControllerFXML("FilialPane");
					AcessorioController acessorioController = (AcessorioController) daoRes.carregarControllerFXML("AcessorioPane");
					CategoriaVeiculoController categoriaVeiculoController = (CategoriaVeiculoController) daoRes.carregarControllerFXML("CategoriaVeiculoPane");
					FuncionarioController funcionrarioController = (FuncionarioController) daoRes.carregarControllerFXML("FuncionarioPane");
					controladores.put("Filiais", filialController);
					controladores.put("Acessórios", acessorioController);
					controladores.put("Categória de Véiculos", categoriaVeiculoController);
					controladores.put("Funcionários", funcionrarioController);
				}
				filtroBox.getItems().addAll(controladores.keySet());
			} catch (DaoException e) {
				e.printStackTrace();
			}
		});
	}
	
}
