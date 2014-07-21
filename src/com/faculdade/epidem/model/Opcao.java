package com.faculdade.epidem.model;

public class Opcao {

	private int Id;
	private String Titulo;
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getTitulo() {
		return Titulo;
	}

	public void setTitulo(String titulo) {
		Titulo = titulo;
	}

	@Override
	public String toString() {
		return Titulo;
	}

}
