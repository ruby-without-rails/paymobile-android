package br.com.frmichetti.paymobile.android.model.compatibility;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

public class Address extends BaseModel {

    private Long id;

    private Customer customer;

    private long cep;

    private String street;

    private String neighborhood;

    private String complement;

    private String number;

    public Address() {
    }

    public Address(Parcel parcel) {

        if(parcel != null){

            this.id = parcel.readLong();

            this.customer = parcel.readParcelable(Customer.class.getClassLoader());

            this.cep = parcel.readLong();

            this.street = parcel.readString();

            this.neighborhood = parcel.readString();

            this.complement = parcel.readString();

            this.number = parcel.readString();
        }


    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
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

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return cep + " , " + street + " . " + number;
    }

    public String toJson() {

        String json = "";
        try {

            json = new JSONObject()
                    .put("cep", this.cep)
                    .put("street", this.street)
                    .put("neighborhood", this.neighborhood)
                    .put("complement", this.complement)
                    .put("number", this.number).toString();

        } catch (JSONException e) {

            e.printStackTrace();

            json = "[ERROR] - [Could not Create a Json]";

        }

        return json;
    }
/*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        if (id != null) {
            parcel.writeLong(id);
        }

        if (customer != null) {
            parcel.writeParcelable(customer, i);
        }

        parcel.writeLong(cep);

        if (street != null) {
            parcel.writeString(street);
        }

        if (neighborhood != null) {
            parcel.writeString(neighborhood);
        }

        if (complement != null) {
            parcel.writeString(complement);
        }

        if (number != null) {
            parcel.writeString(number);
        }


    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {

        public Address createFromParcel(Parcel parcel) {
            return new Address(parcel);
        }

        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    */
}