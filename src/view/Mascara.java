package view;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextFormatter;

public class Mascara {
	
	public static TextFormatter<UnaryOperator<TextFormatter.Change>> getMascaraNumericoInteiro() {
			return new TextFormatter<>(change -> 
			(change.getControlNewText().isEmpty() || change.getControlNewText().matches("\\d*"))? 
					change : null );
	}
	public static TextFormatter<UnaryOperator<TextFormatter.Change>> getMascaraNumericoFlutuante() {
		return new TextFormatter<>(change -> 
		(change.getControlNewText().isEmpty() || change.getControlNewText().matches("\\d+.\\d*|\\d*"))? 
				change : null );
	}
}
