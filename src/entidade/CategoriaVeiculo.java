package entidade;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity(name = "categoria_veiculo" )
public class CategoriaVeiculo extends Entidade {
	
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
	
	
	
	
}
