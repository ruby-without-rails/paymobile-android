package br.com.codecode.paymobile.android.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import br.com.codecode.paymobile.android.model.compatibility.Product;

/**
 * Created by felipe on 01/01/18.
 */

public class ProductDTO {

    @SerializedName("products")
    public ArrayList<Product> products;
}