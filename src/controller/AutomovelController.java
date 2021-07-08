package controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import business.BoAutomovel;
import business.IBoAutomovel;
import dao.DaoRes;
import entidade.Acessorio;
import entidade.Automovel;
import entidade.Entidade;
import entidade.Filial;
import enumeracoes.TamanhoVeiculo;
import enumeracoes.TipoAirBag;
import enumeracoes.TipoAutomovel;
import enumeracoes.TipoCambio;
import enumeracoes.TipoCombustivel;
import excecoes.BoException;
import excecoes.DaoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import view.Mascara;

public class AutomovelController extends CRUDController<Automovel> {

    @FXML
    private TableColumn<Automovel, String> placaCln;

    @FXML
    private TableColumn<Automovel, TipoAutomovel> tipoCln;

    @FXML
    private TableColumn<Automovel, TamanhoVeiculo> tamanhoCln;

    @FXML
    private TableColumn<Automovel, TipoCambio> cambioCln;

    @FXML
    private TableView<Acessorio> aceTabela;

    @FXML
    private TableColumn<Acessorio, String> aceNomeCln;

    @FXML
    private TableColumn<Acessorio, Float> aceValorCln;

    @FXML
    private TableColumn<Acessorio, Boolean> aceDepreciadoCln;

    @FXML
    private Button escolherAcessorioBtn;

    @FXML
    private TextField placaFld;

    @FXML
    private TextField corFld;

    @FXML
    private TextField fabricanteFld;

    @FXML
    private ComboBox<Integer> anoFabricacaoBox;

    @FXML
    private RadioButton simAtivoRb;

    @FXML
    private RadioButton naoAtivoRb;

    @FXML
    private TextField filialFld;

    @FXML
    private Button selectFilialBtn;

    @FXML
    private TextField modeloFld;

    @FXML
    private ComboBox<Integer> portasBox;

    @FXML
    private ComboBox<Integer> passageirosBox;

    @FXML
    private ComboBox<Integer> anoModeloBox;

    @FXML
    private TextField numChassiFld;

    @FXML
    private TextField numMotorFld;

    @FXML
    private ComboBox<TipoCombustivel> combustivelBox;

    @FXML
    private TextField quilometragemFld;

    @FXML
    private TextField torqueMotorFld;

    @FXML
    private RadioButton simLocadoRb;
   
    @FXML
    private ComboBox<TipoCambio> cambioBox;

    @FXML
    private ComboBox<TipoAutomovel> automovelBox;

    @FXML
    private ComboBox<TamanhoVeiculo> tamanhoBox;

    @FXML
    private ComboBox<TipoAirBag> airbagBox;

    @FXML
    private RadioButton naoLocadoRb;
    
    private Automovel automovel;
    
    private Filial filial;
    
    private IBoAutomovel boAutomovel = BoAutomovel.getInstance();
    
