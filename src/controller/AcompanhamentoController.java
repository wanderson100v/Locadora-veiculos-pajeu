package controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import business.BoLocacao;
import business.BoReserva;
import entidade.CategoriaVeiculo;
import entidade.Cliente;
import entidade.Filial;
import entidade.Fisico;
import entidade.Funcionario;
import entidade.Locacao;
import entidade.Reserva;
import entidade.Veiculo;
import enumeracoes.EstadoRerserva;
import enumeracoes.TipoLocacao;
import excecoes.BoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import view.Alerta;

public class AcompanhamentoController {

   
	@FXML
	private Button buscarBtn;
	
	@FXML
	private Button finalizarLocacaoBnt;
	
	@FXML
	private Button cancelarReservaBtn;

	@FXML
	private TextField buscarFld;
	
    @FXML
    private CheckBox lFinalizadaCk;

    @FXML
    private CheckBox locacaoCk;

    @FXML
    private CheckBox reservaCk;

    @FXML
    private CheckBox minhaFilialCk;
  
    @FXML
    private ComboBox<EstadoRerserva> estadoReservaBox;
    
    @FXML
    private TableView<Locacao> locacaoTbl;

    @FXML
    private TableColumn<Locacao, TipoLocacao> lTipoCln;

    @FXML
    private TableColumn<Locacao, Float> lValoCln;
    
    @FXML
    private TableColumn<Locacao, Float> lvaloPagoCln;

    @FXML
    private TableColumn<Locacao, Cliente> lClieCln;

    @FXML
    private TableColumn<Locacao, Veiculo> lVeicCln;

    @FXML
    private TableColumn<Locacao, Fisico> lMotoCln;

    @FXML
    private TableColumn<Locacao, Funcionario> lFuncCln;

    @FXML
    private TableColumn<Locacao, Filial> lFiliRetiCln;

    @FXML
    private TableColumn<Locacao, Filial> lFiliDevuCln;

    @FXML
    private TableColumn<Locacao, LocalDateTime> lDataRetiCln;

    @FXML
    private TableColumn<Locacao, LocalDateTime> lDataDevuCln;
    
    @FXML
    private TableView<Reserva> reservasTbl;

    @FXML
    private TableColumn<Reserva, CategoriaVeiculo> rCateCln;

    @FXML
    private TableColumn<Reserva, Cliente> rClieCln;

    @FXML
    private TableColumn<Reserva, Funcionario> rFuncCln;

    @FXML
    private TableColumn<Reserva, LocalDateTime> rDataRetiCln;

    @FXML
    private TableColumn<Reserva, LocalDateTime> rDataDevuCln;

    @FXML
    private TableColumn<Reserva, Filial> rFiliCln;
    
    @FXML
    private GridPane filialRetiradaPane;

    @FXML
    private TextField dadosFilialRetiFld;

    @FXML
    private Button selectFilialRetiBtn;

    @FXML
    private CheckBox devuMinhaFilialCk;

    @FXML
    private CheckBox filialRetiradaCk;

    @FXML
    private GridPane filialDevolucaoPane;

    @FXML
    private TextField dadosFilialDevuFld;

    @FXML
    private Button selectFilialDevuBtn;

    @FXML
    private CheckBox filialDevolucaoCk;

    @FXML
    private CheckBox retiMinhaFilialCk;

    private Filial filialRetirada, filialDevolucao;

