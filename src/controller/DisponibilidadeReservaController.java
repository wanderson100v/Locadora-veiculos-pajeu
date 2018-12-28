package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import adapter.ReservaDisponibilidade;
import business.BoFuncionario;
import business.BoReserva;
import business.IBoReserva;
import entidade.Filial;
import entidade.Funcionario;
import enumeracoes.Cargo;
import excecoes.BoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import sql.ConnectionFactory;
import view.Alerta;

public class DisponibilidadeReservaController implements IFuncionarioObservadores{


    @FXML
    private Button atualizarTabelaBtn;

    @FXML
    private DatePicker dataDate;

    @FXML
    private ComboBox<Integer> horaBox;

    @FXML
    private RadioButton minhaFilialRb;

    @FXML
    private RadioButton outraFilialRb;

    @FXML
    private TextField dadosFilialFld;

    @FXML
    private Button selecionarFilialBtn;
    
    @FXML
    private TableView<ReservaDisponibilidade> reservasTbl;

    @FXML
    private TableColumn<ReservaDisponibilidade, String> categoriaCln;

    @FXML
    private TableColumn<ReservaDisponibilidade, Integer> reservadoCln;

    @FXML
    private TableColumn<ReservaDisponibilidade, Integer> aReceberCln;

    @FXML
    private TableColumn<ReservaDisponibilidade, Integer> reservavelCln;

    @FXML
    private TableColumn<ReservaDisponibilidade, Integer> previstoCln;
    
    @FXML
    private TableColumn<ReservaDisponibilidade, Float> valorCln;

    @FXML
    private TableColumn<ReservaDisponibilidade, String> descricaoCln;
    
    private IBoReserva boReserva = BoReserva.getInstance();
    private Funcionario funcionario;
    private Filial outraFilial;
    private ToggleGroup toggleGroup;
    
    @FXML
    void initialize() {
    	FuncionarioObservavel.getIntance().getFuncionarioObservadores().add(this);
    }
    
    @FXML
    void actionHandle(ActionEvent event) {
    	try {
	    	Object fonte  = event.getSource();
	    	if(fonte == atualizarTabelaBtn) {
	    		if(toggleGroup.getSelectedToggle()!= null) {
	    			Filial filial = null;
	    			if(dataDate.getValue() != null && horaBox.getValue()!= null ) {
		    			filial = (minhaFilialRb.isSelected()) ? funcionario.getFilial(): outraFilial;
		    			LocalDate data = dataDate.getValue();
		    			reservasTbl.getItems().setAll(boReserva.buscarReservaDisponibilidade(filial.getId(),LocalDateTime.of(data.getYear(),data.getMonthValue(),data.getDayOfMonth(),horaBox.getValue(),0)));
			    		Alerta.getInstance().imprimirMsg("Busca Concluida", reservasTbl.getItems().size()+" resultados",AlertType.INFORMATION);
		    		}else
		    			Alerta.getInstance().imprimirMsg("Alerta", "Um ou mais campos de horario estão vazios",AlertType.WARNING);
	    		}else
	    			Alerta.getInstance().imprimirMsg("Alerta", "É necessário selecionar opção de busca para filial",AlertType.WARNING);
	    	
	    	}else if(fonte == selecionarFilialBtn) {
	    		Filial filial  = Util.selecionarFilialEmDialogo();
	    		if(filial!= null) {
	    			outraFilial = filial;
	    			dadosFilialFld.setText(outraFilial.toString());
	    		}
	    	}else if(fonte == outraFilialRb) {
	    		outraFilial = Util.selecionarFilialEmDialogo();
	    		if(outraFilial!= null) {
	    			dadosFilialFld.setText(outraFilial.toString());
	    			selecionarFilialBtn.setDisable(false);
	    		}else {
	    			outraFilialRb.setSelected(false);
	    			dadosFilialFld.clear();
	    		}
	    	}else if(fonte == minhaFilialRb) {
	    		if(funcionario.getFilial() != null) {
	    			dadosFilialFld.setText(funcionario.getFilial().toString());
	    			selecionarFilialBtn.setDisable(true);
	    		}
	    		else {
	    			dadosFilialFld.clear();
	    			minhaFilialRb.setSelected(false);
	    			Alerta.getInstance().imprimirMsg("Alerta", "Funcionário não esta relacionado a nenhuma filial",AlertType.WARNING);
	    		}
	    	}
    	}catch (BoException e) {
    		Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
    	}
    }

	public void atualizar(Cargo cargo) {
		try {
			if(categoriaCln != null && categoriaCln.getCellValueFactory() == null)
	    		fazerLigacao();
			this.funcionario = null;
			List<Funcionario> funcionarios = BoFuncionario.getInstance().buscaPorBusca(ConnectionFactory.getUser()[0].substring(1));
			if(!funcionarios.isEmpty()) {
				this.funcionario = funcionarios.get(0);
				if(funcionario.getFilial()!= null) {
					minhaFilialRb.setSelected(true);
					dadosFilialFld.setText(funcionario.getFilial().toString());
					reservasTbl.getItems().setAll(boReserva.buscarReservaDisponibilidade(funcionario.getFilial().getId(),LocalDateTime.now()));
				}
			}
		} catch (BoException e) {
			Alerta.getInstance().imprimirMsg("Erro",e.getMessage(), AlertType.ERROR);
		}
	}
    
    private void fazerLigacao() {
    	for(int i =0; i <24; i++) 
    		horaBox.getItems().add(i);
    	
    	dataDate.setValue(LocalDate.now());

    	toggleGroup = new ToggleGroup();
    	minhaFilialRb.setToggleGroup(toggleGroup);
    	outraFilialRb.setToggleGroup(toggleGroup);
    	
    	categoriaCln.setCellValueFactory(new PropertyValueFactory<>("tipoCategoria"));
    	reservadoCln.setCellValueFactory(new PropertyValueFactory<>("reservado"));
    	aReceberCln.setCellValueFactory(new PropertyValueFactory<>("receber"));
    	reservavelCln.setCellValueFactory(new PropertyValueFactory<>("reservavel"));
    	previstoCln.setCellValueFactory(new PropertyValueFactory<>("previsto"));
    	valorCln.setCellValueFactory(new PropertyValueFactory<>("valorDiariaCategoria"));
    	descricaoCln.setCellValueFactory(new PropertyValueFactory<>("descricaoCategoria"));
  
    }
}
