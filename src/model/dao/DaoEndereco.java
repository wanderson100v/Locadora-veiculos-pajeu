package model.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import model.excecoes.DaoException;
import model.vo.Endereco;
public class DaoEndereco extends Dao<Endereco> implements IDaoEndereco{

	public DaoEndereco() {
		super(Endereco.class);
	}
	
	public String buscarCep(String cep) throws DaoException{
        try {
            URL url = new URL("http://viacep.com.br/ws/"+ cep +"/json");
		    URLConnection urlConnection = url.openConnection();
		    InputStream is = urlConnection.getInputStream();
		    BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    StringBuilder jsonSb = new StringBuilder();
		    br.lines().forEach(l -> jsonSb.append(l.trim()));
		    return  jsonSb.toString();
		} catch (Exception e) {
			throw new DaoException("Erro ao gerar endereço, sem conexão com o web service ");
	    }
	}

}
