package app;



import business.BoJuridico;
import business.IBoJuridico;
import dao.DaoRes;
import dao.IDaoRes;
import entidade.Endereco;
import entidade.Juridico;
import enumeracoes.Tela;
import excecoes.BoException;
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
	
	private static Stage stage;
	private static Pane loginPane,homePane;
	private static Scene loginScene,homeScene;
	private static IDaoRes daoRes = DaoRes.getInstance();	
	
	public static void main(String[] args) {
		//launch(args); 
		
		ConnectionFactory.setUser("postgres","admin");
		ConnectionFactory.getConnection().createEntityManager();
		
		
		Juridico juridico = new Juridico();
		juridico.setCodigo("3123123");
		juridico.setCnpj("123-3123-412DA");
		juridico.setInscricaoEstadual("30412312-11");
		juridico.setNome("Funeraria leva e não traz");
		Endereco e = new Endereco();
		
		e.setNumero("3213A");
		e.setBairro("Bairro das dores");
		e.setCidade("Cidade dos infermos");
		e.setEstado("Estado doente");
		e.setCep("13213-543");
		e.setRua("Rua fratura");
		juridico.setEndereco(e);
		
		try {
			IBoJuridico boJuridico = new BoJuridico();
			boJuridico.cadastrarEditar(juridico);
		} catch ( BoException e1) {
			System.out.println(e1.getMessage());
			e1.printStackTrace();
		}
		
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		App.stage = stage;
		try {
			stage.setScene(new Scene(daoRes.carregarPaneFXML(Tela.CARREGAR)));
			stage.setTitle("Locadora veiculos");
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
	
	@Override
	public void stop() throws Exception {
		System.exit(0);
		super.stop();
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
