package br.com.frmichetti.carhollics.android.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Veiculo extends Entidade {

	private static final long serialVersionUID = -8519179405418749911L;

	@Expose
	@SerializedName("Nome")
	private String nome;

	@Expose
	@SerializedName("Marca")
	private String marca;

	@Expose
	@SerializedName("Categoria")
	private Categoria categoria;	
	
	public Veiculo() {
		// TODO Auto-generated constructor stub
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return marca + " " + nome;
	}



}
