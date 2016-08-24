package br.com.frmichetti.carhollics.android.model;

import java.io.Serializable;

public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private int version;

	private long cep;

	private String street;

	private String neighborhood;

	private String number;

	private String complement;

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
		if (!(obj instanceof Address)) {
			return false;
		}
		Address other = (Address) obj;
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

	public long getCep() {
		return cep;
	}

	public void setCep(long cep) {
		this.cep = cep;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		result += "cep: " + cep;
		if (street != null && !street.trim().isEmpty())
			result += ", street: " + street;
		if (neighborhood != null && !neighborhood.trim().isEmpty())
			result += ", neighborhood: " + neighborhood;
		if (number != null && !number.trim().isEmpty())
			result += ", number: " + number;
		if (complement != null && !complement.trim().isEmpty())
			result += ", complement: " + complement;
		return result;
	}
}