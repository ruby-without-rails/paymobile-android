package br.com.frmichetti.carhollics.android.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Checkout extends MyEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private int version;

	private String uuid;

	private Customer customer;

	private Date purchaseDate;

	private String shoppingCart;

	private BigDecimal total;

	private Address address;

	private Vehicle vehicle;

	public Checkout() {
		this.id = 0L;	
	}

	public Checkout(Customer customer, ShoppingCart shoppingCart) {
		this();
		this.setCustomer(customer);
		this.setShoppingCart(shoppingCart.toJson());
		this.setTotal(shoppingCart.getTotal());
	}	

	public Checkout(Customer customer, ShoppingCart shoppingCart, Address address, Vehicle vehicle) {
		super();
		this.customer = customer;
		this.shoppingCart = shoppingCart.toJson();
		this.address = address;
		this.vehicle = vehicle;
		this.setTotal(shoppingCart.getTotal());
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
		if (!(obj instanceof Checkout)) {
			return false;
		}
		Checkout other = (Checkout) obj;
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(String shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (uuid != null && !uuid.trim().isEmpty())
			result += "uuid: " + uuid;
		if (shoppingCart != null && !shoppingCart.trim().isEmpty())
			result += ", shoppingCart: " + shoppingCart;
		return result;
	}
}