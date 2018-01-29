package br.com.codecode.paymobile.android.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;

import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.rest.dto.PaymentDTO;

public class FinishActivity extends AppCompatActivity {
    private LinearLayout l1, l2;
    private Button btnsub;
    private Animation uptodown, downtoup;
    private TextView textViewTotal, textViewOrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PaymentDTO paymentDTO = (PaymentDTO) getIntent().getSerializableExtra("payment_status");

        setContentView(R.layout.finish_activity);
        textViewTotal = findViewById(R.id.tv_total);
        textViewOrderId = findViewById(R.id.tv_order_id);
        textViewTotal.setText(String.valueOf(paymentDTO.orderResult.orderTotal));
        textViewOrderId.setText(textViewOrderId.getText() + " " + paymentDTO.orderResult.orderReference);
        btnsub = findViewById(R.id.buttonsub);
        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);
    }
}
