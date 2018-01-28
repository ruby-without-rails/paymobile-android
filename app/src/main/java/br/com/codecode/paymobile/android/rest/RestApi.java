package br.com.codecode.paymobile.android.rest;

import br.com.codecode.paymobile.android.dto.OrderDTO;
import br.com.codecode.paymobile.android.rest.payloads.OrderPayload;
import br.com.codecode.paymobile.android.rest.payloads.PaymentPayload;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by felipe on 28/01/2018.
 */
public interface RestApi {

    @POST("api/order")
    Call<OrderDTO> createOrder(@Body OrderPayload payload);

    @POST("api/payments/mundipagg/credit-card")
    Call<ResponseBody> paywithCreditCard(@Body PaymentPayload payload);
}
