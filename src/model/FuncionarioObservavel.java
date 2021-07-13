package model;

import model.enumeracoes.Cargo;
import model.vo.Funcionario;

public class FuncionarioObservavel {
	private static FuncionarioObservavel instance;
	
	private ListaDuplamenteEncadeada<IObservadorFuncionario> observadoresFuncionario = new ListaDuplamenteEncadeada<>();
	
	private Cargo cargo;
	
	private Funcionario funcionario;
	
	private FuncionarioObservavel() {}
	
	public static FuncionarioObservavel getIntance() {
		if(instance == null)
			instance = new FuncionarioObservavel();
		return instance;
	}
	
	public void addObservadorFuncionario(IObservadorFuncionario observadorEntidade) {
		this.observadoresFuncionario.adicionarItem(observadorEntidade);
	}
	
	public void removerObservadorFuncionario(IObservadorFuncionario observadorEntidade) {
		this.observadoresFuncionario.removerItem(observadorEntidade);
	}
	
	public void avisarOuvintes() {
		avisarOuvintes(funcionario, cargo);
	}
	
	public void avisarOuvintes(Funcionario funcionario, Cargo cargo) {
		this.cargo = cargo;
		this.funcionario = funcionario;
		if(observadoresFuncionario.isVasia())
			return;
		IteradorListaEncadeada<IObservadorFuncionario> iterador = new IteradorListaEncadeada<IObservadorFuncionario>(observadoresFuncionario);
		IObservadorFuncionario observador = null;
		Item<IObservadorFuncionario> itemCorrente = iterador.getItemCorrente();
		while(itemCorrente!= null) {
			observador = itemCorrente.conteudo;
			observador.atualizar(funcionario, cargo);
			if(!iterador.existeProximo())
				break;
			iterador.proximoItem();
			itemCorrente = iterador.getItemCorrente();
		}
	}
	
		
	static interface Iterador<T extends Object>{
		
		void itemAntececor();
		
		void proximoItem();
		
		boolean existeAnterior();
		
		boolean existeProximo();
		
		Item<T> getItemCorrente();
	}
	
	static class IteradorListaEncadeada<T extends Object> implements Iterador<T> {
		
		private Item<T> itemCorrente;
		
		public IteradorListaEncadeada(ListaDuplamenteEncadeada<T> listaDuplamenteEncadeada) {
			this.itemCorrente = listaDuplamenteEncadeada.primeiroItem;
		}
		
		public void itemAntececor() {
			itemCorrente = itemCorrente.itemAnterior;
		}
		
		public void proximoItem() {
			itemCorrente = itemCorrente.itemPosterior;
		}
		
		public boolean existeAnterior() {
			return (itemCorrente.itemAnterior == null);
		}
		
		public boolean existeProximo() {
			return (itemCorrente.itemPosterior != null);
		}
		
		public Item<T> getItemCorrente() {
			return itemCorrente;
		}
		
	}
	
	
	static class ListaDuplamenteEncadeada<T extends Object> {
		
		Item<T> primeiroItem;
		
		void adicionarItem(T conteudoItem) {
			Item<T> item = new Item<T>(conteudoItem); 
			if(isVasia()) {
				this.primeiroItem = item;
			}else {
				IteradorListaEncadeada<T> iterador = new IteradorListaEncadeada<T>(this);
				while(iterador.existeProximo()) {
					iterador.proximoItem();
				}
				Item<T> itemCorrente = iterador.getItemCorrente();
				itemCorrente.itemPosterior = new Item<T>(conteudoItem);
				itemCorrente.itemPosterior.itemAnterior = itemCorrente;
			}
		}
		
		boolean removerItem(T item) {
			if(primeiroItem != null) {
				IteradorListaEncadeada<T> iterador = new IteradorListaEncadeada<T>(this);
				Item<T> itemCorrente = iterador.getItemCorrente();
				while(itemCorrente != null) {
					if(itemCorrente.conteudo.equals(item)) {
						if(iterador.existeAnterior()) {
							this.primeiroItem = null;
							return true;
						}else{
							iterador.itemAntececor();
							itemCorrente = iterador.getItemCorrente();
							itemCorrente.itemPosterior = itemCorrente.itemPosterior.itemPosterior;
						}
					}
					iterador.proximoItem();
					itemCorrente = iterador.getItemCorrente();
				}
			}
			return false;
		}
		
		int contarItens() {
			int contador = 0;
			if(!isVasia()) {
				IteradorListaEncadeada<T> iterador = new IteradorListaEncadeada<T>(this);
				contador = 1;
				while(iterador.existeProximo()) {
					contador++;
					iterador.proximoItem();
				}
			}
			return contador;
		}
		
		boolean isVasia() {
			return (primeiroItem == null);
		}
		
		
	}
	
	public static class Item<T extends Object>{
		public Item<T> itemAnterior;
		public Item<T> itemPosterior;
		public T conteudo;
		public Item(T conteudo) {
			this.conteudo = conteudo;
		}
	}
	
	/*
	public static void main(String[] args) {
		ListaDuplamenteEncadeada<Integer> lista = new ListaDuplamenteEncadeada<Integer>();
		
		lista.adicionarItem(10);
		lista.adicionarItem(20);
		lista.adicionarItem(3);
		lista.adicionarItem(40);
		lista.adicionarItem(50);
		
		System.out.println(lista.contarItens());
		
		IteradorListaEncadeada<Integer> iterador = new IteradorListaEncadeada<Integer>(lista);
		
		Item<Integer> itemCorrente = iterador.getItemCorrente();
		while(itemCorrente != null) {
			System.out.println("Valor corrente = "+ itemCorrente.conteudo);
			iterador.proximoItem();
			itemCorrente = iterador.getItemCorrente();
		}
	}*/
	
}
