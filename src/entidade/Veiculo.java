package entidade;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import enumeracoes.TipoCombustivel;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
	@NamedQuery(name = "veiculo.totalDisponivel", query = "select count(*) from "
			+ "Veiculo as veiculo inner join veiculo.filial as filial "
			+ "left join veiculo.categoriaVeiculo as categoriaVeiculo"
			+ " where veiculo.ativo = true and locado = false and veiculo.filial = :filial and veiculo.categoriaVeiculo = :categoria"),
	@NamedQuery(name = "veiculo.totalManutencaoPendente", query = "select count(*) from "
			+ "Manutencao as m inner join m.veiculo as v where v = :veiculo and m.estadoManutencao = PENDENTE")
})
public class Veiculo extends Entidade {
  
	private static final long serialVersionUID = 1L;
	private boolean locado, ativo;
	@Column(length = 10, unique = true)
	private String placa;
	@Column(length = 20)
	private String cor,modelo, fabricante;
	@Column(length = 30, name = "numero_chassi",unique = true)
	private String numeroChassi;
	@Column(length = 30, name = "numero_motor",unique = true)
	private String numeroMotor;
	@Column(name = "torque_motor")
	private Float torqueMotor;
	@Column(name = "tipo_combustivel")
	private TipoCombustivel tipoCombustivel;  
	private Integer quilometragem;
	@Column(name = "ano_fabricante")
	private Integer anoFabricante;
	@Column(name = "ano_modelo")
	private Integer anoModelo;
	@Column(name = "qtd_porta")
	private Integer quantidadePortas;
	@Column(name = "qtd_passageiro")
	private Integer quantidadePassageiro;
	@ManyToOne
	private CategoriaVeiculo categoriaVeiculo;
	@ManyToOne
	private Filial filial;
	@OneToMany(mappedBy = "veiculo", targetEntity = Manutencao.class)
	private List<Manutencao> manutencao;
	@OneToMany(mappedBy = "veiculo", targetEntity = Locacao.class)
	private List<Manutencao> locacoes;
	
	
	public Veiculo() {
		// TODO Auto-generated constructor stub
	}

	public Veiculo(boolean locado, boolean ativo, String placa, String cor, String modelo, String fabricante,
			String numeroChassi, String numeroMotor, float torqueMotor, TipoCombustivel tipoCombustivel,
			Integer quilometragem, Integer anoFabricante, Integer anoModelo, Integer quantidadePortas,
			Integer quantidadePassageiro, CategoriaVeiculo categoriaVeiculo, Filial filial) {
		super();
		this.locado = locado;
		this.ativo = ativo;
		this.placa = placa;
		this.cor = cor;
		this.modelo = modelo;
		this.fabricante = fabricante;
		this.numeroChassi = numeroChassi;
		this.numeroMotor = numeroMotor;
		this.torqueMotor = torqueMotor;
		this.tipoCombustivel = tipoCombustivel;
		this.quilometragem = quilometragem;
		this.anoFabricante = anoFabricante;
		this.anoModelo = anoModelo;
		this.quantidadePortas = quantidadePortas;
		this.quantidadePassageiro = quantidadePassageiro;
		this.categoriaVeiculo = categoriaVeiculo;
		this.filial = filial;
	}
	

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
	
	public boolean isAtivo() {
		return ativo;
	}

