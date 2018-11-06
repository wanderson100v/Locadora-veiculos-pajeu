package bussines;

import java.util.List;

import entidade.Entidade;
import excecoes.BoException;

public interface IBussines<T extends Entidade> {
	
	public void cadastrarEditar(T entidade) throws BoException;
	
	public void excluir(T entidade)throws BoException;
	
	public T buscarID(Long id)throws BoException;
	
	public List<T> buscarAll() throws BoException;
}
