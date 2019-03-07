/*
 * Created by Sebastian Paciorek on 7.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 07.03.19 00:13
 */

package buying.tickets.touch.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import buying.tickets.R;
/**
 * Created by Sebastian Paciorek
 */
public class TouchBuyTicketActivity extends AppCompatActivity {

    private static TouchBuyTicketActivity touchBuyTicketActivity;

    private Button discountTicketButton;
    private Button normalTicketButton;
    private String kindOfTicket = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_buyticket);
        setTitle(getResources().getString(R.string.buy_ticket_title));

        touchBuyTicketActivity = this;

        setComponents();
    }

    private void setComponents() {
        discountTicketButton = findViewById(R.id.discount_ticketButton);
        normalTicketButton = findViewById(R.id.normal_ticketButton);

        setDiscountTicketButton();
        setNormalTicketButton();
    }

    private void setDiscountTicketButton() {
        discountTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kindOfTicket = "discount";
                startTicketsActivity();
            }
        });
    }

    private void setNormalTicketButton() {
        normalTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kindOfTicket = "normal";
                startTicketsActivity();
            }
        });
    }

    private void startTicketsActivity() {
        Intent intent = new Intent(this, TouchTicketsActivity.class);
        intent.putExtra("ticket", kindOfTicket);
        startActivity(intent);
    }

    public static TouchBuyTicketActivity getInstance() {
        return touchBuyTicketActivity;
    }
}
