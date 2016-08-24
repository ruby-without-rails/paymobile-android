package br.com.frmichetti.carhollics.android.model;

import java.util.HashSet;
import java.util.Set;

public abstract class Person extends MyEntity {

	private String name;

	private long phone;

	private long mobilePhone;

	private User user;

	private Set<Address> addressess = new HashSet<Address>();

	private Set<Vehicle> vehicles = new HashSet<Vehicle>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public long getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(long mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Address> getAddressess() {
		return this.addressess;
	}

	public void setAddressess(final Set<Address> addressess) {
		this.addressess = addressess;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (name != null && !name.trim().isEmpty())
			result += "name: " + name;
		result += ", phone: " + phone;
		result += ", mobilePhone: " + mobilePhone;
		return result;
	}

	public Set<Vehicle> getVehicles() {
		return this.vehicles;
	}

	public void setVehicles(final Set<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

}