	public void setTorqueMotor(Float torqueMotor) {
		this.torqueMotor = torqueMotor;
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
	
	public Float getTorqueMotor() {
		return torqueMotor;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((anoFabricante == null) ? 0 : anoFabricante.hashCode());
		result = prime * result + ((anoModelo == null) ? 0 : anoModelo.hashCode());
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((categoriaVeiculo == null) ? 0 : categoriaVeiculo.hashCode());
		result = prime * result + ((cor == null) ? 0 : cor.hashCode());
		result = prime * result + ((fabricante == null) ? 0 : fabricante.hashCode());
		result = prime * result + ((filial == null) ? 0 : filial.hashCode());
		result = prime * result + ((locacoes == null) ? 0 : locacoes.hashCode());
		result = prime * result + (locado ? 1231 : 1237);
		result = prime * result + ((manutencao == null) ? 0 : manutencao.hashCode());
		result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
		result = prime * result + ((numeroChassi == null) ? 0 : numeroChassi.hashCode());
		result = prime * result + ((numeroMotor == null) ? 0 : numeroMotor.hashCode());
		result = prime * result + ((placa == null) ? 0 : placa.hashCode());
		result = prime * result + ((quantidadePassageiro == null) ? 0 : quantidadePassageiro.hashCode());
		result = prime * result + ((quantidadePortas == null) ? 0 : quantidadePortas.hashCode());
		result = prime * result + ((quilometragem == null) ? 0 : quilometragem.hashCode());
		result = prime * result + ((tipoCombustivel == null) ? 0 : tipoCombustivel.hashCode());
		result = prime * result + Float.floatToIntBits(torqueMotor);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Veiculo))
			return false;
		Veiculo other = (Veiculo) obj;
		if (anoFabricante == null) {
			if (other.anoFabricante != null)
				return false;
		} else if (!anoFabricante.equals(other.anoFabricante))
			return false;
		if (anoModelo == null) {
			if (other.anoModelo != null)
				return false;
		} else if (!anoModelo.equals(other.anoModelo))
			return false;
		if (ativo != other.ativo)
			return false;
		if (categoriaVeiculo == null) {
			if (other.categoriaVeiculo != null)
				return false;
		} else if (!categoriaVeiculo.equals(other.categoriaVeiculo))
			return false;
		if (cor == null) {
			if (other.cor != null)
				return false;
		} else if (!cor.equals(other.cor))
			return false;
		if (fabricante == null) {
			if (other.fabricante != null)
				return false;
		} else if (!fabricante.equals(other.fabricante))
			return false;
		if (filial == null) {
			if (other.filial != null)
				return false;
		} else if (!filial.equals(other.filial))
			return false;
		if (locacoes == null) {
			if (other.locacoes != null)
				return false;
		} else if (!locacoes.equals(other.locacoes))
			return false;
		if (locado != other.locado)
			return false;
		if (manutencao == null) {
			if (other.manutencao != null)
				return false;
		} else if (!manutencao.equals(other.manutencao))
			return false;
		if (modelo == null) {
			if (other.modelo != null)
				return false;
		} else if (!modelo.equals(other.modelo))
			return false;
		if (numeroChassi == null) {
			if (other.numeroChassi != null)
				return false;
		} else if (!numeroChassi.equals(other.numeroChassi))
			return false;
		if (numeroMotor == null) {
			if (other.numeroMotor != null)
				return false;
		} else if (!numeroMotor.equals(other.numeroMotor))
			return false;
		if (placa == null) {
			if (other.placa != null)
				return false;
		} else if (!placa.equals(other.placa))
			return false;
		if (quantidadePassageiro == null) {
			if (other.quantidadePassageiro != null)
				return false;
		} else if (!quantidadePassageiro.equals(other.quantidadePassageiro))
			return false;
		if (quantidadePortas == null) {
			if (other.quantidadePortas != null)
				return false;
		} else if (!quantidadePortas.equals(other.quantidadePortas))
			return false;
		if (quilometragem == null) {
			if (other.quilometragem != null)
				return false;
		} else if (!quilometragem.equals(other.quilometragem))
			return false;
		if (tipoCombustivel != other.tipoCombustivel)
			return false;
		if (Float.floatToIntBits(torqueMotor) != Float.floatToIntBits(other.torqueMotor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Veiculo [locado=" + locado + ", ativo=" + ativo + ", placa=" + placa + ", cor=" + cor + ", modelo="
				+ modelo + ", fabricante=" + fabricante + ", numeroChassi=" + numeroChassi + ", numeroMotor="
				+ numeroMotor + ", torqueMotor=" + torqueMotor + ", tipoCombustivel=" + tipoCombustivel
				+ ", quilometragem=" + quilometragem + ", anoFabricante=" + anoFabricante + ", anoModelo=" + anoModelo
				+ ", quantidadePortas=" + quantidadePortas + ", quantidadePassageiro=" + quantidadePassageiro
				+ ", categoriaVeiculo=" + categoriaVeiculo + ", filial=" + filial + ", manutencao=" + manutencao
				+ ", locacoes=" + locacoes + "]";
	}
  
	
}
