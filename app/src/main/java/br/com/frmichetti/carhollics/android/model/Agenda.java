package br.com.frmichetti.carhollics.android.model;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Agenda extends Entidade {

	private static final long serialVersionUID = -6023855069712420480L;

	private List<Pedido> pedidos;	

	public Agenda() {
		// TODO Auto-generated constructor stub
		super();
	}


	public static Agenda fromGson(String gson){	

		java.lang.reflect.Type collectionType = new TypeToken<Agenda>() {}.getType();

		Gson in = new GsonBuilder()				
				.excludeFieldsWithoutExposeAnnotation()
				.enableComplexMapKeySerialization()
				.setDateFormat("dd/MM/yyyy")
				.setPrettyPrinting()
				.create();		 		

		return in.fromJson(gson, collectionType);  



	}



}
