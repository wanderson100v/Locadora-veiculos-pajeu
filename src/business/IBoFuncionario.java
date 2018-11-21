package business;

import entidade.Funcionario;
import enumeracoes.Cargo;
import excecoes.BoException;

public interface IBoFuncionario extends IBussines<Funcionario>{
	
	public void cadastrar(Funcionario funcionario ,String senha, Cargo cargo) throws BoException;
	
	public void editaSenha(Funcionario funcionario, String novaSenha) throws BoException;
	
	public void resetarSenha(Funcionario funcionario) throws BoException;
}
