package model.dao;

import model.dao.sql.ConnectionFactory;
import model.excecoes.DaoException;
import model.vo.Backup;

public class DaoBackup extends Dao<Backup> implements IDaoBackup{

	public DaoBackup() {
		super(Backup.class);
	}
	
	public Backup checarBackup() throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			return (Backup) em.createStoredProcedureQuery("checar_backup", Backup.class)
					.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO CHCAR BACKUP");
		}finally {
			em.close();
		}
	}
	
	public Boolean existeBackupPendente() throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			return  (Boolean) em.createNativeQuery("SELECT EXISTS(SELECT 1 FROM backup"
					+ " WHERE (estado = 0 or estado = 1 ) "
					+ "and date(data_ocorrencia) = (current_date + interval ' 1 days'))" )
					.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO CHCAR BACKUP");
		}finally {
			em.close();
		}
	}
		
}
