/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 08.03.19 21:31
 */

package buying.tickets.touch.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import buying.tickets.R;
import buying.tickets.touch.contract.TouchSummaryInterface;
import buying.tickets.touch.presenter.TouchSummaryPresenter;
/**
 * Created by Sebastian Paciorek
 */
public class TouchSummaryActivity extends AppCompatActivity implements TouchSummaryInterface.View {

    private static TouchSummaryActivity touchSummaryActivity;

    private TouchSummaryInterface.Presenter touchSummaryPresenter;

    private TextView nameTextView;
    private TextView priceTextView;
    private Button buyAndPayButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_summary);
        setTitle(getResources().getString(R.string.summary_title));

        touchSummaryActivity = this;

        touchSummaryPresenter = TouchSummaryPresenter.getInstance();
        touchSummaryPresenter.setContext(this);
        touchSummaryPresenter.setView(this);

        setComponents();

        setNameTextView();
        setPriceTextView();
    }

    private void setComponents() {
        nameTextView = findViewById(R.id.summary_nametextView);
        priceTextView = findViewById(R.id.summary_pricetextView);
        buyAndPayButton = findViewById(R.id.buy_and_payButton);
        setBuyAndPayButton();
    }

    public void setNameTextView() {
        nameTextView.setText(touchSummaryPresenter.getTicket().getName());
    }

    public void setPriceTextView() {
        priceTextView.setText(touchSummaryPresenter.getTicket().getPrice());
    }

    private void setBuyAndPayButton() {
        buyAndPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPaymentMethodActivity();
            }
        });
    }

    public void startPaymentMethodActivity() {
        Intent intent = new Intent(this, TouchPaymentMethodActivity.class);
        startActivity(intent);
    }

    public static TouchSummaryActivity getInstance() {
        return touchSummaryActivity;
    }
}
