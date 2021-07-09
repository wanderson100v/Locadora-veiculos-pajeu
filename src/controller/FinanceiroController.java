package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import model.excecoes.BoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.GridPane;
import mode.business.BoLocacao;
import mode.business.BoReserva;
import view.Alerta;

public class FinanceiroController {

	@FXML
    private ComboBox<String> financeiroBox;

    @FXML
    private Label descricaoLbl;

    @FXML
    private GridPane tabelaPane;

    @FXML
    private DatePicker deDate;

    @FXML
    private DatePicker ateDate;
   
    @FXML
    private Button buscarBtn;
    

    @FXML
    private ComboBox<String> groypByBox;

    @FXML
    void initialize() {
    	financeiroBox.getItems().addAll("Locacões finalizadas"
    			,"Reservas Origens de Locação","Reservas Incompletadas");
    	groypByBox.getItems().addAll("Dia","Mês");
    	groypByBox.setValue("Dia");
    	descricaoLbl.setText("");
    }
    
    @FXML
    void actionHandle(ActionEvent event) {
    	try {
    		if(event.getSource() == buscarBtn) {
    			if(financeiroBox.getValue() == null ||deDate.getValue() == null || ateDate.getValue() == null) {
    				Alerta.getInstance().imprimirMsg("Alerta","Um ou mais campos vazios", AlertType.WARNING);
    				return;
    			}
    			
    			List<String> nomesColunaOrganizado = new ArrayList<>();
    			List<Map<String,Object>> registros = null;
    			TableView<Map<String,Object>> tv = null;
    			
    			if(financeiroBox.getValue().equals("Locacões finalizadas") 
    					|| financeiroBox.getValue().equals("Reservas Origens de Locação")) {
    				if(groypByBox.getValue().equals("Dia")) 
    	    			nomesColunaOrganizado.add("devolucao");
    	    		else
    	    			Collections.addAll(nomesColunaOrganizado,"ano","mes");
    				
    				if(financeiroBox.getValue().equals("Locacões finalizadas")) {
	    				registros = BoLocacao.getInstance().buscarLocacoesFinalizadas(deDate.getValue(), ateDate.getValue(), groypByBox.getValue());
	    				Collections.addAll(nomesColunaOrganizado,"locacoes","valor_real","valor_pago","restante");
	    			}else {
	    				registros = BoReserva.getInstance().buscarReservasOrigemLocacaoFinalizada(deDate.getValue(), ateDate.getValue(), groypByBox.getValue());
	    				Collections.addAll(nomesColunaOrganizado,"locacoes_geradas","valor_real","valor_pago","restante");
	    			}
    			}else if(financeiroBox.getValue().equals("Reservas Incompletadas")) {
    				registros = BoReserva.getInstance().buscarReservasImpedidas(deDate.getValue(), ateDate.getValue());
    				Collections.addAll(nomesColunaOrganizado,"codigo_cliente","potencial_perdido","estado","planejada_retirada","planejada_devolucao");
    			}
    			if(registros != null && !registros.isEmpty()) {
    				tv = openDatabase(registros.get(0),nomesColunaOrganizado);
		    		tabelaPane.getChildren().setAll(tv);
		    		tv.getItems().setAll(registros);
		    		Alerta.getInstance().imprimirMsg("Sucesso","Foi encontrado "+registros.size()+" registros", AlertType.WARNING);
	    		}else
	    			Alerta.getInstance().imprimirMsg("Alerta","Nenhum registro para o período", AlertType.WARNING);
    		}
    	}catch(BoException e) {
    		Alerta.getInstance().imprimirMsg("Alerta",e.getMessage(),AlertType.ERROR);
    	}
    
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static TableView<Map<String,Object>> openDatabase(Map<String,Object> registro, List<String> nomesOrganizado){
	    TableView<Map<String,Object>> tableView = new TableView<>();
	    for (String nomeColuna: nomesOrganizado) {
	        TableColumn<Map<String, Object>, Object> tableColumn = new TableColumn<>(nomeColuna.toUpperCase());
	        tableColumn.setCellValueFactory(new MapValueFactory(nomeColuna));
	        tableView.getColumns().add(tableColumn);
	    }
		return tableView;
	}

}
