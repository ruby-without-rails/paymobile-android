package br.com.codecode.paymobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.codecode.paymobile.android.rest.RestApi;
import br.com.codecode.paymobile.android.rest.RestApiFactory;
import br.com.codecode.paymobile.android.rest.payloads.OrderPayload;
import br.com.codecode.paymobile.android.rest.payloads.PaymentPayload;

/**
 * Created by felipe on 28/01/18.
 */

class BaseIntrumentedTest {
    protected Context context;
    protected RestApiFactory restApiFactory;
    protected RestApi restApi;

    private String testToken = "Xv0uTRe866nk7DQC3GI5Kgs8qpEUVxES5J25sBb8JqmasTM69DS6CbWTolgytffFpFBhWQyj9o8BGmGatiV3Au1NgjSKT2TedFaQ0O1Pd8W38gjXyv3HuAKWFZgW5L2OhluN5YWmbT92r9KXHtr1aQE6RDlGbK3YpsIf6HElqcQBln1K9rCik5z0nTtdOwnLVfsOGrcSlAWQEMxcqbb8gl5e7cQBG9TZ1Jqrl5Y4re9BXqQ01SBg7EhlIghTGCx";

    @Before
    public void prepare() throws Exception {
        // Context of the app under test.
        context = InstrumentationRegistry.getTargetContext();

        restApiFactory = new RestApiFactory(context, testToken);
        restApi = restApiFactory.create(RestApi.class);
    }

    protected OrderPayload prepareOrderPayload() {
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

        return payload;

    }

    protected PaymentPayload preparePaymentPayload() {
        PaymentPayload.SelectedPaymentPayload selectedPayment = new PaymentPayload.SelectedPaymentPayload();
        selectedPayment.cardBrand = "Visa";
        selectedPayment.cardNumber = "01111111111111111";
        selectedPayment.expMonth = 4;
        selectedPayment.expYear = 2018;
        selectedPayment.holderName = "Felipe";
        selectedPayment.cvv = "123";
        selectedPayment.installments = 1;

        PaymentPayload payload = new PaymentPayload();
        payload.orderId = 6;
        payload.selectedPayment = selectedPayment;
        payload.total = new BigDecimal(100);

        return payload;
    }
}
