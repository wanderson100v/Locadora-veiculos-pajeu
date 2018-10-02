package controller;

import java.net.URL;
import java.util.ResourceBundle;

import app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button voltarBtn;

    @FXML
    void eventHandler(ActionEvent event) {
    	App.iniTelaLogin();
    }

    @FXML
    void initialize() {
        assert voltarBtn != null : "fx:id=\"voltarBtn\" was not injected: check your FXML file 'HomePane.fxml'.";

    }
}
