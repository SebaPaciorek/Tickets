/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 08.03.19 21:31
 */

package buying.tickets.touch.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import buying.tickets.touch.contract.TouchTicketControlInterface;
import buying.tickets.touch.model.Ticket;
import buying.tickets.touch.model.TicketList;
import io.realm.Realm;
import io.realm.RealmResults;
/**
 * Created by Sebastian Paciorek
 */
public class TouchTicketControlPresenter implements TouchTicketControlInterface.Presenter {

    private static TouchTicketControlPresenter touchTicketControlPresenter;

    private Context context;
    private TouchTicketControlInterface.View view;

    private Realm realm;
    private TicketList ticketList;

    private TouchTicketControlPresenter() {
        realm = Realm.getDefaultInstance();
        createTicketListObject();
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void setView(TouchTicketControlInterface.View view) {
        this.view = view;
    }

    @Override
    public void addNewBoughtTicket(Ticket ticket) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int maxID = 0;
                try {
                    maxID = findMaxID();
                } catch (Exception e) {
                }
                ticket.setId(maxID + 1);
                addBoughtTicket(ticket);
            }
        });
    }

    private void addBoughtTicket(Ticket ticket) {
        ticketList.getTicketRealmList().add(0, ticket);
    }

    private int findMaxID() {
        return Objects.requireNonNull(realm.where(Ticket.class).max("id")).intValue();
    }

    private void createTicketListObject() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ticketList = realm.createObject(TicketList.class);
            }
        });
    }

    @Override
    public List<Ticket> getBoughtTicketsList() {
        final List<Ticket> ticketBoughtTicketsList = new ArrayList<>();


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<TicketList> ticketListRealmResults = realm.where(TicketList.class).findAll();
                List<TicketList> ticketListResult = realm.copyFromRealm(ticketListRealmResults);

                for (TicketList ticketList : ticketListResult
                ) {
                    ticketBoughtTicketsList.addAll(ticketList.getTicketRealmList());
                }
            }
        });
        Collections.sort(ticketBoughtTicketsList);

        return ticketBoughtTicketsList;
    }


    public static TouchTicketControlPresenter getInstance() {
        if (touchTicketControlPresenter == null) {
            touchTicketControlPresenter = new TouchTicketControlPresenter();
        }
        return touchTicketControlPresenter;
    }
}
