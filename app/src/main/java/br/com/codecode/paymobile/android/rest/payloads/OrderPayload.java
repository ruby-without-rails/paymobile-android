package br.com.codecode.paymobile.android.rest.payloads;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by felipe on 28/01/18.
 */

public class OrderPayload extends BaseJson {

    @SerializedName("discount")
    public BigDecimal discount;

    @SerializedName("total")
    public BigDecimal total;

    @SerializedName("cart")
    public PayloadCart cart;

    public static class PayloadCart implements Serializable {

        @SerializedName("products")
        public List<PayloadProduct> products;

    }

    public static class PayloadProduct implements Serializable {

        @SerializedName("name")
        public String name;

        @SerializedName("description")
        public String description;

        @SerializedName("value")
        public BigDecimal value;

        @SerializedName("quantity")
        public int quantity;

    }
}
