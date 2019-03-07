/*
 * Created by Sebastian Paciorek on 7.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 07.03.19 00:13
 */

package buying.tickets.gesture.contract;

import android.content.Context;

import buying.tickets.touch.model.Ticket;
/**
 * Created by Sebastian Paciorek
 */
public interface GestureTicketsControlDetailsInterface {
    interface View {

    }

    interface Presenter {
        void setContext(Context context);

        void setView(GestureTicketsControlDetailsInterface.View view);

        Ticket getTicket();
    }
}
