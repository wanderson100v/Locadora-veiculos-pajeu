package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mode.enumeracoes.Tela;
import model.dao.DaoRes;
import model.dao.IDaoRes;
import model.excecoes.DaoException;

public class App extends Application{
	
	public static Stage stage;
	private static Scene loginScene,homeScene,configScene;
	private static IDaoRes daoRes = DaoRes.getInstance();
	public static Boolean rodando;
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		App.stage = stage;
		try {
			stage.setTitle("Locadora Veiculos Pajeú");
			Pane loginPane = daoRes.carregarPaneFXML(Tela.LOGIN);
			Pane homePane =  daoRes.carregarPaneFXML(Tela.HOME);
			Pane configPane = daoRes.carregarPaneFXML(Tela.CONFIG);
			
			loginScene = new Scene(loginPane);
			homeScene = new Scene(homePane);
			configScene = new Scene(configPane);
			
			iniTelaLogin();
			stage.show();
		}catch (DaoException e) {
			new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK).show();
			e.printStackTrace();
		}
		
	}
	public static void iniTelaLogin() {
		stage.setScene(loginScene);
		stage.setMaximized(false);
		stage.setWidth(450);
		stage.setHeight(450);
		stage.centerOnScreen();
	}
	
	public static void iniTelaMenu() {
		stage.setScene(homeScene);
		stage.setMaximized(true);
	}
	
	public static void iniTelaConfig() {
		stage.setScene(configScene);
		stage.setMaximized(true);
	}
	
	@Override
	public void stop() throws Exception {
		System.exit(0);
		super.stop();
	}
}