    @FXML
    void initialize() {
    	rCateCln.setCellValueFactory(new PropertyValueFactory<>("categoriaVeiculo"));
    	rClieCln.setCellValueFactory(new PropertyValueFactory<>("cliente"));
    	rFuncCln.setCellValueFactory(new PropertyValueFactory<>("funcionario"));
    	rDataDevuCln.setCellValueFactory(new PropertyValueFactory<>("dataDevolucao"));
    	rDataRetiCln.setCellValueFactory(new PropertyValueFactory<>("dataRetirada"));
    	rFiliCln.setCellValueFactory(new PropertyValueFactory<>("filial"));
    	
    	lTipoCln.setCellValueFactory(new PropertyValueFactory<>("tipoLocacao"));
    	lValoCln.setCellValueFactory(new PropertyValueFactory<>("valorDiaria"));
    	lvaloPagoCln.setCellValueFactory(new PropertyValueFactory<>("valorPago"));
    	lClieCln.setCellValueFactory(new PropertyValueFactory<>("cliente"));
    	lVeicCln.setCellValueFactory(new PropertyValueFactory<>("veiculo"));
    	lMotoCln.setCellValueFactory(new PropertyValueFactory<>("motorista"));
    	lFuncCln.setCellValueFactory(new PropertyValueFactory<>("funcionario"));
    	lFiliRetiCln.setCellValueFactory(new PropertyValueFactory<>("filialRetirada"));
    	lFiliDevuCln.setCellValueFactory(new PropertyValueFactory<>("filialEntrega"));
    	lDataRetiCln.setCellValueFactory(new PropertyValueFactory<>("dataRetirada"));
    	lDataDevuCln.setCellValueFactory(new PropertyValueFactory<>("dataDevolucao"));
    	
    	estadoReservaBox.getItems().addAll(EstadoRerserva.values());
    	estadoReservaBox.setValue(EstadoRerserva.PENDENTE);
    }
    
    public void buscaHandle(ActionEvent e) {
    	try {
    		Object fonte = e.getSource();
	    	if(fonte == buscarBtn) {
	    		if(locacaoCk.isSelected() || reservaCk.isSelected()) 
	    		{
		    		if(reservaCk.isSelected()) {
			    		Map<String, String> restricoes = new HashMap<>();
			    		restricoes.put("reserva.estado_reserva", " = "+estadoReservaBox.getValue().ordinal());
						if(filialRetirada != null) 
							restricoes.put("filial.id"," ="+filialRetirada.getId());
						
			    		reservasTbl.getItems().setAll(BoReserva.getInstance().buscaPorBuscaAbrangente(buscarFld.getText().trim(), restricoes));
					}
					if(locacaoCk.isSelected()) {
						Map<String, String> restricoes = new HashMap<>();
						restricoes.put("locacao.finalizado", " = "+lFinalizadaCk.isSelected());
						if(filialRetirada != null) 
							restricoes.put("filialRetirada.id"," ="+filialRetirada.getId());
						if(filialDevolucao != null) 
							restricoes.put("filialEntrega.id"," ="+filialDevolucao.getId());
						locacaoTbl.getItems().setAll(BoLocacao.getInstance().buscaPorBuscaAbrangente(buscarFld.getText().trim(), restricoes));
					}
					
					Alerta.getInstance().imprimirMsg("Sucesso", "Busca concluida. "
							+ ((reservaCk.isSelected())? reservasTbl.getItems().size()+" Reservas, " : "") 
							+ ((locacaoCk.isSelected())? locacaoTbl.getItems().size()+" Locações, ":"")+ "econtradas"
							, AlertType.INFORMATION);
				}else {
					Alerta.getInstance().imprimirMsg("Alerta", "É necessário espeficicar busca para reserva e ou locacao", AlertType.WARNING);
				}
	    		
    		}else if(fonte == selectFilialRetiBtn) {
    			filialRetirada = Util.selecionarFilialEmDialogo();
    			if(filialRetirada != null)
    				dadosFilialRetiFld.setText(filialRetirada.toString());
    		}
    		else if(fonte == selectFilialDevuBtn) {
    			filialDevolucao = Util.selecionarFilialEmDialogo();
    			if(filialDevolucao != null)
    				dadosFilialDevuFld.setText(filialDevolucao.toString());
    		}
    	} catch (BoException e1) {
			e1.printStackTrace();
			Alerta.getInstance().imprimirMsg("Erro", e1.getMessage(), AlertType.ERROR);
		}
    }
    
