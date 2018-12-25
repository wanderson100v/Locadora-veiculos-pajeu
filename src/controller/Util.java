package controller;

import java.util.Optional;

import dao.DaoRes;
import entidade.Filial;
import excecoes.DaoException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import view.Alerta;

public class Util {

	  public static Filial selecionarFilialEmDialogo() {
		  Filial filialSelecionada = null;
		  try {
			  	Alert alerta = new Alert(AlertType.NONE);
				SelecionarFilialController selecionarFilialController;
				selecionarFilialController = (SelecionarFilialController) DaoRes.getInstance().carregarControllerFXML("SelecionarFilialDialog");
				alerta.setDialogPane(selecionarFilialController.getSelecionarFilialDialog());
				Optional<ButtonType> result = alerta.showAndWait();
				if(result.isPresent() && result.get() == ButtonType.FINISH) { 
					filialSelecionada = selecionarFilialController.getFilialTbl().getSelectionModel().getSelectedItem();
					if(filialSelecionada!= null)
						Alerta.getInstance().imprimirMsg("Sucesso","Filial selecionada com sucesso",AlertType.INFORMATION);
				}
		   } catch (DaoException e) {
			    Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		   }
		  return filialSelecionada;
	  }
	
}
