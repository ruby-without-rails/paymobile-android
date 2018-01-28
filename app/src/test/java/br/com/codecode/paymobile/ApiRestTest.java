package br.com.codecode.paymobile;

import org.junit.Test;

import br.com.codecode.paymobile.android.rest.RestApi;
import br.com.codecode.paymobile.android.rest.RestApiFactory;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ApiRestTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_api() throws Exception {
        RestApiFactory restApiFactory = new RestApiFactory();
        RestApi restApi = restApiFactory.create(RestApi.class);

        //restApi.paywithCreditCard("");

    }
}