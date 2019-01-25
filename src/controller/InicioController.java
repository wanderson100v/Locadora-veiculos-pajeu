package controller;

import banco.ReservaHoje;
import business.BoReserva;
import business.IBoReserva;
import enumeracoes.Cargo;
import enumeracoes.EstadoRerserva;
import excecoes.BoException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class InicioController implements IObservadoresEntidade {

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
    	ObservadorEntidade.getIntance().getEntidadeObservadores().add(this);
    	
    	horaCln.setCellValueFactory(new PropertyValueFactory<>("hora"));
        categoriaCln.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        estadoCln.setCellValueFactory(new PropertyValueFactory<>("estadoReserva"));
        clienteCln.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        filialCln.setCellValueFactory(new PropertyValueFactory<>("nomeFilial"));
    }

	@Override
	public void atualizar(Cargo cargo) {
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
											reservasHojeTbl.getItems().clear();
											reservasHojeTbl.getItems().addAll(boReserva.buscarReservaHoje());
											reservasHojeTbl.refresh();
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
