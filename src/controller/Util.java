package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import adapter.ReservaDisponibilidade;
import banco.ReservaPendente;
import business.BoCategoriaVeiculo;
import business.BoReserva;
import dao.DaoRes;
import entidade.Backup;
import entidade.CategoriaVeiculo;
import entidade.Cliente;
import entidade.Filial;
import entidade.Fisico;
import entidade.Funcionario;
import entidade.Locacao;
import entidade.Veiculo;
import excecoes.BoException;
import excecoes.DaoException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
import view.Alerta;

public class Util {

	public static LocalDateTime gerarHorario(DatePicker datePicker , ComboBox<Integer> comboBox) {
		LocalDateTime dataTime = null;
		if(datePicker.getValue() != null && comboBox.getValue() != null) {
			LocalDate data = datePicker.getValue();
			Integer hora = comboBox.getValue();
			dataTime = LocalDateTime.of(data.getYear(),data.getMonthValue(),data.getDayOfMonth(),hora,0);
		}
		return dataTime;
	}
	
	public static void exibirFinalizarLocacaoDialogo(Locacao locacao) {
		try {
			Alert alerta = new Alert(AlertType.NONE);
			FinalizarLocacaoController finalizarLocacaoController;
			finalizarLocacaoController = (FinalizarLocacaoController) DaoRes.getInstance().carregarControllerFXML("FinalizarLocacaoDialogo");
			finalizarLocacaoController.paremetrizadoPor(locacao);
			alerta.setDialogPane(finalizarLocacaoController.getFinalizarLocacaoDialogo());
			alerta.showAndWait();
		} catch (DaoException e) {
			Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
	}
	
	public static void exibirRealizarBackupEmDialogo(Backup backup) {
		try {
			Alert alerta = new Alert(AlertType.NONE);
			BackupDialogoController backupDialogoController;
			backupDialogoController = (BackupDialogoController) DaoRes.getInstance().carregarControllerFXML("BackupDialogo");
			DaoRes.getInstance().addObserver(backupDialogoController);
			backupDialogoController.paremetrizadoPor(backup);
			alerta.setDialogPane(backupDialogoController.getBackupDialogo());
			alerta.showAndWait();
			DaoRes.getInstance().deleteObserver(backupDialogoController);
		} catch (DaoException e) {
			Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
	}
	
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
	

	public static ReservaDisponibilidade selecionarReservaDispoSuperiorEmDialogo(CategoriaVeiculo categoriaVeiculo, Filial filial, LocalDateTime horario) {
		ReservaDisponibilidade reservaSelecionada = null;
		try {
			Alert alerta = new Alert(AlertType.NONE);
			SelecionarReservaDispoController selecionarReservaDispoController;
			selecionarReservaDispoController = (SelecionarReservaDispoController) DaoRes.getInstance().carregarControllerFXML("SelecionarReservaDispoDialog");
			selecionarReservaDispoController.getReservaDispoTbl().getItems().addAll(BoReserva.getInstance().reservaDisponibilidadeSuperior(categoriaVeiculo, filial.getId(), horario));
			alerta.setDialogPane(selecionarReservaDispoController.getSelectReservaDispoDialog());
			Optional<ButtonType> result = alerta.showAndWait();
			if(result.isPresent() && result.get() == ButtonType.FINISH) {
				reservaSelecionada = selecionarReservaDispoController.getReservaDispoTbl().getSelectionModel().getSelectedItem();
				if(reservaSelecionada != null) 
					Alerta.getInstance().imprimirMsg("Sucesso","Reserva Superior selecionada com sucesso",AlertType.INFORMATION);
			}
		} catch (DaoException | BoException e) {
			Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
		return reservaSelecionada;
	}
	
	public static Veiculo selecionarVeiculoEmDialogo(CategoriaVeiculo categoriaVeiculo, Filial filial) {
		Veiculo veiculoSelecionado = null;
		try {
			SelecionarVeiculoController selecionarVeiculoController;
			selecionarVeiculoController = (SelecionarVeiculoController) DaoRes.getInstance().carregarControllerFXML("SelecionarVeiculoDialog");
			if(selecionarVeiculoController.paremetrizadoPor(categoriaVeiculo, filial)) {
				Alert alerta = new Alert(AlertType.NONE);
				alerta.setDialogPane(selecionarVeiculoController.getSelectVeiculoDialog());
				Optional<ButtonType> result = alerta.showAndWait();
				if(result.isPresent() && result.get() == ButtonType.FINISH) {
					veiculoSelecionado = selecionarVeiculoController.getVeiculoTbl().getSelectionModel().getSelectedItem();
					if(veiculoSelecionado != null) 
						Alerta.getInstance().imprimirMsg("Sucesso","Veículo selecionada com sucesso",AlertType.INFORMATION);
				}
			}
			else
				if(Alerta.getInstance().imprimirMsgConfirmacao("Não Há veiculos disponiveis para locação "
					+ "na categoria de veículo reservada. Deseja Selecionar Categoria Superior?")) 
				{
					ReservaDisponibilidade reservaDispoSuperior = selecionarReservaDispoSuperiorEmDialogo(categoriaVeiculo,filial,LocalDateTime.now());
					selecionarVeiculoEmDialogo(BoCategoriaVeiculo.getInstance().buscarID(reservaDispoSuperior.getIdCategoria()), filial);
				}
		} catch (DaoException | BoException e) {
			Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
		return veiculoSelecionado;
	}
	
	public static Veiculo selecionarVeiculoEmDialogo(Filial filial) {
		Veiculo veiculoSelecionado = null;
		try {
			Alert alerta = new Alert(AlertType.NONE);
			SelecionarVeiculoController selecionarVeiculoController;
			selecionarVeiculoController = (SelecionarVeiculoController) DaoRes.getInstance().carregarControllerFXML("SelecionarVeiculoDialog");
			selecionarVeiculoController.paremetrizadoPor(filial);
			alerta.setDialogPane(selecionarVeiculoController.getSelectVeiculoDialog());
			Optional<ButtonType> result = alerta.showAndWait();
			if(result.isPresent() && result.get() == ButtonType.FINISH) {
				veiculoSelecionado = selecionarVeiculoController.getVeiculoTbl().getSelectionModel().getSelectedItem();
				if(veiculoSelecionado != null) 
					Alerta.getInstance().imprimirMsg("Sucesso","Veículo selecionada com sucesso",AlertType.INFORMATION);
			}
		} catch (DaoException e) {
			Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
		return veiculoSelecionado;
	}

	public static Fisico selecionarMotoristaValidoEmDialogo(LocalDate dataSuperior) {
		Fisico motoristaSelecionado = null;
		try {
			Alert alerta = new Alert(AlertType.NONE);
			SelecionarMotoristaController selecionarMotoristaController;
			selecionarMotoristaController = (SelecionarMotoristaController) DaoRes.getInstance().carregarControllerFXML("SelecionarMotoristaDialog");
			selecionarMotoristaController.paremetrizadoPor(dataSuperior);
			alerta.setDialogPane(selecionarMotoristaController.getSelecionarMotoristaDialog());
			Optional<ButtonType> result = alerta.showAndWait();
			if(result.isPresent() && result.get() == ButtonType.FINISH) {
				motoristaSelecionado = selecionarMotoristaController.getMotoristaTbl().getSelectionModel().getSelectedItem();
				if(motoristaSelecionado != null) 
					Alerta.getInstance().imprimirMsg("Sucesso","Motorista selecionada com sucesso",AlertType.INFORMATION);
			}
		} catch (DaoException e) {
			Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
		return motoristaSelecionado;
	}

}
