package model.business;

import model.dao.DaoAutomovel;
import model.vo.Automovel;

public class BoAutomovel extends Bo<Automovel> implements IBoAutomovel {
	
	public BoAutomovel() {
		super(new DaoAutomovel());
	}
	
	@Override
	public void inativarRegistro(Automovel entidade) {
		entidade.setAtivo(false);
	}

}
