package dao;

import entidade.Locacao;

public class DaoLocacao extends Dao<Locacao> implements  IDaoLocacao{

	public DaoLocacao() {
		super(Locacao.class);
	}

}
