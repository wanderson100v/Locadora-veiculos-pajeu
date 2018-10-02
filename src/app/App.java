package app;

import dao.DaoRes;
import dao.IDaoRes;
import enumeracoes.Tela;
import excecoes.DaoException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application{
	
	private static Stage stage;
	private static Pane loginPane,homePane;
	private static Scene loginScene,homeScene;
	private static IDaoRes daoRes = DaoRes.getInstance();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		App.stage = stage;
		try {
			stage.setScene(new Scene(daoRes.carregarPaneFXML(Tela.CARREGAR)));
			stage.setTitle("Funeraria");
			stage.centerOnScreen();
			stage.show();
			
			//carregando todas as demais telas
			loginPane = daoRes.carregarPaneFXML(Tela.LOGIN);
			homePane =  daoRes.carregarPaneFXML(Tela.HOME);
			iniTelaLogin();
			
		}catch (DaoException e) {
			new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK).show();
			e.printStackTrace();
		}
	}
	
	
	public static void iniTelaLogin() {
		if(loginScene == null) 
			loginScene = new Scene(loginPane);
		stage.setScene(loginScene);
		stage.setMaximized(false);
		stage.setWidth(450);
		stage.setHeight(450);
		stage.centerOnScreen();
	}
	
	public static void iniTelaMenu() {
		if(homeScene == null)
			homeScene = new Scene(homePane);
		stage.setScene(homeScene);
		stage.setMaximized(true);
	}
}
