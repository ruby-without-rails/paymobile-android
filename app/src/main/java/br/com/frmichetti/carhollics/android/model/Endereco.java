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

public class Endereco extends Entidade {

	private static final long serialVersionUID = -6880834311008006048L;

	@Expose
	@SerializedName("CEP")
	private Cep cep = new Cep();

	@Expose
	@SerializedName("Numero")
	private String numero;

	@Expose
	@SerializedName("Complemento")
	private String complemento;

	public Endereco() {		
		super();
	}

	public Cep getCep() {
		return cep;
	}

	public void setCep(Cep cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	@Override
	public String toString() {
		return "[cep=" + cep + ", numero=" + numero + ", complemento=" + complemento + "]";
	}


	public static Endereco fromGson(String gson){	

		java.lang.reflect.Type collectionType = new TypeToken<Endereco>() {}.getType();

		Gson in = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.enableComplexMapKeySerialization()
				.setDateFormat("dd/MM/yyyy")
				.setPrettyPrinting()
				.create();		 		

		return in.fromJson(gson, collectionType);  



	}





}
