/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 09.03.19 12:36
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
import buying.tickets.gesture.presenter.GestureTicketsPresenter;
import buying.tickets.speech.presenter.SpeechTicketsPresenter;
import buying.tickets.touch.model.Ticket;
import buying.tickets.touch.view.TouchTicketsActivity;
/**
 * Created by Sebastian Paciorek
 */
public class TicketsRecyclerViewAdapter extends RecyclerView.Adapter<TicketsRecyclerViewAdapter.ViewHolder> {

    private List<Ticket> ticketList;
    private TicketsRecyclerViewAdapter.ViewHolder holder;
    private String method = "touch";

    public TicketsRecyclerViewAdapter(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public TicketsRecyclerViewAdapter(List<Ticket> ticketList, String method) {
        this.ticketList = ticketList;
        this.method = method;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_item_recycler_view, parent, false);
        TicketsRecyclerViewAdapter.ViewHolder viewHolder = new ViewHolder(view);
        holder = viewHolder;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Ticket ticket = ticketList.get(i);

        holder.name.setText(ticket.getName());
        holder.price.setText(ticket.getPrice());
        switch (method) {
            case "touch":

                break;

            case "gesture":
                if (i == GestureTicketsPresenter.getInstance().getCurrentItemSelected()) {
                    holder.view.setBackgroundColor(Color.parseColor("#008577"));
                } else {
                    holder.view.setBackgroundColor(Color.parseColor("#FAFAFA"));
                }
                break;

            case "speech":
                if (i == SpeechTicketsPresenter.getInstance().getCurrentItemSelected()) {
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
        public View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.ticket_name);
            price = itemView.findViewById(R.id.ticket_price);
            view = itemView;

            switch (method) {
                case "touch":
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Ticket ticket = ticketList.get(getAdapterPosition());
                            TouchTicketsActivity.getInstance().startSummaryActivity(ticket);
                        }
                    });
                    break;

                case "gesture":

                    break;

                case "speech":

                    break;
            }
        }

    }
}
