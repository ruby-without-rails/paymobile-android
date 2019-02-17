package br.com.codecode.paymobile.android.rest;

import br.com.codecode.paymobile.android.rest.dto.OrderReceiptDTO;
import br.com.codecode.paymobile.android.rest.dto.PaymentDTO;
import br.com.codecode.paymobile.android.rest.payloads.OrderPayload;
import br.com.codecode.paymobile.android.rest.payloads.PaymentPayload;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by felipe on 28/01/2018.
 */
public interface RestApi {

    @POST("api/order")
    Call<OrderReceiptDTO> createOrder(@Body OrderPayload payload);

    @POST("payments/mundipagg/credit-card")
    Call<PaymentDTO> paywithCreditCard(@Body PaymentPayload payload);
}
