package model.entidade;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ConfiguracoesDefault")
public class ConfiguracoesDefault {
	
	private String ip, localPgDump, localPgRestore, localBackup, userNameDefault;
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getLocalPgDump() {
		return localPgDump;
	}
	
	public void setLocalPgDump(String localPgDump) {
		this.localPgDump = localPgDump;
	}
	
	public String getLocalPgRestore() {
		return localPgRestore;
	}
	
	public void setLocalPgRestore(String localPgRestore) {
		this.localPgRestore = localPgRestore;
	}
	
	public String getLocalBackup() {
		return localBackup;
	}
	
	public void setLocalBackup(String localBackup) {
		this.localBackup = localBackup;
	}
	
	public String getUserNameDefault() {
		return userNameDefault;
	}
	
	public void setUserNameDefault(String userNameDefault) {
		this.userNameDefault = userNameDefault;
	}

}
