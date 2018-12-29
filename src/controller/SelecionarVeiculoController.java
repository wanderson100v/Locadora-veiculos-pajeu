package controller;

import business.BoVeiculo;
import business.IBoVeiculo;
import entidade.CategoriaVeiculo;
import entidade.Filial;
import entidade.Veiculo;
import enumeracoes.TipoCombustivel;
import excecoes.BoException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import view.Alerta;

public class SelecionarVeiculoController {

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
    
    private IBoVeiculo boVeiculo = BoVeiculo.getInstance();
    private Alerta alerta = Alerta.getInstance();
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
		
		pesquisaFld.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(buscaRapidaChk.isSelected() && pesquisaFld.getText().trim().length() > 0) {
		    		try {
		    			if(categoriaVeiculo != null && filial != null) 
							veiculoTbl.getItems().setAll(boVeiculo.buscarVeiculosDisponivel(filial.getId(), categoriaVeiculo.getId(), pesquisaFld.getText().trim()));
						else if(filial != null) 
							veiculoTbl.getItems().setAll(boVeiculo.buscarVeiculosDisponivel(filial.getId(), pesquisaFld.getText().trim()));
						//else 
							// busca por busca
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
					veiculoTbl.getItems().setAll(boVeiculo.buscarVeiculosDisponivel(filial.getId(), categoriaVeiculo.getId(), pesquisaFld.getText().trim()));
				else if(filial != null) 
					veiculoTbl.getItems().setAll(boVeiculo.buscarVeiculosDisponivel(categoriaVeiculo.getId(),pesquisaFld.getText().trim()));
				//else 
					// busca por busca
				
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
    
    public void setParemetrizadoPor(CategoriaVeiculo categoriaVeiculo, Filial filial) {
    	tituloLbl.setText("Selecione Veículo para a categoria "+categoriaVeiculo.getTipo()+ "na filial "+filial.getNome());
    	this.categoriaVeiculo = categoriaVeiculo;
    	this.filial = filial;
    }
    
    public void setParemetrizadoPor(Filial filial) {
    	tituloLbl.setText("Selecione Veículo na filial "+filial.getNome());
    	this.filial = filial;
    }
}
