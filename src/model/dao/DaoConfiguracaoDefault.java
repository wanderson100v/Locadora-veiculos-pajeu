package model.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;

import model.excecoes.DaoException;
import model.entidade.ConfiguracoesDefault;

public class DaoConfiguracaoDefault implements IDaoConfiguracaoDefault {
	private XStream xStream;
	public static IDaoConfiguracaoDefault instance;
	
	private DaoConfiguracaoDefault() {
		this.xStream = new XStream(new Dom4JDriver());
		xStream.processAnnotations(ConfiguracoesDefault.class);
	}
	
	public static IDaoConfiguracaoDefault getInstance() {
		if(instance == null) 
			instance = new DaoConfiguracaoDefault();
		return instance;
	}

	@Override
	public void salvar(ConfiguracoesDefault configuracoesDefault) throws DaoException {
		try {
			File file = new File("Config-default.xml");
			BufferedWriter b = new BufferedWriter(new FileWriter(file));
			b.write(xStream.toXML(configuracoesDefault));
			b.flush();
			b.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO SALVAR CONFIGURAÇÕES PADRÃO");
		}
	}
	@Override
	public ConfiguracoesDefault carregar() throws DaoException {
		try {
			return (ConfiguracoesDefault) xStream.fromXML(new FileReader("Config-default.xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO CARREGAR CONFIGURAÇÕES PADRÃO");
		}
	}

}
	