package business;

import java.util.List;

import dao.DaoFuncionario;
import dao.IDaoFuncionario;
import entidade.Funcionario;
import enumeracoes.Cargo;
import excecoes.BoException;
import excecoes.DaoException;

public class BoFuncionario implements IBoFuncionario {
	private IDaoFuncionario daoFuncionario = new DaoFuncionario();
	private static IBoFuncionario instance;
	
	private BoFuncionario() {}
	
	public static IBoFuncionario getInstance() {
		if(instance == null)
			instance = new BoFuncionario();
		return instance;
	}
	

	@Override
	public void excluir(Funcionario entidade) throws BoException {
		try {
			try {
				daoFuncionario.excluir(entidade,gerarLogin(entidade));
			}catch (DaoException e) {
				entidade.setAtivo(false);
				daoFuncionario.editar(entidade);
				throw new BoException(e.getMessage());
			}
		}catch (DaoException e) {
			System.out.println();
			throw new BoException(e.getMessage());
		}
	}
	
	@Override
	public void cadastrar(Funcionario funcionario, String senha, Cargo cargo) throws BoException {
		try {
			validarAcesso(senha,cargo);
			daoFuncionario.cadastrar(funcionario,gerarLogin(funcionario), senha, cargo);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	public void editar(Funcionario funcionario, String oldLogin)throws BoException{
		try {
			daoFuncionario.editar(funcionario, oldLogin, gerarLogin(funcionario));
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	@Override
	public void editaSenha(Funcionario funcionario, String novaSenha) throws BoException {
		try {
			daoFuncionario.editaSenha(funcionario,gerarLogin(funcionario), novaSenha);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	@Override
	public List<Funcionario> buscarAll() throws BoException {
		try {
			return daoFuncionario.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void resetarSenha(Funcionario funcionario) throws BoException {
		try {
			daoFuncionario.editaSenha(funcionario,gerarLogin(funcionario),funcionario.getCpf());
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	private void validarAcesso(String senha, Cargo cargo) throws BoException {
		StringBuilder erro = new StringBuilder();
		if(senha.length() <6 || senha.length() >11)
			erro.append("A senha informada deve ter tamanho maior que 5 e menor que 12\n");
		if(cargo == null)
			erro.append("Deve ser informado um cargo para funcionarios com senha de acesso\n");
		if(erro.length()>0)
			throw new BoException("Erro(s) ao validar dados de acesso para Funcionario:\n"+erro.toString());
	}
	
	public String gerarLogin(Funcionario funcionario) {
		String primeiroNome = (funcionario.getNome().contains(" "))?  
				funcionario.getNome().substring(0,funcionario.getNome().indexOf(" ")) :
				funcionario.getNome();
		return primeiroNome.toLowerCase()+funcionario.getCpf();
	}

}
