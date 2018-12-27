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
	public List<CategoriaVeiculo> categorizarCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			TypedQuery<CategoriaVeiculo> query= em.createNamedQuery(SELECIONAR_CATEGORIA_CAMINHONETA_CARGA.replace('?','<'),CategoriaVeiculo.class);
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
			TypedQuery<CategoriaVeiculo> query= em.createQuery(SELECIONAR_CAMINHONETA_PASSAGEIRO.replace('?','<'),CategoriaVeiculo.class);
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
			TypedQuery<CategoriaVeiculo> query= em.createQuery(SELECIONAR_AUTOMOVEL_PEQUENO.replace('?','<'),CategoriaVeiculo.class);
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
	
	public List<CategoriaVeiculo> categoriasSuperiorCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			TypedQuery<CategoriaVeiculo> query= em.createQuery(SELECIONAR_CATEGORIA_CAMINHONETA_CARGA.replace('?','>'),CategoriaVeiculo.class);
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
			throw new DaoException("ERRO AO SELECIONAR CATEGORIAS SUPERIORES DE CAMINHONETA DE CARGA");
		}finally {
			em.close();
		}
	}

	public List<CategoriaVeiculo> categoriasSuperiorCaminhonetaPassageiro(Automovel automovel) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			TypedQuery<CategoriaVeiculo> query= em.createQuery(SELECIONAR_CAMINHONETA_PASSAGEIRO.replace('?','>'),CategoriaVeiculo.class);
			query.setParameter("tipoAirBag",automovel.getTipoAirBag());
			query.setParameter("tipoCambio",automovel.getTipoCambio());
			query.setParameter("tamanhoVeiculo",automovel.getTamanhoVeiculo());
			query.setParameter("quantidadePortas",automovel.getQuantidadePortas());
			query.setParameter("quantidadePassageiro",automovel.getQuantidadePassageiro());
			query.setParameter("tipoCombustivel",automovel.getTipoCombustivel());
			
			return query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO SELECIONAR CATEGORIAS SUPERIORES  DE CAMINHONETA DE PASSAGEIROS");
		}finally {
			em.close();
		}
	}

	public List<CategoriaVeiculo> categoriasSuperiorAutomovelPequeno(Automovel automovel) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			TypedQuery<CategoriaVeiculo> query= em.createQuery(SELECIONAR_AUTOMOVEL_PEQUENO.replace('?','>'),CategoriaVeiculo.class);
			query.setParameter("tipoCambio",automovel.getTipoCambio());
			query.setParameter("tamanhoVeiculo",automovel.getTamanhoVeiculo());
			query.setParameter("quantidadePortas",automovel.getQuantidadePortas());
			query.setParameter("quantidadePassageiro",automovel.getQuantidadePassageiro());
			query.setParameter("tipoCombustivel",automovel.getTipoCombustivel());
			
			return query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO SELECIONAR CATEGORIAS SUPERIORES DE AUTOMOVEL PEQUENO");
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
