package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import model.enumeracoes.Cargo;
import model.enumeracoes.EstadoRerserva;
import model.enumeracoes.TipoLocacao;
import model.excecoes.BoException;
import model.vo.CategoriaVeiculo;
import model.vo.Cliente;
import model.vo.Filial;
import model.vo.Fisico;
import model.vo.Funcionario;
import model.vo.Locacao;
import model.vo.Reserva;
import model.vo.Veiculo;
import view.Alerta;

public class AcompanhamentoController extends Controller {

   
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
    private ComboBox<String> estadoReservaBox;
    
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
    private CheckBox funcionarioCk;

    @FXML
    private FlowPane funcionarioPane;

    @FXML
    private TextField funcionarioFld;

    @FXML
    private Button selectFuncioBtn;

    @FXML
    private CheckBox clienteCk;

    @FXML
    private CheckBox motoristaCk;

    @FXML
    private FlowPane motoristaPane;

    @FXML
    private TextField motoristaFld;

    @FXML
    private Button selectMotoristaBtn;
    
    @FXML
    private FlowPane clientePane;

    @FXML
    private TextField clienteFld;

    @FXML
    private Button selectClienteBtn;
    
    @FXML
    private GridPane periodoPane;
    
    @FXML
    private DatePicker deDate;

    @FXML
    private DatePicker ateDate;

    @FXML
    private CheckBox periodoCk;
    
    @FXML
    private CheckBox filialDevolucaoCk;

    @FXML
    private CheckBox retiMinhaFilialCk;

    @FXML
    private Button excluirLocacaoBtn;

    @FXML
    private Button exluirReservaBtn;

    private Filial filialRetirada, filialDevolucao;
    private Fisico motorista;
    private Cliente cliente;
    private Funcionario funcionario;
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
    	
