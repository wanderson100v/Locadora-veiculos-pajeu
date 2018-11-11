package entidade;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import enumeracoes.TipoCombustivel;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Veiculo extends Entidade {
  
	private boolean locado, ativo;
	@Column(length = 10, unique = true ,nullable = false)
	private String placa;
	@Column(length = 20, nullable = false)
	private String cor,modelo, fabricante;
	@Column(length = 30, name = "numero_chassi", nullable = false,unique = true)
	private String numeroChassi;
	@Column(length = 30, name = "numero_motor", nullable = false,unique = true)
	private String numeroMotor;
	@Column(name = "torque_motor", nullable = false)
	private float torqueMotor;
	@Column(name = "tipo_combustivel", nullable = false)
	private TipoCombustivel tipoCombustivel;  
	@Column(nullable = false)
	private Integer quilometragem;
	@Column(name = "ano_fabricante", nullable = false)
	private Integer anoFabricante;
	@Column(name = "ano_modelo", nullable = false)
	private Integer anoModelo;
	@Column(name = "qtd_passageiro", nullable = false)
	private Integer quantidadePortas;
	@Column(name = "qtd_porta", nullable = false)
	private Integer quantidadePassageiro;
	@ManyToOne
	@JoinColumn(nullable = false)
	private CategoriaVeiculo categoriaVeiculo;
	@ManyToOne
	private Filial filial;
	@OneToMany(mappedBy = "veiculo", targetEntity = Manutencao.class)
	private List<Manutencao> manutencao;
	@OneToMany(mappedBy = "veiculo", targetEntity = Locacao.class)
	private List<Manutencao> locacoes;
	
	
	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public void setLocado(boolean locado) {
		this.locado = locado;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public List<Manutencao> getLocacoes() {
		return locacoes;
	}

	public void setLocacoes(List<Manutencao> locacoes) {
		this.locacoes = locacoes;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public String getCor() {
		return cor;
	}
	
	public Boolean getLocado() {
		return locado;
	}

	public void setLocado(Boolean locado) {
		this.locado = locado;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public CategoriaVeiculo getCategoriaVeiculo() {
		return categoriaVeiculo;
	}

	public void setCategoriaVeiculo(CategoriaVeiculo categoriaVeiculo) {
		this.categoriaVeiculo = categoriaVeiculo;
	}

	public List<Manutencao> getManutencao() {
		return manutencao;
	}

	public void setManutencao(List<Manutencao> manutencao) {
		this.manutencao = manutencao;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}
	
	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public Integer getAnoFabricante() {
		return anoFabricante;
	}

	public void setAnoFabricante(Integer anoFabricante) {
		this.anoFabricante = anoFabricante;
	}

	public Integer getAnoModelo() {
		return anoModelo;
	}

	public void setAnoModelo(Integer anoModelo) {
		this.anoModelo = anoModelo;
	}

	public Integer getQuantidadePortas() {
		return quantidadePortas;
	}

	public void setQuantidadePortas(Integer quantidadePortas) {
		this.quantidadePortas = quantidadePortas;
	}

	public Integer getQuantidadePassageiro() {
		return quantidadePassageiro;
	}

	public void setQuantidadePassageiro(Integer quantidadePassageiro) {
		this.quantidadePassageiro = quantidadePassageiro;
	}

	public String getPlaca() {
		return placa;
	}
	
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
	public String getNumeroChassi() {
		return numeroChassi;
	}
	
	public void setNumeroChassi(String numeroChassi) {
		this.numeroChassi = numeroChassi;
	}
	
	public String getNumeroMotor() {
		return numeroMotor;
	}
	
	public void setNumeroMotor(String numeroMotor) {
		this.numeroMotor = numeroMotor;
	}
	
	public float getTorqueMotor() {
		return torqueMotor;
	}
	
	public void setTorqueMotor(float torqueMotor) {
		this.torqueMotor = torqueMotor;
	}
	
	public TipoCombustivel getTipoCombustivel() {
		return tipoCombustivel;
	}
	
	public void setTipoCombustivel(TipoCombustivel tipoCombustivel) {
		this.tipoCombustivel = tipoCombustivel;
	}
	
	public Integer getQuilometragem() {
		return quilometragem;
	}
	
	public void setQuilometragem(Integer quilometragem) {
		this.quilometragem = quilometragem;
	}
  
}
