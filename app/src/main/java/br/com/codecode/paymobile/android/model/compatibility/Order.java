package br.com.codecode.paymobile.android.model.compatibility;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by felipe on 28/01/18.
 */

public class Order implements Serializable {

    @SerializedName("id")
    public int id;

    // @SerializedName("created_at")
    public Date createdAt;

    @SerializedName("json_cart")
    public JsonCart jsonCart;

    @SerializedName("discount")
    public BigDecimal discount;

    @SerializedName("total")
    public BigDecimal total;

    @SerializedName("customer_id")
    public int customerId;

    @SerializedName("canceled_at")
    public Date canceledAt;


    public class JsonCart implements Serializable {

        @SerializedName("products")
        public List<JsonProduct> products;

    }

    public class JsonProduct implements Serializable {

        @SerializedName("name")
        public String name;

        @SerializedName("description")
        public String description;

        @SerializedName("value")
        public BigDecimal value;

        @SerializedName("total")
        public int total;

        @SerializedName("quantity")
        public int quantity;

    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", discount=" + discount +
                ", total=" + total +
                '}';
    }
}


