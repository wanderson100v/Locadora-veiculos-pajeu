package controller;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.enumeracoes.TamanhoVeiculo;
import model.enumeracoes.TipoAcionamentoEmbreagem;
import model.enumeracoes.TipoAirBag;
import model.enumeracoes.TipoAutomovel;
import model.enumeracoes.TipoCambio;
import model.enumeracoes.TipoCombustivel;
import model.excecoes.BoException;
import model.vo.Automovel;
import model.vo.CaminhonetaCarga;
import model.vo.CategoriaVeiculo;
import model.vo.Entidade;

public class CategoriaVeiculoController extends CRUDController<CategoriaVeiculo> {

    @FXML
    private TableColumn<CategoriaVeiculo, String> tipoCln;

    @FXML
    private TableColumn<CategoriaVeiculo, Float> valorCln;

    @FXML
    private TableColumn<CategoriaVeiculo, String> descricaoCln;

    @FXML
    private TextField quilometragemRevisaoFld;

    @FXML
    private TextField horaRevisaoFld;

    @FXML
    private TextField tipoFld;

    @FXML
    private TextField valorDiariaFld;

    @FXML
    private TextField horaLimpezaFld;

    @FXML
    private ComboBox<String> tipoVeiculoBox;

    @FXML
    private TextArea descricaoFld;

    @FXML
    private VBox tipoVeiculoPane;

    @FXML
    private GridPane caminhonetaCargaPane;
    
    @FXML
    private GridPane automovelPane;

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
    private TextField torqueMotorFld;

    @FXML
    private Label automovelLbl;

    @FXML
    private GridPane airbagPane;

    @FXML
    private ComboBox<TipoAirBag> airbagBox;

    @FXML
    private ComboBox<TipoCambio> cambioBox;

    @FXML
    private ComboBox<TamanhoVeiculo> tamanhoBox;

    @FXML
    private ComboBox<Integer> portasBox;

    @FXML
    private ComboBox<Integer> passageirosBox;

    @FXML
    private ComboBox<TipoCombustivel> combustivelBox;
    
    private CategoriaVeiculo categoriaVeiculo;
    
    @FXML
    void initialize() {
    	super.initialize();
    	tipoVeiculoBox.getItems().addAll("Caminhoneta Carga", "Caminhoneta Passageiro", "Automóvel Comum");
    	
    	tipoCln.setCellValueFactory( new PropertyValueFactory<>("tipo"));
    	valorCln.setCellValueFactory( new PropertyValueFactory<>("valorDiaria"));
    	descricaoCln.setCellValueFactory( new PropertyValueFactory<>("descricao"));
    	
    	aciEmbreagemBox.getItems().addAll(TipoAcionamentoEmbreagem.values());	
    	
    	cambioBox.getItems().addAll(TipoCambio.values());
    	tamanhoBox.getItems().addAll(TamanhoVeiculo.values());
    	airbagBox.getItems().addAll(TipoAirBag.values());
    	for(int i = 1 ; i <50 ; i ++)
    		passageirosBox.getItems().add(i);
    	portasBox.getItems().addAll(passageirosBox.getItems());
    	combustivelBox.getItems().addAll(TipoCombustivel.values());
    	/*
    	desempenhoFld.setTextFormatter(Mascara.getMascaraNumericoFlutuante());
    	potenciaFld.setTextFormatter(Mascara.getMascaraNumericoFlutuante());
    	capCargaFld.setTextFormatter(Mascara.getMascaraNumericoInteiro());
    	capCombustivelFld.setTextFormatter(Mascara.getMascaraNumericoInteiro());
    	disEixosFld.setTextFormatter(Mascara.getMascaraNumericoFlutuante());
    	torqueMotorFld.setTextFormatter(Mascara.getMascaraNumericoFlutuante());
    	
    	quilometragemRevisaoFld.setTextFormatter(Mascara.getMascaraNumericoInteiro());
    	valorDiariaFld.setTextFormatter(Mascara.getMascaraNumericoFlutuante());
    	horaLimpezaFld.setTextFormatter(Mascara.getMascaraNumericoInteiro());
    	horaRevisaoFld.setTextFormatter(Mascara.getMascaraNumericoInteiro());
    	*/
    }

