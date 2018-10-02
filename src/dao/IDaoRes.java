package dao;

import java.awt.image.BufferedImage;

import enumeracoes.Tela;
import excecoes.DaoException;
import javafx.scene.layout.Pane;

public interface IDaoRes {
	public Pane carregarPaneFXML(Tela tela) throws DaoException;
	public BufferedImage carregarImg(String img) throws DaoException;
}
