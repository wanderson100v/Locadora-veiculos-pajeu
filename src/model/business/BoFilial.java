package model.business;

import model.dao.DaoFilial;
import model.vo.Filial;

public class BoFilial extends Bo<Filial> implements IBoFilial{
	
	public BoFilial() {
		super(new DaoFilial());
	}
	
	@Override
	public void inativarRegistro(Filial entidade) {
		entidade.setAtivo(false);
	}

}
