package br.com.frmichetti.carhollics.android.model.compatibility;

import android.os.Parcelable;

import java.util.Date;

public abstract class BaseModel implements Parcelable {

    private transient int version;

    private transient Date createdAt;

    private transient Date updatedAt;

    public BaseModel() {}

    public int getVersion() {
        return this.version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    private void onUpdate() {
        this.updatedAt = new Date();
    }

    private void onCreate() {
        this.createdAt = new Date();
    }

}
