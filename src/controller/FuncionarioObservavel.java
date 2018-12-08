package controller;

import java.util.ArrayList;
import java.util.List;

import enumeracoes.Cargo;

public class FuncionarioObservavel {
	private static FuncionarioObservavel instance;
	private List<IFuncionarioObservadores> funcionarioObservadores = new ArrayList<>();
	
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
		for(IFuncionarioObservadores e :funcionarioObservadores)
			e.atualizar(cargo);
	}
}
