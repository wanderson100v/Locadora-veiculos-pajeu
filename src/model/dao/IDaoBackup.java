package model.dao;

import model.excecoes.DaoException;
import model.vo.Backup;

public interface IDaoBackup extends IDao<Backup>{
	
	public Backup checarBackup() throws DaoException;

	public Boolean existeBackupPendente() throws DaoException;
}
