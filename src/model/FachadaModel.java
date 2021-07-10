package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import model.adapter.ReservaDisponibilidade;
import model.banco.ReservaHoje;
import model.banco.ReservaPendente;
import model.business.BoAcessorio;
import model.business.BoAutomovel;
import model.business.BoBackup;
import model.business.BoCaminhonetaCarga;
import model.business.BoCategoriaVeiculo;
import model.business.BoEndereco;
import model.business.BoFilial;
import model.business.BoFisico;
import model.business.BoFuncionario;
import model.business.BoJuridico;
import model.business.BoLocacao;
import model.business.BoManutencao;
import model.business.BoReserva;
import model.business.BoVeiculo;
import model.business.IBoAcessorio;
import model.business.IBoAutomovel;
import model.business.IBoBackup;
import model.business.IBoCaminhonetaCarga;
import model.business.IBoCategoriaVeiculo;
import model.business.IBoEndereco;
import model.business.IBoFilial;
import model.business.IBoFisico;
import model.business.IBoFuncionario;
import model.business.IBoJuridico;
import model.business.IBoLocacao;
import model.business.IBoManutencao;
import model.business.IBoReserva;
import model.business.IBoVeiculo;
import model.enumeracoes.Cargo;
import model.enumeracoes.EstadoRerserva;
import model.excecoes.BoException;
import model.excecoes.ValidarException;
import model.vo.Acessorio;
import model.vo.Automovel;
import model.vo.Backup;
import model.vo.CaminhonetaCarga;
import model.vo.CategoriaVeiculo;
import model.vo.Endereco;
import model.vo.Filial;
import model.vo.Fisico;
import model.vo.Funcionario;
import model.vo.Juridico;
import model.vo.Locacao;
import model.vo.Manutencao;
import model.vo.Reserva;
import model.vo.Veiculo;

public class FachadaModel {
	
	IBoAutomovel iboAutomovel;
	IBoCategoriaVeiculo iboCategoriaVeiculo;
	IBoCaminhonetaCarga iboCaminhonetaCarga;
	IBoLocacao iboLocacao;
	IBoVeiculo iboVeiculo;
	IBoFisico iboFisico;
	IBoJuridico iboJuridico;
	IBoReserva iboReserva;
	IBoBackup iboBackup;
	IBoEndereco iboEndereco;
	IBoFilial iboFilial;
	IBoManutencao iboManutencao;
	IBoFuncionario iboFuncionario;
	IBoAcessorio iboAcessorio;
	
	public static FachadaModel instance;
	
	public static FachadaModel getInstance() {
		if(instance == null)
			instance = new FachadaModel();
		return instance;
	}
	
	private FachadaModel() {
		this.iboAutomovel = new BoAutomovel();
		this.iboCategoriaVeiculo = new BoCategoriaVeiculo();
		this.iboCaminhonetaCarga = new BoCaminhonetaCarga();
		this.iboLocacao = new BoLocacao();
		this.iboVeiculo = new BoVeiculo();
		this.iboFisico = new BoFisico();
		this.iboJuridico = new BoJuridico();
		this.iboReserva = new BoReserva();
		this.iboBackup = new BoBackup();
		this.iboEndereco = new BoEndereco();
		this.iboFilial = new BoFilial();
		this.iboManutencao = new BoManutencao();
		this.iboFuncionario = new BoFuncionario();
		this.iboAcessorio = new BoAcessorio();
	}
	
	//--Veículo
	public long totalVeiculoDisponivel(Filial filial, CategoriaVeiculo categoria)throws BoException{
		return iboVeiculo.totalVeiculoDisponivel(filial, categoria);
	}
	
	public long totalManutencoesPententes(Veiculo veiculo) throws BoException{
		return iboVeiculo.totalManutencoesPententes(veiculo);
	}
	
	public List<Veiculo> buscarVeiculosDisponivel(Long filialId, Long categoriaVeiculoId, String dadoVeiculo) throws BoException
	{
		return iboVeiculo.buscarVeiculosDisponivel(filialId, categoriaVeiculoId, dadoVeiculo);
	}

	public List<Veiculo> buscarVeiculosDisponivel(Long filialId, String dadoVeiculo) throws BoException{
		return iboVeiculo.buscarVeiculosDisponivel(filialId, dadoVeiculo);
	}
	
	public List<Veiculo> buscarVeiculos(String busca) throws BoException{
		return iboVeiculo.buscaPorBuscaAbrangente(busca);
	}
	
	
	//-- Automóvel
	public void cadastrarEditarAutomovel(Automovel automovel) throws BoException {
		if(automovel.getId() == null) {
			CategoriaVeiculo categoriaVeiculo = iboCategoriaVeiculo.categorizarAutomovel(automovel);
			automovel.setCategoriaVeiculo(categoriaVeiculo);
		}
		iboAutomovel.cadastrarEditar(automovel);
	}
	