    @FXML
    void initialize() {
    	super.initialize();
    	ToggleGroup toggleGroup = new ToggleGroup();
    	simAtivoRb.setToggleGroup(toggleGroup);
    	naoAtivoRb.setToggleGroup(toggleGroup);
    	
    	ToggleGroup toggleGroup2 = new ToggleGroup();
    	simLocadoRb.setToggleGroup(toggleGroup2);
    	naoLocadoRb.setToggleGroup(toggleGroup2);
    	
    	placaCln.setCellValueFactory( new PropertyValueFactory<>("placa"));
    	tipoCln.setCellValueFactory( new PropertyValueFactory<>("tipoAutomovel"));
    	tamanhoCln.setCellValueFactory( new PropertyValueFactory<>("tamanhoVeiculo"));
    	cambioCln.setCellValueFactory( new PropertyValueFactory<>("tipoCambio"));
    	
    	aceNomeCln.setCellValueFactory( new PropertyValueFactory<>("nome"));
    	aceValorCln.setCellValueFactory( new PropertyValueFactory<>("valor"));
    	aceDepreciadoCln.setCellValueFactory( new PropertyValueFactory<>("depreciado"));
    
    	cambioBox.getItems().addAll(TipoCambio.values());
    	automovelBox.getItems().addAll(TipoAutomovel.values());
    	tamanhoBox.getItems().addAll(TamanhoVeiculo.values());
    	airbagBox.getItems().addAll(TipoAirBag.values());
    	
    	combustivelBox.getItems().addAll(TipoCombustivel.values());
    	Integer anoVeiculosModernos = 1886;
    	Integer proximosDezAnos = LocalDate.now().getYear() + 3;
    	for(int i = proximosDezAnos ; i >= anoVeiculosModernos ; i --)
    		anoFabricacaoBox.getItems().add(i);
    	anoModeloBox.getItems().addAll(anoFabricacaoBox.getItems());
    	for(int i = 1 ; i <50 ; i ++)
    		passageirosBox.getItems().add(i);
    	portasBox.getItems().addAll(passageirosBox.getItems());
    	
    	torqueMotorFld.setTextFormatter(Mascara.getMascaraNumericoFlutuante());
    	quilometragemFld.setTextFormatter(Mascara.getMascaraNumericoInteiro());
    }
    
