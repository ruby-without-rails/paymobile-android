package br.com.codecode.paymobile.android.rest.payloads;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.List;

import br.com.codecode.paymobile.android.rest.BaseJson;

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

    public static class PayloadCart extends BaseJson {

        @SerializedName("products")
        public List<PayloadProduct> products;

    }

    public static class PayloadProduct extends BaseJson {

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
