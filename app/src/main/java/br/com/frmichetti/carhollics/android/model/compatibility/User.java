/**
 *
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 * */
package br.com.frmichetti.carhollics.android.model.compatibility;

import java.util.UUID;

public class User extends BaseModel {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String uuid;

	private boolean active;

	private String email;

	private String firebaseUUID;

	private String firebaseMessageToken;
	
	public User() {}
	
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}	

	public void generateUUID() {
		this.setUuid(UUID.randomUUID().toString());
	}	

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (email != null && !email.trim().isEmpty())
			result += "email: " + email;
		if (uuid != null && !uuid.trim().isEmpty())
			result += ", uuid: " + uuid;
		if (firebaseUUID != null && !firebaseUUID.trim().isEmpty())
			result += ", firebaseUUID: " + firebaseUUID;
		if (firebaseMessageToken != null
				&& !firebaseMessageToken.trim().isEmpty())
			result += ", firebaseMessageToken: " + firebaseMessageToken;
		result += ", active: " + active;
		return result;
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
	



}