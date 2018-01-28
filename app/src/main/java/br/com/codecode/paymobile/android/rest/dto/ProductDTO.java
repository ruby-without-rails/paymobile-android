package br.com.codecode.paymobile.android.rest.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import br.com.codecode.paymobile.android.model.compatibility.Product;
import br.com.codecode.paymobile.android.rest.BaseJson;

/**
 * Created by felipe on 01/01/18.
 */

public class ProductDTO extends BaseJson {

    @SerializedName("products")
    public ArrayList<Product> products;
}
