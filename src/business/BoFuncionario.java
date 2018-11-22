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
	public void cadastrarEditar(Funcionario entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				daoFuncionario.editar(entidade);
			}else {
				daoFuncionario.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(Funcionario entidade) throws BoException {
		try {
			if(entidade.getLocacoes() != null || entidade.getReservas()!= null) {
				entidade.setAtivo(false);
				daoFuncionario.editar(entidade);
			}else {
				daoFuncionario.excluir(entidade);
			}
		}catch (DaoException e) {
			System.out.println();
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public Funcionario buscarID(Long id) throws BoException {
		try {
			return daoFuncionario.buscarID(id);
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
	public List<Funcionario> buscarPorExemplo(Funcionario exemploEntidade) throws BoException {
		try {
			return daoFuncionario.buscarPorExemplo(exemploEntidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void cadastrar(Funcionario funcionario, String senha, Cargo cargo) throws BoException {
		try {
			validarSenha(senha);
			daoFuncionario.cadastrar(funcionario, senha, cargo);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	private void validarSenha(String senha) throws BoException {
		senha = senha.trim();
		if(senha.length() <6 || senha.length() >11)
			throw new BoException("A SENHA INFORMADA DEVE TER TAMANHO ENTRE O INTERVALO DE 6 A 11 CARACTERES");
		
	}

	@Override
	public void editaSenha(Funcionario funcionario, String novaSenha) throws BoException {
		try {
			daoFuncionario.editaSenha(funcionario, novaSenha);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void resetarSenha(Funcionario funcionario) throws BoException {
		try {
			daoFuncionario.editaSenha(funcionario,funcionario.getCpf());
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