    public void actionHandle(ActionEvent e) {
    	try {
    		if(e.getSource() == estadoReservaBox) {
    			cancelarReservaBtn.setDisable(estadoReservaBox.getValue() != EstadoRerserva.PENDENTE);
    			reservasTbl.getItems().clear();
    		}
    		else if(e.getSource() == lFinalizadaCk) {
				finalizarLocacaoBnt.setDisable(lFinalizadaCk.isSelected());
				locacaoTbl.getItems().clear();
    		}
    		else if(e.getSource() == filialRetiradaCk) {
    			retiMinhaFilialCk.setDisable(!filialRetiradaCk.isSelected());
    			filialRetiradaPane.setDisable(!filialRetiradaCk.isSelected());
    			if(!filialRetiradaCk.isSelected()) {
    				dadosFilialRetiFld.clear();
    				filialRetirada = null;
    			}
    		}
    		else if(e.getSource() == filialDevolucaoCk) {
    			devuMinhaFilialCk.setDisable(!filialDevolucaoCk.isSelected());
    			filialDevolucaoPane.setDisable(!filialDevolucaoCk.isSelected());
    			if(!filialDevolucaoCk.isSelected()) {
    				dadosFilialDevuFld.clear();
    				filialDevolucao = null;
    			}
    		}
    		else if(e.getSource() == retiMinhaFilialCk) {
    			if(retiMinhaFilialCk.isSelected()) {
	    			Filial filial = ObservadorEntidade.getIntance().getFuncionario().getFilial();
	    			if( filial != null) {
	    				filialRetirada =filial;
	    				dadosFilialRetiFld.setText(filialRetirada.toString());
	    			}else {
	    				retiMinhaFilialCk.setSelected(false);
	    				Alerta.getInstance().imprimirMsg("Alerta", "O Funcionário logado não esta relacionado a nenhuma filial",
	    						AlertType.WARNING);
	    			}
    			}
    			filialRetiradaPane.setDisable(retiMinhaFilialCk.isSelected());
    		}
    		else if(e.getSource() == devuMinhaFilialCk) {
    			if(devuMinhaFilialCk.isSelected()) {
	    			Filial filial = ObservadorEntidade.getIntance().getFuncionario().getFilial();
	    			if( filial != null) {
	    				filialDevolucao =filial;
	    				dadosFilialDevuFld.setText(filialDevolucao.toString());
	    			}else {
	    				devuMinhaFilialCk.setSelected(false);
	    				Alerta.getInstance().imprimirMsg("Alerta", "O Funcionário logado não esta relacionado a nenhuma filial",
	    						AlertType.WARNING);
	    			}
    			}
    			filialDevolucaoPane.setDisable(devuMinhaFilialCk.isSelected());
    		}
    		else if(e.getSource() == cancelarReservaBtn) 
    		{
    			Reserva reservaSelecionada = reservasTbl.getSelectionModel().getSelectedItem();
    			if(reservaSelecionada != null && Alerta.getInstance().imprimirMsgConfirmacao("Precione \"Ok\" para cancelar "
    					+ "reserva com o cliente "+ reservaSelecionada.getCliente())) {
    				reservaSelecionada.setEstadoReserva(EstadoRerserva.CANCELADO);
	    			BoReserva.getInstance().cadastrarEditar(reservaSelecionada);
	    			reservasTbl.getItems().remove(reservaSelecionada);
	    			Alerta.getInstance().imprimirMsg("Sucesso","Reserva cancelada com sucesso ",AlertType.INFORMATION);
    			}else
    				Alerta.getInstance().imprimirMsg("Alerta", "Não há nenhuma reserva pendente seleciona na tabela", AlertType.WARNING);
    		}
    		else if(e.getSource() == finalizarLocacaoBnt) 
    		{
    			Locacao locacaoSelecionada = locacaoTbl.getSelectionModel().getSelectedItem();
    			if(locacaoSelecionada != null && Alerta.getInstance().imprimirMsgConfirmacao("Precione \"Ok\" para iniciar tela para "
    					+ "finalizar locacao com o cliente "+ locacaoSelecionada.getCliente())) {
    				Util.exibirFinalizarLocacaoDialogo(locacaoSelecionada);
    			}else
    				Alerta.getInstance().imprimirMsg("Alerta", "Não há nenhuma locação pendente seleciona na tabela", AlertType.WARNING);
    			
    		}
			
    	} catch (BoException e1) {
			e1.printStackTrace();
			Alerta.getInstance().imprimirMsg("Erro", e1.getMessage(), AlertType.ERROR);
		}
    }
}
