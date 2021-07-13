package model.business;

import java.util.List;

import model.dao.DaoFuncionario;
import model.dao.IDaoFuncionario;
import model.enumeracoes.Cargo;
import model.excecoes.BoException;
import model.excecoes.DaoException;
import model.vo.Funcionario;

public class BoFuncionario extends Bo<Funcionario> implements IBoFuncionario {
	
	private IDaoFuncionario daoFuncionario;
	
	public BoFuncionario() {
		super(new DaoFuncionario());
		this.daoFuncionario = (IDaoFuncionario) daoEntidade;
	}
	
	@Override
	public void cadastrar(Funcionario funcionario, String senha, String confirmacaoSenha, Cargo cargo) throws BoException {
		try {
			validarAcesso(senha, confirmacaoSenha ,cargo);
			daoFuncionario.cadastrar(funcionario,gerarLogin(funcionario), senha, cargo);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	protected void exluirEntidade(Funcionario funcionario) throws DaoException {
		daoFuncionario.excluir(funcionario, gerarLogin(funcionario));
	}
	
	@Override
	public void inativarRegistro(Funcionario entidade) {
		entidade.setAtivo(false);
	}
	
	
	public void editar(Funcionario antigofuncionario, Funcionario novoFuncionario)throws BoException{
		try {
			String loginAntigo = gerarLogin(antigofuncionario);
			String novoLogin = gerarLogin(novoFuncionario);
			daoFuncionario.editar(novoFuncionario, loginAntigo, novoLogin);
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
	public Funcionario buscaPorLogin(String login) throws BoException {
		try {
			return daoFuncionario.buscaPorCpf(login.substring(1));
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
	
	@Override
	public Cargo requisitarGralDeAcesso(Funcionario funcionario) throws BoException {
		return requisitarGralDeAcesso(gerarLogin(funcionario));
	}
	
	@Override
	public Cargo requisitarGralDeAcesso(String login) throws BoException {
		try {
			List<String> cargos = daoFuncionario.requisitarGralDeAcesso(login);
			if(cargos.isEmpty())
				throw new BoException("USUÁRIO INVÁLIDO");
			return Cargo.getCargo(cargos.get(0));
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	public void utilizarGralAcesso(Cargo cargo) throws BoException{
		try {
			daoFuncionario.utilizarGralAcesso(cargo);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	@Override
	public void alterarGralAcesso(Funcionario funcionario,Cargo oldCargo, Cargo newCargo) throws BoException {
		try {
			daoFuncionario.alterarGralAcesso(gerarLogin(funcionario), oldCargo, newCargo);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
		
	}
	
	public String gerarLogin(Funcionario funcionario) {
		return Character.toLowerCase(funcionario.getNome().charAt(0))+funcionario.getCpf();
	}
	
	private void validarAcesso(String senha, String confirmacaoSenha, Cargo cargo) throws BoException {
		StringBuilder erro = new StringBuilder();
		if(!senha.equals(confirmacaoSenha))
			erro.append("A senha e a confirmação infromada não correspondem\n");
		if(senha.length() <6 || senha.length() >11)
			erro.append("A senha informada deve ter tamanho maior que 5 e menor que 12\n");
		if(cargo == null)
			erro.append("Deve ser informado um cargo para funcionarios com senha de acesso\n");
		if(erro.length()>0)
			throw new BoException("Erro(s) ao validar dados de acesso para Funcionario:\n"+erro.toString());
	}

}
