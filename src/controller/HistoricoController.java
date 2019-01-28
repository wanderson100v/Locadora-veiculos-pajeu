package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dao.Dao;
import enumeracoes.Tabela;
import excecoes.DaoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import view.Alerta;

public class HistoricoController {

    @FXML
    private DatePicker deDate;

    @FXML
    private DatePicker ateDate;

    @FXML
    private Button buscarBtn;

    @FXML
    private GridPane tablePane;
    
    @FXML
    private ComboBox<Tabela> tabelaBox;

    @FXML
    void initialize() {
    	tabelaBox.getItems().addAll(Tabela.values());
    	tabelaBox.setValue(Tabela.CLIENTE);
    	deDate.setValue(LocalDate.now());
    	ateDate.setValue(LocalDate.now());
    }
    
    @FXML
    void actionHandle(ActionEvent event) {
    	try {
    		List<Map<String,Object>> dados = Dao.buscarLog(deDate.getValue(), ateDate.getValue(),tabelaBox.getValue());
			if(!dados.isEmpty()) {
	    		TableView<Map<String,Object>> tv = openDatabase(dados.get(0));
	    		tv.getItems().setAll(dados);
	    		tablePane.getChildren().clear();
	    		tablePane.getChildren().add(tv);
	    		Alerta.getInstance().imprimirMsg("Sucesso","Foi encontrado "+dados.size()+" registros", AlertType.WARNING);
    		}else
    			Alerta.getInstance().imprimirMsg("Alerta","Nenhum registro para o período", AlertType.WARNING);
    	}catch(DaoException e) {
    		Alerta.getInstance().imprimirMsg("Erro", e.getMessage(), AlertType.ERROR);
    	}
    }
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static TableView<Map<String,Object>> openDatabase(Map<String,Object> data){
	    TableView<Map<String,Object>> tableView = new TableView<>();
	    tableView.setMaxWidth(1000);
	    tableView.setLayoutX(30);
	    List<String> primeirasColunas = new ArrayList<>();
	    Collections.addAll(primeirasColunas, "data_acao","autor","alteracao","id");
	    for(String e : data.keySet())
	    	if(!primeirasColunas.contains(e))
	    		primeirasColunas.add(e);
	    for (String nomeColuna: primeirasColunas) {
	        TableColumn<Map<String, Object>, Object> tableColumn = new TableColumn<>(nomeColuna);
	        tableColumn.setCellValueFactory(new MapValueFactory(nomeColuna));
	        tableView.getColumns().add(tableColumn);
	    }
		return tableView;
	}

}
