/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 09.03.19 16:15
 */

package buying.tickets.speech.contract;

import android.content.Context;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import buying.tickets.touch.model.Ticket;

/**
 * Created by Sebastian Paciorek
 */
public interface SpeechTicketControlInterface {
    interface View {

        Button getReturnButton();

        void setSelectedReturnButton(boolean isSelected);

        void setListeningActionsInfoTextView(String message);

        void setCountDownTimerStartListening();

        void setProgressBarValue(int value);

        void showProgressBar(boolean show);

        void showProgressInfo(boolean show);

        void stopListening();

        void showListeningErrorInfoMatchInfo(boolean show);

        void startMainActivity();
    }

    interface Presenter {
        void setContext(Context context);

        void setView(SpeechTicketControlInterface.View view);

        void addNewBoughtTicket(Ticket ticket);

        List<Ticket> getBoughtTicketsList();

        void findMatch(ArrayList<String> voiceResults);
    }
}
