package model.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import model.excecoes.DaoException;
import model.vo.Automovel;
import model.vo.CaminhonetaCarga;
import model.vo.CategoriaVeiculo;
import model.dao.sql.ConnectionFactory;

public class DaoCategoriaVeiculo extends Dao<CategoriaVeiculo> implements IDaoCategoriaVeiculo  {

	public DaoCategoriaVeiculo() {
		super(CategoriaVeiculo.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<CategoriaVeiculo> buscarCategoriasCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			Query query= em.createNativeQuery(SELECIONAR_CATEGORIA_CAMINHONETA_CARGA.replace('?','<'),CategoriaVeiculo.class);
			query.setParameter("potencia",caminhonetaCarga.getPotencia());
			query.setParameter("desenpenho",caminhonetaCarga.getDesenpenho());
			query.setParameter("capacidadeCarga",caminhonetaCarga.getCapacidadeCarga());
			query.setParameter("tipoAcionamentoEmbreagem",caminhonetaCarga.getTipoAcionamentoEmbreagem().ordinal());
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

	@SuppressWarnings("unchecked")
	public List<CategoriaVeiculo> buscarCategoriasCaminhonetaPassageiro(Automovel automovel) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			Query query = em.createNativeQuery(SELECIONAR_CAMINHONETA_PASSAGEIRO.replace('?','<'),CategoriaVeiculo.class);
			query.setParameter("tipoAirBag",automovel.getTipoAirBag().ordinal());
			query.setParameter("tipoCambio",automovel.getTipoCambio().ordinal());
			query.setParameter("tamanhoVeiculo",automovel.getTamanhoVeiculo().ordinal());
			query.setParameter("quantidadePortas",automovel.getQuantidadePortas());
			query.setParameter("quantidadePassageiro",automovel.getQuantidadePassageiro());
			query.setParameter("tipoCombustivel",automovel.getTipoCombustivel().ordinal());
			return query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO SELECIONAR CATEGORIAS DE CAMINHONETA DE PASSAGEIROS");
		}finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<CategoriaVeiculo> buscarCategoriaAutomovelPequeno(Automovel automovel) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			Query query = em.createNativeQuery(SELECIONAR_AUTOMOVEL_PEQUENO.replace('?','<'),CategoriaVeiculo.class);
			System.err.println(automovel.getTipoCambio());
			query.setParameter("tipoCambio",automovel.getTipoCambio().ordinal());
			query.setParameter("tamanhoVeiculo",automovel.getTamanhoVeiculo().ordinal());
			query.setParameter("quantidadePortas",automovel.getQuantidadePortas());
			query.setParameter("quantidadePassageiro",automovel.getQuantidadePassageiro());
			query.setParameter("tipoCombustivel",automovel.getTipoCombustivel().ordinal());
			return query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO SELECIONAR CATEGORIAS DE AUTOMOVEL PEQUENO");
		}finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<CategoriaVeiculo> buscarCategoriasSuperiorCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			Query query= em.createNativeQuery(SELECIONAR_CATEGORIA_CAMINHONETA_CARGA.replace('?','>'),CategoriaVeiculo.class);
			query.setParameter("potencia",caminhonetaCarga.getPotencia());
			query.setParameter("desenpenho",caminhonetaCarga.getDesenpenho());
			query.setParameter("capacidadeCarga",caminhonetaCarga.getCapacidadeCarga());
			query.setParameter("tipoAcionamentoEmbreagem",caminhonetaCarga.getTipoAcionamentoEmbreagem().ordinal());
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

	@SuppressWarnings("unchecked")
	public List<CategoriaVeiculo> buscarCategoriasSuperiorCaminhonetaPassageiro(Automovel automovel) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			Query query= em.createNativeQuery(SELECIONAR_CAMINHONETA_PASSAGEIRO.replace('?','>'),CategoriaVeiculo.class);
			query.setParameter("tipoAirBag",automovel.getTipoAirBag().ordinal());
			query.setParameter("tipoCambio",automovel.getTipoCambio().ordinal());
			query.setParameter("tamanhoVeiculo",automovel.getTamanhoVeiculo().ordinal());
			query.setParameter("quantidadePortas",automovel.getQuantidadePortas());
			query.setParameter("quantidadePassageiro",automovel.getQuantidadePassageiro());
			query.setParameter("tipoCombustivel",automovel.getTipoCombustivel().ordinal());
			return query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO SELECIONAR CATEGORIAS SUPERIORES  DE CAMINHONETA DE PASSAGEIROS");
		}finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<CategoriaVeiculo> buscarCategoriasSuperiorAutomovelPequeno(Automovel automovel) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			Query query= em.createNativeQuery(SELECIONAR_AUTOMOVEL_PEQUENO.replace('?','>'),CategoriaVeiculo.class);
			query.setParameter("tipoCambio",automovel.getTipoCambio().ordinal());
			query.setParameter("tamanhoVeiculo",automovel.getTamanhoVeiculo().ordinal());
			query.setParameter("quantidadePortas",automovel.getQuantidadePortas());
			query.setParameter("quantidadePassageiro",automovel.getQuantidadePassageiro());
			query.setParameter("tipoCombustivel",automovel.getTipoCombustivel().ordinal());
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
			TypedQuery<CategoriaVeiculo> typedQuery = em.createQuery(BUSCA_POR_BUSCA, CategoriaVeiculo.class);
			typedQuery.setParameter("tipo","%"+categoriaVeiculo.getTipo()+"%");
			typedQuery.setParameter("descricao","%"+categoriaVeiculo.getDescricao()+"%");
			typedQuery.setParameter("quilometragemRevisao",categoriaVeiculo.getQuilometragemRevisao());
			typedQuery.setParameter("horasRevisao",categoriaVeiculo.getHorasRevisao());
			typedQuery.setParameter("horasLimpesa",categoriaVeiculo.getHorasLimpesa());
			typedQuery.setParameter("valorDiaria",categoriaVeiculo.getValorDiaria());
			return typedQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR CATEGORIAS DE VE�?CULO POR BUSCA - CONTATE ADM");
		}finally {
			em.close();
		}
	}
}
