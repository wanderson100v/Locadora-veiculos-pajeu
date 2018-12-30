package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import adapter.ReservaDisponibilidade;
import banco.ReservaPendente;
import business.BoCategoriaVeiculo;
import business.BoFuncionario;
import business.BoLocacao;
import business.BoReserva;
import business.BoVeiculo;
import business.IBoLocacao;
import entidade.Cliente;
import entidade.Filial;
import entidade.Fisico;
import entidade.Funcionario;
import entidade.Locacao;
import entidade.Reserva;
import entidade.Veiculo;
import enumeracoes.Cargo;
import enumeracoes.TipoLocacao;
import excecoes.BoException;
import excecoes.ValidarException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import sql.ConnectionFactory;
import view.Alerta;

public class IniciarLocacaoController implements IFuncionarioObservadores {

    @FXML
    private Button selectFuncionarioBtn;

    @FXML
    private Button selectMotoristaBtn;

    @FXML
    private Button selectFilialRetiBtn;

    @FXML
    private TextField funcionarioFld;

    @FXML
    private TextField filialRetiFld;

    @FXML
    private TextField motoristaFld;

    @FXML
    private Button locarBtn;

    @FXML
    private Button selectClienteBtn;

    @FXML
    private TextField clienteFld;

    @FXML
    private DatePicker retiradaDate;

    @FXML
    private ComboBox<Integer> horaRetiradaBox;

    @FXML
    private DatePicker entregaDate;

    @FXML
    private ComboBox<Integer> horaDevolucaoBox;

    @FXML
    private ComboBox<TipoLocacao> tipoLocacaoBox;

    @FXML
    private Button selectFilialDevoBtn;

    @FXML
    private TextField filialDevuFld;

    @FXML
    private CheckBox outroFuncionarioCk;

    @FXML
    private CheckBox outraFilialCk;

    @FXML
    private CheckBox clienteMotoristaCk;

    @FXML
    private CheckBox  horarioAtualCk;

    @FXML
    private Button selectReservaBtn;

    @FXML
    private CheckBox aproveitarReservaCk;

    @FXML
    private TextField reservaFld;
   
    @FXML
    private TextField veiculoFld;
    
    @FXML
    private Button calcularValBtn;

    @FXML
    private TextField valLocacaoFld;

    @FXML
    private TextField valPagoFld;

    @FXML
    private TextField trocoFld;

    @FXML
    private Button selectVeiculoBtn;

    private Locacao locacao = new Locacao();
    private Funcionario funcionario;
    private IBoLocacao boLocacao = BoLocacao.getInstance();
    
    
    @FXML
    void initialize() {
    	FuncionarioObservavel.getIntance().getFuncionarioObservadores().add(this);
    	for(int i = 1 ; i <25 ; i++)
    		horaRetiradaBox.getItems().add(i);
    	horaDevolucaoBox.getItems().addAll(horaRetiradaBox.getItems());
    	horaAtual();
    	tipoLocacaoBox.getItems().addAll(TipoLocacao.values());
    	selectReservaBtn.setDisable(true);
    	selectFuncionarioBtn.setDisable(true);
    	valPagoFld.setOnAction((e)->{
    		if(!valLocacaoFld.getText().isEmpty()) {
    			trocoFld.setText(Float.parseFloat(valLocacaoFld.getText()) - Float.parseFloat(valPagoFld.getText())+"");
    			locacao.setValorPago(Float.parseFloat(valPagoFld.getText()));
    		}
    	});
    	
    }
    
