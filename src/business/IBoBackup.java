package business;

import java.time.LocalDateTime;

import entidade.Backup;
import excecoes.BoException;

public interface IBoBackup extends IBussines<Backup>{

	public LocalDateTime adiarBackup(Backup backup, Integer horas) throws BoException;
	
	public void finalizarBackup(Backup backup , Integer horaProximoBackup) throws BoException;
	
	public Backup checarBackup() throws BoException;
	
}
