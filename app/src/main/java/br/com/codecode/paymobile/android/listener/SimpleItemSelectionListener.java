package br.com.codecode.paymobile.android.listener;

/**
 * Created by felipe on 06/01/18.
 */


import br.com.codecode.paymobile.android.model.compatibility.Product;

/**
 * Callback for Simple Item Selection
 */
public interface SimpleItemSelectionListener {
    void onDownload(Product product);

    void onShare(Product product);

    void onRename(Product product);

    void onDelete(Product product);
}
