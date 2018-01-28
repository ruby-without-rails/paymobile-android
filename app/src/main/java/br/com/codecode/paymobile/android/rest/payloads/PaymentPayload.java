package br.com.codecode.paymobile.android.rest.payloads;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

import br.com.codecode.paymobile.android.rest.BaseJson;

/**
 * Created by felipe on 28/01/18.
 */

public class PaymentPayload extends BaseJson {

    @SerializedName("order_id")
    public int orderId;

    @SerializedName("selected_payment")
    public SelectedPaymentPayload selectedPayment;

    @SerializedName("total")
    public BigDecimal total;

    public static class SelectedPaymentPayload extends BaseJson {

        @SerializedName("card_brand")
        public String cardBrand;

        @SerializedName("card_number")
        public String cardNumber;

        @SerializedName("exp_month")
        public int expMonth;

        @SerializedName("exp_year")
        public int expYear;

        @SerializedName("holder_name")
        public String holderName;

        @SerializedName("cvv")
        public String cvv;

        @SerializedName("installments")
        public int installments;
    }
}
