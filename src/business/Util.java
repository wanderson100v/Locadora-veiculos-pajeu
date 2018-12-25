package business;

import excecoes.ValidarException;

public class Util {
	
	public static void validarCausaInicial(Throwable e, String msg, String... restricoes) throws ValidarException {
		while(e.getCause() != null)
			e = e.getCause();
		for(String restricao : restricoes) 
			if(e.getMessage().contains(restricao.toLowerCase())){
				throw new ValidarException(msg);
		}
	}
	
}
