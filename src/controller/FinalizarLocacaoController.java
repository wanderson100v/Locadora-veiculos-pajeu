package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import business.BoLocacao;
import business.BoManutencao;
import entidade.Locacao;
import entidade.Manutencao;
import enumeracoes.EstadoManutencao;
import enumeracoes.TipoManutencao;
import excecoes.BoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import view.Alerta;

public class FinalizarLocacaoController {

    @FXML
    private CheckBox etCk;

    @FXML
    private TextField novaQuiloFld;

    @FXML
    private GridPane etHorarioPane;

    @FXML
    private GridPane etHorarioCustomPane;

    @FXML
    private DatePicker etDate;

    @FXML
    private ComboBox<Integer> etHoraBox;

    @FXML
    private CheckBox etAtualCk;

    @FXML
    private CheckBox lpCk;

    @FXML
    private CheckBox tudoAtualCk;

    @FXML
    private TextField valorRFld;

    @FXML
    private Button finalizarBtn;

    @FXML
    private GridPane devuHorarioCustomPane;

    @FXML
    private DatePicker davuDate;

    @FXML
    private ComboBox<Integer> devuHoraBox;

    @FXML
    private GridPane lpHorarioPane;

    @FXML
    private GridPane lpHorarioCustomPane;

    @FXML
    private DatePicker lpDate;

    @FXML
    private ComboBox<Integer> lpHoraBox;

    @FXML
    private ComboBox<Integer> lpCustoHoraBox;
    
    @FXML
    private ComboBox<Integer> rsCustoHoraBox;
   
    @FXML
    private ComboBox<Integer> etCustoHoraBox;

    @FXML
    private CheckBox lpAtualCk;

    @FXML
    private CheckBox devuAtualCk;

    @FXML
    private TextArea detalhesArea;
    
    @FXML
    private DialogPane finalizarLocacaoDialogo;
    
    @FXML
    private GridPane revisaoPane;

    @FXML
    private DatePicker rsDate;

    @FXML
    private ComboBox<Integer> rsHoraBox;

    @FXML
    private TextField rsCustoFld;
    
    private Locacao locacao;

    @FXML
    void initialize() {
    	for(int i = 0; i < 24; i++)
    		devuHoraBox.getItems().add(i);
    	lpCustoHoraBox.setItems(devuHoraBox.getItems());
    	etCustoHoraBox.setItems(devuHoraBox.getItems());
    	rsCustoHoraBox.setItems(devuHoraBox.getItems());
    	etHoraBox.setItems(devuHoraBox.getItems());
    	etHoraBox.setValue(0);
    	lpHoraBox.setItems(devuHoraBox.getItems());
    	lpHoraBox.setValue(2);
    	rsHoraBox.setItems(devuHoraBox.getItems());
    	rsHoraBox.setValue(5);
    }
    
