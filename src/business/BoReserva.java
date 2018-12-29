package business;

import java.time.LocalDateTime;
import java.util.List;

import adapter.ReservaDisponibilidade;
import banco.ReservaHoje;
import banco.ReservaPendente;
import dao.DaoReserva;
import dao.IDaoReserva;
import entidade.Automovel;
import entidade.CaminhonetaCarga;
import entidade.CategoriaVeiculo;
import entidade.Cliente;
import entidade.Filial;
import entidade.Reserva;
import excecoes.BoException;
import excecoes.DaoException;

public class BoReserva implements IBoReserva {
	private IDaoReserva daoReserva = new DaoReserva();
	private IBoVeiculo boVeiculo = BoVeiculo.getInstance();
	private IBoLocacao boLocacao = BoLocacao.getInstance();
	private static IBoReserva instance;
	
	private BoReserva() {}
	
	public static IBoReserva getInstance() {
		if(instance == null)
			instance = new BoReserva();
		return instance;
	}
	
	/*
	public static void main(String[] args) {
		ConnectionFactory.setUser("postgres","admin");
		Endereco endereco = new Endereco();
		endereco.setCep("3213-a");
		
		Endereco endereco2 = new Endereco();
		endereco2.setCep("adsd312-a");
		
		Filial filial = new Filial();
		filial.setAtivo(true);
		filial.setEndereco(endereco);
		filial.setNome("Cabana veiculo");
		
		Fisico fisico = new Fisico();
		fisico.setAtivo(true);
		fisico.setNome("Wanderson Pereira");
		fisico.setCpf("3123dsad-13");
		fisico.setDataNascimento(LocalDate.of(1998, 06, 17));
		fisico.setEndereco(endereco2);
		fisico.setEmail("wanderson@Exemplo.com");
		fisico.setTelefone("(32)d3213");
		fisico.setSexo(Sexo.MASCULINO);
		
		try {
			Filial filial = BoFilial.getInstance().buscarID(new Long(15));
			Funcionario funcionario = BoFuncionario.getInstance().buscarID(new Long(22));
			
			Reserva reserva = new Reserva();
			reserva.setCategoriaVeiculo(BoCategoriaVeiculo.getInstance().buscarID(new Long(3)));
			reserva.setCliente(BoFisico.getInstance().buscarID(new Long(13)));
			reserva.setDataDevolucao(LocalDateTime.of(2018,11,30,12,0));
			reserva.setDataRetirada(LocalDateTime.of(2018,11,22,11,0));
			reserva.setEstadoReserva(EstadoRerserva.PENDENTE);
			reserva.setFilial(filial);
			reserva.setFuncionario(funcionario);
			reserva.setValor(30f);
			
			getInstance().cadastrarEditar(reserva);
		} catch (BoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	
	@Override
	public void cadastrarEditar(Reserva entidade) throws BoException {
		try {
			if(entidade.getId() != null)
				daoReserva.editar(entidade);
			else {
				validarConcorrenciaReserva(entidade);
				daoReserva.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(Reserva entidade) throws BoException {
		try {
			daoReserva.excluir(entidade);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());	
		}
	}

	@Override
	public Reserva buscarID(Long id) throws BoException {
		try {
			return daoReserva.buscarID(id);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());		
		}
	}

	@Override
	public List<Reserva> buscarAll() throws BoException {
		try {
			return daoReserva.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Reserva> buscarPorExemplo(Reserva exemploEntidade) throws BoException {
		try {
			return daoReserva.buscarPorExemplo(exemploEntidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	private void validarConcorrenciaReserva(Reserva reserva)throws BoException {
		long totalPrevistoLocacao = boLocacao.totalLocacoePrevisaoEntrega(reserva.getFilial(),reserva.getCategoriaVeiculo(),reserva.getDataRetirada());
		long totalDisponivel = boVeiculo.totalVeiculoDisponivel(reserva.getFilial(),reserva.getCategoriaVeiculo());
		long totalReservado = totalReservaDataRetirada(reserva.getCategoriaVeiculo());
		System.out.println();
		System.out.println(totalDisponivel+"total disponivel");
		System.out.println(totalPrevistoLocacao+"total previsto locado");
		System.out.println(totalReservado+"total reservado");
		System.out.println();
		System.out.println(totalDisponivel+totalPrevistoLocacao+"total disponivel e previsto");
		long reservavel = totalDisponivel+totalPrevistoLocacao;
		if(totalDisponivel+totalPrevistoLocacao == 0)
			throw new BoException("Não Há veiculos disponiveis para essa categoria na filial");
		if(reservavel <= totalReservado)
			throw new BoException("NÃO HÁ VEICULOS SUFICIENTES DISPONIVEIS PARA RESERVA ESSA DATA NESTA CATEGORIA E FILIAL");
	}
	
	@Override
	public List<ReservaHoje> buscarReservaHoje() throws BoException {
		try {
			return daoReserva.buscarReservaHoje();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	

	@Override
	public List<ReservaPendente> buscarReservaPendente(String dadoCliente) throws BoException {
		try {
			Cliente cliente = new Cliente();
			cliente.setNome(dadoCliente);
			cliente.setCodigo(dadoCliente);
			cliente.setEmail(dadoCliente);
			cliente.setTelefone(dadoCliente);
			return daoReserva.buscarReservaPendente(cliente);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	@Override
	public List<ReservaPendente> buscarReservaPendente(String dadoCliente, Filial filial) throws BoException {
		try {
			Cliente cliente = new Cliente();
			cliente.setNome(dadoCliente);
			cliente.setCodigo(dadoCliente);
			cliente.setEmail(dadoCliente);
			cliente.setTelefone(dadoCliente);
			return daoReserva.buscarReservaPendente(cliente,filial);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	@Override
	public List<ReservaDisponibilidade> reservaDisponibilidadeSuperior(CategoriaVeiculo categoriaVeiculo, Long filialId,
			LocalDateTime horario) throws BoException {
		try {
			if(categoriaVeiculo.getVeiculoExemplo() instanceof CaminhonetaCarga)
				return daoReserva.buscarReservaDisponibilidadeSuperior((CaminhonetaCarga) categoriaVeiculo.getVeiculoExemplo(), filialId, horario);
			else if(categoriaVeiculo.getVeiculoExemplo() instanceof Automovel) {
				return daoReserva.buscarReservaDisponibilidadeSuperior((Automovel) categoriaVeiculo.getVeiculoExemplo(), filialId, horario);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<ReservaDisponibilidade> buscarReservaDisponibilidade(Long filialId, LocalDateTime horario)
			throws BoException {
		try {
			return daoReserva.buscarReservaDisponibilidade(filialId, horario);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	public Boolean disponibilidadeCategoriaEmFilial(Long categoriaVeiculoId, Long filialId, LocalDateTime horario) throws BoException{
		try {
		 if(daoReserva.buscarReservaDisponibilidade(categoriaVeiculoId, filialId,horario).getDisponivel() >0)
			 return true;
		 return false;
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	@Override
	public long totalReservaDataRetirada(CategoriaVeiculo categoriaVeiculo) throws BoException {
		try {
			return daoReserva.totalReservaDataRetirada(categoriaVeiculo);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
}
