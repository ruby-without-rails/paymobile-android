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

public class Cep extends Entidade{

	private static final long serialVersionUID = -1019788844812243053L;

	@Expose
	@SerializedName("CEP")
	private Long cep;    

	public Cep() {	
		super();
		this.cep = 0L;
	}

	public Long getCep() {
		return cep;
	}

	public void setCep(Long cep) {
		this.cep = cep;
	}

	@Override
	public String toString() {
		return cep.toString();
	}


	public static Cep fromGson(String gson){	

		java.lang.reflect.Type collectionType = new TypeToken<Cep>() {}.getType();

		Gson in = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.enableComplexMapKeySerialization()
				.setDateFormat("dd/MM/yyyy")
				.setPrettyPrinting()
				.create();		 		

		return in.fromJson(gson, collectionType);  



	}



}
