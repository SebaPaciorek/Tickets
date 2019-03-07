/*
 * Created by Sebastian Paciorek on 7.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 07.03.19 11:30
 */

package buying.tickets.speech.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import buying.tickets.R;

/**
 * Created by Sebastian Paciorek
 */
public class SpeechMainActivity extends AppCompatActivity {

    private static SpeechMainActivity speechMainActivity;

    private Button buyTicketButton;
    private Button ticketControlButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_main);
        setTitle(getResources().getString(R.string.tickets_title));
        speechMainActivity = this;

        setComponents();
    }

    private void setComponents() {
        buyTicketButton = findViewById(R.id.speech_main_buy_ticketButton);
        ticketControlButton = findViewById(R.id.speech_main_ticket_controlButton);

        setBuyTicketsButton();
        setTicketControlButtonButton();
    }

    private void setBuyTicketsButton() {
        buyTicketButton.setClickable(false);
    }

    private void setTicketControlButtonButton() {
        ticketControlButton.setClickable(false);
    }

    public static SpeechMainActivity getInstance() {
        return speechMainActivity;
    }
}
