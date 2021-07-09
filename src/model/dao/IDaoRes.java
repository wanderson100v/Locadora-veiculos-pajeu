package model.dao;

import java.awt.image.BufferedImage;

import model.excecoes.DaoException;
import javafx.scene.layout.Pane;
import mode.enumeracoes.Tela;

public interface IDaoRes {
	
	
	public Pane carregarPaneFXML(Tela tela) throws DaoException;
	
	public Pane carregarPaneFXML(String tela) throws DaoException;

	public Object carregarControllerFXML(String tela)throws DaoException;
	
	public BufferedImage carregarImg(String img) throws DaoException;
}
