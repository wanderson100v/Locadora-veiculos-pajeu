package controller;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.FachadaModel;
import model.dao.DaoConfiguracaoDefault;
import model.dao.DaoRes;
import model.dao.IDaoConfiguracaoDefault;
import model.dao.sql.ConnectionFactory;
import model.enumeracoes.Cargo;
import model.enumeracoes.EstadoBackup;
import model.excecoes.BoException;
import model.excecoes.DaoException;
import model.vo.Backup;
import model.vo.ConfiguracoesDefault;
import model.vo.Funcionario;
import view.Alerta;

@SuppressWarnings("deprecation")
public class BackupController extends Controller implements Observer{

	
	@FXML
    private TextField pastaBackupFld;

    @FXML
    private Button selectPastaBackupBtn;

    @FXML
    private TextField nomeArqBackupFld;

    @FXML
    private TextArea detalheBackupArea;
    
    @FXML
    private TextArea  descriBackupArea;

    @FXML
    private Button arquivarBtn;

    @FXML
    private TextField arqRestauFld;

    @FXML
    private Button selectArqRestauBtn;

    @FXML
    private TextArea detalheRestauArea;

    @FXML
    private Button restaurarBtn;

    @FXML
    private TextField pastaDefaultBackupFld;

    @FXML
    private Button pastaDefaultBackupBtn;

    @FXML
    private TextField ipBancoFld;

    @FXML
    private TextField arqPgRestoreFld;

    @FXML
    private TextField arqPgDumpFld;

    @FXML
    private Button arqPgDumpBtn;

    @FXML
    private Button arqPgRestoreBtn;

    @FXML
    private Button restauConfigBtn;

    @FXML
    private Button aplicConfigBtn;

    @FXML
    private DatePicker agendDate;

    @FXML
    private ComboBox<Integer> agendHoraBox;

    @FXML
    private ListView<Backup> bakupLv;

    @FXML
    private Button agendarBtn;
    
    private boolean restaurarcao;
    
    private IDaoConfiguracaoDefault daoConfiguracaoDefault;
    
    private FachadaModel fachadaModel;
    
    
    @FXML
    void initialize() {
    	DaoRes.getInstance().addObserver(this);
    	this.fachadaModel = FachadaModel.getInstance();
    	
    	daoConfiguracaoDefault = DaoConfiguracaoDefault.getInstance();
    	detalheBackupArea.positionCaret(detalheBackupArea.getLength());
    	agendDate.setValue(LocalDate.now());
    	for(int i =0 ; i < 24 ; i++)
    		agendHoraBox.getItems().add(i);
    	agendHoraBox.setValue(LocalTime.now().plusHours(1).getHour());
    }
    
    private void popularListViewBackup() {
    	try {
    		List<Backup> backupHistory = this.fachadaModel.buscarTodosBackups();
    		if(backupHistory != null && !backupHistory.isEmpty())
    			bakupLv.getItems().setAll(backupHistory);
		} catch (BoException e) {
			e.printStackTrace();
		}
    }
    
    public void backupHandle(ActionEvent e) {
    	if(e.getSource() == selectPastaBackupBtn) {
    		File caminho = mostrarDirectoryChooser();
    		if(caminho!= null)
    			pastaBackupFld.setText(caminho.toString());;
    	}
    	else if(e.getSource() == arquivarBtn) {
	    	try {
	    		String[] user = ConnectionFactory.getUser();
	    		detalheBackupArea.clear();
				restaurarcao = false;
	    		DaoRes.getInstance().executarBackup(pastaBackupFld.getText(),nomeArqBackupFld.getText().trim(),
						user[0], user[1],"backup");
	    		Alerta.getInstance().imprimirMsg("Sucesso", "Backup realizado com sucesso", AlertType.INFORMATION);
			} catch (DaoException e1) {
				Alerta.getInstance().imprimirMsg("Erro", e1.getMessage(), AlertType.ERROR);
			}
    	}else if(e.getSource() == agendarBtn) {
    		String[] user = ConnectionFactory.getUser();
    		Backup backup = new Backup();
    		backup.setAutor(user[0]);
    		backup.setDataOcorrencia(Util.gerarHorario(agendDate, agendHoraBox));
    		backup.setEstado(EstadoBackup.PENDENTE);
    		backup.setDescricao("");
    		try {
				this.fachadaModel.cadastrarEditarBackup(backup);
				popularListViewBackup();
				Alerta.getInstance().imprimirMsg("Sucesso", "Backup agendado com sucesso", AlertType.INFORMATION);
			} catch (BoException e1) {
				Alerta.getInstance().imprimirMsg("Erro", e1.getMessage(), AlertType.ERROR);
			}
    		
    	}
    }
    
