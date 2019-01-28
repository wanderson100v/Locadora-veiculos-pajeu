package entidade;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "categoria_veiculo" )
@NamedQuery(name = "categoriaVeiculo.buscaPorBusca" , 
		query = "select c from entidade.CategoriaVeiculo as c"
			+ " where upper(c.tipo) like upper(:tipo)"
			+ " or upper(c.descricao) like upper(:descricao)"
			+ " or c.quilometragemRevisao = :quilometragemRevisao"
			+ " or c.horasRevisao = :horasRevisao"
			+ " or c.horasLimpesa = :horasLimpesa"
			+ " or c.valorDiaria = :valorDiaria")
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "categoria_veiculo_seq", allocationSize =1)
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
	@Column(name = "valor_livre", nullable = false)
	private float valorLivre;
	@Column(name = "valor_km", nullable = false)
	private float valorKm;
	@OneToOne(cascade = CascadeType.REMOVE,targetEntity = Veiculo.class)
	private Veiculo veiculoExemplo;
	
	
	public float getValorLivre() {
		return valorLivre;
	}

	public void setValorLivre(float valorLivre) {
		this.valorLivre = valorLivre;
	}

	public float getValorKm() {
		return valorKm;
	}

	public void setValorKm(float valorKm) {
		this.valorKm = valorKm;
	}

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
		int result = super.hashCode();
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + Float.floatToIntBits(horasLimpesa);
		result = prime * result + Float.floatToIntBits(horasRevisao);
		result = prime * result + Float.floatToIntBits(quilometragemRevisao);
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((valorDiaria == null) ? 0 : valorDiaria.hashCode());
		result = prime * result + Float.floatToIntBits(valorKm);
		result = prime * result + Float.floatToIntBits(valorLivre);
		result = prime * result + ((veiculoExemplo == null) ? 0 : veiculoExemplo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoriaVeiculo other = (CategoriaVeiculo) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
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
		if (Float.floatToIntBits(valorKm) != Float.floatToIntBits(other.valorKm))
			return false;
		if (Float.floatToIntBits(valorLivre) != Float.floatToIntBits(other.valorLivre))
			return false;
		if (veiculoExemplo == null) {
			if (other.veiculoExemplo != null)
				return false;
		} else if (!veiculoExemplo.equals(other.veiculoExemplo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tipo " + tipo + ", Valor Diaria " + valorDiaria + ", Valor KMLivre=" 
				+ valorLivre + ", Valor KMControle" + valorKm;
	}
	
}