	@Override
	protected void cadastrarEditar(Boolean cadastrar, String opcao) throws BoException {
		CategoriaVeiculo categoriaVeiculo = null;
			if(cadastrar) {
			categoriaVeiculo = new CategoriaVeiculo();
			 if(tipoVeiculoBox.getValue() != null) {
				 if(tipoVeiculoBox.getValue().equals("Caminhoneta Carga")) {
						
						CaminhonetaCarga caminhonetaCarga = new CaminhonetaCarga(Float.parseFloat(torqueMotorFld.getText()), 
								Float.parseFloat(desempenhoFld.getText()),Float.parseFloat(potenciaFld.getText()), 
								Float.parseFloat(disEixosFld.getText()),aciEmbreagemBox.getValue(), 
								Integer.parseInt(capCargaFld.getText()), Integer.parseInt(capCargaFld.getText()));
						categoriaVeiculo.setVeiculoExemplo(caminhonetaCarga);
				
				 }else {
					if(tipoVeiculoBox.getValue().equals("Caminhoneta Passageiro"))
						categoriaVeiculo.setVeiculoExemplo(new Automovel(cambioBox.getValue(),TipoAutomovel.CAMINHONETA_PASSAGEIRO, 
								airbagBox.getValue(),tamanhoBox.getValue()));
					else
						categoriaVeiculo.setVeiculoExemplo(new Automovel(cambioBox.getValue(),TipoAutomovel.CONVENCIONAL,
								tamanhoBox.getValue()));
					categoriaVeiculo.getVeiculoExemplo().setQuantidadePortas(portasBox.getValue());
					categoriaVeiculo.getVeiculoExemplo().setTipoCombustivel(combustivelBox.getValue());
					categoriaVeiculo.getVeiculoExemplo().setQuantidadePassageiro(passageirosBox.getValue());
				}
			 }else
				 alerta.imprimirMsg("Alerta","É preciso adicionar um veiculo de exemplo", AlertType.WARNING);
		}
		else
			categoriaVeiculo = this.categoriaVeiculo;
			
		categoriaVeiculo.setTipo(tipoFld.getText().trim());
		categoriaVeiculo.setDescricao(descricaoFld.getText().trim());
		categoriaVeiculo.setHorasLimpesa(Integer.parseInt(horaLimpezaFld.getText()));
		categoriaVeiculo.setHorasRevisao(Integer.parseInt(horaRevisaoFld.getText()));;
		categoriaVeiculo.setQuilometragemRevisao(Integer.parseInt(quilometragemRevisaoFld.getText()));
		categoriaVeiculo.setValorDiaria(Float.parseFloat(valorDiariaFld.getText()));
		
		fachadaModel.cadastrarEditarCategoriaVeiculo(categoriaVeiculo);
		
		alerta.imprimirMsg("Sucesso","Categoria de veículo"+opcao+" com sucesso", AlertType.INFORMATION);
		
	}

	@Override
	protected void excluir() throws BoException {
		fachadaModel.excluirCategoriaVeiculo(this.categoriaVeiculo);
		alerta.imprimirMsg("Sucesso ao exluir","Categoria de veículo excluida com sucesso", 
				AlertType.INFORMATION);
	}

	@Override
	void popularTabela(String busca) {
		try {
			List<CategoriaVeiculo> categoriasVeiculos = fachadaModel.buscarCategoriasVeiculo(busca);
			entidadeTabela.getItems().setAll(categoriasVeiculos);
			alerta.imprimirMsg("Busca concluída","Foram econtrados "+categoriasVeiculos.size()+" resultado(s)",AlertType.INFORMATION);
		} catch (BoException e) {
			alerta.imprimirMsg("Erro",e.getMessage(),AlertType.ERROR);
		}
	}

