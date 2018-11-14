package dao;

import entidade.Funcionario;

public class DaoFuncionario extends Dao<Funcionario> implements IDaoFuncionario{

	public DaoFuncionario() {
		super(Funcionario.class);
	}

}
