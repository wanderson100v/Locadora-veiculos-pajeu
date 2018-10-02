package dao;


import java.awt.image.BufferedImage;

import enumeracoes.Tela;
import excecoes.DaoException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class DaoRes implements IDaoRes{
	public static DaoRes instance;
	
	private DaoRes() {}

	public static DaoRes getInstance() {
		if(instance == null)
			instance = new DaoRes();
		return instance;
	}
	
	public Pane carregarPaneFXML(Tela tela)throws DaoException {
		try {
			return FXMLLoader.load(getClass().getClassLoader().getResource("view/"+tela+".fxml"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("Erro ao carregar tela "+tela);
		}
	}

	@Override
	public BufferedImage carregarImg(String img) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}
	

}
