package entidade;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity(name = "categoria_veiculo" )
@NamedQueries({
	@NamedQuery(name = "categoriaVeiculo.categorizarCaminhonetaCarga" ,
			query = "select categoriaVeiculo from entidade.CategoriaVeiculo as categoriaVeiculo "
					+ "inner join categoriaVeiculo.veiculoExemplo as caminhonetaCarga "
					+ "where caminhonetaCarga.potencia <= :potencia "
					+ "and caminhonetaCarga.desenpenho <= :desenpenho "
					+ "and caminhonetaCarga.capacidadeCarga <= :capacidadeCarga "
					+ "and caminhonetaCarga.tipoAcionamentoEmbreagem <= :tipoAcionamentoEmbreagem "
					+ "and caminhonetaCarga.distanciaEixos <= :distanciaEixos "
					+ "and caminhonetaCarga.capacidadeCombustivel <= :capacidadeCombustivel "
					+ "and caminhonetaCarga.torqueMotor <= :torqueMotor "
					+ "order by(caminhonetaCarga.capacidadeCarga, caminhonetaCarga.potencia, caminhonetaCarga.torqueMotor,"
					+ "caminhonetaCarga.desenpenho, caminhonetaCarga.distanciaEixos, caminhonetaCarga.tipoAcionamentoEmbreagem, "
					+ "caminhonetaCarga.tipoCombustivel, caminhonetaCarga.capacidadeCombustivel) desc"),
	@NamedQuery(name = "categoriaVeiculo.categorizarAutomovelPequeno" ,
			query = "select categoriaVeiculo from entidade.CategoriaVeiculo as categoriaVeiculo "
					+ "inner join categoriaVeiculo.veiculoExemplo as automovel "
					+ "where automovel.tipoAutomovel = 0  "
					+ "and automovel.tipoCambio <= :tipoCambio "
					+ "and automovel.tamanhoVeiculo <= :tamanhoVeiculo "
					+ "and automovel.quantidadePortas <= :quantidadePortas "
					+ "and automovel.quantidadePassageiro <= :quantidadePassageiro "
					+ "and automovel.tipoCombustivel <= :tipoCombustivel "
					+ "order by(automovel.tipoCambio ,automovel.tamanhoVeiculo, "
					+ "automovel.quantidadePortas, automovel.quantidadePassageiro, "
					+ "automovel.tipoCombustivel) desc"),
	@NamedQuery(name = "categoriaVeiculo.categorizarCaminhonetaPassageiro" ,
			query = "select categoriaVeiculo from entidade.CategoriaVeiculo as categoriaVeiculo "
					+ "inner join categoriaVeiculo.veiculoExemplo as automovel "
					+ "where automovel.tipoAutomovel = 1 "
					+ "and automovel.tipoAirBag <= :tipoAirBag "
					+ "and automovel.tipoCambio <= :tipoCambio "
					+ "and automovel.tamanhoVeiculo <= :tamanhoVeiculo "
					+ "and automovel.quantidadePortas <= :quantidadePortas "
					+ "and automovel.quantidadePassageiro <= :quantidadePassageiro "
					+ "and automovel.tipoCombustivel <= :tipoCombustivel "
					+ "order by(automovel.tipoAirBag, automovel.tipoCambio ,automovel.tamanhoVeiculo, "
					+ "automovel.quantidadePortas, automovel.quantidadePassageiro, automovel.tipoCombustivel) desc"),
	@NamedQuery(name = "categoriaVeiculo.buscaPorBusca" , 
			query = "select c from entidade.CategoriaVeiculo as c"
					+ " where upper(c.tipo) like upper(:tipo)"
					+ " or upper(c.descricao) like upper(:descricao)"
					+ " or c.quilometragemRevisao = :quilometragemRevisao"
					+ " or c.horasRevisao = :horasRevisao"
					+ " or c.horasLimpesa = :horasLimpesa"
					+ " or c.valorDiaria = :valorDiaria")
})
public class CategoriaVeiculo extends Entidade {
	
	private static final long serialVersionUID = 1L;
	@Column(length = 10, unique = true)
	private String tipo;
	@Column(name = "quilometragem_revisao", nullable = false)
	private float quilometragemRevisao;
	@Column(name = "horas_revisao", nullable = false)
	private float horasRevisao;
	@Column(name = "horas_limpesa", nullable = false)
	private float horasLimpesa;
	@Column(name = "valor_diaria",nullable = false)
	private Float valorDiaria;
	@Column(nullable = false)
	private String descricao;
	@OneToOne(cascade = CascadeType.REMOVE,targetEntity = Veiculo.class)
	private Veiculo veiculoExemplo;
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public float getQuilometragemRevisao() {
		return quilometragemRevisao;
	}
	
	public void setQuilometragemRevisao(float quilometragemRevisao) {
		this.quilometragemRevisao = quilometragemRevisao;
	}
	
	public float getHorasRevisao() {
		return horasRevisao;
	}
	
	public void setHorasRevisao(float horasRevisao) {
		this.horasRevisao = horasRevisao;
	}
	
	public float getHorasLimpesa() {
		return horasLimpesa;
	}
	
	public void setHorasLimpesa(float horasLimpesa) {
		this.horasLimpesa = horasLimpesa;
	}
	
	public Float getValorDiaria() {
		return valorDiaria;
	}
	
	public void setValorDiaria(Float valorDiaria) {
		this.valorDiaria = valorDiaria;
	}

	public Veiculo getVeiculoExemplo() {
		return veiculoExemplo;
	}

	public void setVeiculoExemplo(Veiculo veiculoExemplo) {
		this.veiculoExemplo = veiculoExemplo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(horasLimpesa);
		result = prime * result + Float.floatToIntBits(horasRevisao);
		result = prime * result + Float.floatToIntBits(quilometragemRevisao);
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((valorDiaria == null) ? 0 : valorDiaria.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CategoriaVeiculo))
			return false;
		CategoriaVeiculo other = (CategoriaVeiculo) obj;
		if (Float.floatToIntBits(horasLimpesa) != Float.floatToIntBits(other.horasLimpesa))
			return false;
		if (Float.floatToIntBits(horasRevisao) != Float.floatToIntBits(other.horasRevisao))
			return false;
		if (Float.floatToIntBits(quilometragemRevisao) != Float.floatToIntBits(other.quilometragemRevisao))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (valorDiaria == null) {
			if (other.valorDiaria != null)
				return false;
		} else if (!valorDiaria.equals(other.valorDiaria))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CategoriaVeiculo [tipo=" + tipo + ", quilometragemRevisao=" + quilometragemRevisao + ", horasRevisao="
				+ horasRevisao + ", horasLimpesa=" + horasLimpesa + ", valorDiaria=" + valorDiaria + ", veiculos="
				+"]";
	}
	
}
