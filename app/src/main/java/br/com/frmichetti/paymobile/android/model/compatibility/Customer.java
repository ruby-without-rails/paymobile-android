package br.com.frmichetti.paymobile.android.model.compatibility;

import android.os.Parcel;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Customer extends BaseModel {
    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("cpf")
    private String cpf;
    @SerializedName("fcm_id")
    private String fcmId;
    @SerializedName("fcm_message_token")
    private String fcmMessageToken;
    @SerializedName("email")
    private String email;
    @SerializedName("token")
    private String token;

    public Customer() {
    }

    public Customer(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getLong("id");
            this.cpf = jsonObject.getString("cpf");
            this.fcmId = jsonObject.getString("fcm_id");
            this.email = jsonObject.getString("email");
            this.name = jsonObject.getString("name");
            this.token = jsonObject.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Customer(Parcel parcel) {
        this.id = parcel.readLong();
        this.cpf = parcel.readString();
        this.fcmMessageToken = parcel.readString();
        this.fcmId = parcel.readString();
        this.email = parcel.readString();
        this.name = parcel.readString();
        this.token = parcel.readString();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getFcmId() {
        return fcmId;
    }

    public void setFcmId(String fcmId) {
        this.fcmId = fcmId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (name != null && !name.trim().isEmpty())
            result += "name: " + name;
        result += ", cpf: " + cpf;
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

    public String getFcmMessageToken() {
        return fcmMessageToken;
    }

    public void setFcmMessageToken(String fcmMessageToken) {
        this.fcmMessageToken = fcmMessageToken;
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
    */
}