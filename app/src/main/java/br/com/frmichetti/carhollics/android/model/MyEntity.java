package br.com.frmichetti.carhollics.android.model;

import android.util.Log;

public abstract class MyEntity implements BaseEntity {

	public MyEntity() {
		Log.d("[Criando Instancia de " , this.getClass().getSimpleName() + "]");
	}

}
