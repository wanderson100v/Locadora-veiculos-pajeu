package model.business;

import model.dao.DaoCaminhonetaCarga;
import model.vo.CaminhonetaCarga;

public class BoCaminhonetaCarga extends Bo<CaminhonetaCarga> implements IBoCaminhonetaCarga{
	
	public BoCaminhonetaCarga() {
		super(new DaoCaminhonetaCarga());
	}

	@Override
	public void inativarRegistro(CaminhonetaCarga entidade) {
		entidade.setAtivo(false);
	}
	
}
