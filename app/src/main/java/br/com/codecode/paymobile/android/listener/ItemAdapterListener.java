package br.com.codecode.paymobile.android.listener;

/**
 * Created by felipe on 06/01/18.
 */


import br.com.codecode.paymobile.android.model.compatibility.Product;

/**
 * Callback for Item Selected
 */
public interface ItemAdapterListener {
    void onItemSelected(Product product);
}
