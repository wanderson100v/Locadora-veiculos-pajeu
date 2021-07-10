package model.business;

import java.time.LocalDate;
import java.time.LocalDateTime;

import model.dao.DaoBackup;
import model.dao.IDaoBackup;
import model.enumeracoes.EstadoBackup;
import model.excecoes.BoException;
import model.excecoes.DaoException;
import model.vo.Backup;

public class BoBackup extends BoAdapter<Backup> implements IBoBackup {
	
	private IDaoBackup daoBackup;

	public BoBackup() {
		super(new DaoBackup());
		this.daoBackup = (IDaoBackup) daoEntidade;
	}
	
	public LocalDateTime adiarBackup(Backup backup, Integer horas) throws BoException {
		backup.setEstado(EstadoBackup.ADIADO);
		backup.setDataOcorrencia(backup.getDataOcorrencia().plusHours(horas));
		cadastrarEditar(backup);
		return backup.getDataOcorrencia();
	}

	public void finalizarBackup(Backup backup, Integer horaBackupAmanha) throws BoException {
		backup.setEstado(EstadoBackup.REALIZADO);
		cadastrarEditar(backup);
		if(horaBackupAmanha!= null) {
			Backup backupAmanha = new Backup();
			backupAmanha.setAutor(backup.getAutor());
			backupAmanha.setDescricao("");
			backupAmanha.setEstado(EstadoBackup.PENDENTE);
			LocalDate amanha = LocalDate.now().plusDays(1);
			backupAmanha.setDataOcorrencia(LocalDateTime.of(amanha.getYear()
					,amanha.getMonthValue(),amanha.getDayOfMonth(),horaBackupAmanha,0));
			cadastrarEditar(backupAmanha);
		}
	}

	@Override
	public Backup checarBackup() throws BoException {
		try {
			return daoBackup.checarBackup();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public Boolean existeBackupPendente() throws BoException {
		try {
			return daoBackup.existeBackupPendente();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	
}
