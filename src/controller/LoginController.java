package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import app.App;
import dao.DaoRes;
import excecoes.DaoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private TextField senhaField;

    @FXML
    private Button cadastroBtn;

    @FXML
    private Button loginBtn;
    
    //private DialogPane dialogPane;

    @FXML
    void eventHandler(ActionEvent event) {
    	if(event.getSource() == loginBtn) {
    		/*try {
				/*dialogPane = (DialogPane) DaoRes.getInstance().carregarPaneFXML("Dialogo");
				Alert alerta = new Alert(AlertType.NONE);
				alerta.setDialogPane(dialogPane);
				Optional<ButtonType> button =alerta.showAndWait();
				if(button.isPresent())
					if(button.get() == ButtonType.FINISH)
						System.out.println("Ok");
					else if( button.get() == ButtonType.CANCEL){
						System.out.println("cancelou");
					}
    		} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/	
    		App.iniTelaMenu();
    		
    	}
    }

    @FXML
    void initialize() {

    }
}
