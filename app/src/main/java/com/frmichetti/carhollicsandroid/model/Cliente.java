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


public class Cliente extends Pessoa {

	private static final long serialVersionUID = 5825703326100838685L;

	@Expose
	@SerializedName("CPF")
	private Long cpf;

	public Cliente() {
		this.cpf = 0L;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	@Override
	public String toString() {
		return super.getNome();
	}
	
	



}