	@FXML
    void buttonHandle(ActionEvent event) {
    	Button btn = (Button) event.getSource();
    	try {
    		if(btn == selectClienteBtn) 
    		{
    			Cliente cliente = Util.selecionarClienteEmDialogo();
	    		if(cliente!= null) {
					clienteFld.setText(cliente.toString());
					locacao.setCliente(cliente);
					locacao.setReservaOrigem(null);
					reservaFld.clear();
					locacao.setVeiculo(null);
					veiculoFld.clear();
				}
    		}
    		else if(btn == selectMotoristaBtn) 
    		{
	    		if(entregaDate.getValue() != null) {
	    			Fisico motorista = Util.selecionarMotoristaValidoEmDialogo(entregaDate.getValue());
	    			if(motorista != null) {
	    				locacao.setMotorista(motorista);
	    				motoristaFld.setText(motorista.toString());
	    			}
	    		}else
	    			Alerta.getInstance().imprimirMsg("Alerta","É necessário selecionar a data de retirada para em seguida selecionar motoristas validos até o fim do período de locação", AlertType.WARNING);
	    			
    		}
    		else if(btn == selectReservaBtn) 
    		{
    			if(locacao.getCliente() != null && locacao.getFilialRetirada()!= null) 
    			{
    				ReservaPendente reservaPendente = Util.selecionarReservaPendenteEmDialogo(locacao.getCliente(), 
    						locacao.getFilialRetirada());
		    		if(reservaPendente!= null) 
		    		{
		    			Reserva reserva  = BoReserva.getInstance().buscarID(reservaPendente.getId());
	    				reservaFld.setText(reserva.toString());
    					locacao.setReservaOrigem(reserva);
		    		}
	    		}
    			else
	    			Alerta.getInstance().imprimirMsg("Alerta","É necessário selecionar um cliente e filial de retirada antes de "
	    					+ "selecionar reserva para aproveitamento na locação", AlertType.WARNING);
    		}
    		else if(btn == selectVeiculoBtn) 
    		{
    			Veiculo veiculo = null;
    			if(aproveitarReservaCk.isSelected())
    				if(locacao.getReservaOrigem() != null) 
    					veiculo = Util.selecionarVeiculoEmDialogo(locacao.getReservaOrigem().getCategoriaVeiculo(),
    							locacao.getFilialRetirada());
    				else
    					Alerta.getInstance().imprimirMsg("Alerta", "Aproveitamento de reserva ativo. Selecione reserva antes do veículo", AlertType.WARNING);
    			else 
    				if(locacao.getFilialRetirada() != null) 
						veiculo = Util.selecionarVeiculoEmDialogo(locacao.getFilialRetirada());
    				else
    					Alerta.getInstance().imprimirMsg("Alerta", "É necessário selecionar filial de origem de locação antes de veículo", AlertType.WARNING);
    			if(veiculo != null) {
    				locacao.setVeiculo(veiculo);
    				veiculoFld.setText(veiculo.toString());
    			}
    		}
    		else if(btn == selectFuncionarioBtn) 
    		{
	    		Funcionario funcionario = Util.selecionarFucnionarioEmDialogo();
	    		if(funcionario!= null) {
					funcionarioFld.setText(funcionario.toString());
					locacao.setFuncionario(funcionario);
				}
	    	}
    		else if(btn == selectFilialRetiBtn) 
    		{
				Filial filialRetirada = Util.selecionarFilialEmDialogo();
	    		if(filialRetirada!= null) {
					filialRetiFld.setText(filialRetirada.toString());
					locacao.setFilialRetirada(filialRetirada);
				}
	    	}
    		else if(btn == selectFilialDevoBtn) 
    		{
				Filial filialDevolucao = Util.selecionarFilialEmDialogo();
	    		if(filialDevolucao!= null) {
					filialDevuFld.setText(filialDevolucao.toString());
					locacao.setFilialEntrega(filialDevolucao);
				}
	    	}
    		else if(btn == calcularValBtn) 
    		{
				pegarDadosTela();
    			Object[] tupla = boLocacao.calcularValorLocacaoDetalhamento(locacao);
				valLocacaoFld.setText(tupla[0]+"");
				Alerta.getInstance().imprimirMsg("Detalhes",tupla[1]+"", AlertType.INFORMATION);
    		}
    		else if(btn == locarBtn) {
    			try{
    				pegarDadosTela();
		    		boLocacao.cadastrarEditar(locacao);
		    		Alerta.getInstance().imprimirMsg("Sucesso ao cadastrar","Locação iniciada com sucesso",AlertType.INFORMATION);
		    		this.locacao = new Locacao();
		    		limparCampos();
	    		}catch (BoException e) {
	    			Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), (e instanceof ValidarException)? AlertType.WARNING : AlertType.ERROR);
	    			if(motoristaFld.getText().isEmpty())
	        			locacao.setMotorista(null);
	        		if(filialDevuFld.getText().isEmpty())
	        			locacao.setFilialEntrega(null);
	    		}
	    	}
    	} catch (BoException e) {
    		Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
    	}
    	
    }
    
    @FXML
    void checkHandle(ActionEvent event) {
    	CheckBox fonte = (CheckBox) event.getSource();
    	if(fonte == clienteMotoristaCk) 
    	{
    		selectMotoristaBtn.setDisable(clienteMotoristaCk.isSelected());
			locacao.setMotorista(null);
			motoristaFld.clear();
    	}
    	else if(fonte == aproveitarReservaCk)
    	{
	    	selectReservaBtn.setDisable(!aproveitarReservaCk.isSelected());
	    	outraFilialCk.setDisable(aproveitarReservaCk.isSelected());
	    	outraFilialCk.setSelected(!aproveitarReservaCk.isSelected());
	    	selectFilialRetiBtn.setDisable(aproveitarReservaCk.isSelected());
    	}
    	else if(fonte == outroFuncionarioCk) 
    	{
    		selectFuncionarioBtn.setDisable(!outroFuncionarioCk.isSelected());
    		if(!outroFuncionarioCk.isSelected()) {
    			funcionarioFld.setText(funcionario.toString());
    			locacao.setFuncionario(funcionario);
    		}else {
    			locacao.setFuncionario(null);
    			funcionarioFld.clear();
    		}
    	}
    	else if(fonte == outraFilialCk)
    	{
    		if(funcionario.getFilial() != null) {
    			selectFilialRetiBtn.setDisable(!outraFilialCk.isSelected());
    			if(!outraFilialCk.isSelected()) {
    				filialRetiFld.setText(funcionario.getFilial().toString());
    				locacao.setFilialRetirada(funcionario.getFilial());
    			}else {
    				locacao.setFilialRetirada(null);
    				filialRetiFld.clear();
    			}
    		}else {
    			outraFilialCk.setSelected(true);
    			Alerta.getInstance().imprimirMsg("Alerta", "Filial do Funcionário não pode ser utilizada. O Funcíonario nã esta relacionado a nenhuma filial",AlertType.WARNING);
    			
    		}
	    }
    	else if(fonte == horarioAtualCk)
    	{
	    	retiradaDate.setDisable(horarioAtualCk.isSelected());
	    	horaRetiradaBox.setDisable(horarioAtualCk.isSelected());
	    }
    	
    }

    public void atualizar(Cargo cargo) {
  		try {
  			this.funcionario = null;
  			List<Funcionario> funcionarios = BoFuncionario.getInstance().buscaPorBusca(ConnectionFactory.getUser()[0].substring(1));
  			if(!funcionarios.isEmpty()) {
  				this.funcionario = funcionarios.get(0);
  				locacao.setFuncionario(funcionario);
  				funcionarioFld.setText(funcionario.toString());
  				if(funcionario.getFilial()!= null) {
  					filialRetiFld.setText(funcionario.getFilial().toString());
  					locacao.setFilialRetirada(funcionario.getFilial());
  					outraFilialCk.setSelected(false);
  				}else
  					outraFilialCk.setSelected(true);
  			}
  		} catch (BoException e) {
  			Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
  		}
  	}
    
    private void horaAtual() {
    	retiradaDate.setValue(LocalDate.now());
    	entregaDate.setValue(LocalDate.now());
    	horaDevolucaoBox.setValue(LocalDateTime.now().getHour());
    	horaRetiradaBox.setValue(LocalDateTime.now().getHour());
	}

    private void pegarDadosTela() {
    	LocalDate dataRetidada = retiradaDate.getValue();
		LocalDate dataEntrega = entregaDate.getValue();
		if(horarioAtualCk.isSelected())
			locacao.setDataRetirada(LocalDateTime.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue(),
					LocalDate.now().getDayOfMonth(),LocalDateTime.now().getHour(),0));
		else
			locacao.setDataRetirada(LocalDateTime.of(dataRetidada.getYear(), dataRetidada.getMonthValue(),
					dataRetidada.getDayOfMonth(), horaRetiradaBox.getValue(),0));
		locacao.setDataDevolucao(LocalDateTime.of(dataEntrega.getYear(), dataEntrega.getMonthValue(), 
				dataEntrega.getDayOfMonth(), horaDevolucaoBox.getValue(),0));
		locacao.setTipoLocacao(tipoLocacaoBox.getValue());
    }
    
    
    private void limparCampos() {
    	clienteFld.clear();
    	motoristaFld.clear();
    	reservaFld.clear();
    	veiculoFld.clear();
    	valLocacaoFld.clear();
    	valPagoFld.clear();
    	trocoFld.clear();
    	funcionarioFld.setText(funcionarioFld.toString());
    	locacao.setFuncionario(funcionario);
    	filialRetiFld.clear();
    	filialDevuFld.clear();
    	clienteMotoristaCk.setSelected(false);
    	aproveitarReservaCk.setSelected(false);
    	outroFuncionarioCk.setSelected(false);
    	horarioAtualCk.setSelected(true);
    	retiradaDate.setDisable(true);
    	horaRetiradaBox.setDisable(true);
    	outraFilialCk.setSelected(funcionario.getFilial() == null);
    	horarioAtualCk.setSelected(true);
    	horaAtual();
    }
}
