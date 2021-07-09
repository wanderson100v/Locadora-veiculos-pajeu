package mode.business;

import java.util.List;

import model.excecoes.BoException;
import model.entidade.Entidade;

public interface IBussines<T extends Entidade> {

	public void cadastrarEditar(T entidade) throws BoException;
	
	public void excluir(T entidade)throws BoException;
	
	public T buscarID(Long id)throws BoException;
	
	public List<T> buscarAll() throws BoException;
	
	public List<T> buscarPorExemplo(T exemploEntidade)throws BoException;
	
	public List<T> buscaPorBuscaAbrangente(String busca) throws BoException;
	
}
