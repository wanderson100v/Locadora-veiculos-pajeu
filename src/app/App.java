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
	
	private static Stage stage;
	private static Pane loginPane,homePane;
	private static Scene loginScene,homeScene;
	private static IDaoRes daoRes = DaoRes.getInstance();
	// PropertyValueException - not null
	// PSQLException - unique
	// @MappedSuperClass // dado que não é um @Entity não tera tabela no banco...
	// associar registros a usuarios de banco, hipotese 1 : referenciar tb_users
	
	
	public static void main(String[] args) {
		ConnectionFactory.getConnection().createEntityManager().close();
		
		//launch(args);
		
		/*Juridico juridico = new Juridico();
		juridico.setCodigo("3123123");
		juridico.setCnpj("fdsfsf");
		juridico.setInscricaoEstadual("5234324");
		juridico.setNome("Empresa tal 6");
		//Endereco e = new Endereco();
		
		/*e.setNumero(3213);
		e.setBairro("bairro tal4");
		e.setCidade("cidade tal4");
		e.setEstado("estado tal4");
		e.setRua("rua tal 2");
		juridico.setEndereco(e);
		try {
			//new Dao<Endereco>(Endereco.class).cadastrar(e);
			//System.out.println(juridico.getEndereco().getId());
			ClienteBo.getInstance().cadastrarEditar(juridico);
		} catch (DaoException | BoException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
			e1.printStackTrace();
		}*/
		
		//new Dao<Fisico>(Fisico.class).transacao(new Fisico());
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
