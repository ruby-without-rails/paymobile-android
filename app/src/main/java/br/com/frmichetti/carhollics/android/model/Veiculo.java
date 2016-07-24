/**
 *
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 * */
package br.com.frmichetti.carhollics.android.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

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
		super();
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


	public static Veiculo fromGson(String gson){	

		java.lang.reflect.Type collectionType = new TypeToken<Veiculo>() {}.getType();

		Gson in = new GsonBuilder()	
				.excludeFieldsWithoutExposeAnnotation()
				.enableComplexMapKeySerialization()
				.setDateFormat("dd/MM/yyyy")
				.setPrettyPrinting()
				.create();		 		
		return in.fromJson(gson, collectionType);  



	}



}
