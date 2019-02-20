package br.com.codecode.paymobile.android.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.model.IntentKeys;
import br.com.codecode.paymobile.android.model.ShoppingCart;
import br.com.codecode.paymobile.android.rest.dto.PaymentDTO;

import static br.com.codecode.paymobile.android.model.IntentKeys.PAYMENT_STATUS_BUNDLE_KEY;
import static br.com.codecode.paymobile.android.model.IntentKeys.SHOPPING_CART_BUNDLE_KEY;

public class FinishActivity extends AppCompatActivity {
    private LinearLayout l1, l2;
    private Button btnsub;
    private Animation uptodown, downtoup;
    private TextView textViewTotal, textViewOrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PaymentDTO paymentDTO = (PaymentDTO) getIntent().getSerializableExtra(PAYMENT_STATUS_BUNDLE_KEY);

        setContentView(R.layout.finish_activity);
        textViewTotal = findViewById(R.id.tv_total);
        textViewOrderId = findViewById(R.id.tv_order_id);
        textViewTotal.setText(String.format("%10.2f", paymentDTO.orderTotal.divideToIntegralValue(new BigDecimal(100))));
        textViewOrderId.setText(textViewOrderId.getText() + " " + paymentDTO.orderResult.orderReference);
        btnsub = findViewById(R.id.buttonsub);
        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);

        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra(SHOPPING_CART_BUNDLE_KEY, new ShoppingCart()));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
