package model.business;

import java.time.LocalDateTime;

import model.excecoes.BoException;
import model.vo.Backup;

public interface IBoBackup extends IBussines<Backup>{

	public LocalDateTime adiarBackup(Backup backup, Integer horas) throws BoException;
	
	public void finalizarBackup(Backup backup , Integer horaProximoBackup) throws BoException;
	
	public Backup checarBackup() throws BoException;
	
	public Boolean existeBackupPendente() throws BoException;
	
}
