package model.business;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.dao.DaoEndereco;
import model.enumeracoes.Estado;
import model.excecoes.BoException;
import model.vo.Endereco;

public class BoEndereco extends BoAdapter<Endereco> implements IBoEndereco{
	
	public BoEndereco() {
		super(new DaoEndereco());
	}
	
    public Endereco gerarEndereco(String cep) throws BoException{
    	
    	 String json = buscarCep(cep);
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
    
    private  String buscarCep(String cep) throws BoException{
        try {
            URL url = new URL("http://viacep.com.br/ws/"+ cep +"/json");
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder jsonSb = new StringBuilder();
            br.lines().forEach(l -> jsonSb.append(l.trim()));
            return  jsonSb.toString();
        } catch (Exception e) {
        	throw new BoException("Erro ao gerar endereço, sem conexão com o web service ");
        }
    }

	
}
