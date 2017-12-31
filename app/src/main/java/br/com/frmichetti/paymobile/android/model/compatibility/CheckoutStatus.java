package br.com.frmichetti.paymobile.android.model.compatibility;

import java.io.Serializable;

public enum CheckoutStatus implements Serializable {

    NEW, ANALYSING, INQUEUE, COMPLETE;

/*
    public static final Creator<CheckoutStatus> CREATOR = new Creator<CheckoutStatus>() {
        @Override
        public CheckoutStatus createFromParcel(Parcel in) {
            return CheckoutStatus.values()[in.readInt()];
        }

        @Override
        public CheckoutStatus[] newArray(int size) {
            return new CheckoutStatus[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(ordinal());
    }
*/

}
