package controller;

import java.util.ArrayList;
import java.util.List;

import business.BoFuncionario;
import dao.DaoConfiguracaoDefault;
import entidade.ConfiguracoesDefault;
import entidade.Funcionario;
import enumeracoes.Cargo;
import excecoes.BoException;
import excecoes.DaoException;
import javafx.scene.control.Alert.AlertType;
import sql.ConnectionFactory;
import view.Alerta;

public class ObservadorEntidade {
	private static ObservadorEntidade instance;
	private List<IObservadoresEntidade> entidadeObservadores = new ArrayList<>();
	private Funcionario funcionario;
	private ConfiguracoesDefault configuracoesDefault;
	
	
	private ObservadorEntidade() {}
	
	public static ObservadorEntidade getIntance() {
		if(instance == null)
			instance = new ObservadorEntidade();
		return instance;
	}
	
	public List<IObservadoresEntidade> getEntidadeObservadores() {
		return entidadeObservadores;
	}
	
	public void avisarOuvintes(Cargo cargo) {
		try {
			funcionario = BoFuncionario.getInstance().buscaPorCpf(ConnectionFactory.getUser()[0].substring(1));
			configuracoesDefault = DaoConfiguracaoDefault.getInstance().carregar();
			for(IObservadoresEntidade e :entidadeObservadores)
				e.atualizar(cargo);
		} catch (BoException| DaoException e) {
			e.printStackTrace();
			Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}
	
	public ConfiguracoesDefault getConfiguracoesDefault() {
		return configuracoesDefault;
	}
	
	public void setConfiguracoesDefault(ConfiguracoesDefault configuracoesDefault) {
		this.configuracoesDefault = configuracoesDefault;
	}
}
