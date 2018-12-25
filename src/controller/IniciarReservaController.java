package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import business.BoReserva;
import business.IBoReserva;
import dao.DaoRes;
import entidade.CategoriaVeiculo;
import entidade.Cliente;
import entidade.Filial;
import entidade.Funcionario;
import entidade.Reserva;
import enumeracoes.EstadoRerserva;
import excecoes.BoException;
import excecoes.DaoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import view.Alerta;
import javafx.scene.control.Alert.AlertType;

public class IniciarReservaController {

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
    
    private IBoReserva boReserva = BoReserva.getInstance();
    
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
    			Alert alerta = new Alert(AlertType.NONE);
				SelecionarClienteController selecionarClienteController;
				selecionarClienteController = (SelecionarClienteController) DaoRes.getInstance().carregarControllerFXML("SelecionarClienteDialog");
				alerta.setDialogPane(selecionarClienteController.getSelecionarClienteDialog());
				Optional<ButtonType> result = alerta.showAndWait();
				if(result.isPresent() && result.get() == ButtonType.FINISH) {
					Cliente cliente = selecionarClienteController.getClienteTbl().getSelectionModel().getSelectedItem();
					if(cliente!= null) {
						clienteFld.setText(cliente.toString());
						reserva.setCliente(cliente);
					}
				}
	    	}else if(btn == selectFuncionarioBtn) {
	    		Alert alerta = new Alert(AlertType.NONE);
				SelecionarFuncionarioController selecionarFuncionarioController;
				selecionarFuncionarioController = (SelecionarFuncionarioController) DaoRes.getInstance().carregarControllerFXML("SelecionarFuncionarioDialog");
				alerta.setDialogPane(selecionarFuncionarioController.getSelecionarFuncionarioDialog());
				Optional<ButtonType> result = alerta.showAndWait();
				if(result.isPresent() && result.get() == ButtonType.FINISH) {
					Funcionario funcionario = selecionarFuncionarioController.getFuncionarioTbl().getSelectionModel().getSelectedItem();
					if(funcionario!= null) {
						funcionarioFld.setText(funcionario.toString());
						reserva.setFuncionario(funcionario);
					}
				}
	    	}else if(btn == selectFilialBtn) {
	    		Alert alerta = new Alert(AlertType.NONE);
				SelecionarFilialController selecionarFilialController;
				selecionarFilialController = (SelecionarFilialController) DaoRes.getInstance().carregarControllerFXML("SelecionarFilialDialog");
				alerta.setDialogPane(selecionarFilialController.getSelecionarFilialDialog());
				Optional<ButtonType> result = alerta.showAndWait();
				if(result.isPresent() && result.get() == ButtonType.FINISH) {
					Filial filial = selecionarFilialController.getFilialTbl().getSelectionModel().getSelectedItem();
					if(filial!= null) {
						filialFld.setText(filial.toString());
						reserva.setFilial(filial);
					}
				}
	    	}else if(btn == selectCategoriaBtn) {
	    		Alert alerta = new Alert(AlertType.NONE);
				SelecionarCategoriaVeiculoController selecionarCategoriaVeiculoController;
				selecionarCategoriaVeiculoController = (SelecionarCategoriaVeiculoController) DaoRes.getInstance().carregarControllerFXML("SelecionarCategoriaVeiculoDialog");
				alerta.setDialogPane(selecionarCategoriaVeiculoController.getSelecionarCategoriaVeiculoDialog());
				Optional<ButtonType> result = alerta.showAndWait();
				if(result.isPresent() && result.get() == ButtonType.FINISH) {
					CategoriaVeiculo categoriaVeiculo = selecionarCategoriaVeiculoController.getCategoriaVeiculoTbl().getSelectionModel().getSelectedItem();
					if(categoriaVeiculo != null) {
						categoriaFld.setText(categoriaVeiculo .toString());
						reserva.setCategoriaVeiculo(categoriaVeiculo );
					}
				}
	    	}else if(btn == reservarBtn) {
	    		reserva.setEstadoReserva(EstadoRerserva.PENDENTE);
	    		LocalDate dataRetidada = retiradaDate.getValue();
	    		LocalDate dataEntrega = entregaDate.getValue();
	    		reserva.setDataRetirada(LocalDateTime.of(dataRetidada.getYear(), dataRetidada.getMonthValue(), dataRetidada.getDayOfMonth(), horaRetiradaBox.getValue(),0));
	    		reserva.setDataDevolucao(LocalDateTime.of(dataEntrega.getYear(), dataEntrega.getMonthValue(), dataEntrega.getDayOfMonth(), horaDevolucaoBox.getValue(),0));
	    		boReserva.cadastrarEditar(reserva);
	    		Alerta.getInstance().imprimirMsg("Sucesso ao cadastrar","Reserva iniciada com sucesso",AlertType.INFORMATION);
	    		this.reserva = new Reserva();
	    		limparCampos();
	    	}
    	} catch (BoException | DaoException e) {
    		Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
    		limparCampos();
		}
    	
    }
    
    private void limparCampos() {
    	clienteFld.clear();
    	funcionarioFld.clear();
    	filialFld.clear();
    	categoriaFld.clear();
    
    }
   
}
