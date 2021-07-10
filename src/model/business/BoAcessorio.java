package model.business;

import model.dao.DaoAcessorio;
import model.vo.Acessorio;

public class BoAcessorio extends BoAdapter<Acessorio> implements IBoAcessorio{
	
	public BoAcessorio() {
		super(new DaoAcessorio());
	}

	/*public static void main(String[] args) {
		Acessorio entidade = new Acessorio();
		entidade.setValor(10f);
		entidade.setDepreciado(false);
		entidade.setNome("DVD");
		
		Acessorio entidade2 = new Acessorio();
		entidade2.setValor(11f);
		entidade2.setDepreciado(false);
		entidade2.setNome("Ar condicionado");
		
		Acessorio entidade3 = new Acessorio();
		entidade3.setValor(12f);
		entidade3.setDepreciado(false);
		entidade3.setNome("Radio");
		
		Acessorio entidade4 = new Acessorio();
		entidade4.setValor(13f);
		entidade4.setDepreciado(false);
		entidade4.setNome("Frigobar");
		
		Acessorio entidade5 = new Acessorio();
		entidade5.setValor(13f);
		entidade5.setDepreciado(false);
		entidade5.setNome("Camêra ré");
		try {
			sql.ConnectionFactory.setUser("postgres","admin");
			getInstance().cadastrarEditar(entidade);
			getInstance().cadastrarEditar(entidade2);
			getInstance().cadastrarEditar(entidade3);
			getInstance().cadastrarEditar(entidade4);
			getInstance().cadastrarEditar(entidade5);
		} catch (BoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