	@Override
	void popularDescricao(Entidade entidade) {
		this.categoriaVeiculo = (CategoriaVeiculo) entidade;
		
		if(categoriaVeiculo.getVeiculoExemplo() instanceof CaminhonetaCarga) {
			CaminhonetaCarga caminhonetaCarga =(CaminhonetaCarga) categoriaVeiculo.getVeiculoExemplo();
			tipoVeiculoBox.setValue("Caminhoneta Carga");
			desempenhoFld.setText(""+caminhonetaCarga.getDesenpenho());
			potenciaFld.setText(""+caminhonetaCarga.getPotencia());
			disEixosFld.setText(""+caminhonetaCarga.getDistanciaEixos());
			aciEmbreagemBox.setValue(caminhonetaCarga.getTipoAcionamentoEmbreagem());;
			capCargaFld.setText(""+caminhonetaCarga.getCapacidadeCarga());
			capCombustivelFld.setText(""+caminhonetaCarga.getCapacidadeCombustivel());
			torqueMotorFld.setText(""+caminhonetaCarga.getTorqueMotor());
			
		}else if(categoriaVeiculo.getVeiculoExemplo() instanceof Automovel) {
			Automovel automovel =(Automovel) categoriaVeiculo.getVeiculoExemplo();
			if(automovel.getTipoAutomovel() == TipoAutomovel.CAMINHONETA_PASSAGEIRO) {
				tipoVeiculoBox.setValue("Caminhoneta Passageiro");
				airbagPane.setVisible(true);
				airbagBox.setValue(automovel.getTipoAirBag());
			}else {
				tipoVeiculoBox.setValue("Automóvel Comum");
				airbagPane.setVisible(false);
			}
			tamanhoBox.setValue(automovel.getTamanhoVeiculo());
			cambioBox.setValue(automovel.getTipoCambio());
			combustivelBox.setValue(automovel.getTipoCombustivel());
			portasBox.setValue(automovel.getQuantidadePortas());
			passageirosBox.setValue(automovel.getQuantidadePassageiro());
		}
		tipoFld.setText(categoriaVeiculo.getTipo());
		descricaoFld.setText(categoriaVeiculo.getDescricao());
		horaLimpezaFld.setText(""+categoriaVeiculo.getHorasLimpesa());
		horaRevisaoFld.setText(""+categoriaVeiculo.getHorasRevisao());
		valorDiariaFld.setText(""+categoriaVeiculo.getValorDiaria());
		quilometragemRevisaoFld.setText(""+categoriaVeiculo.getQuilometragemRevisao());
	}

	@Override
	void limparCampos() {
		categoriaVeiculo  =null;
		
		tipoFld.clear();
		descricaoFld.clear();
		horaLimpezaFld.clear();
		horaRevisaoFld.clear();
		quilometragemRevisaoFld.clear();
		valorDiariaFld.clear();
		tipoVeiculoBox.setValue(null);
		tipoVeiculoPane.getChildren().clear();
		desempenhoFld.clear();
		potenciaFld.clear();
		disEixosFld.clear();
		aciEmbreagemBox.setValue(null);
		capCargaFld.clear();
		capCombustivelFld.clear();
		torqueMotorFld.clear();
		airbagBox.setValue(null);
		tamanhoBox.setValue(null);
		cambioBox.setValue(null);
		combustivelBox.setValue(null);
		portasBox.setValue(null);
		passageirosBox.setValue(null);
		
		System.gc();
	}
	
	@FXML
    void actionHandle(ActionEvent event) {
		if(event.getSource() == tipoVeiculoBox)
			if(tipoVeiculoBox.getValue() != null) 
				if(tipoVeiculoBox.getValue().equals("Caminhoneta Carga")) {
					tipoVeiculoPane.getChildren().clear();
					tipoVeiculoPane.getChildren().setAll(caminhonetaCargaPane);
				}else if(tipoVeiculoBox.getValue().equals("Caminhoneta Passageiro")) {
					automovelLbl.setText("Caminhoneta Passageiro de Parametro");
					airbagPane.setVisible(true);
					tipoVeiculoPane.getChildren().clear();
					tipoVeiculoPane.getChildren().setAll(automovelPane);
				}else if(tipoVeiculoBox.getValue().equals("Automóvel Comum")) {
					automovelLbl.setText("Automóvel Comum de Parametro");
					airbagPane.setVisible(false);
					tipoVeiculoPane.getChildren().clear();
					tipoVeiculoPane.getChildren().setAll(automovelPane);
				}
    }

}
