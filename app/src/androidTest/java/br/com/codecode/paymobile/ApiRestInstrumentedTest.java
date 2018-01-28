package br.com.codecode.paymobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.codecode.paymobile.android.dto.OrderDTO;
import br.com.codecode.paymobile.android.rest.RestApi;
import br.com.codecode.paymobile.android.rest.RestApiFactory;
import br.com.codecode.paymobile.android.rest.payloads.OrderPayload;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApiRestInstrumentedTest {
    private Context context;
    private RestApiFactory restApiFactory;
    private RestApi restApi;

    @Before
    public void prepare() throws Exception {
        // Context of the app under test.
        context = InstrumentationRegistry.getTargetContext();

        restApiFactory = new RestApiFactory(context, "Xv0uTRe866nk7DQC3GI5Kgs8qpEUVxES5J25sBb8JqmasTM69DS6CbWTolgytffFpFBhWQyj9o8BGmGatiV3Au1NgjSKT2TedFaQ0O1Pd8W38gjXyv3HuAKWFZgW5L2OhluN5YWmbT92r9KXHtr1aQE6RDlGbK3YpsIf6HElqcQBln1K9rCik5z0nTtdOwnLVfsOGrcSlAWQEMxcqbb8gl5e7cQBG9TZ1Jqrl5Y4re9BXqQ01SBg7EhlIghTGCx");
        restApi = restApiFactory.create(RestApi.class);
    }

    @Test
    public void useAppContext() throws Exception {
        assertNotNull(context);
        assertEquals("br.com.frmichetti.paymobile.android", context.getPackageName());

        assertNotNull(restApiFactory);
        assertNotNull(restApi);
    }


    @Test
    public void createAnOrder() throws Exception {

        OrderPayload.PayloadProduct payProduct = new OrderPayload.PayloadProduct();

        payProduct.description = "teste desc";
        payProduct.name = "Teste";
        payProduct.quantity = 1;
        payProduct.value = new BigDecimal(50);

        List<OrderPayload.PayloadProduct> products = new ArrayList<>();
        products.add(payProduct);

        OrderPayload.PayloadCart payCart = new OrderPayload.PayloadCart();
        payCart.products = products;

        OrderPayload payload = new OrderPayload();
        payload.discount = BigDecimal.ZERO;
        payload.total = new BigDecimal(100);
        payload.cart = payCart;

        Call<OrderDTO> responseBodyCall = restApi.createOrder(payload);
        Response<OrderDTO> bodyResponse = responseBodyCall.execute();
        assertEquals(200, bodyResponse.code());

    }
}
