package br.com.frmichetti.carhollics.android.model.compatibility;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Customer extends BaseModel {

    private Long id;

    private User user;

    private String name;

    private long cpf;

    private long phone;

    private long mobilePhone;

    private ArrayList<Vehicle> vehicles = new ArrayList<>();

    public Customer() {
    }

    public Customer(Parcel parcel) {

        if (parcel != null) {

            this.id = parcel.readLong();

            this.user = parcel.readParcelable(User.class.getClassLoader());

            this.name = parcel.readString();

            this.cpf = parcel.readLong();

            this.phone = parcel.readLong();

            this.mobilePhone = parcel.readLong();

            parcel.readTypedList(this.vehicles, Vehicle.CREATOR);
        }


    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCpf() {
        return cpf;
    }

    public void setCpf(long cpf) {
        this.cpf = cpf;
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

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (name != null && !name.trim().isEmpty())
            result += "name: " + name;
        result += ", cpf: " + cpf;
        result += ", phone: " + phone;
        result += ", mobilePhone: " + mobilePhone;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) obj;
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

    public ArrayList<Vehicle> getVehicles() {
        return this.vehicles;
    }

    public void setVehicles(final ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
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

        if (user != null) {
            parcel.writeParcelable(user, i);
        }

        if (name != null) {
            parcel.writeString(name);
        }

        parcel.writeLong(cpf);

        parcel.writeLong(phone);

        parcel.writeLong(mobilePhone);

        if (vehicles != null) {
            parcel.writeTypedList(vehicles);
        }


    }

    public static final Parcelable.Creator<Customer> CREATOR = new Parcelable.Creator<Customer>() {

        public Customer createFromParcel(Parcel parcel) {
            return new Customer(parcel);
        }

        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };
}