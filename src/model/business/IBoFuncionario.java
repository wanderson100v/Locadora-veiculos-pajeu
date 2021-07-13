package model.business;

import java.util.List;

import model.enumeracoes.Cargo;
import model.excecoes.BoException;
import model.vo.Funcionario;

public interface IBoFuncionario {
	
	public void cadastrar(Funcionario funcionario ,String senha, String confirmacaoSenha,  Cargo cargo) throws BoException;
	
	public void editaSenha(Funcionario funcionario, String novaSenha) throws BoException;
	
	public void excluir(Funcionario entidade) throws BoException;
	
	public void editar(Funcionario antigofuncionario, Funcionario novoFuncionario)throws BoException;
	
	public List<Funcionario> buscaPorBuscaAbrangente(String busca) throws BoException;
	
	public void resetarSenha(Funcionario funcionario) throws BoException;
	
	public Cargo requisitarGralDeAcesso(Funcionario funcionario) throws BoException;
	
	public Cargo requisitarGralDeAcesso(String login) throws BoException;
	
	public void utilizarGralAcesso(Cargo cargo) throws BoException;
	
	public void alterarGralAcesso(Funcionario funcionario,Cargo oldCargo, Cargo newCargo) throws BoException;
	
	public String gerarLogin(Funcionario funcionario);
	
	public List<Funcionario> buscarAll() throws BoException;
	
	public Funcionario buscaPorLogin(String login) throws BoException;
	
}
