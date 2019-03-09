/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 09.03.19 12:35
 */

package buying.tickets.touch.presenter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import buying.tickets.R;
import buying.tickets.gesture.presenter.GestureTicketControlPresenter;
import buying.tickets.touch.model.Ticket;
import buying.tickets.touch.view.TouchTicketControlActivity;
/**
 * Created by Sebastian Paciorek
 */
public class BoughtTicketRecyclerViewAdapter extends RecyclerView.Adapter<BoughtTicketRecyclerViewAdapter.ViewHolder>{

    private List<Ticket> ticketList;
    private BoughtTicketRecyclerViewAdapter.ViewHolder holder;

    private String method = "touch";

    public BoughtTicketRecyclerViewAdapter(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public BoughtTicketRecyclerViewAdapter(List<Ticket> ticketList, String method) {
        this.ticketList = ticketList;
        this.method = method;
    }

    @NonNull
    @Override
    public BoughtTicketRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bought_ticket_item_recycler_view, parent, false);
        BoughtTicketRecyclerViewAdapter.ViewHolder viewHolder = new BoughtTicketRecyclerViewAdapter.ViewHolder(view);
        holder = viewHolder;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BoughtTicketRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        final Ticket ticket = ticketList.get(i);

        holder.name.setText(ticket.getName());
        holder.price.setText(ticket.getPrice());
        holder.date.setText(ticket.getDate());

        switch (method) {
            case "touch":

                break;

            case "gesture":
                if (i == GestureTicketControlPresenter.getInstance().getCurrentItemSelected()) {
                    holder.view.setBackgroundColor(Color.parseColor("#008577"));
                } else {
                    holder.view.setBackgroundColor(Color.parseColor("#FAFAFA"));
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (ticketList != null) {
            return ticketList.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView price;
        public TextView date;
        public View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.bought_ticket_name);
            price = itemView.findViewById(R.id.bought_ticket_price);
            date = itemView.findViewById(R.id.bought_ticket_date);
            view = itemView;

            switch (method) {
                case "touch":
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Ticket ticket = ticketList.get(getAdapterPosition());
                            TouchTicketControlActivity.getInstance().startTicketControlDetailsActivity(ticket);
                        }
                    });
                    break;

                case "gesture":

                    break;
            }
        }
    }
}
