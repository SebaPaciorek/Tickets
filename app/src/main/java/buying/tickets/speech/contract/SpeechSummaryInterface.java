/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 09.03.19 12:35
 */

package buying.tickets.speech.contract;

import android.content.Context;

import buying.tickets.touch.model.Ticket;

/**
 * Created by Sebastian Paciorek
 */
public interface SpeechSummaryInterface {

    interface View {

    }

    interface Presenter {
        void setContext(Context context);

        void setView(SpeechSummaryInterface.View view);

        Ticket getTicket();

        void setTicket(Ticket ticket);

    }
}
