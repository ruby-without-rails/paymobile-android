package br.com.frmichetti.carhollics.android.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class User extends MyEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private int version;

	private String uuid;

	private String firebaseUUID;

	private String firebaseMessageToken;

	private Date createdAt;

	private String login;

	private String password;

	private boolean active;

	private String email;

	private Set<SystemRole> roles = new HashSet<SystemRole>();

	public User() {
		this.id = 0L;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (uuid != null && !uuid.trim().isEmpty())
			result += "uuid: " + uuid;
		if (firebaseUUID != null && !firebaseUUID.trim().isEmpty())
			result += ", firebaseUUID: " + firebaseUUID;
		if (firebaseMessageToken != null
				&& !firebaseMessageToken.trim().isEmpty())
			result += ", firebaseMessageToken: " + firebaseMessageToken;
		if (login != null && !login.trim().isEmpty())
			result += ", login: " + login;
		if (password != null && !password.trim().isEmpty())
			result += ", password: " + password;
		result += ", active: " + active;
		if (email != null && !email.trim().isEmpty())
			result += ", email: " + email;
		return result;
	}

	public Set<SystemRole> getRoles() {
		return this.roles;
	}

	public void setRoles(final Set<SystemRole> roles) {
		this.roles = roles;
	}
	

	private void doPrepare(){
		generateUUID();
		encode();
	}

	public void generateUUID(){
		this.setUuid(UUID.randomUUID().toString());		
	}

	public void encode(){

		String rawPasswd = this.password;

		if((rawPasswd != null)){

//			this.setPassword(PassGenerator.generate(rawPasswd));

		}   

	}
}