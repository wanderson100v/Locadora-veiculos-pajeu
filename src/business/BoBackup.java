package business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import dao.DaoBackup;
import dao.IDaoBackup;
import entidade.Backup;
import enumeracoes.EstadoBackup;
import excecoes.BoException;
import excecoes.DaoException;

public class BoBackup implements IBoBackup {
	
	private static IBoBackup instance;
	private IDaoBackup daoBackup;
	
	public static IBoBackup getInstance() {
		if(instance == null) 
			instance = new BoBackup();
		return instance;
	}
	
	private BoBackup() {
		daoBackup = new DaoBackup();
	}
	
	@Override
	public void cadastrarEditar(Backup entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				daoBackup.editar(entidade);
			}else {
				daoBackup.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(Backup entidade) throws BoException {
		try {
			daoBackup.excluir(entidade);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public Backup buscarID(Long id) throws BoException {
		try {
			return daoBackup.buscarID(id);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());		
		}
	}

	@Override
	public List<Backup> buscarAll() throws BoException {
		try {
			return daoBackup.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Backup> buscarPorExemplo(Backup exemploEntidade) throws BoException {
		try {
			return daoBackup.buscarPorExemplo(exemploEntidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Backup> buscaPorBuscaAbrangente(String busca) throws BoException {
		try {
			return daoBackup.buscaPorBuscaAbrangente(busca);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	@Override
	public List<Backup> buscaPorBuscaAbrangente(String busca, Map<String, String> restricoes) throws BoException {
		try {
			return daoBackup.buscaPorBuscaAbrangente(busca, restricoes);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	public LocalDateTime adiarBackup(Backup backup, Integer horas) throws BoException {
		backup.setEstado(EstadoBackup.ADIADO);
		backup.setDataOcorrencia(backup.getDataOcorrencia().plusHours(horas));
		cadastrarEditar(backup);
		return backup.getDataOcorrencia();
	}

	public void finalizarBackup(Backup backup, Integer horaBackupAmanha) throws BoException {
		backup.setEstado(EstadoBackup.REALIZADO);
		Backup backupAmanha = new Backup();
		backupAmanha.setAutor(backup.getAutor());
		backupAmanha.setDescricao("");
		backupAmanha.setEstado(EstadoBackup.PENDENTE);
		LocalDate amanha = LocalDate.now().plusDays(1);
		backupAmanha.setDataOcorrencia(LocalDateTime.of(amanha.getYear()
				,amanha.getMonthValue(),amanha.getDayOfMonth(),horaBackupAmanha,0));
		cadastrarEditar(backupAmanha);
		cadastrarEditar(backup);
	}

	@Override
	public Backup checarBackup() throws BoException {
		try {
			return daoBackup.checarBackup();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	
}
