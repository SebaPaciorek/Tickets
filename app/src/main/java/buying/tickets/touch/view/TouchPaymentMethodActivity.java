/*
 * Created by Sebastian Paciorek on 6.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 06.03.19 23:54
 */

package buying.tickets.touch.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;

import buying.tickets.R;
import buying.tickets.gesture.contract.AccelerometerInterface;
/**
 * Created by Sebastian Paciorek
 */
public class TouchPaymentMethodActivity extends AppCompatActivity {

    private static TouchPaymentMethodActivity touchPaymentMethodActivity;

    private View mastercardView;
    private View masterpassView;
    private View visaView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_paymentmethod);
        setTitle(getResources().getString(R.string.payment_method_title));

        touchPaymentMethodActivity = this;

        setComponents();
    }

    private void setComponents() {
        mastercardView = findViewById(R.id.mastercard_method_layout);
        masterpassView = findViewById(R.id.masterpass_method_layout);
        visaView = findViewById(R.id.visa_method_layout);

        setMastercardView();
        setMasterpassView();
        setVisaView();
    }

    private void setMastercardView() {
        mastercardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPaymentActivity();
            }
        });
    }

    private void setMasterpassView() {
        masterpassView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPaymentActivity();
            }
        });
    }

    private void setVisaView() {
        visaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPaymentActivity();
            }
        });
    }

    private void startPaymentActivity() {
        Intent intent = new Intent(this, TouchPaymentActivity.class);
        startActivity(intent);
        finish();
    }

    public static TouchPaymentMethodActivity getInstance() {
        return touchPaymentMethodActivity;
    }

}
