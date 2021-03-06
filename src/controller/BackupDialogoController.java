package controller;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

import app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import model.dao.DaoRes;
import model.dao.sql.ConnectionFactory;
import model.excecoes.BoException;
import model.excecoes.DaoException;
import model.vo.Backup;
import view.Alerta;

@SuppressWarnings("deprecation")
public class BackupDialogoController extends ControllerAdapter implements Observer{

	@FXML 
	private DialogPane backupDialogo;
	
    @FXML
    private TextField pastaBackupFld;

    @FXML
    private Button selectPastaBtn;
    
    @FXML
    private Button arquivarBtn;

    @FXML
    private TextField nomeArqFld;

    @FXML
    private TextArea descricaoArea;

    @FXML
    private ComboBox<Integer> adiarHoraBox;
    
    @FXML
    private ComboBox<Integer> proximaHoraBox;

    @FXML
    private Button adiarConfirmarBtn;

    @FXML
    private TextArea detalhesArea;
    
    private Backup backup;
    
    @FXML
    void initialize() {
    	for(int i =0 ; i < 24 ; i++)
    		proximaHoraBox.getItems().add(i);
    	int horasAteFinalDia = 24 - LocalDateTime.now().getHour();
    	for(int i =horasAteFinalDia ; i < 24 ; i++)
    		adiarHoraBox.getItems().add(i);
    	try {
			if(!this.fachadaModel.existeBackupPendente())
				proximaHoraBox.setVisible(false);
		} catch (BoException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void actionHandle(ActionEvent event) {
    	
    	Object fonte = event.getSource();
    	try {
	    	if(fonte == adiarConfirmarBtn) {
	    		if(adiarHoraBox.getValue() != null && Alerta.getInstance()
	    				.imprimirMsgConfirmacao("Realmente deseja ediar o backup em ")) {
	    			LocalDateTime proximaDatahora = this.fachadaModel.adiarBackup(backup, adiarHoraBox.getValue());
	    			Alerta.getInstance().imprimirMsg("Sucesso", "Backup adiado em "+adiarHoraBox.getValue()+ "novo horario - "+proximaDatahora, AlertType.INFORMATION);
	    			adiarConfirmarBtn.setDisable(true);
	    			arquivarBtn.setDisable(true);
	    		}else
	    			Alerta.getInstance().imprimirMsg("Alerta", "Informe a quantidade de horas que deseja adiar o backup",AlertType.WARNING);
	    		
	    	}else if(fonte ==selectPastaBtn) {
	    		DirectoryChooser directoryChooser = new DirectoryChooser();
	    	    directoryChooser.setTitle("Selecionar pasta para Backup");
	    	    File selectDirectory = directoryChooser.showDialog(App.stage);
	    		if(selectDirectory!= null)
	    			pastaBackupFld.setText(selectDirectory.toString());;
	    	}else if(fonte == arquivarBtn) {
	    		try {
		    		if(!pastaBackupFld.getText().isEmpty() && (proximaHoraBox.isVisible() && proximaHoraBox.getValue() != null)) {
			    		String[] user = ConnectionFactory.getUser();
			    		detalhesArea.clear();
			    		DaoRes.getInstance().executarBackup(pastaBackupFld.getText(),nomeArqFld.getText().trim(),
								user[0], user[1],"backup");
			    		this.fachadaModel.finalizarBackup(backup, proximaHoraBox.getValue());
			    		Alerta.getInstance().imprimirMsg("Sucesso", "Backup de banco finalizada com sucesso", AlertType.INFORMATION);
			    		adiarConfirmarBtn.setDisable(true);
		    			arquivarBtn.setDisable(true);
		    		}else
						Alerta.getInstance().imprimirMsg("Alerta", " ?? necess??rio selecioanr a pata e informar a hora de backup de amanh?? antes de realiza????o de backup", AlertType.WARNING);
				} catch (DaoException e1) {
					Alerta.getInstance().imprimirMsg("Erro", e1.getMessage(), AlertType.ERROR);
				}
	    	}
	    }
    	catch (BoException e) {
    		Alerta.getInstance().imprimirMsg("Erro", e.getMessage(), AlertType.ERROR);
    	}
    	
    }
    
    @Override
	public void update(Observable o, Object arg) {
		detalhesArea.setText(detalhesArea.getText()+"\n"+((String)(arg)));
	}

	public void paremetrizadoPor(Backup backup) {
		this.backup = backup;
	}
	
	public DialogPane getBackupDialogo() {
		return backupDialogo;
	}

}
