package controller;

import app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ConfiguracaoController {

    @FXML
    private Button voltarBtn;
    
    @FXML
    void actionEvent(ActionEvent event) {
    	App.iniTelaMenu();
    }
    
}
