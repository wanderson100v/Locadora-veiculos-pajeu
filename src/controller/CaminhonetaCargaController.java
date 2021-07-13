package controller;

import java.time.LocalDate;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import model.enumeracoes.TipoAcionamentoEmbreagem;
import model.enumeracoes.TipoCombustivel;
import model.excecoes.BoException;
import model.vo.CaminhonetaCarga;
import model.vo.Entidade;
import model.vo.Filial;
import view.Mascara;

public class CaminhonetaCargaController extends CRUDController<CaminhonetaCarga> {

    @FXML
    private TableColumn<CaminhonetaCarga, String> placaCln;

    @FXML
    private TableColumn<CaminhonetaCarga, Float> potenciaCln;

    @FXML
    private TableColumn<CaminhonetaCarga, Float> desempenhoCln;

    @FXML
    private TableColumn<CaminhonetaCarga, Integer> capCargaCln;

    @FXML
    private TextField desempenhoFld;

    @FXML
    private TextField potenciaFld;

    @FXML
    private TextField disEixosFld;

    @FXML
    private TextField capCombustivelFld;

    @FXML
    private TextField capCargaFld;

    @FXML
    private ComboBox<TipoAcionamentoEmbreagem> aciEmbreagemBox;

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
    private RadioButton naoLocadoRb;

    private CaminhonetaCarga caminhonetaCarga;
    
    private Filial filial;
    
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
    	desempenhoCln.setCellValueFactory( new PropertyValueFactory<>("desempenho"));
    	potenciaCln.setCellValueFactory( new PropertyValueFactory<>("potencia"));
    	capCargaCln.setCellValueFactory( new PropertyValueFactory<>("capacidadeCarga"));
    
    	combustivelBox.getItems().addAll(TipoCombustivel.values());
    	aciEmbreagemBox.getItems().addAll(TipoAcionamentoEmbreagem.values());
    	
    	Integer anoVeiculosModernos = 1886;
    	Integer proximosDezAnos = LocalDate.now().getYear() + 10;
    	for(int i = proximosDezAnos ; i >= anoVeiculosModernos ; i --)
    		anoFabricacaoBox.getItems().add(i);
    	anoModeloBox.getItems().addAll(anoFabricacaoBox.getItems());
    	for(int i = 50 ; i > 0 ; i --)
    		passageirosBox.getItems().add(i);
    	portasBox.getItems().addAll(passageirosBox.getItems());
    	
    	desempenhoFld.setTextFormatter(Mascara.getMascaraNumericoFlutuante());
    	potenciaFld.setTextFormatter(Mascara.getMascaraNumericoFlutuante());
    	capCargaFld.setTextFormatter(Mascara.getMascaraNumericoInteiro());
    	capCombustivelFld.setTextFormatter(Mascara.getMascaraNumericoInteiro());
    	disEixosFld.setTextFormatter(Mascara.getMascaraNumericoFlutuante());
    	
