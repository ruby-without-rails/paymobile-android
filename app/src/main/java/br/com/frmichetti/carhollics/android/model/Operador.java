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
import com.google.gson.reflect.TypeToken;

public class Operador extends Pessoa{

	private static final long serialVersionUID = 5225214364350531214L;

	public Operador() {
		super();
	}


	public static Operador fromGson(String gson){	

		java.lang.reflect.Type collectionType = new TypeToken<Operador>() {}.getType();

		Gson in = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.enableComplexMapKeySerialization()
				.setDateFormat("dd/MM/yyyy")
				.setPrettyPrinting()
				.create();		 		

		return in.fromJson(gson, collectionType);  



	}


}
