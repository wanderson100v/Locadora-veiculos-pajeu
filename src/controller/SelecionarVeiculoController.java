package controller;

import model.enumeracoes.TipoCombustivel;
import model.excecoes.BoException;
import model.vo.CategoriaVeiculo;
import model.vo.Filial;
import model.vo.Veiculo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class SelecionarVeiculoController extends ControllerAdapter{

    @FXML
    private DialogPane selectVeiculoDialog;

    @FXML
    private Label tituloLbl;

    @FXML
    private TextField pesquisaFld;

    @FXML
    private CheckBox buscaRapidaChk;

    @FXML
    private TableView<Veiculo> veiculoTbl;

    @FXML
    private TableColumn<Veiculo, String> placaCln;

    @FXML
    private TableColumn<Veiculo, String> numChassiCln;

    @FXML
    private TableColumn<Veiculo, String> numMotorCln;

    @FXML
    private TableColumn<Veiculo, CategoriaVeiculo> categoriaCln;

    @FXML
    private TableColumn<Veiculo, Float> torqueCln;

    @FXML
    private TableColumn<Veiculo, TipoCombustivel> combusCln;
    
    @FXML
    private TableColumn<Veiculo, String> modeloCln;

    @FXML
    private TableColumn<Veiculo, Integer> anoModeloCln;

    @FXML
    private TableColumn<Veiculo, String> fabricanteCln;

    @FXML
    private TableColumn<Veiculo, Integer> anoFabriCln;

    @FXML
    private TableColumn<Veiculo, Integer> quilomCln;

    @FXML
    private TableColumn<Veiculo, String> corCln;

    @FXML
    private TableColumn<Veiculo, String> passagCln;

    @FXML
    private TableColumn<Veiculo, String> portasCln;
    
    @FXML
    private ComboBox<String> tipoBox;
    
    private CategoriaVeiculo categoriaVeiculo;
    
    private Filial filial;
    
    @FXML
    void initialize() {
    	placaCln.setCellValueFactory( new PropertyValueFactory<>("placa"));
		numChassiCln.setCellValueFactory( new PropertyValueFactory<>("numeroChassi"));
		numMotorCln.setCellValueFactory( new PropertyValueFactory<>("numeroMotor"));
		categoriaCln.setCellValueFactory( new PropertyValueFactory<>("categoriaVeiculo"));
		torqueCln.setCellValueFactory( new PropertyValueFactory<>("torqueMotor"));
		combusCln.setCellValueFactory( new PropertyValueFactory<>("tipoCombustivel"));
		modeloCln.setCellValueFactory( new PropertyValueFactory<>("modelo"));
		anoModeloCln.setCellValueFactory( new PropertyValueFactory<>("anoModelo"));
		fabricanteCln.setCellValueFactory( new PropertyValueFactory<>("fabricante"));
		anoFabriCln.setCellValueFactory( new PropertyValueFactory<>("anoFabricante"));
		quilomCln.setCellValueFactory( new PropertyValueFactory<>("quilometragem"));
		corCln.setCellValueFactory( new PropertyValueFactory<>("cor"));
		passagCln.setCellValueFactory( new PropertyValueFactory<>("quantidadePassageiro"));
		portasCln.setCellValueFactory( new PropertyValueFactory<>("quantidadePortas"));
		
		tipoBox.getItems().addAll("Automovel/Passageiro","Caminhoneta de carga");
		
		pesquisaFld.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(buscaRapidaChk.isSelected() && pesquisaFld.getText().trim().length() > 0) {
		    		try {
		    			if(categoriaVeiculo != null && filial != null) 
							veiculoTbl.getItems().setAll(fachadaModel.buscarVeiculosDisponivel(filial.getId(), categoriaVeiculo.getId(), pesquisaFld.getText().trim()));
						else if(filial != null) 
							veiculoTbl.getItems().setAll(fachadaModel.buscarVeiculosDisponivel(filial.getId(), pesquisaFld.getText().trim()));
						else
							if(tipoBox.getValue().equals("Automovel/Passageiro"))
								veiculoTbl.getItems().setAll(fachadaModel.buscarAutomoveis(pesquisaFld.getText().trim()));
							else
								veiculoTbl.getItems().setAll(fachadaModel.buscarCaminhonetasCarga(pesquisaFld.getText().trim()));
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
				if(categoriaVeiculo != null && filial != null) 
					veiculoTbl.getItems().setAll(fachadaModel.buscarVeiculosDisponivel(filial.getId(), categoriaVeiculo.getId(), pesquisaFld.getText().trim()));
				else if(filial != null) 
					veiculoTbl.getItems().setAll(fachadaModel.buscarVeiculosDisponivel(filial.getId(),pesquisaFld.getText().trim()));
				else 
					veiculoTbl.getItems().setAll(fachadaModel.buscarVeiculos(pesquisaFld.getText().trim()));
				
				alerta.imprimirMsg("Busca concluída","Foram econtrados "+veiculoTbl.getItems().size()+" resultado(s)",AlertType.INFORMATION);
			} catch (BoException e) {
				alerta.imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
			}
    	}
    }

    
    public TableView<Veiculo> getVeiculoTbl() {
    	return veiculoTbl;
    }
    
    public DialogPane getSelectVeiculoDialog() {
		return selectVeiculoDialog;
	}
    
    public boolean paremetrizadoPor(CategoriaVeiculo categoriaVeiculo, Filial filial) throws BoException{
		veiculoTbl.getItems().setAll(fachadaModel.buscarVeiculosDisponivel(filial.getId(), categoriaVeiculo.getId(),""));
    	if(veiculoTbl.getItems().size() == 0)
    		return false;
		tituloLbl.setText("Selecione veículo para a categoria "+categoriaVeiculo.getTipo()+ "na filial "+filial.getNome());
		this.categoriaVeiculo = categoriaVeiculo;
    	this.filial = filial;
    	return true;
    }
    
    public void paremetrizadoPor(Filial filial) {
    	if(filial!= null)
    		tituloLbl.setText("Selecione veículo na filial "+filial.getNome());
    	else
    		tipoBox.setVisible(true);
    	this.filial = filial;
    }
}
