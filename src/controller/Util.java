package controller;

import java.util.Optional;

import banco.ReservaPendente;
import business.BoReserva;
import dao.DaoRes;
import entidade.CategoriaVeiculo;
import entidade.Cliente;
import entidade.Filial;
import entidade.Funcionario;
import excecoes.BoException;
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
	
	public static Cliente selecionarClienteEmDialogo() {
		Cliente clienteSelecionado = null;
		try {
			Alert alerta = new Alert(AlertType.NONE);
			SelecionarClienteController selecionarClienteController;
			selecionarClienteController = (SelecionarClienteController) DaoRes.getInstance().carregarControllerFXML("SelecionarClienteDialog");
			alerta.setDialogPane(selecionarClienteController.getSelecionarClienteDialog());
			Optional<ButtonType> result = alerta.showAndWait();
			if(result.isPresent() && result.get() == ButtonType.FINISH) {
				clienteSelecionado = selecionarClienteController.getClienteTbl().getSelectionModel().getSelectedItem();
				if(clienteSelecionado!= null) 
					Alerta.getInstance().imprimirMsg("Sucesso","Cliente selecionado com sucesso",AlertType.INFORMATION);
			}
		} catch (DaoException e) {
			Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
		return clienteSelecionado;
	}
	
	public static Funcionario selecionarFucnionarioEmDialogo() {
		Funcionario funcionarioSelecionado = null;
		try {
			Alert alerta = new Alert(AlertType.NONE);
			SelecionarFuncionarioController selecionarFuncionarioController;
			selecionarFuncionarioController = (SelecionarFuncionarioController) DaoRes.getInstance().carregarControllerFXML("SelecionarFuncionarioDialog");
			alerta.setDialogPane(selecionarFuncionarioController.getSelecionarFuncionarioDialog());
			Optional<ButtonType> result = alerta.showAndWait();
			if(result.isPresent() && result.get() == ButtonType.FINISH) {
				funcionarioSelecionado = selecionarFuncionarioController.getFuncionarioTbl().getSelectionModel().getSelectedItem();
				if(funcionarioSelecionado!= null)
					Alerta.getInstance().imprimirMsg("Sucesso","Funcionario selecionado com sucesso",AlertType.INFORMATION);
			}
		} catch (DaoException e) {
			Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
		return funcionarioSelecionado;
	}
	
	public static CategoriaVeiculo selecionarCategoriaVeiculoEmDialogo() {
		CategoriaVeiculo categoriaSelecionada = null;
		try {
			Alert alerta = new Alert(AlertType.NONE);
			SelecionarCategoriaVeiculoController selecionarCategoriaVeiculoController;
			selecionarCategoriaVeiculoController = (SelecionarCategoriaVeiculoController) DaoRes.getInstance().carregarControllerFXML("SelecionarCategoriaVeiculoDialog");
			alerta.setDialogPane(selecionarCategoriaVeiculoController.getSelecionarCategoriaVeiculoDialog());
			Optional<ButtonType> result = alerta.showAndWait();
			if(result.isPresent() && result.get() == ButtonType.FINISH) {
				categoriaSelecionada = selecionarCategoriaVeiculoController.getCategoriaVeiculoTbl().getSelectionModel().getSelectedItem();
				if(categoriaSelecionada != null) 
					Alerta.getInstance().imprimirMsg("Sucesso","Categoria de Veículo selecionada com sucesso",AlertType.INFORMATION);
			}
		} catch (DaoException e) {
			Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
		return categoriaSelecionada;
	}
	
	public static ReservaPendente selecionarReservaPendenteEmDialogo(Cliente cliente, Filial filial) {
		ReservaPendente reservaSelecionada = null;
		try {
			Alert alerta = new Alert(AlertType.NONE);
			SelecionarReservaPendenteController selecionarReservaPendenteController;
			selecionarReservaPendenteController = (SelecionarReservaPendenteController) DaoRes.getInstance().carregarControllerFXML("SelecionarReservaPendenteDialog");
			selecionarReservaPendenteController.getReservasTbl().getItems().addAll(BoReserva.getInstance().buscarReservaPendente(cliente.getCodigo(), filial));
			alerta.setDialogPane(selecionarReservaPendenteController.getSelecionarReservaDialog());
			Optional<ButtonType> result = alerta.showAndWait();
			if(result.isPresent() && result.get() == ButtonType.FINISH) {
				reservaSelecionada = selecionarReservaPendenteController.getReservasTbl().getSelectionModel().getSelectedItem();
				if(reservaSelecionada != null) 
					Alerta.getInstance().imprimirMsg("Sucesso","Reserva do cliente "+cliente.getNome()+" selecionada com sucesso",AlertType.INFORMATION);
			}
		} catch (DaoException | BoException e) {
			Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
		return reservaSelecionada;
	}

}
