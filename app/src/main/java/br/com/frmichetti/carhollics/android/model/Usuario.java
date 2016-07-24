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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Usuario extends Entidade {

	private static final long serialVersionUID = -3957204851329387910L;

	@Expose
	@SerializedName("UUID")
	private String uuid;

	@Expose
	@SerializedName("FirebaseUUID")
	private String firebaseUUID;

	@Expose
	@SerializedName("FirebaseMessageToken")
	private String firebaseMessageToken;

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

	private List<SystemRole> roles = new ArrayList<SystemRole>();

	public Usuario() {		
		super();
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

	public String getFirebaseMessageToken() {
		return firebaseMessageToken;
	}

	public void setFirebaseMessageToken(String firebaseMessageToken) {
		this.firebaseMessageToken = firebaseMessageToken;
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

		String rawPasswd = this.senha;

		if((rawPasswd != null)){

			this.setSenha((rawPasswd));

		}   

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
	public String toString() {
		return login;
	}    


	public static Usuario fromGson(String gson){	

		java.lang.reflect.Type collectionType = new TypeToken<Usuario>() {}.getType();

		Gson in = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.enableComplexMapKeySerialization()
				.setDateFormat("dd/MM/yyyy")
				.setPrettyPrinting()
				.create();		 		

		return in.fromJson(gson, collectionType);  



	}

}
