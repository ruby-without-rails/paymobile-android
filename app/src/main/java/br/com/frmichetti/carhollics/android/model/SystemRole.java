/**
 *
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 * */
package br.com.frmichetti.carhollics.android.model;

import android.util.Log;

public class SystemRole {

	private String name;

	public SystemRole() {
		Log.d("[Criando Instancia de "
				, this.getClass().getSimpleName() + "]");
	}

	public SystemRole(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



}
