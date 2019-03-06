/*
 * Created by Sebastian Paciorek on 6.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 06.03.19 23:54
 */

package buying.tickets.touch.presenter;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import buying.tickets.R;
import buying.tickets.touch.contract.TouchTicketsInterface;
import buying.tickets.touch.model.Ticket;
/**
 * Created by Sebastian Paciorek
 */
public class TouchTicketsPresenter implements TouchTicketsInterface.Presenter {

    private static TouchTicketsPresenter touchTicketsPresenter;

    private Context context;
    private TouchTicketsInterface.View view;

    private List<Ticket> ticketList;

    public TouchTicketsPresenter() {
        ticketList = new ArrayList<>();
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void setView(TouchTicketsInterface.View view) {
        this.view = view;
    }

    @Override
    public List<Ticket> getTicketList() {
        return ticketList;
    }

    @Override
    public void setDiscountTicketList() {
        ticketList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(String.valueOf(context.getResources().getString(R.string.discount_tickets_json)));
            for (int i = 0; i < jsonArray.length(); i++) {
                convertJSONObjectToTicket(jsonArray.getJSONObject(i));
            }
        } catch (JSONException e) {
            view.showMessage(String.valueOf(context.getResources().getString(R.string.error_get_tickets_json_message)));
        }
    }

    @Override
    public void setNormalTicketList() {
        ticketList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(String.valueOf(context.getResources().getString(R.string.normal_tickets_json)));
            for (int i = 0; i < jsonArray.length(); i++) {
                convertJSONObjectToTicket(jsonArray.getJSONObject(i));
            }
        } catch (JSONException e) {
            view.showMessage(String.valueOf(context.getResources().getString(R.string.error_get_tickets_json_message)));
        }
    }

    private void convertJSONObjectToTicket(JSONObject jsonObject) {
        Ticket ticket = new Ticket();
        try {
            ticket.setName(jsonObject.get("name").toString());
            ticket.setPrice(jsonObject.get("price").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        addTicketToList(ticket);
    }

    private void addTicketToList(Ticket ticket) {
        ticketList.add(ticket);
    }

    public static TouchTicketsPresenter getInstance() {
        if (touchTicketsPresenter == null) {
            touchTicketsPresenter = new TouchTicketsPresenter();
        }
        return touchTicketsPresenter;
    }
}
