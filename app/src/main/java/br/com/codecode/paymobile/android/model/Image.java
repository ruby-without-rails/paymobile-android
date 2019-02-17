package br.com.codecode.paymobile.android.model;

import android.content.Context;

import br.com.codecode.paymobile.android.R;

public class Image {
    private Image(){}
    public Image(Context context){
        this.context = context;
    }
    private Context context;
    private String small;
    private String medium;
    private String large;
    private String name;
    private String timestamp;

    public String getMedium() {
        return medium.replace("{{host}}", context.getResources().getString(R.string.server));
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getSmall() {
        return small.replace("{{host}}", context.getResources().getString(R.string.server));
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return large.replace("{{host}}", context.getResources().getString(R.string.server));
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
