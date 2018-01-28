package br.com.codecode.paymobile.android.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import br.com.codecode.paymobile.android.MyApplication;
import br.com.codecode.paymobile.android.model.SelectedPaymentData;
import br.com.codecode.paymobile.android.model.compatibility.Order;
import br.com.codecode.paymobile.android.rest.RestApi;
import br.com.codecode.paymobile.android.rest.RestApiFactory;
import br.com.codecode.paymobile.android.rest.dto.PaymentDTO;
import br.com.codecode.paymobile.android.rest.payloads.PaymentPayload;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by felipe on 28/01/18.
 */

public class TaskPayWithCreditCard extends AsyncTask<SelectedPaymentData, String, PaymentDTO> {
    private Order order;
    private RestApiFactory restApiFactory;
    private RestApi restApi;
    private ProgressDialog dialog;
    private Context context;
    private PaymentDTO paymentDTO;


    private TaskPayWithCreditCard() {
        Log.d("DEBUG-TASK", "create TaskSaveCustomer");
    }

    public TaskPayWithCreditCard(Context context, Order order) {
        this();
        this.order = order;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        restApiFactory = new RestApiFactory(context, MyApplication.getSessionToken().getKey());
        restApi = restApiFactory.create(RestApi.class);

        dialog = new ProgressDialog(context);

        dialog.setTitle("Processando");

        dialog.setIndeterminate(true);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setMessage("Iniciando a Tarefa de Pagamento");

        dialog.show();

    }

    @Override
    protected PaymentDTO doInBackground(SelectedPaymentData... selectedPaymentData) {

        PaymentPayload payload = preparePaymentPayload(selectedPaymentData[0], order);

        Call<PaymentDTO> responseBodyCall = restApi.paywithCreditCard(payload);
        Response<PaymentDTO> bodyResponse = null;
        try {
            bodyResponse = responseBodyCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        paymentDTO = bodyResponse.body();

        return paymentDTO;
    }

    protected PaymentPayload preparePaymentPayload(SelectedPaymentData paymentData, Order order) {
        PaymentPayload.SelectedPaymentPayload selectedPayment = new PaymentPayload.SelectedPaymentPayload();
        selectedPayment.cardBrand = paymentData.getCardBrand();
        selectedPayment.cardNumber = paymentData.getCardNumber();
        selectedPayment.expMonth = paymentData.getCardValidityMonth();
        selectedPayment.expYear = paymentData.getCardValidityYear();
        selectedPayment.holderName = paymentData.getCardHolderName();
        selectedPayment.cvv = paymentData.getCardCvv();

        // TODO fixed installments
        selectedPayment.installments = 1;

        PaymentPayload payload = new PaymentPayload();
        payload.orderId = order.id;
        payload.selectedPayment = selectedPayment;
        payload.total = order.total;

        return payload;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        dialog.setMessage(String.valueOf(values[0]));
    }

    @Override
    protected void onPostExecute(PaymentDTO result) {
        dialog.setMessage("Tarefa Finalizada!");

        dialog.dismiss();
    }
}
