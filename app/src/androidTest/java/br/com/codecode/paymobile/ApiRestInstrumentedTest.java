package br.com.codecode.paymobile;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.codecode.paymobile.android.rest.dto.OrderDTO;
import br.com.codecode.paymobile.android.rest.dto.PaymentDTO;
import br.com.codecode.paymobile.android.rest.payloads.OrderPayload;
import br.com.codecode.paymobile.android.rest.payloads.PaymentPayload;
import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApiRestInstrumentedTest extends BaseIntrumentedTest {


    @Test
    public void useAppContext() throws Exception {
        assertNotNull(context);
        assertEquals("br.com.frmichetti.paymobile.android", context.getPackageName());

        assertNotNull(restApiFactory);
        assertNotNull(restApi);
    }


    @Test
    public void createAnOrder() throws Exception {

        OrderPayload payload = prepareOrderPayload();

        Call<OrderDTO> responseBodyCall = restApi.createOrder(payload);
        Response<OrderDTO> bodyResponse = responseBodyCall.execute();
        assertEquals(200, bodyResponse.code());

    }

    @Test
    public void makeAPayment() throws Exception {

        PaymentPayload payload = preparePaymentPayload();

        Call<PaymentDTO> responseBodyCall = restApi.paywithCreditCard(payload);
        Response<PaymentDTO> bodyResponse = responseBodyCall.execute();
        assertEquals(200, bodyResponse.code());

    }
}
