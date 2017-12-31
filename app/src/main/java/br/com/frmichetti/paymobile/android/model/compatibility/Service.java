package br.com.frmichetti.paymobile.android.model.compatibility;

import android.os.Parcel;

import java.math.BigDecimal;

public class Service extends BaseModel {

    private Long id;

    private String title;

    private String description;

    private String observation;

    private int duration;

    private BigDecimal price;

    private transient byte[] thumbnail;

    public Service() {
    }

    public Service(Parcel parcel) {

        if (parcel != null) {

            this.id = parcel.readLong();

            this.title = parcel.readString();

            this.description = parcel.readString();

            this.observation = parcel.readString();

            this.duration = parcel.readInt();

            this.price = new BigDecimal(parcel.readDouble());

            //TODO FIXME I DONT KNOW How TO READ THIS
            //this.thumbnail = parcel.readByteArray(thumbnail);

        }


    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Service)) {
            return false;
        }
        Service other = (Service) obj;
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

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return title;
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

        if (title != null) {
            parcel.writeString(title);
        }

        if (description != null) {
            parcel.writeString(description);
        }


        if (observation != null) {
            parcel.writeString(observation);
        }

        parcel.writeInt(duration);


        if (price != null) {
            parcel.writeDouble(price.doubleValue());
        }

       // parcel.writeByteArray(thumbnail);

    }

    public static final Parcelable.Creator<Service> CREATOR = new Parcelable.Creator<Service>() {

        public Service createFromParcel(Parcel parcel) {
            return new Service(parcel);
        }

        public Service[] newArray(int size) {
            return new Service[size];
        }
    };
    */
}