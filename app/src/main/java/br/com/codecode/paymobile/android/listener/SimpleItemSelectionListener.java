package br.com.codecode.paymobile.android.listener;

/**
 * Created by felipe on 06/01/18.
 */


import android.view.MenuItem;

import br.com.codecode.paymobile.android.model.compatibility.Product;

/**
 * Callback for Simple Item Selection
 */
public interface SimpleItemSelectionListener {
    void onDetails(Product product);

    void onShare(Product product, MenuItem menuItem);

    void onGallery(Product product);
}
