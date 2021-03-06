package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import model.enumeracoes.EstadoRerserva;
import model.excecoes.BoException;
import model.vo.CategoriaVeiculo;
import model.vo.Cliente;
import model.vo.Filial;
import model.vo.Funcionario;
import model.vo.Reserva;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import view.Alerta;

public class IniciarReservaController extends ControllerAdapter{

    @FXML
    private Button selectFuncionarioBtn;

    @FXML
    private Button selectCategoriaBtn;

    @FXML
    private Button selectFilialBtn;

    @FXML
    private Button reservarBtn;

    @FXML
    private Button selectClienteBtn;

    @FXML
    private DatePicker retiradaDate;

    @FXML
    private DatePicker entregaDate;

    @FXML
    private ComboBox<Integer> horaRetiradaBox;

    @FXML
    private ComboBox<Integer> horaDevolucaoBox;
    
    @FXML
    private TextField filialFld;

    @FXML
    private TextField categoriaFld;

    @FXML
    private TextField clienteFld;
    
    @FXML
    private TextField funcionarioFld;
    
    private Reserva reserva = new Reserva();
   
    @FXML
    void initialize() {
    	for(int i = 1 ; i <25 ; i++)
    		horaRetiradaBox.getItems().add(i);
    	horaDevolucaoBox.getItems().addAll(horaRetiradaBox.getItems());
    	retiradaDate.setValue(LocalDate.now());
    	entregaDate.setValue(LocalDate.now());
    }
    
    @FXML
    void actionHandle(ActionEvent event) {
    	Button btn = (Button) event.getSource();
    	try {
    		if(btn == selectClienteBtn) {
    			Cliente cliente = Util.selecionarClienteEmDialogo();
	    		if(cliente!= null) {
					clienteFld.setText(cliente.toString());
					reserva.setCliente(cliente);
				}
	    	}else if(btn == selectFuncionarioBtn) {
	    		Funcionario funcionario = Util.selecionarFucnionarioEmDialogo();
	    		if(funcionario!= null) {
					funcionarioFld.setText(funcionario.toString());
					reserva.setFuncionario(funcionario);
				}
	    	}else if(btn == selectFilialBtn) {
				Filial filial = Util.selecionarFilialEmDialogo();
	    		if(filial!= null) {
					filialFld.setText(filial.toString());
					reserva.setFilial(filial);
				}
	    	}else if(btn == selectCategoriaBtn) {
	    		CategoriaVeiculo categoriaVeiculo = Util.selecionarCategoriaVeiculoEmDialogo();
	    		if(categoriaVeiculo!= null) {
					categoriaFld.setText(categoriaVeiculo.toString());
					reserva.setCategoriaVeiculo(categoriaVeiculo);
				}
	    	}else if(btn == reservarBtn) {
	    		reserva.setEstadoReserva(EstadoRerserva.PENDENTE);
	    		LocalDate dataRetidada = retiradaDate.getValue();
	    		LocalDate dataEntrega = entregaDate.getValue();
	    		reserva.setDataRetirada(LocalDateTime.of(dataRetidada.getYear(), dataRetidada.getMonthValue(), dataRetidada.getDayOfMonth(), horaRetiradaBox.getValue(),0));
	    		reserva.setDataDevolucao(LocalDateTime.of(dataEntrega.getYear(), dataEntrega.getMonthValue(), dataEntrega.getDayOfMonth(), horaDevolucaoBox.getValue(),0));
	    		fachadaModel.cadastrarEditarReserva(reserva);
	    		Alerta.getInstance().imprimirMsg("Sucesso ao cadastrar","Reserva iniciada com sucesso",AlertType.INFORMATION);
	    		this.reserva = new Reserva();
	    		limparCampos();
	    	}
    	} catch (BoException e) {
    		Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
    		limparCampos();
		}
    	
    }
    
    private void limparCampos() {
    	clienteFld.clear();
    	funcionarioFld.clear();
    	filialFld.clear();
    	categoriaFld.clear();
    	entregaDate.setValue(LocalDate.now());
    	horaDevolucaoBox.setValue(null);
    	retiradaDate.setValue(LocalDate.now());
    	horaRetiradaBox.setValue(null);
    }
   
}
