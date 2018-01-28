package br.com.codecode.paymobile.android.rest.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import br.com.codecode.paymobile.android.rest.BaseJson;

/**
 * Created by felipe on 28/01/18.
 */

public class PaymentDTO extends BaseJson {

    @SerializedName("authorization_code")
    public String authorizationCode;

    @SerializedName("success")
    public boolean success;

    @SerializedName("order_result")
    public OrderResultDTO orderResult;

    public static class OrderResultDTO extends BaseJson {

        // @SerializedName("CreateDate")
        public Date createDate;

        @SerializedName("OrderKey")
        public String orderKey;

        @SerializedName("OrderReference")
        public String orderReference;
    }
}
