package dao;


import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Observable;

import entidade.ConfiguracoesDefault;
import enumeracoes.Tela;
import excecoes.DaoException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class DaoRes extends Observable implements IDaoRes{
	public static DaoRes instance;
	
	private DaoRes() {}

	public static DaoRes getInstance() {
		if(instance == null)
			instance = new DaoRes();
		return instance;
	}
	
	public Pane carregarPaneFXML(Tela tela)throws DaoException {
		try {
			return FXMLLoader.load(getClass().getClassLoader().getResource("view/"+tela+".fxml"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("Erro ao carregar tela "+tela);
		}
	}
	public Pane carregarPaneFXML(String tela)throws DaoException {
		try {
			return FXMLLoader.load(getClass().getClassLoader().getResource("view/"+tela+".fxml"));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("Erro ao carregar tela "+tela);
		}
	}
	
	public Object carregarControllerFXML(String tela)throws DaoException {
		try {
			 FXMLLoader loader  = new FXMLLoader(getClass().getClassLoader().getResource("view/"+tela+".fxml"));
			 loader.load();
			 return loader.getController();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("Erro ao carregar controlador "+tela);
		}
	}

	@Override
	public BufferedImage carregarImg(String img) throws DaoException {
		return null;
	}
	
	 public void executarBackup(String path, String nomeBackup, String userName, String databasePassword, String type) throws DaoException{

	        File backupFilePath = new File(path);
	        System.out.println(backupFilePath.toString());
	        if (!backupFilePath.exists()) {
	            File dir = backupFilePath;
	            dir.mkdirs();
	        }

	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        String backupFileName = "backup_"+nomeBackup+"_PBD_" + sdf.format(new Date()) + ".backup";
	        
	        List<String> commands = getPgComands(backupFilePath, backupFileName , userName, type);
	        if (!commands.isEmpty()) {
	            try {
	                ProcessBuilder pb = new ProcessBuilder(commands);
	                pb.environment().put("PGPASSWORD", databasePassword);

	                Process process = pb.start();

	                try (BufferedReader buf = new BufferedReader(
	                        new InputStreamReader(process.getErrorStream()))) {
	                    String line = buf.readLine();
	                    while (line != null) {
	                    	setChanged();
	                    	notifyObservers(line);
	                        line = buf.readLine();
	                    }
	                }
	                
	                process.waitFor();
	                process.destroy();

	            } catch (IOException | InterruptedException ex) {
	            	ex.printStackTrace();
	            	throw new DaoException("ERRO AO REALIZAR BACKUP DO TIPO "+type);
	            }
	        } else 
	        	  throw new DaoException("ERRO AO REALIZAR BACKUP : PARAMETROS INVALIDOS");
	    }
	 
	    private List<String> getPgComands(File backupFilePath, String backupFileName, String userName, String type) throws DaoException {

	    	
	    	ConfiguracoesDefault configuracoesDefault = DaoConfiguracaoDefault.getInstance().carregar();
	  
	    	if(configuracoesDefault == null)
	    		throw new DaoException("Não existe um arquivo de configurações padrões");
	        ArrayList<String> commands = new ArrayList<>();
	        switch (type) {
	            case "backup":
	                commands.add(configuracoesDefault.getLocalPgDump());
	                commands.add("-h"); //database server host
	                commands.add(configuracoesDefault.getIp());
	                commands.add("-p"); //database server port number
	                commands.add("5432");
	                commands.add("-U"); //connect as specified database user
	                commands.add(userName);
	                commands.add("-F"); //output file format (custom, directory, tar, plain text (default))
	                commands.add("c");
	                commands.add("-b"); //include large objects in dump
	                commands.add("-v"); //verbose mode
	                commands.add("-f"); //output file or directory name
	                commands.add(backupFilePath + File.separator + backupFileName);
	                commands.add("-d"); //database name
	                commands.add("PBD");
	                System.out.println(commands);
	                break;
	            case "restore":
	                commands.add(configuracoesDefault.getLocalPgRestore());
	                commands.add("-h");
	                commands.add(configuracoesDefault.getIp());
	                commands.add("-p");
	                commands.add("5432");
	                commands.add("-U");
	                commands.add(userName);
	                commands.add("-d");
	                commands.add("PBD");
	                commands.add("-v");
	                commands.add(backupFilePath.toString());
	                break;
	            default:
	                return Collections.emptyList();
	        }
	        return commands;
	    }
	

}
