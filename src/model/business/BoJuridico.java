package model.business;

import java.util.List;

import model.dao.DaoJuridico;
import model.dao.IDaoJuridico;
import model.excecoes.BoException;
import model.excecoes.DaoException;
import model.vo.Juridico;

public class BoJuridico extends Bo<Juridico> implements IBoJuridico {
	
	private IDaoJuridico daoJuridico;
	
	public BoJuridico() {
		super(new DaoJuridico());
		this.daoJuridico = (IDaoJuridico) daoEntidade;
	}
	
	@Override
	public void cadastrar(Juridico entidade) throws BoException, DaoException {
		atribuirCodigo(entidade);
		super.cadastrar(entidade);
	}
	
	@Override
	public void inativarRegistro(Juridico entidade) {
		entidade.setAtivo(false);
	}
	
	
	private void atribuirCodigo(Juridico entidade){
		entidade.setCodigo("PJ"+entidade.getCnpj());
	}

	@Override
	public List<Juridico> buscaPorBuscaAbrangente(String busca, Juridico juridico) throws BoException {
		try {
			return daoJuridico.buscaPorBuscaAbrangente(busca, juridico);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
}