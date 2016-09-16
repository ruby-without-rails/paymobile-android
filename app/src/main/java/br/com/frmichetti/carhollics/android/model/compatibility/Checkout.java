package br.com.frmichetti.carhollics.android.model.compatibility;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Checkout extends BaseModel {

    private Long id;

    private String uuid;

    private Date purchaseDate = new Date();

    private Customer customer;

    private Vehicle vehicle;

    private String address;

    private String shoppingCart;

    private BigDecimal total;

    private CheckoutStatus status;

    public Checkout() {
        this.status = CheckoutStatus.NEW;
    }

    public Checkout(Parcel parcel) {

        if (parcel != null) {

            this.id = parcel.readLong();

            this.uuid = parcel.readString();

            this.purchaseDate = new Date(parcel.readLong());

            this.customer = parcel.readParcelable(Customer.class.getClassLoader());

            this.vehicle = parcel.readParcelable(Vehicle.class.getClassLoader());

            this.address = parcel.readString();

            this.shoppingCart = parcel.readString();

            this.total = new BigDecimal(parcel.readDouble());

            //TODO FIXME read status
            this.status = (CheckoutStatus) parcel.readSerializable();
        }


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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
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

    public CheckoutStatus getStatus() {
        return status;
    }

    public void setStatus(CheckoutStatus status) {
        this.status = status;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        if (id != null) {
            parcel.writeLong(id);
        }

        if (uuid != null) {
            parcel.writeString(uuid);
        }

        if (purchaseDate != null) {
            parcel.writeLong(purchaseDate.getTime());
        }

        if (customer != null) {
            parcel.writeParcelable(customer, i);
        }

        if (vehicle != null) {
            parcel.writeParcelable(vehicle, i + 1);
        }

        if (address != null) {
            parcel.writeString(address);
        }

        if (shoppingCart != null) {
            parcel.writeString(shoppingCart);
        }

        if (total != null) {
            parcel.writeDouble(total.doubleValue());
        }

        if (status != null) {
            parcel.writeSerializable(status);
        }

    }


    public static final Parcelable.Creator<Checkout> CREATOR = new Parcelable.Creator<Checkout>() {

        public Checkout createFromParcel(Parcel parcel) {
            return new Checkout(parcel);
        }

        public Checkout[] newArray(int size) {
            return new Checkout[size];
        }
    };
}