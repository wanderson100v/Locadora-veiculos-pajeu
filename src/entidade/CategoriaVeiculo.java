package entidade;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity(name = "categoria_veiculo" )
public class CategoriaVeiculo extends Entidade {
	
	private static final long serialVersionUID = 1L;
	@Column(length = 10)
	private String tipo;
	@Column(name = "quilometragem_revisao", nullable = false)
	private float quilometragemRevisao;
	@Column(name = "horas_revisao", nullable = false)
	private float horasRevisao;
	@Column(name = "horas_limpesa", nullable = false)
	private float horasLimpesa;
	@Column(name = "valor_diaria",nullable = false)
	private Float valorDiaria;
	@OneToMany(mappedBy = "categoriaVeiculo" , targetEntity = Veiculo.class)
	private List<Veiculo> veiculos;
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public float getQuilometragemRevisao() {
		return quilometragemRevisao;
	}
	
	public void setQuilometragemRevisao(float quilometragemRevisao) {
		this.quilometragemRevisao = quilometragemRevisao;
	}
	
	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(horasLimpesa);
		result = prime * result + Float.floatToIntBits(horasRevisao);
		result = prime * result + Float.floatToIntBits(quilometragemRevisao);
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((valorDiaria == null) ? 0 : valorDiaria.hashCode());
		result = prime * result + ((veiculos == null) ? 0 : veiculos.hashCode());
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
		if (veiculos == null) {
			if (other.veiculos != null)
				return false;
		} else if (!veiculos.equals(other.veiculos))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CategoriaVeiculo [tipo=" + tipo + ", quilometragemRevisao=" + quilometragemRevisao + ", horasRevisao="
				+ horasRevisao + ", horasLimpesa=" + horasLimpesa + ", valorDiaria=" + valorDiaria + ", veiculos="
				+ veiculos + "]";
	}
	
}
