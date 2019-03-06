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
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import buying.tickets.R;
import buying.tickets.touch.contract.TouchTicketsInterface;
import buying.tickets.touch.model.Ticket;
import buying.tickets.touch.presenter.TicketsRecyclerViewAdapter;
import buying.tickets.touch.presenter.TouchSummaryPresenter;
import buying.tickets.touch.presenter.TouchTicketsPresenter;
/**
 * Created by Sebastian Paciorek
 */
public class TouchTicketsActivity extends AppCompatActivity implements TouchTicketsInterface.View {

    private static TouchTicketsActivity touchTicketsActivity;

    private TouchTicketsInterface.Presenter touchTicketsPresenter;

    private RecyclerView recyclerView;
    private TicketsRecyclerViewAdapter ticketsRecyclerViewAdapter;

    private LinearLayoutManager recyclerLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private String kindOfTicket = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_tickets);

        touchTicketsActivity = this;

        touchTicketsPresenter = TouchTicketsPresenter.getInstance();
        touchTicketsPresenter.setContext(this);
        touchTicketsPresenter.setView(this);

        setComponents();

        setLayoutManager();
        setDividerItemDecoration();

        getExtras();
        setContent();

        showTickets();
    }

    private void setComponents() {
        recyclerView = findViewById(R.id.discount_tickets_recyclerview);
    }

    private void setLayoutManager() {
        recyclerLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerLayoutManager);
    }

    private void setDividerItemDecoration() {
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), recyclerLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void getExtras() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            kindOfTicket = bundle.getString("ticket");
        }
    }

    private void setContent() {
        if (kindOfTicket != null) {
            if (kindOfTicket.equals("discount")) {
                touchTicketsPresenter.setDiscountTicketList();
                setTitle(getResources().getString(R.string.discount_ticket_title));
            } else {
                touchTicketsPresenter.setNormalTicketList();
                setTitle(getResources().getString(R.string.normal_ticket_title));
            }
        }
    }

    private void showTickets() {
        List<Ticket> ticketList = touchTicketsPresenter.getTicketList();

        if (ticketList.size() > 0) {
            ticketsRecyclerViewAdapter = new TicketsRecyclerViewAdapter(ticketList, "touch");
        } else {
            ticketsRecyclerViewAdapter = new TicketsRecyclerViewAdapter(new ArrayList<>(), "touch");
        }

        recyclerView.setAdapter(ticketsRecyclerViewAdapter);
        ticketsRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void startSummaryActivity(Ticket ticket) {
        TouchSummaryPresenter.getInstance().setTicket(ticket);
        Intent intent = new Intent(this, TouchSummaryActivity.class);
        startActivity(intent);
    }

    public static TouchTicketsActivity getInstance() {
        return touchTicketsActivity;
    }


}