    	estadoReservaBox.getItems().add("Todos");
    	for(EstadoRerserva e : EstadoRerserva.values())
    		estadoReservaBox.getItems().add(e.toString());
    	estadoReservaBox.setValue("Todos");
    }
    
    public void buscaHandle(ActionEvent e) {
    	try {
    		Object fonte = e.getSource();
	    	if(fonte == buscarBtn) {
	    		if(locacaoCk.isSelected() || reservaCk.isSelected()) 
	    		{
	    			
	    			if(reservaCk.isSelected()) {
	    				Reserva reserva = new Reserva();
		    			reserva.setCliente(cliente);
	    				reserva.setFuncionario(funcionario);
			    		if(!estadoReservaBox.getValue().equals("Todos"))
			    			reserva.setEstadoReserva(EstadoRerserva.getEstadoReserva(estadoReservaBox.getValue()));
			    		reserva.setFilial(filialRetirada);
	    				reservasTbl.getItems().setAll(fachadaModel.buscaReservas(buscarFld.getText().trim(),reserva, deDate.getValue(), ateDate.getValue()));
					}
					if(locacaoCk.isSelected()) {
						Locacao locacao = new Locacao();
						Boolean finalizado = null;
						if(!lFinalizadaCk.isIndeterminate())
							finalizado =lFinalizadaCk.isSelected();
						locacao.setFinalizado(finalizado);
						locacao.setCliente(cliente);
						locacao.setFuncionario(funcionario);
						locacao.setMotorista(motorista);
						locacao.setFilialEntrega(filialDevolucao);
						locacao.setFilialRetirada(filialRetirada);
						
						locacaoTbl.getItems().setAll(fachadaModel.buscarLocacoes(buscarFld.getText().trim(), locacao, deDate.getValue(), ateDate.getValue()));
					}
					
					Alerta.getInstance().imprimirMsg("Sucesso", "Busca concluída. "
							+ ((reservaCk.isSelected())? reservasTbl.getItems().size()+" Reservas, " : "") 
							+ ((locacaoCk.isSelected())? locacaoTbl.getItems().size()+" Loca��es, ":"")+ "econtradas"
							, AlertType.INFORMATION);
				}else {
					Alerta.getInstance().imprimirMsg("Alerta", "Nenhum tipo de busca foi selecionado", AlertType.WARNING);
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
    		else if(fonte == selectMotoristaBtn) {
    			motorista = Util.selecionarMotoristaValidoEmDialogo(LocalDate.now());
    			if(motorista != null)
    				motoristaFld.setText(motorista.toString());
    		}
    		else if(fonte == selectFuncioBtn) {
    			funcionario = Util.selecionarFucnionarioEmDialogo();
    			if(funcionario != null)
    				funcionarioFld.setText(funcionario.toString());
    		}
    		else if(fonte == selectClienteBtn) {
    			cliente = Util.selecionarClienteEmDialogo();
    			if(cliente != null)
    				clienteFld.setText(cliente.toString());
    		}
	    	
    	} catch (BoException e1) {
			e1.printStackTrace();
			Alerta.getInstance().imprimirMsg("Erro", e1.getMessage(), AlertType.ERROR);
		}
    }
    
    public void actionHandle(ActionEvent e) {
    	Filial filial = null;
    	if(funcionario !=null)
    		filial = funcionario.getFilial();
    	try {
    		if(e.getSource() == estadoReservaBox) {
    			cancelarReservaBtn.setDisable(!estadoReservaBox.getValue().equals("Pendente"));
    			reservasTbl.getItems().clear();
    		}
    		else if(e.getSource() == motoristaCk) {
    			motoristaPane.setDisable(!motoristaCk.isSelected());
    			if(!motoristaCk.isSelected()) {
    				motoristaFld.clear();
    				motorista = null;
    			}
    		}else if(e.getSource() == funcionarioCk) {
    			funcionarioPane.setDisable(!funcionarioCk.isSelected());
    			if(!funcionarioCk.isSelected()) {
    				funcionarioFld.clear();
    				funcionario = null;
    			}
    		}else if(e.getSource() == clienteCk) {
    			clientePane.setDisable(!clienteCk.isSelected());
    			if(!clienteCk.isSelected()) {
    				clienteFld.clear();
    				cliente = null;
    			}
    		}else if(e.getSource() == periodoCk) {
    			periodoPane.setDisable(!periodoCk.isSelected());
    			if(!periodoCk.isSelected()) {
    				deDate.setValue(null);
    				ateDate.setValue(null);
    			}
    		}else if(e.getSource() == lFinalizadaCk) {
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
	    			if( filial != null) {
	    				filialRetirada =filial;
	    				dadosFilialRetiFld.setText(filialRetirada.toString());
	    			}else {
	    				retiMinhaFilialCk.setSelected(false);
	    				Alerta.getInstance().imprimirMsg("Alerta", "O funcionário logado não está relacionado a nenhuma filial",
	    						AlertType.WARNING);
	    			}
    			}
    			filialRetiradaPane.setDisable(retiMinhaFilialCk.isSelected());
    		}
    		else if(e.getSource() == devuMinhaFilialCk) {
    			if(devuMinhaFilialCk.isSelected()) {
	    			if( filial != null) {
	    				filialDevolucao =filial;
	    				dadosFilialDevuFld.setText(filialDevolucao.toString());
	    			}else {
	    				devuMinhaFilialCk.setSelected(false);
	    				Alerta.getInstance().imprimirMsg("Alerta", "O funcion�rio logado não está relacionado a nenhuma filial",
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
	    			fachadaModel.cadastrarEditarReserva(reservaSelecionada);
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
    				Alerta.getInstance().imprimirMsg("Alerta", "Não ha nenhuma locações pendente seleciona na tabela", AlertType.WARNING);
    			
    		}else if(e.getSource() == excluirLocacaoBtn) {
    			Locacao locacaoSelecionada = locacaoTbl.getSelectionModel().getSelectedItem();
    			if(locacaoSelecionada != null && Alerta.getInstance()
    					.imprimirMsgConfirmacao("Realmente deseja exluir a locaçõa com o cliente: "+cliente+" ? ")) {
    				fachadaModel.excluirLocacao(locacaoSelecionada);
    			}
    		}else if(e.getSource() == exluirReservaBtn) {
    			Reserva reservaSelecionada = reservasTbl.getSelectionModel().getSelectedItem();
    			if(reservaSelecionada != null && Alerta.getInstance()
    					.imprimirMsgConfirmacao("Realmente deseja exluir a Reserva com o cliente: "+cliente+" ? ")) {
    				fachadaModel.excluirReserva(reservaSelecionada);
    			}
    		}
			
    	} catch (BoException e1) {
			e1.printStackTrace();
			Alerta.getInstance().imprimirMsg("Erro", e1.getMessage(), AlertType.ERROR);
		}
    }


	@Override
	public void atualizar(Funcionario funcionario, Cargo cargo) {
		this.funcionario = funcionario;
	}
}
