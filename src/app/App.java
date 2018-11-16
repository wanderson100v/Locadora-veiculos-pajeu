package app;




import business.BoCategoriaVeiculo;
import business.BoFilial;
import business.BoFuncionario;
import business.BoJuridico;
import business.BoReserva;
import business.IBoJuridico;
import dao.DaoCliente;
import dao.DaoEndereco;
import dao.DaoRes;
import dao.IDaoRes;
import entidade.CategoriaVeiculo;
import entidade.Cliente;
import entidade.Endereco;
import entidade.Filial;
import entidade.Funcionario;
import entidade.Juridico;
import entidade.Reserva;
import enumeracoes.Cargo;
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
	//	ConnectionFactory.getConnection();
		try {
			/*
			
			Endereco endereco = new Endereco();
			endereco.setCep("31231-a");
			Endereco endereco2 = new Endereco();
			endereco2.setCep("83542-b");
			Endereco endereco3 = new Endereco();
			endereco3.setCep("64323-c");
			
			Juridico juridico = new Juridico();
			juridico.setNome("Cabana Bar");
			juridico.setAtivo(true);
			juridico.setCnpj("3123d-dasdvas23");
			juridico.setEmail("cabana@gmail.com");
			juridico.setTelefone("(87)313123");
			juridico.setInscricaoEstadual("512306455-31B");
			juridico.setEndereco(endereco);
			
			Juridico juridico2 = new Juridico();
			juridico2.setNome("Jequitine");
			juridico2.setAtivo(true);
			juridico2.setCnpj("534dfoop4321");
			juridico2.setEmail("japit@gmail.com");
			juridico2.setTelefone("(32)2435845");
			juridico2.setInscricaoEstadual("485yrefgsd-2312");
			juridico2.setEndereco(endereco2);
			
			Juridico juridico3 = new Juridico();
			juridico3.setNome("Fast fata");
			juridico3.setAtivo(true);
			juridico3.setCnpj("dsada124asfdasd");
			juridico3.setEmail("fata@gmail.com");
			juridico3.setTelefone("(64)6821342");
			juridico3.setInscricaoEstadual("534fjsdkjf-4231");
			juridico3.setEndereco(endereco3);
			
			IBoJuridico boJuridico = new BoJuridico();
			boJuridico.cadastrarEditar(juridico);
			boJuridico.cadastrarEditar(juridico2);
			boJuridico.cadastrarEditar(juridico3);

			Funcionario funcionario = new Funcionario("wanderson", "wanderson100v", Cargo.ADM);
		
			Endereco endereco4 = new Endereco();
			endereco4.setCep("dasd-d");
			
			Filial filial = new Filial();
			filial.setAtivo(true);
			filial.setEndereco(endereco4);
			filial.setNome("Veiculos Pajeu");
			
			CategoriaVeiculo categoriaVeiculo = new CategoriaVeiculo();
			categoriaVeiculo.setHorasLimpesa(15.3f);
			categoriaVeiculo.setHorasRevisao(31.6f);
			categoriaVeiculo.setQuilometragemRevisao(50000);
			categoriaVeiculo.setTipo("ATLUXO");
			categoriaVeiculo.setValorDiaria(30.6f);
			
			Reserva reserva = new Reserva();
			reserva.setCategoriaVeiculo(categoriaVeiculo);
			reserva.setCliente(new BoJuridico().buscarID(new Long(3)));
			reserva.setDataDevolucao(new Date());
			reserva.setDateRetirada(new Date());
			reserva.setFilial(filial);
			reserva.setFuncionario(funcionario);
			reserva.setValor(categoriaVeiculo.getValorDiaria());
			
			new BoFilial().cadastrarEditar(filial);
			new BoFuncionario().cadastrarEditar(funcionario);
			new BoCategoriaVeiculo().cadastrarEditar(categoriaVeiculo);
			new BoReserva().cadastrarEditar(reserva);
			*/
			new BoJuridico().excluir(new BoJuridico().buscarID(new Long(3)));
			
		} catch (BoException e) {
			//e.printStackTrace();
		}
		System.exit(0);
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
