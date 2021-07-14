package model.business;

import java.util.List;

import model.dao.DaoCliente;
import model.dao.IDaoCliente;
import model.excecoes.BoException;
import model.excecoes.DaoException;
import model.vo.Cliente;

public class BoCliente implements IBoCliente{
	
	private IDaoCliente  daoCliente = new DaoCliente();
	
	public void validar(Cliente cliente) throws BoException{
		StringBuilder error = new StringBuilder();
		
		String telefone = cliente.getTelefone();
		String email = cliente.getEmail();
		
		
		if(email != null && !email.isEmpty() && !isEmailValido(email)) {
			error.append("\t- Email invalido, o mesmo deve estar na seguinte forma exemplo@servidor.com");
		}else
			cliente.setEmail(null);
		
		if(telefone != null && telefone.length()> 3 && !telefone.isEmpty() && !isTelNumeroValido(telefone)) {
			error.append("\t- O Telefone informado deve ser expresso númericamente");
		}else
			cliente.setTelefone(null);
		
		if(error.length() > 0)
			throw new BoException("Os seguintes problemas foram encontrados:\n"+ error.toString());
	}
	
	private boolean isTelNumeroValido(String telefone){
		return telefone.matches("(\\(\\d{3}\\) \\d{5}-?\\d{4})|"
				+ "(\\(\\d{2}\\) \\d{4}-?\\d{4})");
	}
	
	private boolean isEmailValido(String email) {
		return email.matches(".*@.*\\..*");
	}

	@Override
	public List<Cliente> buscaPorBuscaAbrangente(String busca) throws BoException {
		try {
			return daoCliente.buscaPorBuscaAbrangente(busca);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
