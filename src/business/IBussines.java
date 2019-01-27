package business;

import java.util.List;
import java.util.Map;

import entidade.Entidade;
import excecoes.BoException;

public interface IBussines<T extends Entidade> {

	public void cadastrarEditar(T entidade) throws BoException;
	
	public void excluir(T entidade)throws BoException;
	
	public T buscarID(Long id)throws BoException;
	
	public List<T> buscarAll() throws BoException;
	
	public List<T> buscarPorExemplo(T exemploEntidade)throws BoException;
	
	List<T> buscaPorBuscaAbrangente(String busca) throws BoException;
	
	public List<T> buscaPorBuscaAbrangente(String busca, Map<String, String> restricoes) throws BoException;
}
