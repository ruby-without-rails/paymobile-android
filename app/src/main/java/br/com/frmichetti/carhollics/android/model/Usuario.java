/**
 *
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://codecode.com.br
 * @see mailto:frmichetti@gmail.com
 * */
package br.com.frmichetti.carhollics.android.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usuario extends Entidade {

	private static final long serialVersionUID = -3957204851329387910L;

	@Expose
	@SerializedName("UUID")
	private String uuid;

	@Expose
	@SerializedName("FirebaseUUID")
	private String firebaseUUID;

	@Expose
	@SerializedName("Login")
	private String login;

	private String senha;

	@Expose
	@SerializedName("Email")
	private String email;

	@Expose
	@SerializedName("Valido")
	private boolean valido;	

	private List<SystemRole> roles = new ArrayList<>();

	public Usuario() {

	}

	public String getUUID() {
		return uuid;
	}

	public String getFirebaseUUID() {
		return firebaseUUID;
	}

	public void setFirebaseUUID(String firebaseUUID) {
		this.firebaseUUID = firebaseUUID;
	}

	protected void setUUID(String uuid) {
		this.uuid = uuid;
	}


	private void doPrepare(){
		generateUUID();
		encode();
	}

	public void generateUUID(){
		this.setUUID(UUID.randomUUID().toString());		
	}

	public void encode(){
		//TODO FIXME Hash Provisório
//		setSenha(new String(PassGenerator.gerarHash(getSenha(), "SHA-256")));
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}	

	public List<SystemRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SystemRole> roles) {
		this.roles = roles;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		result = prime * result + (valido ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		if (valido != other.valido)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return login;
	}        

}
