/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 08.03.19 21:31
 */

package buying.tickets.touch.contract;

import android.content.Context;

import buying.tickets.touch.model.Ticket;
/**
 * Created by Sebastian Paciorek
 */
public interface TouchTicketsControlDetailsInterface {
    interface View {

    }

    interface Presenter {
        void setContext(Context context);

        void setView(TouchTicketsControlDetailsInterface.View view);

        Ticket getTicket();
    }
}
