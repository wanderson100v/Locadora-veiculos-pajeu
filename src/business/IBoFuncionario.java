package business;

import java.util.List;

import entidade.Funcionario;
import enumeracoes.Cargo;
import excecoes.BoException;
import excecoes.DaoException;

public interface IBoFuncionario {
	
	public void cadastrar(Funcionario funcionario ,String senha, Cargo cargo) throws BoException;
	
	public void editaSenha(Funcionario funcionario, String novaSenha) throws BoException;
	
	public void excluir(Funcionario entidade) throws BoException;
	
	public void editar(Funcionario funcionario, String oldLogin)throws BoException;
	
	public void resetarSenha(Funcionario funcionario) throws BoException;
	
	public Cargo requisitarGralDeAcesso() throws BoException;
	
	public String gerarLogin(Funcionario funcionario);
	
	public List<Funcionario> buscarAll() throws BoException;
}
