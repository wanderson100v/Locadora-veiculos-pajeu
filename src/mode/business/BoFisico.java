package mode.business;

import java.time.LocalDate;
import java.util.List;

import model.dao.DaoFisico;
import model.dao.IDaoFisico;
import model.entidade.Fisico;
import model.excecoes.BoException;
import model.excecoes.DaoException;
import model.excecoes.ValidarException;

public class BoFisico implements IBoFisico{
	private static IBoFisico instance;
	private IDaoFisico daoFisico = new DaoFisico();
	
	private BoFisico() {}
	
	public static IBoFisico getInstance() {
		if(instance == null)
			instance = new BoFisico();
		return instance;
	}
	
	@Override
	public void cadastrarEditar(Fisico entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				daoFisico.editar(entidade);
			}else {
				atribuirCodigo(entidade);
				daoFisico.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(Fisico entidade) throws BoException {
		try {
			daoFisico.excluir(entidade);
		}catch (DaoException e) {
			try {
				Util.validarCausaInicial(e,"","not-null","violates foreign key");
				throw new BoException(e.getMessage());
			}catch (ValidarException ValidarException) {
				entidade.setAtivo(false);
				cadastrarEditar(entidade);
				throw new BoException("IMPOSSIBILIDADE DE EXLUSÃO : H�? REGISTROS DEPENDENTES, CLIENTE INATIVADO");
			}
		}
	}

	@Override
	public Fisico buscarID(Long id) throws BoException {
		try {
			return daoFisico.buscarID(id);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Fisico> buscarAll() throws BoException {
		try {
			return daoFisico.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	@Override
	public List<Fisico> buscarPorExemplo(Fisico exemploEntidade) throws BoException {
		try {
			return daoFisico.buscarPorExemplo(exemploEntidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	@Override
	public List<Fisico> buscarMotoristasValidos(LocalDate dataSuperior, String dadoMotorista) throws BoException {
		try {
			Fisico fisico = new Fisico();
			fisico.setCpf(dadoMotorista);
			fisico.setNome(dadoMotorista);
			fisico.setEmail(dadoMotorista);
			fisico.setCpf(dadoMotorista);
			fisico.setIdentificacaoMotorista(dadoMotorista);
			fisico.setNumeroHabilitacao(dadoMotorista);
			return daoFisico.buscarMotoristasValidos(fisico, dataSuperior);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	public void validarMotorista(Fisico fisico) throws ValidarException {
		StringBuilder erro = new StringBuilder();
		if(fisico.getIdentificacaoMotorista() == null)
			erro.append("\tSem identificação\n");
		if(fisico.getDataValidadeHabilitacao() == null)
			erro.append("\tSem data de validade de habilitação\n");
		else if (fisico.getDataValidadeHabilitacao().isBefore(LocalDate.now()))
			erro.append("\tHabilitação vencida\n");
		if(fisico.getNumeroHabilitacao() == null)
			erro.append("\tSem numero de habilitação");
		if(erro.length() >0)
			throw new ValidarException("Foram encontrado os seguintes erros no motorista:\n "+erro.toString());
		
	}

	private void atribuirCodigo(Fisico entidade){
		entidade.setCodigo("PJ"+entidade.getCpf());
	}

	@Override
	public List<Fisico> buscaPorBuscaAbrangente(String busca) throws BoException {
		try {
			return daoFisico.buscaPorBuscaAbrangente(busca);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Fisico> buscaPorBuscaAbrangente(String busca, Fisico fisico) throws BoException {
		try {
			return daoFisico.buscaPorBuscaAbrangente(busca,fisico);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
}
