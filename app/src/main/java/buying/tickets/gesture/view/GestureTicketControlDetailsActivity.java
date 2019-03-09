/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 08.03.19 21:31
 */

package buying.tickets.gesture.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import buying.tickets.R;
import buying.tickets.gesture.contract.GestureTicketsControlDetailsInterface;
import buying.tickets.gesture.presenter.GestureTicketControlDetailsPresenter;
/**
 * Created by Sebastian Paciorek
 */
public class GestureTicketControlDetailsActivity extends AppCompatActivity implements GestureTicketsControlDetailsInterface.View {

    private static GestureTicketControlDetailsActivity gestureTicketControlDetailsActivity;

    private GestureTicketsControlDetailsInterface.Presenter gestureTicketControlDetailsPresenter;

    private TextView nameTextView;
    private TextView priceTextView;
    private TextView dateBoughtTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_ticket_control_details);
        setTitle(getResources().getString(R.string.ticket_control_details));

        gestureTicketControlDetailsActivity = this;

        gestureTicketControlDetailsPresenter = GestureTicketControlDetailsPresenter.getInstance();
        gestureTicketControlDetailsPresenter.setContext(this);
        gestureTicketControlDetailsPresenter.setView(this);

        setComponents();

        setNameTextView();
        setPriceTextView();
        setDateBoughtTextView();
    }

    private void setComponents() {
        nameTextView = findViewById(R.id.gesture_ticket_control_details_nametextView);
        priceTextView = findViewById(R.id.gesture_ticket_control_details_pricetextView);
        dateBoughtTextView = findViewById(R.id.gesture_ticket_control_details_date_boughttextView);
    }

    private void setNameTextView() {
        nameTextView.setText(gestureTicketControlDetailsPresenter.getTicket().getName());
    }

    private void setPriceTextView() {
        priceTextView.setText(gestureTicketControlDetailsPresenter.getTicket().getPrice());
    }

    private void setDateBoughtTextView() {
        dateBoughtTextView.setText(gestureTicketControlDetailsPresenter.getTicket().getDate());
    }

    public static GestureTicketControlDetailsActivity getInstance() {
        return gestureTicketControlDetailsActivity;
    }
}
