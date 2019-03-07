/*
 * Created by Sebastian Paciorek on 7.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 07.03.19 00:13
 */

package buying.tickets.touch.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import buying.tickets.R;
import buying.tickets.touch.contract.TouchTicketsControlDetailsInterface;
import buying.tickets.touch.presenter.TouchSummaryPresenter;
import buying.tickets.touch.presenter.TouchTicketControlDetailsPresenter;
/**
 * Created by Sebastian Paciorek
 */
public class TouchTicketControlDetailsActivity extends AppCompatActivity implements TouchTicketsControlDetailsInterface.View {

    private static TouchTicketControlDetailsActivity touchTicketControlDetailsActivity;

    private TouchTicketsControlDetailsInterface.Presenter touchTicketControlDetailsPresenter;

    private TextView nameTextView;
    private TextView priceTextView;
    private TextView dateBoughtTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_ticket_control_details);
        setTitle(getResources().getString(R.string.ticket_control_details));

        touchTicketControlDetailsActivity = this;

        touchTicketControlDetailsPresenter = TouchTicketControlDetailsPresenter.getInstance();
        touchTicketControlDetailsPresenter.setContext(this);
        touchTicketControlDetailsPresenter.setView(this);

        setComponents();

        setNameTextView();
        setPriceTextView();
        setDateBoughtTextView();
    }

    private void setComponents() {
        nameTextView = findViewById(R.id.ticket_control_details_nametextView);
        priceTextView = findViewById(R.id.ticket_control_details_pricetextView);
        dateBoughtTextView = findViewById(R.id.ticket_control_details_date_boughttextView);
    }

    private void setNameTextView() {
        nameTextView.setText(touchTicketControlDetailsPresenter.getTicket().getName());
    }

    private void setPriceTextView() {
        priceTextView.setText(touchTicketControlDetailsPresenter.getTicket().getPrice());
    }

    private void setDateBoughtTextView(){
        dateBoughtTextView.setText(touchTicketControlDetailsPresenter.getTicket().getDate());
    }

    public static TouchTicketControlDetailsActivity getInstance() {
        return touchTicketControlDetailsActivity;
    }
}
