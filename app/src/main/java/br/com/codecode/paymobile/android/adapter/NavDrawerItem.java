/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.codecode.paymobile.android.adapter;

import android.graphics.drawable.Drawable;

public class NavDrawerItem {

    private boolean showNotify;

    private String title;

    private Drawable resourceId;

    public NavDrawerItem() {
    }

    public NavDrawerItem(boolean showNotify, String title, Drawable resourceId) {
        this.showNotify = showNotify;
        this.title = title;
        this.resourceId = resourceId;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getResourceId() {
        return resourceId;
    }

    public void setResourceId(Drawable resourceId) {
        this.resourceId = resourceId;
    }
}
