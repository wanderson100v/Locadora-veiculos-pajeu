package controller;

import org.controlsfx.control.Notifications;

import model.excecoes.BoException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mode.business.BoBackup;
import mode.business.BoManutencao;
import mode.business.BoReserva;
import mode.business.IBoReserva;
import mode.enumeracoes.Cargo;
import mode.enumeracoes.EstadoRerserva;
import model.banco.ReservaHoje;
import model.entidade.Backup;
import model.entidade.Funcionario;

public class InicioController implements IObservadorFuncionario {

    @FXML
    private TableView<ReservaHoje> reservasHojeTbl;

    @FXML
    private TableColumn<ReservaHoje, Integer> horaCln;

    @FXML
    private TableColumn<ReservaHoje, String> categoriaCln;

    @FXML
    private TableColumn<ReservaHoje, EstadoRerserva> estadoCln;

    @FXML
    private TableColumn<ReservaHoje, String> clienteCln;

    @FXML
    private TableColumn<ReservaHoje, String> filialCln;

    private IBoReserva boReserva = BoReserva.getInstance();
    
    private Thread thread;
    
    private boolean rodando = true;
    
    @FXML
    void initialize() {
    	FuncionarioObservavel.getIntance().addObservadorFuncionario(this);
    	
    	horaCln.setCellValueFactory(new PropertyValueFactory<>("hora"));
        categoriaCln.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        estadoCln.setCellValueFactory(new PropertyValueFactory<>("estadoReserva"));
        clienteCln.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        filialCln.setCellValueFactory(new PropertyValueFactory<>("nomeFilial"));
    }

	@Override
	public void atualizar(Funcionario funcionario, Cargo cargo) {
		if(cargo == null) {
			rodando = false;
			thread = null;
		}else {
			if(thread == null) {
				rodando = true;
				 thread = new Thread(new Runnable() {
						@Override
						public void run() {
							while(rodando) {
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										try {
											//verificando necessidade de atualizações 
											//para reserva
											reservasHojeTbl.getItems().clear();
											reservasHojeTbl.getItems().addAll(boReserva.buscarReservaHoje());
											reservasHojeTbl.refresh();
											//para manutenção
											int manutencaoFinalizada = BoManutencao.getInstance().checarManutencao();
											if(manutencaoFinalizada >0)
												Notifications.create().title("Manutenções finalizadas")
												.text("Foram finalizadas "+manutencaoFinalizada+" manutenções de veículos")
												.position(Pos.BOTTOM_RIGHT).showInformation();
											//para backup
											Backup backup = BoBackup.getInstance().checarBackup();
											if(backup!= null) 
												Util.exibirRealizarBackupEmDialogo(backup);
										} catch (BoException e) {
											e.printStackTrace();
										}
									}
								});
								try {
									Thread.sleep(60000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}				
					}
				);
				thread.start();
			}
		}
	}
}
