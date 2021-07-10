package controller;

import java.util.ArrayList;
import java.util.List;

import model.enumeracoes.Cargo;
import model.vo.Funcionario;

public class FuncionarioObservavel {
	private static FuncionarioObservavel instance;
	private List<IObservadorFuncionario> entidadeObservadores = new ArrayList<>();
	
	private FuncionarioObservavel() {}
	
	public static FuncionarioObservavel getIntance() {
		if(instance == null)
			instance = new FuncionarioObservavel();
		return instance;
	}
	
	public void addObservadorFuncionario(IObservadorFuncionario observadorEntidade) {
		this.entidadeObservadores.add(observadorEntidade);
	}
	
	public void avisarOuvintes(Funcionario funcionario, Cargo cargo) {
		for(IObservadorFuncionario e :entidadeObservadores)
			e.atualizar(funcionario,cargo);
	}
	
}
