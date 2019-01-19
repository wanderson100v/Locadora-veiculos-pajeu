package controller;

import java.util.ArrayList;
import java.util.List;

import business.BoFuncionario;
import entidade.Funcionario;
import enumeracoes.Cargo;
import excecoes.BoException;
import javafx.scene.control.Alert.AlertType;
import sql.ConnectionFactory;
import view.Alerta;

public class FuncionarioObservavel {
	private static FuncionarioObservavel instance;
	private List<IFuncionarioObservadores> funcionarioObservadores = new ArrayList<>();
	private Funcionario funcionario;
	
	private FuncionarioObservavel() {}
	
	public static FuncionarioObservavel getIntance() {
		if(instance == null)
			instance = new FuncionarioObservavel();
		return instance;
	}
	
	public List<IFuncionarioObservadores> getFuncionarioObservadores() {
		return funcionarioObservadores;
	}
	
	public void avisarOuvintes(Cargo cargo) {
		try {
			funcionario = BoFuncionario.getInstance().buscaPorCpf(ConnectionFactory.getUser()[0].substring(1));
			for(IFuncionarioObservadores e :funcionarioObservadores)
				e.atualizar(cargo);
		} catch (BoException e) {
			Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}
}
