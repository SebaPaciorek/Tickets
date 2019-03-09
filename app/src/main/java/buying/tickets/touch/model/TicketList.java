/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 08.03.19 21:31
 */

package buying.tickets.touch.model;

import io.realm.RealmList;
import io.realm.RealmObject;
/**
 * Created by Sebastian Paciorek
 */
public class TicketList extends RealmObject {

    private RealmList<Ticket> ticketRealmList;

    public TicketList() {
    }

    public RealmList<Ticket> getTicketRealmList() {
        return ticketRealmList;
    }

    public void setTicketRealmList(RealmList<Ticket> ticketRealmList) {
        this.ticketRealmList = ticketRealmList;
    }
}
