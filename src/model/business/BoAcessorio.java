package model.business;

import model.dao.DaoAcessorio;
import model.vo.Acessorio;

public class BoAcessorio extends BoAdapter<Acessorio> implements IBoAcessorio{
	
	public BoAcessorio() {
		super(new DaoAcessorio());
	}
	
}
