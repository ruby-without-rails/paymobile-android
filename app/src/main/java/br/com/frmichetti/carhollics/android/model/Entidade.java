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

import java.io.Serializable;

public abstract class Entidade implements Serializable,BaseEntity {

	private static final long serialVersionUID = 8297266652535609044L;

	@Expose
	@SerializedName("Id")
	private Long id;

	public Entidade() {		

		System.out.println("[Criando Instancia de " + this.getClass().getSimpleName() + "]");

		this.id=0L;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	

	public String toGson(){

		String gson = "{}";

		if(this != null){

			Gson out = new GsonBuilder()
					.setPrettyPrinting()
					.excludeFieldsWithoutExposeAnnotation()
					.enableComplexMapKeySerialization()
					.setDateFormat("dd/MM/yyyy")
					.create();

			gson = out.toJson(this);

		}

		return gson;


	}


	public static Entidade fromGson(String gson){	

		java.lang.reflect.Type collectionType = new TypeToken<Entidade>() {}.getType();

		Gson in = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.enableComplexMapKeySerialization()
				.setDateFormat("dd/MM/yyyy")
				.setPrettyPrinting()
				.create();		 		

		return in.fromJson(gson, collectionType);  

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entidade other = (Entidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}




}
