package controller;

import java.awt.TextField;
import java.time.LocalDate;
import java.util.Optional;

import business.BoReserva;
import business.IBoReserva;
import dao.DaoRes;
import entidade.Filial;
import entidade.Reserva;
import excecoes.DaoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;

public class IniciarReservaController {

    @FXML
    private Button selectFuncionarioBtn;

    @FXML
    private Button selectCategoriaBtn;

    @FXML
    private Button filialBtn;

    @FXML
    private Button reservarBtn;

    @FXML
    private Button selectFilialDevolucaoBtn;

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
    private TextField filialEntregaFld;

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
	    		
	    	}else if(btn == selectFuncionarioBtn) {
	    		
	    	}else if(btn == filialBtn) {
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
	    		
	    	}else if(btn == reservarBtn) {
	    		this.reserva = new Reserva();
	    	}
    	} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

   
}
