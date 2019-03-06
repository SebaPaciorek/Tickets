/*
 * Created by Sebastian Paciorek on 6.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 06.03.19 23:54
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
