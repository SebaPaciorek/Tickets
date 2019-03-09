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

import buying.tickets.R;
/**
 * Created by Sebastian Paciorek
 */
public class TouchMainActivity extends AppCompatActivity {

    private Button buyTicketButton;
    private Button ticketControlButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_main);
        setTitle(getResources().getString(R.string.tickets_title));

        setComponents();
    }

    private void setComponents() {
        buyTicketButton = findViewById(R.id.buy_ticketButton);
        ticketControlButton = findViewById(R.id.ticket_controlButton);

        setBuyTicketButton();
        setTicketControlButton();
    }

    private void setBuyTicketButton() {
        buyTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBuyTicketActivity();
            }
        });
    }

    private void setTicketControlButton() {
        ticketControlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBTicketControlActivity();
            }
        });
    }

    private void startBuyTicketActivity() {
        Intent intent = new Intent(this, TouchBuyTicketActivity.class);
        startActivity(intent);
    }

    private void startBTicketControlActivity() {
        Intent intent = new Intent(this, TouchTicketControlActivity.class);
        startActivity(intent);
    }


}
