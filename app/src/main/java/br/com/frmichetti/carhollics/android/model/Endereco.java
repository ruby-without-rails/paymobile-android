/**
 *
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://codecode.com.br
 * @see mailto:frmichetti@gmail.com
 * */
package br.com.frmichetti.carhollics.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
	
	



}
