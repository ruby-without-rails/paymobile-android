package br.com.frmichetti.carhollics.android.model.compatibility;

import android.os.Parcel;
import android.os.Parcelable;

public class Category extends BaseModel {

    private Long id;

    private String description;

    public Category() {
    }

    public Category(Parcel parcel) {

        if (parcel != null) {

            this.id = parcel.readLong();

            this.description = parcel.readString();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (description != null && !description.trim().isEmpty())
            result += "description: " + description;
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

        if (description != null) {
            parcel.writeString(description);
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