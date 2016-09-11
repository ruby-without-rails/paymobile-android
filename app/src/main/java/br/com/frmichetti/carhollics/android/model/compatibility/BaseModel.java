package br.com.frmichetti.carhollics.android.model.compatibility;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private int version;

    private Date createdAt;

    private Date updatedAt;

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
