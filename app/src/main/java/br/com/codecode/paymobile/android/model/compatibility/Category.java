package br.com.codecode.paymobile.android.model.compatibility;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class Category extends BaseModel {

    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    public Category() {
    }

    public Category(Parcel parcel) {

        if (parcel != null) {

            this.id = parcel.readLong();

            this.name = parcel.readString();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (name != null && !name.trim().isEmpty())
            result += "name: " + name;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Category)) {
            return false;
        }
        Category other = (Category) obj;
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

        if (name != null) {
            parcel.writeString(name);
        }
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {

        public Category createFromParcel(Parcel parcel) {
            return new Category(parcel);
        }

        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
    */
}