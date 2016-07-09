package br.com.frmichetti.carhollics.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Categoria extends Entidade {

	private static final long serialVersionUID = -7732358124731760479L;
	
	@Expose
	@SerializedName("Descricao")
	private String descricao;	
	
	public Categoria() {
		// TODO Auto-generated constructor stub
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return descricao;
	}
	
	
	
	
	

}