	public void excluirAutomovel(Automovel automovel) throws BoException {
		iboAutomovel.excluir(automovel);
	}

	public List<Automovel> buscarAutomoveis(String busca) throws BoException{
		return iboAutomovel.buscaPorBuscaAbrangente(busca);
	}
	
	
	//-- Caminhoneta de Carga
	public void cadastrarEditarCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga)throws BoException  {
		if(caminhonetaCarga.getId() == null)
			caminhonetaCarga.setCategoriaVeiculo(iboCategoriaVeiculo.categorizarCaminhonetaCarga(caminhonetaCarga));
		iboCaminhonetaCarga.cadastrarEditar(caminhonetaCarga);
	}
	
	public void excluirCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws BoException{
		iboCaminhonetaCarga.excluir(caminhonetaCarga);
	}
	
	public List<CaminhonetaCarga> buscarCaminhonetasCarga(String busca) throws BoException{
		return iboCaminhonetaCarga.buscaPorBuscaAbrangente(busca);
	}
	
	//-- Categoria de veiculo
	public void cadastrarEditarCategoriaVeiculo(CategoriaVeiculo categoriaVeiculo) throws BoException {
		if(categoriaVeiculo.getId() == null) {
			Veiculo veiculoExemplo = categoriaVeiculo.getVeiculoExemplo();
			categoriaVeiculo.setVeiculoExemplo(null);
			iboCategoriaVeiculo.cadastrarEditar(categoriaVeiculo);
			veiculoExemplo.setCategoriaVeiculo(categoriaVeiculo);
			iboVeiculo.cadastrarEditar(veiculoExemplo);
			categoriaVeiculo.setVeiculoExemplo(veiculoExemplo);
			iboCategoriaVeiculo.cadastrarEditar(categoriaVeiculo);
		}else
			iboCategoriaVeiculo.cadastrarEditar(categoriaVeiculo);
	}
	
	public void excluirCategoriaVeiculo(CategoriaVeiculo categoriaVeiculo) throws BoException {
		iboCategoriaVeiculo.excluir(categoriaVeiculo);
	}
	
	public List<CategoriaVeiculo> buscarCategoriasVeiculo(String busca) throws BoException{
		return iboCategoriaVeiculo.buscaPorBuscaAbrangente(busca);
	}
	
	public CategoriaVeiculo buscarCategoriaVeiculoPorId(Long id) throws BoException {
		return iboCategoriaVeiculo.buscarID(id);
	}
	
	//-- Reservas
	public void cadastrarEditarReserva(Reserva reserva) throws BoException {
		iboReserva.cadastrarEditar(reserva);
	}
	
	public void excluirReserva(Reserva reserva) throws BoException {
		iboReserva.excluir(reserva);
	}
	
	public List<Reserva> buscaReservas(String busca, Reserva reserva,LocalDate de , LocalDate ate) throws BoException{
		return iboReserva.buscaPorBuscaAbrangente(busca, reserva, de, ate);
	}
	
	public Reserva buscarReservaPorID(Long id) throws BoException{
		return iboReserva.buscarID(id);
	}
	
	public List<ReservaPendente> buscarReservaPendente(String dadoCliente) throws BoException {
		return iboReserva.buscarReservaPendente(dadoCliente);
	}
	
	public List<ReservaPendente> buscarReservaPendente(String dadoCliente, Filial filial) throws BoException {
		return iboReserva.buscarReservaPendente(dadoCliente, filial);
	}
	
	public long totalReservaDataRetirada(CategoriaVeiculo categoriaVeiculo) throws BoException{
		return iboReserva.totalReservaDataRetirada(categoriaVeiculo);
	}
	
	public List<ReservaHoje> buscarReservaHoje() throws BoException{
		return iboReserva.buscarReservaHoje();
	}
	
	public List<ReservaDisponibilidade> reservaDisponibilidadeSuperior(CategoriaVeiculo categoriaVeiculo, Long filialId,LocalDateTime horario) throws BoException{
		return iboReserva.reservaDisponibilidadeSuperior(categoriaVeiculo, filialId, horario);
	}
	
	public List<ReservaDisponibilidade> buscarReservaDisponibilidade(Long filialId, LocalDateTime horario)throws BoException{
		return iboReserva.buscarReservaDisponibilidade(filialId, horario);
	}
	
	public Boolean disponibilidadeCategoriaEmFilial(Long categoriaVeiculoId, Long filialId, LocalDateTime horario) throws BoException{
		return iboReserva.disponibilidadeCategoriaEmFilial(categoriaVeiculoId, filialId, horario);
	}

	public List<Map<String, Object>> buscarReservasOrigemLocacaoFinalizada(LocalDate de , LocalDate ate, String agruparPor) throws BoException{
		return iboReserva.buscarReservasOrigemLocacaoFinalizada(de, ate, agruparPor);
	}

	public List<Map<String, Object>> buscarReservasImpedidas(LocalDate de , LocalDate ate)throws BoException{
		return iboReserva.buscarReservasImpedidas(de, ate);
	}
	
	//-- Locação
	public void cadastrarEditarLocacao(Locacao locacao) throws BoException {
		if(locacao.getId() == null) {
			StringBuilder erroLocacao = new StringBuilder();
			iboLocacao.validarLocacao(locacao, erroLocacao);
			if(locacao.getMotorista() == null && locacao.getCliente() instanceof Fisico) {
				locacao.setMotorista((Fisico) locacao.getCliente());
			}else
				erroLocacao.append("\tO cliente não é um motorista apto\n, é necessário selecioanr um motorista\n");
			
			iboFisico.validarClienteMotoristaLocacao(locacao.getMotorista(),locacao.getDataDevolucao().toLocalDate(),erroLocacao);
			
			if(erroLocacao.length()>0)
				throw new ValidarException("Erro(s) ao cadastrar locação : \n"+ erroLocacao.toString());
			
			iboLocacao.cadastrarEditar(locacao);
			locacao.getVeiculo().setLocado(true);
			iboVeiculo.cadastrarEditar(locacao.getVeiculo());
			if(locacao.getReservaOrigem()!= null) {
				locacao.getReservaOrigem().setEstadoReserva(EstadoRerserva.EFETIVADA);
				iboReserva.cadastrarEditar(locacao.getReservaOrigem());
			}
		}else
			iboLocacao.cadastrarEditar(locacao);
	}
	
	public void excluirLocacao(Locacao locacao) throws BoException {
		iboLocacao.excluir(locacao);
	}
	
	public long totalLocacoePrevisaoEntrega(Filial filialEntrega ,CategoriaVeiculo categoriaVeiculo ,LocalDateTime dataLimite)throws BoException{
		return iboLocacao.totalLocacoePrevisaoEntrega(filialEntrega, categoriaVeiculo, dataLimite);
	}
	
	public Object[] calcularValorLocacaoDetalhamento(Locacao locacao) throws BoException{
		return iboLocacao.calcularValorLocacaoDetalhamento(locacao);
	}
	
	public Object[] calcularValorLocacaoDetalhamento(Locacao locacao, int novaQuilometragem, LocalDateTime dataDevulucaoAtt, 
			Boolean abastecer , Boolean limpeza) throws BoException{
		return iboLocacao.calcularValorLocacaoDetalhamento(locacao, novaQuilometragem, dataDevulucaoAtt, abastecer, limpeza);
	}
	
	public List<Map<String, Object>> buscarLocacoesFinalizadas(LocalDate de , LocalDate ate, String agruparPor) throws BoException{
		return iboLocacao.buscarLocacoesFinalizadas(de, ate, agruparPor);
	}
	
	public List<Locacao> buscarLocacoes(String busca, Locacao locacao,LocalDate de , LocalDate ate) throws BoException {
		return iboLocacao.buscaPorBuscaAbrangente(busca, locacao, de, ate);
	}
	
	
	//-- Cliente físico
	public void cadastrarEditarClienteFisico(Fisico fisico) throws BoException {
		iboFisico.cadastrarEditar(fisico);
	}
	
	public void excluirClienteFisico(Fisico fisico) throws BoException {
		iboFisico.excluir(fisico);
	}
	
	public List<Fisico> buscarMotoristasValidos(LocalDate dataSuperior, String dadoMotorista) throws BoException{
		return iboFisico.buscarMotoristasValidos(dataSuperior, dadoMotorista);
	}
	
	public List<Fisico> buscarClientesFisicos(String busca, Fisico fisico) throws BoException{
		return iboFisico.buscaPorBuscaAbrangente(busca, fisico);
	}
	
	public List<Fisico> buscarClientesFisicos(String busca) throws BoException{
		return iboFisico.buscaPorBuscaAbrangente(busca);
	}
	
	//-Cliente jurídico
	public void cadastrarEditarClienteJuridico(Juridico juridico) throws BoException {
		iboJuridico.cadastrarEditar(juridico);
	}
	
	public void excluirClienteJuridico(Juridico juridico) throws BoException {
		iboJuridico.excluir(juridico);
	}
	
	public List<Juridico> buscarClientesJuridicos(String busca, Juridico juridico) throws BoException{
		return iboJuridico.buscaPorBuscaAbrangente(busca, juridico);
	}
	
	public List<Juridico> buscarClientesJuridicos(String busca) throws BoException{
		return iboJuridico.buscaPorBuscaAbrangente(busca);
	}
	
	//--Endereco
	public Endereco gerarEndereco(String cep) throws BoException{
		return iboEndereco.gerarEndereco(cep);
	}
	
	//--Filial 
	public void cadastrarEditarFilial(Filial filial) throws BoException {
		iboFilial.cadastrarEditar(filial);
	}
	
	public void excluirFilial(Filial filial) throws BoException {
		iboFilial.excluir(filial);
	}
	
	public List<Filial> buscarFiliais(String busca) throws BoException{
		return iboFilial.buscaPorBuscaAbrangente(busca);
	}
	
	//--Manutenções de veículos
	public void cadastrarEditarManutencao(Manutencao manutencao) throws BoException {
		iboManutencao.cadastrarEditar(manutencao);
	}
	
	public void excluirManutencao(Manutencao manutencao) throws BoException {
		iboManutencao.excluir(manutencao);
	}
	
	public List<Manutencao> buscarManutencoes(String busca, Manutencao manutencao) throws BoException{
		return iboManutencao.buscaPorBuscaAbrangente(busca, manutencao);
	}
	
	public int checarManutencao() throws BoException{
		return iboManutencao.checarManutencao();
	}
	
	//--Funcionário
	public void cadastrarFuncionario(Funcionario funcionario ,String senha, Cargo cargo) throws BoException{
		iboFuncionario.cadastrar(funcionario, senha, cargo);
	}
	
	public void editaSenha(Funcionario funcionario, String novaSenha) throws BoException{
		iboFuncionario.editaSenha(funcionario, novaSenha);
	}
	
	public void excluirFuncionario(Funcionario funcionario) throws BoException{
		iboFuncionario.excluir(funcionario);
	}
	
	public void editarFuncionario(Funcionario antigofuncionario, Funcionario novoFuncionario )throws BoException{
		String loginAntigo = iboFuncionario.gerarLogin(antigofuncionario);
		iboFuncionario.editar(novoFuncionario, loginAntigo);
	}
	
	public List<Funcionario> buscarFuncionarios(String busca) throws BoException{
		return iboFuncionario.buscaPorBuscaAbrangente(busca);
	}
	
	public void resetarSenha(Funcionario funcionario) throws BoException{
		iboFuncionario.resetarSenha(funcionario);
	}
	
	public Cargo requisitarGralDeAcesso(Funcionario funcionario) throws BoException{
		return iboFuncionario.requisitarGralDeAcesso(funcionario);
	}
	
	public Cargo requisitarGralDeAcesso(String login) throws BoException{
		return iboFuncionario.requisitarGralDeAcesso(login);
	}
	
	public void utilizarGralAcesso(Cargo cargo) throws BoException{
		iboFuncionario.utilizarGralAcesso(cargo);
	}
	
	public void alterarGralAcesso(Funcionario funcionario,Cargo oldCargo, Cargo newCargo) throws BoException{
		iboFuncionario.alterarGralAcesso(funcionario, oldCargo, newCargo);
	}
	
	public List<Funcionario> buscarTodosFuncionarios() throws BoException{
		return iboFuncionario.buscarAll();
	}
	
	public Funcionario buscaPorLogin(String login) throws BoException{
		return iboFuncionario.buscaPorLogin(login);
	}
	
	//--Acessório
	
	public void cadastrarEditarAcessorio(Acessorio acessorio) throws BoException {
		iboAcessorio.cadastrarEditar(acessorio);
	}
	
	public void excluirAcessorio(Acessorio acessorio) throws BoException {
		iboAcessorio.excluir(acessorio);
	}
	
	public List<Acessorio> buscarAcessorios(String busca) throws BoException{
		return iboAcessorio.buscaPorBuscaAbrangente(busca);
	}
	
	public List<Acessorio> buscarTodoAcessorios() throws BoException{
		return iboAcessorio.buscarAll();
	}
	
	//--Backup
	public void cadastrarEditarBackup(Backup backup) throws BoException {
		iboBackup.cadastrarEditar(backup);
	}
	
	public List<Backup> buscarTodosBackups() throws BoException{
		return iboBackup.buscarAll();
	}
	
	public LocalDateTime adiarBackup(Backup backup, Integer horas) throws BoException{
		return iboBackup.adiarBackup(backup, horas);
	}
	
	public void finalizarBackup(Backup backup , Integer horaProximoBackup) throws BoException{
		iboBackup.finalizarBackup(backup, horaProximoBackup);
	}
	
	public Backup checarBackup() throws BoException{
		return iboBackup.checarBackup();
	}
	
	public Boolean existeBackupPendente() throws BoException{
		return iboBackup.existeBackupPendente();
	}
	
}
