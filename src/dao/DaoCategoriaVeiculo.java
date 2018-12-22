package dao;

import java.util.List;

import javax.persistence.TypedQuery;

import entidade.Automovel;
import entidade.CaminhonetaCarga;
import entidade.CategoriaVeiculo;
import entidade.Entidade;
import excecoes.DaoException;
import excecoes.DaoExclusaoException;
import sql.ConnectionFactory;

public class DaoCategoriaVeiculo extends Dao<CategoriaVeiculo> implements IDaoCategoriaVeiculo  {

	public DaoCategoriaVeiculo() {
		super(CategoriaVeiculo.class);
	}
	
	
	@Override
	public void excluir(CategoriaVeiculo t) throws DaoException{
		try{
			em = ConnectionFactory.getConnection();
			em.getTransaction().begin();
			Entidade entidade = em.merge(t);
			em.remove(entidade);
			em.getTransaction().commit();
		}catch (Exception e) {
			e.printStackTrace();
			Throwable causaBase = e.getCause();
			while(causaBase.getCause() != null)
				causaBase = causaBase.getCause();
			if(causaBase.getMessage().contains("not-null") || causaBase.getMessage().contains("violates foreign key"))
				throw new DaoExclusaoException("IMPOSSIBILIDADE DE EXLUSÃO : HÁ VEICULOS NA CATEGORIA");
			throw new DaoException("ERRO AO EXLUIR CATEGORIA DE VEICULO, CONTATE ADM");
		}finally {
			em.close();
		}
	}
	
	@Override
	public List<CategoriaVeiculo> categorizarCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws DaoException {
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

			return query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO SELECIONAR CATEGORIAS DE CAMINHONETA DE CARGA");
		}finally {
			em.close();
		}
	}

	@Override
	public List<CategoriaVeiculo> categorizarCaminhonetaPassageiro(Automovel automovel) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			TypedQuery<CategoriaVeiculo> query= em.createNamedQuery(CATEGORIZAR_CAMINHONETA_PASSAGEIRO,CategoriaVeiculo.class);
			query.setParameter("tipoAirBag",automovel.getTipoAirBag());
			query.setParameter("tipoCambio",automovel.getTipoCambio());
			query.setParameter("tamanhoVeiculo",automovel.getTamanhoVeiculo());
			query.setParameter("quantidadePortas",automovel.getQuantidadePortas());
			query.setParameter("quantidadePassageiro",automovel.getQuantidadePassageiro());
			query.setParameter("tipoCombustivel",automovel.getTipoCombustivel());
			
			return query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO SELECIONAR CATEGORIAS DE CAMINHONETA DE PASSAGEIROS");
		}finally {
			em.close();
		}
	}

	@Override
	public List<CategoriaVeiculo> categorizarAutomovelPequeno(Automovel automovel) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			TypedQuery<CategoriaVeiculo> query= em.createNamedQuery(CATEGORIZAR_AUTOMOVEL_PEQUENO,CategoriaVeiculo.class);
			query.setParameter("tipoCambio",automovel.getTipoCambio());
			query.setParameter("tamanhoVeiculo",automovel.getTamanhoVeiculo());
			query.setParameter("quantidadePortas",automovel.getQuantidadePortas());
			query.setParameter("quantidadePassageiro",automovel.getQuantidadePassageiro());
			query.setParameter("tipoCombustivel",automovel.getTipoCombustivel());
			
			return query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO SELECIONAR CATEGORIAS DE AUTOMOVEL PEQUENO");
		}finally {
			em.close();
		}
	}


	@Override
	public List<CategoriaVeiculo> buscaPorBusca(CategoriaVeiculo categoriaVeiculo) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			TypedQuery<CategoriaVeiculo> typedQuery = em.createNamedQuery(BUSCA_POR_BUSCA, CategoriaVeiculo.class);
			typedQuery.setParameter("tipo","%"+categoriaVeiculo.getTipo()+"%");
			typedQuery.setParameter("descricao","%"+categoriaVeiculo.getDescricao()+"%");
			typedQuery.setParameter("quilometragemRevisao",categoriaVeiculo.getQuilometragemRevisao());
			typedQuery.setParameter("horasRevisao",categoriaVeiculo.getHorasRevisao());
			typedQuery.setParameter("horasLimpesa",categoriaVeiculo.getHorasLimpesa());
			typedQuery.setParameter("valorDiaria",categoriaVeiculo.getValorDiaria());
			return typedQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR CATEGORIAS DE VEÍCULO POR BUSCA - CONTATE ADM");
		}finally {
			em.close();
		}
	}
}
