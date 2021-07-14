package model.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import model.excecoes.DaoException;
import model.vo.CategoriaVeiculo;
import model.vo.Filial;
import model.vo.Veiculo;
import model.dao.sql.ConnectionFactory;

public class DaoVeiculo extends Dao<Veiculo> implements IDaoVeiculo {

	public DaoVeiculo() {
		super(Veiculo.class);
	}

	@Override
	public long totalVeiculoDisponivel(Filial filial, CategoriaVeiculo categoria)throws DaoException {
		long total = 0;
		try {
			em = ConnectionFactory.getConnection();
			Query query = em.createQuery(TOTAL_DISPONIVEL);
			query.setParameter("filial",filial);
			query.setParameter("categoria", categoria);
			total =(Long) query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR TOTAL DE VEICULOS DISPONIVEIS POR FILIAL E CATEGORIA DE VEICULO ");
		}
		return total;
	}
	
	@SuppressWarnings("unchecked")
	public List<Veiculo> buscarVeiculosDisponivel(Long filialId,Long categoriaVeiculoId, Veiculo veiculo)throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			List<BigInteger> ids = em.createNativeQuery
			(
					 " select distinct v.id from veiculo as v"
					+" inner join manutencao as m on (v.id != m.veiculo_id or m.estado = 2)"
					+" inner join filial as f on(f.id = v.filial_id)"
					+" inner join categoria_veiculo as c on(c.id = v.categoriaveiculo_id)"
					+" where v.ativo = true"
					+" and v.locado = false"
					+" and (upper(v.cor) like upper(:cor)"
					+" or upper(v.fabricante) like upper(:fabricante)"
					+" or upper(v.modelo) like upper(:modelo)"
					+" or upper(v.numero_chassi) like upper(:chassi)"
					+" or upper(v.placa) like upper(:placa)"
					+" or upper(v.numero_motor) like upper(:numMotor))"
					+" and f.id = :filialId"
					+" and c.id = :categoriaVeiculoId ")
			.setParameter("cor","%"+veiculo.getCor()+"%")
			.setParameter("fabricante","%"+veiculo.getFabricante()+"%")
			.setParameter("modelo","%"+veiculo.getModelo()+"%")
			.setParameter("chassi","%"+veiculo.getNumeroChassi()+"%")
			.setParameter("placa","%"+veiculo.getPlaca()+"%")
			.setParameter("numMotor","%"+veiculo.getNumeroMotor()+"%")
			.setParameter("filialId",filialId)
			.setParameter("categoriaVeiculoId", categoriaVeiculoId)
			.getResultList();
			List<Veiculo> veiculos = new ArrayList<>();
			for(BigInteger id : ids)
				veiculos.add(buscarID(id.longValueExact()));
			return veiculos;
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR VEICULOS DIPONIVEIS POR FILIAL E CATEGORIA ");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Veiculo> buscarVeiculosDisponivel(Long filialId, Veiculo veiculo)throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			List<BigInteger> ids = em.createNativeQuery
					(
					 " select distinct v.id from veiculo as v"
					+" inner join manutencao as m on (v.id != m.veiculo_id or m.estado = 2)"
					+" inner join filial as f on(f.id = v.filial_id)"
					+" where v.ativo = true"
					+" and v.locado = false"
					+" and (upper(v.cor) like upper(:cor)"
					+" or upper(v.fabricante) like upper(:fabricante)"
					+" or upper(v.modelo) like upper(:modelo)"
					+" or upper(v.numero_chassi) like upper(:chassi)"
					+" or upper(v.placa) like upper(:placa)"
					+" or upper(v.numero_motor) like upper(:numMotor))"
					+" and f.id = :filialId")
			.setParameter("cor","%"+veiculo.getCor()+"%")
			.setParameter("fabricante","%"+veiculo.getFabricante()+"%")
			.setParameter("modelo","%"+veiculo.getModelo()+"%")
			.setParameter("chassi","%"+veiculo.getNumeroChassi()+"%")
			.setParameter("placa","%"+veiculo.getPlaca()+"%")
			.setParameter("numMotor","%"+veiculo.getNumeroMotor()+"%")
			.setParameter("filialId",filialId)
			.getResultList();
			List<Veiculo> veiculos = new ArrayList<>();
			for(BigInteger id : ids)
				veiculos.add(buscarID(id.longValueExact()));
			return veiculos;
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR VEICULOS DIPONIVEIS POR FILIAL ");
		}
	}

	@Override
	public long totalManutencaoPendente(Veiculo veiculo) throws DaoException {
		long total = 0;
		try {
			em = ConnectionFactory.getConnection();
			Query query = em.createNamedQuery(TOTAL_MANUTENCAO_PENDENTE);
			query.setParameter("veiculo",veiculo);
			total =(Long) query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR TOTAL DE MANUTENCÕES DO VEICULO");
		}
		return total;
	}

}
