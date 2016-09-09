package br.com.frmichetti.carhollics.android.model.compatibility;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Checkout extends BaseModel {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String uuid;

    private Date purchaseDate = new Date();

    private Customer customer;

    private Vehicle vehicle;

    private Address address;

    private BigDecimal total;

    private String shoppingCart;

    public Checkout() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(String shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    private void generateUUID() {
        this.setUuid(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (uuid != null && !uuid.trim().isEmpty())
            result += "uuid: " + uuid;
        return result;
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


}