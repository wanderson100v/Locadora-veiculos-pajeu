package controller;

import org.controlsfx.control.Notifications;

import model.excecoes.BoException;
import model.vo.Backup;
import model.vo.Funcionario;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.banco.ReservaHoje;
import model.enumeracoes.Cargo;
import model.enumeracoes.EstadoRerserva;

public class InicioController extends Controller{

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

    private Thread thread;
    
    private boolean rodando = true;
    
    @FXML
    void initialize() {
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
											atualizarTbl();
											mostrarQuantidadeManutencoesFinalizadas();
											checarNecessidadeBackup();
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
	
	private void atualizarTbl() throws BoException{
		reservasHojeTbl.getItems().clear();
		reservasHojeTbl.getItems().addAll(fachadaModel.buscarReservaHoje());
		reservasHojeTbl.refresh();
	}
	
	private void mostrarQuantidadeManutencoesFinalizadas() throws BoException {
		int manutencaoFinalizada = fachadaModel.checarManutencao();
		if(manutencaoFinalizada >0)
			Notifications.create().title("Manutenções finalizadas finalizadas")
			.text("Foram finalizadas "+manutencaoFinalizada+" manutenções de veículos")
			.position(Pos.BOTTOM_RIGHT).showInformation();
	}
	
	public void checarNecessidadeBackup()  throws BoException{
		Backup backup = fachadaModel.checarBackup();
		if(backup!= null) 
			Util.exibirRealizarBackupEmDialogo(backup);
	}
}
