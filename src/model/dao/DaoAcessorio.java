package model.dao;


import java.util.ArrayList;
import java.util.List;

import model.dao.sql.ConnectionFactory;
import model.excecoes.DaoException;
import model.vo.Acessorio;

public class DaoAcessorio extends Dao<Acessorio> implements IDaoAcessorio {
	
	public DaoAcessorio() {
		super(Acessorio.class);
	}
	/*
	public List<Acessorio> buscarPorVeiculoID(Long veiculo_id) throws DaoException {
		List<Acessorio> t = new ArrayList<>();
		try {
			em = ConnectionFactory.getConnection();
			t =  em.createQuery("from "+CAMINHO_CLASSE+"acessorio a inner join acessorio.veiculo", tipoDaClasse).getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("OCORREU UM ERRO AO BUSCAR TODOS "+tipoDaClasse.getSimpleName().toUpperCase()+", CONTATE O ADM.");
		}finally {
			em.close();
		}
		return t;
	}*/

}
