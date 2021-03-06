package controller;

import java.time.LocalDateTime;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.banco.ReservaPendente;
import model.enumeracoes.Cargo;
import model.enumeracoes.EstadoRerserva;
import model.excecoes.BoException;
import model.vo.Filial;
import model.vo.Funcionario;
import model.vo.Reserva;
import view.Alerta;

public class AcompanhamentoReservaController extends Controller{

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
   
    private Funcionario funcionario;
    private Filial outraFilial;
    private ToggleGroup toggleGroup;
    
    @FXML
    void actionHandle(ActionEvent event) {
    	try {
	    	Object fonte  = event.getSource();
	    	if(fonte == buscarBtn) {
	    		if(toggleGroup.getSelectedToggle()!= null) {
		    		if(empresaRb.isSelected()) {
		    			reservasTbl.getItems().setAll(fachadaModel.buscarReservaPendente(dadosClienteFld.getText()));
		    		}else {
		    			Filial filial = null;
		    			filial = (minhaFilialRb.isSelected()) ? funcionario.getFilial(): outraFilial;
		    			reservasTbl.getItems().setAll(fachadaModel.buscarReservaPendente(dadosClienteFld.getText(),filial));
		    		}
		    		Alerta.getInstance().imprimirMsg("Busca Concluida", reservasTbl.getItems().size()+" resultados",AlertType.INFORMATION);
	    		}else
	    			Alerta.getInstance().imprimirMsg("Alerta","Op????o de busca para filial n??o selecionada",AlertType.WARNING);
	    	
	    	}else if(fonte == cancelarReservaBtn) {
	    		
	    		ReservaPendente reservaPendente  = reservasTbl.getSelectionModel().getSelectedItem();
	    		if(reservaPendente != null) {
	    			if(Alerta.getInstance().imprimirMsgConfirmacao("Precione \"Ok\" para cancelar reserva com o cliente de c??digo "+reservaPendente.getCodigoCliente())) {
		    			Reserva reserva  = fachadaModel.buscarReservaPorID(reservaPendente.getId());
		    			reserva.setEstadoReserva(EstadoRerserva.CANCELADO);
		    			fachadaModel.cadastrarEditarReserva(reserva);
		    			reservasTbl.getItems().setAll(fachadaModel.buscarReservaPendente(dadosClienteFld.getText()));
		    			Alerta.getInstance().imprimirMsg("Sucesso", "Reserva cancelada com sucesso ",AlertType.INFORMATION);
	    			}
	    		}else
	    			Alerta.getInstance().imprimirMsg("Alerta", "N??o h?? reserva selecionada na tabela",AlertType.WARNING);
	    	
	    	}else if(fonte == outraFilialRb) {
	    		
	    		outraFilial = Util.selecionarFilialEmDialogo();
	    		if(outraFilial!= null) {
	    			filialCln.setVisible(false);
	    			dadosFilialFld.setText(outraFilial.toString());
	    		}
	    		else {
	    			outraFilialRb.setSelected(false);
	    			dadosFilialFld.clear();
	    		}
	    	}else if(fonte == empresaRb) {
	    	
	    		filialCln.setVisible(true);
	    		dadosFilialFld.clear();
	    	}else if(fonte == minhaFilialRb) {
	    		if(funcionario.getFilial() != null) {
	    			filialCln.setVisible(false);
	    			dadosFilialFld.setText(funcionario.getFilial().toString());
	    		}
	    		else {
	    			dadosFilialFld.clear();
	    			minhaFilialRb.setSelected(false);
	    			Alerta.getInstance().imprimirMsg("Alerta", "O Funcion??rio n??o esta relacionado a nenhuma filial",AlertType.WARNING);
	    		}
	    	
	    	}
    	}catch (BoException e) {
    		Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
    	}
    }

	public void atualizar(Funcionario funcionario, Cargo cargo) {
		if(categoriaCln != null && categoriaCln.getCellValueFactory() == null)
    		fazerLigacao();
			this.funcionario = funcionario;
			System.out.println(funcionario);
			if(funcionario.getFilial() != null) {
				if(minhaFilialRb != null ) minhaFilialRb.setSelected(true);
				if(dadosFilialFld != null ) dadosFilialFld.setText(funcionario.getFilial().toString());
			}
	
	}

	private void fazerLigacao() {
    	toggleGroup = new ToggleGroup();
    	minhaFilialRb.setToggleGroup(toggleGroup);
    	empresaRb.setToggleGroup(toggleGroup);
    	outraFilialRb.setToggleGroup(toggleGroup);
    	
    	categoriaCln.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        devolucaoCln.setCellValueFactory(new PropertyValueFactory<>("devolucao"));
    	retiradaCln.setCellValueFactory(new PropertyValueFactory<>("retirada"));
    	codigoClienteCln.setCellValueFactory(new PropertyValueFactory<>("codigoCliente"));
    	funcionarioCln.setCellValueFactory(new PropertyValueFactory<>("nomeFuncionario"));
    	filialCln.setCellValueFactory(new PropertyValueFactory<>("nomeFilial"));
    }
}


