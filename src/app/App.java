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
import sql.ConnectionFactory;

public class App extends Application{
	
	public static Stage stage;
	private static Pane loginPane,homePane;
	private static Scene loginScene,homeScene;
	private static IDaoRes daoRes = DaoRes.getInstance();	
	
	public static void main(String[] args) {
		ConnectionFactory.setUser("postgres","admin");

		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		App.stage = stage;
		try {
			stage.setTitle("Locadora Veiculos Pajeú");
			loginPane = daoRes.carregarPaneFXML(Tela.LOGIN);
			homePane =  daoRes.carregarPaneFXML(Tela.HOME);
			loginScene = new Scene(loginPane);
			homeScene = new Scene(homePane);
			iniTelaLogin();
			stage.show();
		}catch (DaoException e) {
			new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK).show();
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() throws Exception {
		System.exit(0);
		super.stop();
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
		//stage.setMaximized(true);
		stage.setWidth(800);
		stage.setHeight(600);
		stage.centerOnScreen();
	}
}
