package br.com.codecode.paymobile.android.model.compatibility;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseModel implements Serializable {

    private transient int version;

    private transient Date createdAt;

    private transient Date updatedAt;

    public BaseModel() {
    }

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