    @FXML
    void actionHandle(ActionEvent e) {
    	try {
    		attDescricao();
    		try {
	    		revisaoPane.setDisable(Float.parseFloat(novaQuiloFld.getText().trim()) 
	    				< locacao.getVeiculo().getCategoriaVeiculo().getQuilometragemRevisao());
    		}catch (NumberFormatException ex) {}
    		
    		if(e.getSource() == devuAtualCk)
	    		devuHorarioCustomPane.setDisable(devuAtualCk.isSelected());
	    	else if(e.getSource() == etAtualCk)
	    		etHorarioCustomPane.setDisable(etAtualCk.isSelected());
	    	else if(e.getSource() == lpAtualCk) 
	    		lpHorarioCustomPane.setDisable(lpAtualCk.isSelected());
	    	else if(e.getSource() == etCk)
	    		etHorarioPane.setDisable(!etCk.isSelected());
	    	else if(e.getSource() == lpCk)
		    	lpHorarioPane.setDisable(!lpCk.isSelected());
	    	else if(e.getSource()== finalizarBtn)
	    	{
	    		StringBuilder errosEmTela = new StringBuilder();
	    		
	    		if(!devuAtualCk.isSelected() && ( davuDate.getValue() == null || devuHoraBox.getValue() == null))
	    				errosEmTela.append("- É nescessário informar a data e hora de devolução de locação\n");
	    		if(etCk.isSelected() && (!etAtualCk.isSelected() && ( etDate.getValue() == null || etHoraBox.getValue() == null)))
	    				errosEmTela.append("- Abastecimento para veículo selecionado, é necessário também definir data e hora para execução\n");
	    		if(lpCk.isSelected() && (!lpAtualCk.isSelected() && ( lpDate.getValue() == null || lpHoraBox.getValue() == null)))
	    				errosEmTela.append("- Limpeza para veículo selecionada, é necessário também definir data e hora para execução\n");
	    		if(!revisaoPane.isDisable() && ( rsDate.getValue() == null || rsHoraBox.getValue() == null || rsCustoFld.getText().trim().isEmpty()))
    				errosEmTela.append("- A nova quilometragem do véiculo requer a definição de dados para revsão,"
    						+ " é necessário definir data e hora como também o custo que a filial ira gastar na revisão\n");
	    		
	    		int quilometragem = 0;
	    		float valorPago = 0f;
	    		float custoRevisao = 0;
	    		
	    		try {
		    		quilometragem = Integer.parseInt(novaQuiloFld.getText());
		    		valorPago = Float.parseFloat(valorRFld.getText());
		    		custoRevisao = Float.parseFloat(rsCustoFld.getText());
	    		}catch (NumberFormatException ex) {
	    			errosEmTela.append("- Entrada invalida para campo numerico");
	    		}
	    		
	    		if(errosEmTela.length() >0) {
	    			Alerta.getInstance().imprimirMsg("Alerta",errosEmTela.toString(), AlertType.WARNING);
	    			return;
	    		}	
		    		
		    		LocalDateTime horarioDevu = (!devuAtualCk.isSelected())? Util.gerarHorario(davuDate, devuHoraBox): LocalDateTime.now();
					
		    		Object[] dados =  BoLocacao.getInstance().calcularValorLocacaoDetalhamento(locacao ,Integer.parseInt(novaQuiloFld.getText())
							, horarioDevu, etCk.isSelected(), lpCk.isSelected());
					
					locacao.setValorPago(valorPago);
					locacao.getVeiculo().setLocado(false);
					locacao.getVeiculo().setQuilometragem(quilometragem);
					locacao.setValorDiaria((float)dados[0]);
					locacao.setFinalizado(true);
					BoLocacao.getInstance().cadastrarEditar(locacao);
					StringBuilder sucessoFinalizacao = new StringBuilder();
					
					if(etAtualCk.isSelected()) {
						BoManutencao.getInstance().cadastrarEditar(new Manutencao((!etAtualCk.isSelected())? 
								Util.gerarHorario(etDate, etHoraBox) : LocalDateTime.now() ,TipoManutencao.ABASTECIMENTO,
								EstadoManutencao.PENDENTE,0f,etCustoHoraBox.getValue(),locacao.getVeiculo()));
						sucessoFinalizacao.append("\n- Nova manutenção de abastecimento de combustível pendente");
					}
					if(lpCk.isSelected()) {
						BoManutencao.getInstance().cadastrarEditar(new Manutencao((!lpAtualCk.isSelected())?
								Util.gerarHorario(lpDate, lpHoraBox) : LocalDateTime.now(),TipoManutencao.LIMPEZA,
								EstadoManutencao.PENDENTE,0f,lpCustoHoraBox.getValue(),locacao.getVeiculo()));
						sucessoFinalizacao.append("\n- Nova manutenção de limpeza pendente");
					}
					if(!revisaoPane.isDisable()) {
						BoManutencao.getInstance().cadastrarEditar(new Manutencao( Util.gerarHorario(rsDate, rsHoraBox)
								,TipoManutencao.REVISAO,EstadoManutencao.PENDENTE, custoRevisao ,rsCustoHoraBox.getValue(),locacao.getVeiculo()));
						sucessoFinalizacao.append("\n- Nova manutenção de revisão pendente");
					}
					Alerta.getInstance().imprimirMsg("Sucesso", "Locação Finalizada com exito"+sucessoFinalizacao.toString(), AlertType.INFORMATION);
					finalizarBtn.setDisable(true);
	    	}
	    } catch (BoException ex) {
			Alerta.getInstance().imprimirMsg("Erro",ex.getMessage(), AlertType.ERROR);
			ex.printStackTrace();
		}
    		
    }
    
    private void attDescricao() throws BoException {
    	try {
	    	if((devuAtualCk.isSelected() ||( davuDate.getValue() != null && devuHoraBox.getValue() != null)) && !novaQuiloFld.getText().trim().isEmpty()) {
	    		LocalDate data = davuDate.getValue();
	    		LocalDateTime horarioDevu = null;
	    		if(!devuAtualCk.isSelected()) {
	    			horarioDevu	= LocalDateTime.of(data.getYear(),data.getMonthValue(),data.getDayOfMonth()
	    					,devuHoraBox.getValue(), 0);
	    		}else
	    			horarioDevu = LocalDateTime.now();
				String detales =(String) BoLocacao.getInstance().calcularValorLocacaoDetalhamento(locacao ,Integer.parseInt(novaQuiloFld.getText())
						, horarioDevu, etCk.isSelected(), lpCk.isSelected())[1];
				detalhesArea.setText(detales);
	    	}
    	} catch (NumberFormatException e) {
    		Alerta.getInstance().imprimirMsg("Alerta","Deve ser informada uma quilometragem de valor inteiro", AlertType.WARNING);
    	}
    }
    
    public void paremetrizadoPor(Locacao locacao) {
    	this.locacao = locacao;
    }
    
    public DialogPane getFinalizarLocacaoDialogo() {
		return finalizarLocacaoDialogo;
	}
}
