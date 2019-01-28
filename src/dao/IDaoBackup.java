package dao;

import entidade.Backup;
import excecoes.DaoException;

public interface IDaoBackup extends IDao<Backup>{
	
	public Backup checarBackup() throws DaoException;

	public Boolean existeBackupPendente() throws DaoException;
}
