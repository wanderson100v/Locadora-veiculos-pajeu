package controller;

import java.time.LocalDateTime;

import banco.ReservaPendente;
import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SelecionarReservaPendenteController {

    @FXML
    private DialogPane selecionarReservaDialog;
    
    @FXML
    private TableView<ReservaPendente> reservasTbl;

    @FXML
    private TableColumn<ReservaPendente, String> categoriaCln;

    @FXML
    private TableColumn<ReservaPendente, String> funcionarioCln;

    @FXML
    private TableColumn<ReservaPendente, LocalDateTime> retiradaCln;

    @FXML
    private TableColumn<ReservaPendente, LocalDateTime> devolucaoCln;

    @FXML
    void initialize() {
    	categoriaCln.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        devolucaoCln.setCellValueFactory(new PropertyValueFactory<>("devolucao"));
    	retiradaCln.setCellValueFactory(new PropertyValueFactory<>("retirada"));
    	funcionarioCln.setCellValueFactory(new PropertyValueFactory<>("nomeFuncionario"));
    	
    }
    
    public TableView<ReservaPendente> getReservasTbl() {
	   return reservasTbl;
	}
    
    public DialogPane getSelecionarReservaDialog() {
    	return selecionarReservaDialog;
    }
}
