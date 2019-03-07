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
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import buying.tickets.R;
import buying.tickets.touch.contract.TouchTicketControlInterface;
import buying.tickets.touch.model.Ticket;
import buying.tickets.touch.presenter.BoughtTicketRecyclerViewAdapter;
import buying.tickets.touch.presenter.TouchTicketControlDetailsPresenter;
import buying.tickets.touch.presenter.TouchTicketControlPresenter;
/**
 * Created by Sebastian Paciorek
 */
public class TouchTicketControlActivity extends AppCompatActivity implements TouchTicketControlInterface.View {

    private static TouchTicketControlActivity touchTicketControlActivity;

    private TouchTicketControlInterface.Presenter touchTicketControlPresenter;

    private RecyclerView boughtTicketsRecyclerView;
    private BoughtTicketRecyclerViewAdapter boughtTicketRecyclerViewAdapter;

    private LinearLayoutManager recyclerLayoutManager;
    private DividerItemDecoration dividerItemDecoration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_ticketcontrol);
        setTitle(getResources().getString(R.string.ticket_control_title));

        touchTicketControlActivity = this;

        touchTicketControlPresenter = TouchTicketControlPresenter.getInstance();
        touchTicketControlPresenter.setContext(this);
        touchTicketControlPresenter.setView(this);

        setComponents();
        setLayoutManager();
        setDividerItemDecoration();

        showTickets();
    }

    private void setComponents() {
        boughtTicketsRecyclerView = findViewById(R.id.bought_ticketsRecyclerView);
    }

    private void setLayoutManager() {
        recyclerLayoutManager = new LinearLayoutManager(this);
        boughtTicketsRecyclerView.setLayoutManager(recyclerLayoutManager);
    }

    private void setDividerItemDecoration() {
        dividerItemDecoration = new DividerItemDecoration(boughtTicketsRecyclerView.getContext(), recyclerLayoutManager.getOrientation());
        boughtTicketsRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void showTickets() {
        List<Ticket> ticketList = touchTicketControlPresenter.getBoughtTicketsList();

        if (ticketList != null) {
            if (ticketList.size() > 0) {
                boughtTicketRecyclerViewAdapter = new BoughtTicketRecyclerViewAdapter(ticketList, "touch");
            } else {
                boughtTicketRecyclerViewAdapter = new BoughtTicketRecyclerViewAdapter(new ArrayList<>(), "touch");
            }
        } else {
            boughtTicketRecyclerViewAdapter = new BoughtTicketRecyclerViewAdapter(new ArrayList<>(), "touch");
        }

        boughtTicketsRecyclerView.setAdapter(boughtTicketRecyclerViewAdapter);
        boughtTicketRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void startTicketControlDetailsActivity(Ticket ticket) {
        TouchTicketControlDetailsPresenter.getInstance().setTicket(ticket);
        Intent intent = new Intent(this, TouchTicketControlDetailsActivity.class);
        startActivity(intent);
    }

    public static TouchTicketControlActivity getInstance() {
        return touchTicketControlActivity;
    }
}