    public void restauracaoHandle(ActionEvent e) {
    	if(e.getSource() == selectArqRestauBtn) {
    		FileChooser fileChooser = new FileChooser();
    	    fileChooser.setTitle("Open Resource File");
    	    ConfiguracoesDefault configuracoesDefault;
			try {
				configuracoesDefault = DaoConfiguracaoDefault.getInstance().carregar();
				if(configuracoesDefault != null)
					fileChooser.setInitialDirectory(new File(configuracoesDefault.getLocalBackup()));
	    	   
			} catch (DaoException e1) {
				e1.printStackTrace();
			}
			fileChooser.getExtensionFilters().addAll( new ExtensionFilter("Arquivos de Backup", "*.backup"));
			File selectedFile = fileChooser.showOpenDialog(App.stage);
    	    if(selectedFile!= null)
    			arqRestauFld.setText(selectedFile.toString());
    	   
    	}
    	else if(e.getSource() == arquivarBtn) {
	    	try {
	    		if(!arqRestauFld.getText().isEmpty()) {
		    		String[] user = ConnectionFactory.getUser();
		    		detalheRestauArea.clear();
		    		restaurarcao = true;
		    		DaoRes.getInstance().executarBackup(pastaBackupFld.getText(),nomeArqBackupFld.getText().trim(),
							user[0], user[1],"restore");
		    		Backup backup = new Backup();
		    		backup.setAutor(user[0]);
		    		backup.setDataOcorrencia(LocalDateTime.now());
		    		backup.setEstado(EstadoBackup.REALIZADO);
		    		backup.setDescricao(descriBackupArea.getText().trim());
		    		this.fachadaModel.cadastrarEditarBackup(backup);
		    		Alerta.getInstance().imprimirMsg("Sucesso", "Restauração de banco finalizada com sucesso", AlertType.INFORMATION);
				}else
					Alerta.getInstance().imprimirMsg("Alerta", "É necessário selecionar arquivo antes de"
							+ " restauração de backup", AlertType.WARNING);
			} catch (DaoException | BoException e1) {
				Alerta.getInstance().imprimirMsg("Erro", e1.getMessage(), AlertType.ERROR);
			}
    	}
    }
 
    public void configuracoesHandle(ActionEvent e) {
    	if(e.getSource() == pastaDefaultBackupBtn) {
    		File caminho = mostrarDirectoryChooser();
    		if(caminho!= null)
    			pastaDefaultBackupFld.setText(caminho.toString());;
    	}
    	else if(e.getSource() == arqPgDumpBtn) {
    		FileChooser fileChooser = new FileChooser();
    	    fileChooser.setTitle("Selecionar arquivo executavel pg_dump.exe");
    	    fileChooser.getExtensionFilters().addAll( new ExtensionFilter("pg_dump", "pg_dump.exe"));
    	    File selectedFile = fileChooser.showOpenDialog(App.stage);
    	    if(selectedFile != null) {
    	    	arqPgDumpFld.setText(selectedFile.toString());
    	    }
    	}
    	else if(e.getSource() == arqPgRestoreBtn) {
    		FileChooser fileChooser = new FileChooser();
    	    fileChooser.setTitle("Selecionar arquivo executavel pg_restore.exe");
    	    fileChooser.getExtensionFilters().addAll( new ExtensionFilter("pg_restore", "pg_restore.exe"));
    	    File selectedFile = fileChooser.showOpenDialog(App.stage);
    	    if(selectedFile != null)
    	    	arqPgRestoreFld.setText(selectedFile.toString());
    	}
    	else if(e.getSource() == aplicConfigBtn) {
	    	try {
	    		if(!pastaDefaultBackupFld.getText().isEmpty() && !arqPgDumpFld.getText().isEmpty() 
	    				&& !arqPgRestoreFld.getText().isEmpty() && !ipBancoFld.getText().trim().isEmpty()) {
		    		
	    			ConfiguracoesDefault configuracoesDefault = new ConfiguracoesDefault();
	    			configuracoesDefault.setIp(ipBancoFld.getText().trim());
	    			configuracoesDefault.setLocalBackup(pastaDefaultBackupFld.getText());
		    		configuracoesDefault.setLocalPgDump(arqPgDumpFld.getText());
		    		configuracoesDefault.setLocalPgRestore(arqPgRestoreFld.getText());
		    		DaoConfiguracaoDefault.getInstance().salvar(configuracoesDefault);
		    		atualizarTela();
		    		Alerta.getInstance().imprimirMsg("Sucesso", "Novas configurações salvas com sucesso", AlertType.INFORMATION);
				}else
					Alerta.getInstance().imprimirMsg("Alerta", "Todos os campos da tela de configurações "
							+ "devem estar preenchidos", AlertType.WARNING);
			} catch (DaoException e1) {
				Alerta.getInstance().imprimirMsg("Erro", e1.getMessage(), AlertType.ERROR);
			}
    	}else if(e.getSource() == restauConfigBtn)
    		atualizarTela();
    }
    

	@Override
	public void atualizar(Funcionario funcionario, Cargo cargo) {
		atualizarTela();
		popularListViewBackup();
	}
	
	private void atualizarTela() {
		ConfiguracoesDefault configuracoesDefault;
		try {
			configuracoesDefault = daoConfiguracaoDefault.carregar();
			if(configuracoesDefault != null) {
				//tela config
				pastaDefaultBackupFld.setText(configuracoesDefault.getLocalBackup());
				arqPgDumpFld.setText(configuracoesDefault.getLocalPgDump());
				arqPgRestoreFld.setText(configuracoesDefault.getLocalPgRestore());
				ipBancoFld.setText(configuracoesDefault.getIp());
				// tela bakcup
				pastaBackupFld.setText(configuracoesDefault.getLocalBackup());
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
	
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(restaurarcao) {
			detalheRestauArea.setText(detalheRestauArea.getText()+"\n"+((String)(arg)));
		}else
			detalheBackupArea.setText(detalheBackupArea.getText()+"\n"+((String)(arg)));
	}

	private File mostrarDirectoryChooser() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
	    directoryChooser.setTitle("Selecionar pasta para Backup");
	    File selectDirectory = directoryChooser.showDialog(App.stage);
	    return selectDirectory;
	}
}
