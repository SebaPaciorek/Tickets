/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 09.03.19 13:01
 */

package buying.tickets.speech.contract;

import android.content.Context;
import android.widget.Button;

import java.util.ArrayList;

import buying.tickets.touch.model.Ticket;

/**
 * Created by Sebastian Paciorek
 */
public interface SpeechSummaryInterface {

    interface View {
        Button getBuyAndPayButton();

        Button getReturnButton();

        void setSelectedBuyAndPayButton(boolean isSelected);

        void setSelectedReturnButton(boolean isSelected);

        void setListeningActionsInfoTextView(String message);

        void setCountDownTimerStartListening();

        void setProgressBarValue(int value);

        void showProgressBar(boolean show);

        void showProgressInfo(boolean show);

        void startPaymentActivity();

        void startTicketsActivity();

        void stopListening();

        void showListeningErrorInfoMatchInfo(boolean show);
    }

    interface Presenter {
        void setContext(Context context);

        void setView(SpeechSummaryInterface.View view);

        Ticket getTicket();

        void setTicket(Ticket ticket);

        void findMatch(ArrayList<String> voiceResults);
    }
}
