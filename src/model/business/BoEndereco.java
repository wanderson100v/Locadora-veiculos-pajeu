package model.business;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.dao.DaoEndereco;
import model.dao.IDaoEndereco;
import model.enumeracoes.Estado;
import model.excecoes.BoException;
import model.excecoes.DaoException;
import model.vo.Endereco;

public class BoEndereco extends BoAdapter<Endereco> implements IBoEndereco{
	
	private IDaoEndereco daoEndereco;
	
	public BoEndereco() {
		super(new DaoEndereco());
		this.daoEndereco = (IDaoEndereco) this.daoEntidade;
	}
	
	@Override
	public void cadastrarEditar(Endereco entidade) throws BoException {
		validarCEP(entidade.getCep());
		super.cadastrarEditar(entidade);
	}
	
    public Endereco gerarEndereco(String cep) throws BoException{
		String json;
		try {
			json = daoEndereco.buscarCep(cep);
		} catch (DaoException e) {
			throw new BoException(e.getMessage()); 
		}
		
		Map<String,String> mapa = new HashMap<>();
		Matcher matcher = Pattern.compile("\"\\D.*?\": \".*?\"").matcher(json);
		while (matcher.find()) {
		    String[] group = matcher.group().split(":");
		    mapa.put(group[0].replaceAll("\"", "").trim(), group[1].replaceAll("\"", "").trim());
		}
		
		if(mapa.isEmpty()) 
			throw new BoException("Erro ao gerar endereço automaticamente a partir de cep");
		
		Endereco e = new Endereco();    
		e.setCep(cep);
		e.setBairro(mapa.get("bairro"));
		e.setCidade(mapa.get("localidade"));
		e.setRua(mapa.get("logradouro"));
		e.setEstado(Estado.getEstado(mapa.get("uf")));
		
		return e;
	}
    
    public void validarEndereco(Endereco endereco) throws BoException{
    	validarCEP(endereco.getCep());
    }
    
    private void validarCEP(String cep) throws BoException{
    	if(cep == null)
    		return;
    	boolean isCepValido = cep.matches("[0-9]{5}-[0-9]{3}");
    	if(!isCepValido)
    		throw new BoException("O CEP informado não é valido. O mesmo deve estar na forma 00000-000");
    }
	
}
