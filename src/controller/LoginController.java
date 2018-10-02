package controller;

import java.net.URL;
import java.util.ResourceBundle;

import app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    @FXML
    void eventHandler(ActionEvent event) {
    	if(event.getSource() == loginBtn)
    		App.iniTelaMenu();
    }

    @FXML
    void initialize() {

    }
}
