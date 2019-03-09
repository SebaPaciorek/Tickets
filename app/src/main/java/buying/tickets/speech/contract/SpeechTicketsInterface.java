/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 09.03.19 12:36
 */

package buying.tickets.speech.contract;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import buying.tickets.touch.model.Ticket;

/**
 * Created by Sebastian Paciorek
 */
public interface SpeechTicketsInterface {

    interface View {
        void showMessage(String message);

        void notifyDataSetChangedTicketsRecyclerView();

        void setSelectedReturnButton(boolean isSelected);

        void setListeningActionsInfoTextView(String message);

        void setCountDownTimerStartListening();

        void setProgressBarValue(int value);

        void showProgressBar(boolean show);

        void showProgressInfo(boolean show);

        void startSummaryActivity();

        void startBuyTicketActivity();

        void stopListening();

        void showListeningErrorInfoMatchInfo(boolean show);
    }

    interface Presenter {
        void setContext(Context context);

        void setView(SpeechTicketsInterface.View view);

        List<Ticket> getTicketList();

        void setDiscountTicketList();

        void setNormalTicketList();

        int getCurrentItemSelected();

        void findMatch(ArrayList<String> voiceResults);
    }
}
