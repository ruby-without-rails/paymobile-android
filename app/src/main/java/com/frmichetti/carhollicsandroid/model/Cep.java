/**
 *
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://codecode.com.br
 * @see mailto:frmichetti@gmail.com
 * */
package com.frmichetti.carhollicsandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cep extends Entidade{

	private static final long serialVersionUID = -1019788844812243053L;

	@Expose
	@SerializedName("CEP")
	private Long cep;    

	public Cep() {		
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
	
	

}
