package dao;

import java.util.List;

import javax.persistence.TypedQuery;

import entidade.Automovel;
import entidade.CaminhonetaCarga;
import entidade.CategoriaVeiculo;
import excecoes.DaoException;
import sql.ConnectionFactory;

public class DaoCategoriaVeiculo extends Dao<CategoriaVeiculo> implements IDaoCategoriaVeiculo  {

	public DaoCategoriaVeiculo() {
		super(CategoriaVeiculo.class);
	}
	
	@Override
	public CategoriaVeiculo categorizarCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws DaoException {
		CategoriaVeiculo categoriaVeiculo = null;
		try {
			em = ConnectionFactory.getConnection();
			TypedQuery<CategoriaVeiculo> query= em.createNamedQuery(CATEGORIZAR_CAMINHONETA_CARGA,CategoriaVeiculo.class);
			query.setParameter("potencia",caminhonetaCarga.getPotencia());
			query.setParameter("desenpenho",caminhonetaCarga.getDesenpenho());
			query.setParameter("capacidadeCarga",caminhonetaCarga.getCapacidadeCarga());
			query.setParameter("tipoAcionamentoEmbreagem",caminhonetaCarga.getTipoAcionamentoEmbreagem());
			query.setParameter("distanciaEixos",caminhonetaCarga.getDistanciaEixos());
			query.setParameter("capacidadeCombustivel",caminhonetaCarga.getCapacidadeCombustivel());
			query.setParameter("torqueMotor",caminhonetaCarga.getTorqueMotor());

			List<CategoriaVeiculo> categoriasCandidata = query.getResultList();
			categoriaVeiculo = categoriasCandidata.get(0);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO CATEGORIZAR CAMINHONETA DE CARGA");
		}finally {
			em.close();
		}
		return categoriaVeiculo;
	}

	@Override
	public CategoriaVeiculo categorizarCaminhonetaPassageiro(Automovel automovel) throws DaoException {
		CategoriaVeiculo categoriaVeiculo = null;
		try {
			em = ConnectionFactory.getConnection();
			TypedQuery<CategoriaVeiculo> query= em.createNamedQuery(CATEGORIZAR_CAMINHONETA_PASSAGEIRO,CategoriaVeiculo.class);
			query.setParameter("tipoAirBag",automovel.getTipoAirBag());
			query.setParameter("tipoCambio",automovel.getTipoCambio());
			query.setParameter("tamanhoVeiculo",automovel.getTamanhoVeiculo());
			query.setParameter("quantidadePortas",automovel.getQuantidadePortas());
			query.setParameter("quantidadePassageiro",automovel.getQuantidadePassageiro());
			query.setParameter("tipoCombustivel",automovel.getTipoCombustivel());
			
			List<CategoriaVeiculo> categoriasCandidata = query.getResultList();
			if(categoriasCandidata.size() == 0 )
				throw new DaoException("POSSIVELMENTE NÃO HÁ CATEGORIAS PARA O VEICULO");
			categoriaVeiculo = categoriasCandidata.get(0);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO CATEGORIZAR CAMINHONETA DE PASSAGEIROS");
		}finally {
			em.close();
		}
		return categoriaVeiculo;
	}

	@Override
	public CategoriaVeiculo categorizarAutomovelPequeno(Automovel automovel) throws DaoException {
		CategoriaVeiculo categoriaVeiculo = null;
		try {
			em = ConnectionFactory.getConnection();
			TypedQuery<CategoriaVeiculo> query= em.createNamedQuery(CATEGORIZAR_AUTOMOVEL_PEQUENO,CategoriaVeiculo.class);
			query.setParameter("tipoCambio",automovel.getTipoCambio());
			query.setParameter("tamanhoVeiculo",automovel.getTamanhoVeiculo());
			query.setParameter("quantidadePortas",automovel.getQuantidadePortas());
			query.setParameter("quantidadePassageiro",automovel.getQuantidadePassageiro());
			query.setParameter("tipoCombustivel",automovel.getTipoCombustivel());
			
			List<CategoriaVeiculo> categoriasCandidata = query.getResultList();
			categoriaVeiculo = categoriasCandidata.get(0);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO CATEGORIZAR AUTOMOVEL PEQUENO");
		}finally {
			em.close();
		}
		return categoriaVeiculo;
	}
}