    	torqueMotorFld.setTextFormatter(Mascara.getMascaraNumericoFlutuante());
    	quilometragemFld.setTextFormatter(Mascara.getMascaraNumericoInteiro());
    }
    
	@Override
	protected void cadastrarEditar(Boolean cadastrar,  String opcao) throws BoException {
		CaminhonetaCarga caminhonetaCarga = (cadastrar)? 
				new CaminhonetaCarga() :this.caminhonetaCarga;
		
		caminhonetaCarga.setAtivo(simAtivoRb.isSelected());
		caminhonetaCarga.setLocado(simLocadoRb.isSelected());
		caminhonetaCarga.setPlaca(placaFld.getText().trim());
		caminhonetaCarga.setCor(corFld.getText().trim());
		caminhonetaCarga.setModelo(modeloFld.getText().trim());
		caminhonetaCarga.setFabricante(fabricanteFld.getText().trim());
		caminhonetaCarga.setNumeroChassi(numChassiFld.getText().trim());
		caminhonetaCarga.setNumeroMotor(numMotorFld.getText().trim());
		caminhonetaCarga.setTorqueMotor(Float.parseFloat(torqueMotorFld.getText()));
		caminhonetaCarga.setTipoCombustivel(combustivelBox.getValue());
		caminhonetaCarga.setQuilometragem(Integer.parseInt(quilometragemFld.getText()));
		caminhonetaCarga.setAnoFabricante(anoFabricacaoBox.getValue());
		caminhonetaCarga.setAnoModelo(anoModeloBox.getValue());
		caminhonetaCarga.setQuantidadePortas(portasBox.getValue());
		caminhonetaCarga.setQuantidadePassageiro(passageirosBox.getValue());
		caminhonetaCarga.setFilial(filial);
		
		caminhonetaCarga.setDesenpenho(Float.parseFloat(desempenhoFld.getText()));
		caminhonetaCarga.setPotencia(Float.parseFloat(potenciaFld.getText()));
		caminhonetaCarga.setDistanciaEixos(Float.parseFloat(disEixosFld.getText()));
		caminhonetaCarga.setTipoAcionamentoEmbreagem(aciEmbreagemBox.getValue());
		caminhonetaCarga.setCapacidadeCarga(Integer.parseInt(capCargaFld.getText()));
		caminhonetaCarga.setCapacidadeCombustivel(Integer.parseInt(capCombustivelFld.getText()));
		
		fachadaModel.cadastrarEditarCaminhonetaCarga(caminhonetaCarga);
		alerta.imprimirMsg("Sucesso ao cadastrar","Caminhoneta de Carga "+opcao+" com sucesso", AlertType.INFORMATION);
	}

	@Override
	protected void excluir() throws BoException {
		fachadaModel.excluirCaminhonetaCarga(this.caminhonetaCarga);
		alerta.imprimirMsg("Sucesso ao exluir","Caminhoneta de Carga exlcuida com sucesso", 
				AlertType.INFORMATION);
	}

	@Override
	void popularTabela(String busca) {
		try {
			List<CaminhonetaCarga> caminhonetaCargas = fachadaModel.buscarCaminhonetasCarga(busca);
			entidadeTabela.getItems().setAll(caminhonetaCargas);
			alerta.imprimirMsg("Busca concluída","Foram econtrados "+caminhonetaCargas.size()+" resultado(s)",AlertType.INFORMATION);
		} catch (BoException e) {
			alerta.imprimirMsg("Erro",e.getMessage(),AlertType.ERROR);
		}
	}

	@Override
	void popularDescricao(Entidade entidade) {
		this.caminhonetaCarga = (CaminhonetaCarga) entidade;
		
		simAtivoRb.setSelected(caminhonetaCarga.isAtivo());
		simLocadoRb.setSelected(caminhonetaCarga.getLocado());
		placaFld.setText(caminhonetaCarga.getPlaca());
		corFld.setText(caminhonetaCarga.getCor());
		modeloFld.setText(caminhonetaCarga.getModelo());
		fabricanteFld.setText(caminhonetaCarga.getFabricante());
		numChassiFld.setText(caminhonetaCarga.getNumeroChassi());
		numMotorFld.setText(caminhonetaCarga.getNumeroMotor());
		torqueMotorFld.setText(""+caminhonetaCarga.getTorqueMotor());
		combustivelBox.setValue(caminhonetaCarga.getTipoCombustivel());
		quilometragemFld.setText(""+caminhonetaCarga.getQuilometragem());
		anoFabricacaoBox.setValue(caminhonetaCarga.getAnoFabricante());
		anoModeloBox.setValue(caminhonetaCarga.getAnoModelo());
		portasBox.setValue(caminhonetaCarga.getQuantidadePortas());
		passageirosBox.setValue(caminhonetaCarga.getQuantidadePassageiro());
		
		desempenhoFld.setText(""+caminhonetaCarga.getDesenpenho());
		potenciaFld.setText(""+caminhonetaCarga.getPotencia());
		disEixosFld.setText(""+caminhonetaCarga.getDistanciaEixos());
		aciEmbreagemBox.setValue(caminhonetaCarga.getTipoAcionamentoEmbreagem());
		capCargaFld.setText(""+caminhonetaCarga.getCapacidadeCarga());
		capCombustivelFld.setText(""+caminhonetaCarga.getCapacidadeCombustivel());
		
		if(caminhonetaCarga.getFilial() != null)
			filialFld.setText(caminhonetaCarga.getFilial().toString());
		alerta.imprimirMsg("Categoria do veículo",caminhonetaCarga.getCategoriaVeiculo().toString(), AlertType.INFORMATION);
		
	}

	@Override
	void limparCampos() {
		this.caminhonetaCarga = null;
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
		filialFld.clear();
		
		
		desempenhoFld.clear();
		potenciaFld.clear();
		disEixosFld.clear();
		aciEmbreagemBox.setValue(null);
		capCargaFld.clear();
		capCombustivelFld.clear();
		
		System.gc();
	}
	
	@FXML
    void actionHandle(ActionEvent event) {
		if(event.getSource() == selectFilialBtn) {
			Filial filial = Util.selecionarFilialEmDialogo();
			if(filial!= null) {
				filialFld.setText(filial.toString());
				this.filial = filial;
			}
		}
    }

}
