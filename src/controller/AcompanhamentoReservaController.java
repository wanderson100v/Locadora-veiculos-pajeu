package controller;

import java.time.LocalDateTime;

import banco.ReservaPendente;
import business.BoReserva;
import business.IBoReserva;
import entidade.Reserva;
import enumeracoes.EstadoRerserva;
import excecoes.BoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import view.Alerta;

public class AcompanhamentoReservaController{

	@FXML
    private AnchorPane acompanhamentoReservaPane;
	
    @FXML
    private TextField dadosClienteFld;

    @FXML
    private Button buscarBtn;

    @FXML
    private TableView<ReservaPendente> reservasTbl;

    @FXML
    private TableColumn<ReservaPendente, String> categoriaCln;

    @FXML
    private TableColumn<ReservaPendente, String> codigoClienteCln;

    @FXML
    private TableColumn<ReservaPendente, String> funcionarioCln;

    @FXML
    private TableColumn<ReservaPendente, LocalDateTime> retiradaCln;

    @FXML
    private TableColumn<ReservaPendente, LocalDateTime> devolucaoCln;

    @FXML
    private TableColumn<ReservaPendente, String> filialCln;

    @FXML
    private Button cancelarReservaBtn;

    @FXML
    private RadioButton minhaFilialRb;
   
    @FXML
    private RadioButton  empresaRb;

    @FXML
    private RadioButton outraFilialRb;

    @FXML
    private TextField dadosFilialFld;
    
    private IBoReserva boReserva = BoReserva.getInstance();

    @FXML
    void actionHandle(ActionEvent event) {
    	if(cancelarReservaBtn != null && categoriaCln.getCellValueFactory() == null)
    		initialize();
    	try {
    		System.out.println("eveto");
	    	Object fonte  = event.getSource();
	    	if(fonte == buscarBtn) {
	    		reservasTbl.getItems().setAll(boReserva.buscarReservaPendente(dadosClienteFld.getText()));
	    		Alerta.getInstance().imprimirMsg("Busca Concluida", reservasTbl.getItems().size()+" resultados",AlertType.INFORMATION);
	    	}else if(fonte == cancelarReservaBtn) {
	    		ReservaPendente reservaPendente  = reservasTbl.getSelectionModel().getSelectedItem();
	    		if(reservaPendente != null 
	    				&& Alerta.getInstance().imprimirMsgConfirmacao("Precione \"Ok\" para cancelar reserva com o cliente de código "+reservaPendente.getCodigoCliente())) {
	    			Reserva reserva  = boReserva.buscarID(reservaPendente.getId());
	    			reserva.setEstadoReserva(EstadoRerserva.CANCELADO);
	    			boReserva.cadastrarEditar(reserva);
	    			reservasTbl.getItems().setAll(boReserva.buscarReservaPendente(dadosClienteFld.getText()));
	    			Alerta.getInstance().imprimirMsg("Sucesso", "Reserva cancelada com sucesso ",AlertType.INFORMATION);
	    		}else
	    			Alerta.getInstance().imprimirMsg("Alerta", "Não há reserva selecionada na tabela",AlertType.WARNING);
	    	}else if(fonte == outraFilialRb) {
	    		filialCln.setVisible(false);
	    	}else if(fonte == empresaRb) {
	    		filialCln.setVisible(true);
	    	}else if(fonte == minhaFilialRb) {
	    		filialCln.setVisible(false);
	    	}
    	}catch (BoException e) {
    		Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
    	}
    }

    void initialize() {
    	System.out.println("inicalizando");
    	categoriaCln.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        devolucaoCln.setCellValueFactory(new PropertyValueFactory<>("devolucao"));
    	retiradaCln.setCellValueFactory(new PropertyValueFactory<>("retirada"));
    	codigoClienteCln.setCellValueFactory(new PropertyValueFactory<>("codigoCliente"));
    	funcionarioCln.setCellValueFactory(new PropertyValueFactory<>("nomeFuncionario"));
    	filialCln.setCellValueFactory(new PropertyValueFactory<>("nomeFilial"));
    	
    }
}


