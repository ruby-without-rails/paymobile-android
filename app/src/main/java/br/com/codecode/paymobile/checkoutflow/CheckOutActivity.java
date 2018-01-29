package br.com.codecode.paymobile.checkoutflow;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.util.concurrent.ExecutionException;

import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.model.SelectedPaymentData;
import br.com.codecode.paymobile.android.model.compatibility.Order;
import br.com.codecode.paymobile.android.rest.dto.PaymentDTO;
import br.com.codecode.paymobile.android.rest.payloads.PaymentPayload;
import br.com.codecode.paymobile.android.tasks.TaskPayWithCreditCard;
import br.com.codecode.paymobile.android.view.activity.FinishActivity;
import br.com.codecode.paymobile.checkoutflow.CCFragment.CCNameFragment;
import br.com.codecode.paymobile.checkoutflow.CCFragment.CCNumberFragment;
import br.com.codecode.paymobile.checkoutflow.CCFragment.CCSecureCodeFragment;
import br.com.codecode.paymobile.checkoutflow.CCFragment.CCValidityFragment;
import br.com.codecode.paymobile.checkoutflow.Utils.CreditCardUtils;
import br.com.codecode.paymobile.checkoutflow.Utils.ViewPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckOutActivity extends FragmentActivity implements FragmentManager.OnBackStackChangedListener {

    @BindView(R.id.btnNext)
    protected Button btnNext;

    public CardFrontFragment cardFrontFragment;
    public CardBackFragment cardBackFragment;

    //This is our viewPager
    private ViewPager viewPager;

    protected CCNumberFragment numberFragment;
    protected CCNameFragment nameFragment;
    protected CCValidityFragment validityFragment;
    protected CCSecureCodeFragment secureCodeFragment;

    private int total_item;
    private boolean backTrack = false;

    private boolean mShowingBack = false;

    private String cardNumber, cardCVV, cardValidity, cardName;

    private Context context;
    private int cardBrand;
    private Order order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        context = this;
        order = (Order) getIntent().getSerializableExtra("order");

        ButterKnife.bind(this);


        cardFrontFragment = new CardFrontFragment();
        cardBackFragment = new CardBackFragment();

        if (savedInstanceState == null) {
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, cardFrontFragment).commit();

        } else {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }

        getFragmentManager().addOnBackStackChangedListener(this);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(4);
        setupViewPager(viewPager);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == total_item)
                    btnNext.setText("SUBMIT");
                else
                    btnNext.setText("NEXT");

                Log.d("track", "onPageSelected: " + position);

                if (position == total_item) {
                    flipCard();
                    backTrack = true;
                } else if (position == total_item - 1 && backTrack) {
                    flipCard();
                    backTrack = false;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = viewPager.getCurrentItem();
                if (pos < total_item) {
                    viewPager.setCurrentItem(pos + 1);
                } else {
                    checkEntries();
                }

            }
        });


    }

    public void checkEntries() {
        cardName = nameFragment.getName();
        cardNumber = numberFragment.getCardNumber();
        cardValidity = validityFragment.getValidity();
        cardCVV = secureCodeFragment.getValue();
        cardBrand = CreditCardUtils.getCardType(cardNumber);

        if (TextUtils.isEmpty(cardName)) {
            Toast.makeText(context, R.string.enter_valid_cc_name, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cardNumber) || !CreditCardUtils.isValid(cardNumber.replace(" ", ""))) {
            Toast.makeText(context, R.string.enter_valid_cc_number, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cardValidity) || !CreditCardUtils.isValidDate(cardValidity)) {
            Toast.makeText(context, R.string.enter_valid_cc_validity, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cardCVV) || cardCVV.length() < 3) {
            Toast.makeText(context, R.string.enter_valid_cc_cvv, Toast.LENGTH_SHORT).show();
        } else {
            SelectedPaymentData paymentData = new SelectedPaymentData(cardName, cardNumber, cardValidity, cardCVV, cardBrand);

            try {

                PaymentDTO paymentDTO = new TaskPayWithCreditCard(context, order).execute(paymentData).get();
                if (paymentDTO.success) {
                    startActivity(new Intent(context, FinishActivity.class).putExtra("payment_status", paymentDTO));
                    finish();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onBackStackChanged() {
        mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        numberFragment = new CCNumberFragment();
        nameFragment = new CCNameFragment();
        validityFragment = new CCValidityFragment();
        secureCodeFragment = new CCSecureCodeFragment();
        adapter.addFragment(numberFragment);
        adapter.addFragment(nameFragment);
        adapter.addFragment(validityFragment);
        adapter.addFragment(secureCodeFragment);

        total_item = adapter.getCount() - 1;
        viewPager.setAdapter(adapter);

    }

    private void flipCard() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }
        // Flip to the back.
        // setCustomAnimations(int enter, int exit, int popEnter, int popExit)

        mShowingBack = true;

        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)
                .replace(R.id.fragment_container, cardBackFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        int pos = viewPager.getCurrentItem();
        if (pos > 0) {
            viewPager.setCurrentItem(pos - 1);
        } else
            super.onBackPressed();
    }

    public void nextClick() {
        btnNext.performClick();
    }
}
