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
import entidade.Funcionario;
import entidade.Locacao;
import entidade.Reserva;
import entidade.Veiculo;
import enumeracoes.Cargo;
import enumeracoes.TipoLocacao;
import excecoes.BoException;
import javafx.event.ActionEvent;
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
    	retiradaDate.setValue(LocalDate.now());
    	entregaDate.setValue(LocalDate.now());
    	tipoLocacaoBox.getItems().addAll(TipoLocacao.values());
    	selectReservaBtn.setDisable(true);
    	selectFuncionarioBtn.setDisable(true);
    	
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
				}
    		}
    		else if(btn == selectMotoristaBtn) 
    		{
	    		
    			//...
    			
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
		    			boolean sucesso = false;
		    			if(!BoVeiculo.getInstance().buscarVeiculosDisponivel(reserva.getFilial().getId(),
		    					reserva.getCategoriaVeiculo().getId(),"").isEmpty()) 
		    				sucesso = true;
		    			else 
		    				if(Alerta.getInstance().imprimirMsgConfirmacao("Não Há veiculos disponiveis para locação "
		    						+ "na categoria de veículo reservada. Deseja Selecionar Categoria Superior?")) 
		    				{
		    					locacao.setValorDiaria(reserva.getCategoriaVeiculo().getValorDiaria());
		    					ReservaDisponibilidade reservaDispoSuperior = Util.selecionarReservaDispoSuperiorEmDialogo(reserva.getCategoriaVeiculo(),reserva.getFilial(),LocalDateTime.now());
		    					reserva.setCategoriaVeiculo(BoCategoriaVeiculo.getInstance().buscarID(reservaDispoSuperior.getIdCategoria()));
		    					sucesso = true;
		    				}
		    			if(sucesso) 
		    			{
		    				veiculoFld.clear();
		    				locacao.setVeiculo(null);
		    				reservaFld.setText(reserva.toString());
	    					filialRetiFld.setText(reserva.getFilial().toString());
	    					locacao.setReservaOrigem(reserva);
	    					locacao.setFilialRetirada(reserva.getFilial());
		    			}
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
    				if(locacao.getReservaOrigem() != null && locacao.getReservaOrigem().getCategoriaVeiculo() != null) 
    					veiculo = Util.selecionarVeiculoEmDialogo(locacao.getReservaOrigem().getCategoriaVeiculo(),
    							locacao.getReservaOrigem().getFilial());
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
    		else if(btn == locarBtn) {
	    		LocalDate dataRetidada = retiradaDate.getValue();
	    		LocalDate dataEntrega = entregaDate.getValue();
	    		locacao.setDataRetirada(LocalDateTime.of(dataRetidada.getYear(), dataRetidada.getMonthValue(),
	    				dataRetidada.getDayOfMonth(), horaRetiradaBox.getValue(),0));
	    		locacao.setDataDevolucao(LocalDateTime.of(dataEntrega.getYear(), dataEntrega.getMonthValue(), 
	    				dataEntrega.getDayOfMonth(), horaDevolucaoBox.getValue(),0));
	    		boLocacao.cadastrarEditar(locacao);
	    		Alerta.getInstance().imprimirMsg("Sucesso ao cadastrar","Reserva iniciada com sucesso",AlertType.INFORMATION);
	    		this.locacao = new Locacao();
	    		limparCampos();
	    	}
    	} catch (BoException e) {
    		Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
    		limparCampos();
		}
    	
    }
    
    @FXML
    void checkHandle(ActionEvent event) {
    	CheckBox fonte = (CheckBox) event.getSource();
    	if(fonte == clienteMotoristaCk) 
    	{
    		selectMotoristaBtn.setDisable(clienteMotoristaCk.isSelected());
    		if(clienteMotoristaCk.isSelected()) {
    			// validar cliente
    			motoristaFld.setText("O Motorista da Locação Será o Cliente");
    		}else {
    			locacao.setMotorista(null);
    			motoristaFld.clear();
    		}
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
  				if(funcionario.getFilial()!= null) {
  					filialRetiFld.setText(funcionario.getFilial().toString());
  					outraFilialCk.setSelected(false);
  				}else
  					outraFilialCk.setSelected(true);
  			}
  		} catch (BoException e) {
  			Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
  		}
  	}
    
    private void limparCampos() {
    	clienteFld.clear();
    	motoristaFld.clear();
    	reservaFld.clear();
    	funcionarioFld.clear();
    	filialRetiFld.clear();
    	filialDevuFld.clear();
    	entregaDate.setValue(LocalDate.now());
    	horaDevolucaoBox.setValue(null);
    	retiradaDate.setValue(LocalDate.now());
    	horaRetiradaBox.setValue(null);
    	clienteMotoristaCk.setSelected(false);
    	aproveitarReservaCk.setSelected(false);
    	outroFuncionarioCk.setSelected(false);
    	horarioAtualCk.setSelected(true);
    	retiradaDate.setDisable(true);
    	horaRetiradaBox.setDisable(true);
    	outraFilialCk.setSelected(funcionario.getFilial() == null);
    	horarioAtualCk.setSelected(true);
    }
}