	@Override
	void crudHandle(Button btn) {
		try {	
    		if(btn == cadastrarBtn || btn == editarBtn) {
    			Automovel automovel = (btn == cadastrarBtn)? 
    					new Automovel() :this.automovel;
    			
    			
    			automovel.setAtivo(simAtivoRb.isSelected());
    			automovel.setLocado(simLocadoRb.isSelected());
    			automovel.setPlaca(placaFld.getText().trim());
    			automovel.setCor(corFld.getText().trim());
    			automovel.setModelo(modeloFld.getText().trim());
    			automovel.setFabricante(fabricanteFld.getText().trim());
    			automovel.setNumeroChassi(numChassiFld.getText().trim());
    			automovel.setNumeroMotor(numMotorFld.getText().trim());
    			automovel.setTorqueMotor(Float.parseFloat(torqueMotorFld.getText()));
    			automovel.setTipoCombustivel(combustivelBox.getValue());
    			automovel.setQuilometragem(Integer.parseInt(quilometragemFld.getText()));
    			automovel.setAnoFabricante(anoFabricacaoBox.getValue());
    			automovel.setAnoModelo(anoModeloBox.getValue());
    			automovel.setQuantidadePortas(portasBox.getValue());
    			automovel.setQuantidadePassageiro(passageirosBox.getValue());
    			automovel.setFilial(filial);
    			
    			automovel.setTipoCambio(cambioBox.getValue());
    			automovel.setTipoAutomovel(automovelBox.getValue());
    			automovel.setTamanhoVeiculo(tamanhoBox.getValue());
    			automovel.setTipoAirBag(airbagBox.getValue());
    			automovel.setAcessorios(aceTabela.getItems());
    			
    			System.out.println(automovel.getFilial());
    			
				boAutomovel.cadastrarEditar(automovel);
				alerta.imprimirMsg("Sucesso ao cadastrar","Automóvel"
						+((automovel.equals(this.automovel))? "editado": "cadastrado") 
						+" com sucesso", AlertType.INFORMATION);
				
	    	}else if(btn == excluirBtn){
	    		
	    		boAutomovel.excluir(this.automovel);
	    		alerta.imprimirMsg("Sucesso ao exluir","Automóvel exlcuido com sucesso", 
	    				AlertType.INFORMATION);
	    		limparCampos();
	    	
	    	}else if(btn == limparBtn){
	    		
	    		limparCampos();
	    		
	    	}
    	} catch (BoException e) {
			alerta.imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	void popularTabela(String busca) {
		try {
			List<Automovel> automoveis = boAutomovel.buscaPorBuscaAbrangente(busca);
			entidadeTabela.getItems().setAll(automoveis);
			alerta.imprimirMsg("Busca concluída","Foram econtrados "+automoveis.size()+" resultado(s)",AlertType.INFORMATION);
		} catch (BoException e) {
			alerta.imprimirMsg("Erro",e.getMessage(),AlertType.ERROR);
		}
	}

	@Override
	void popularDescricao(Entidade entidade) {
		this.automovel = (Automovel) entidade;
		
		simAtivoRb.setSelected(automovel.isAtivo());
		simLocadoRb.setSelected(automovel.getLocado());
		placaFld.setText(automovel.getPlaca());
		corFld.setText(automovel.getCor());
		modeloFld.setText(automovel.getModelo());
		fabricanteFld.setText(automovel.getFabricante());
		numChassiFld.setText(automovel.getNumeroChassi());
		numMotorFld.setText(automovel.getNumeroMotor());
		torqueMotorFld.setText(""+automovel.getTorqueMotor());
		combustivelBox.setValue(automovel.getTipoCombustivel());
		quilometragemFld.setText(""+automovel.getQuilometragem());
		anoFabricacaoBox.setValue(automovel.getAnoFabricante());
		anoModeloBox.setValue(automovel.getAnoModelo());
		portasBox.setValue(automovel.getQuantidadePortas());
		passageirosBox.setValue(automovel.getQuantidadePassageiro());
		
		cambioBox.setValue(automovel.getTipoCambio());
		automovelBox.setValue(automovel.getTipoAutomovel());
		tamanhoBox.setValue(automovel.getTamanhoVeiculo());
		airbagBox.setValue(automovel.getTipoAirBag());
		aceTabela.getItems().setAll(automovel.getAcessorios());
		if(automovel.getFilial() != null)
			filialFld.setText(automovel.getFilial().toString());
		alerta.imprimirMsg("Categoria do Véiculo",automovel.getCategoriaVeiculo().toString(), AlertType.INFORMATION);
	}

	@Override
	void limparCampos() {
		this.automovel = null;
		this.filial = null;
		
		simAtivoRb.setSelected(true);
		simLocadoRb.setSelected(false);
		placaFld.clear();
		corFld.clear();
		modeloFld.clear();
		fabricanteFld.clear();
		numChassiFld.clear();
		numMotorFld.clear();
		torqueMotorFld.clear();
		combustivelBox.setValue(null);
		quilometragemFld.clear();
		anoFabricacaoBox.setValue(null);
		anoModeloBox.setValue(null);
		portasBox.setValue(null);
		passageirosBox.setValue(null);
		aceTabela.getItems().clear();
		filialFld.clear();
		
		cambioBox.setValue(null);
		automovelBox.setValue(null);
		tamanhoBox.setValue(null);
		airbagBox.setValue(null);
		
		System.gc();
	}
	
	@FXML
    void actionHandle(ActionEvent event) {
		try {
			if(event.getSource() == selectFilialBtn) {
				Filial filial = Util.selecionarFilialEmDialogo();
				if(filial!= null) {
					filialFld.setText(filial.toString());
					this.filial = filial;
				}
			}else if(event.getSource() == escolherAcessorioBtn) {
					Alert alerta = new Alert(AlertType.NONE);
					SelecionarAcessoriosController selecionarAcessoriosController = (SelecionarAcessoriosController) DaoRes.getInstance().carregarControllerFXML("SelecionarAcessoriosDialog");
					selecionarAcessoriosController.getMeusListView().getItems().addAll(aceTabela.getItems());
					alerta.setDialogPane(selecionarAcessoriosController.getSelecionarAcessoriosDialog());
					Optional<ButtonType> result = alerta.showAndWait();
					if(result.isPresent() && result.get() == ButtonType.FINISH) {
						aceTabela.getItems().clear();
						aceTabela.getItems().addAll(selecionarAcessoriosController.getMeusListView().getItems());
					}
			}
		} catch (DaoException e) {
			alerta.imprimirMsg("Erro", e.getMessage(), AlertType.ERROR);
		}
    }
}
