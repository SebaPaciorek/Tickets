/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 09.03.19 16:16
 */

package buying.tickets.speech.contract;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sebastian Paciorek
 */
public interface SpeechPaymentMethodInterface {
    interface View {
        void setSelectedMastercard(boolean isSelected);

        void setSelectedMasterpass(boolean isSelected);

        void setSelectedVisa(boolean isSelected);

        void setSelectedReturnButton(boolean isSelected);

        Button getReturnButton();

        TextView getMastercardTextView();

        TextView getMasterpassTextView();

        TextView getVisaTextView();

        void setListeningActionsInfoTextView(String message);

        void setCountDownTimerStartListening();

        void setProgressBarValue(int value);

        void showProgressBar(boolean show);

        void showProgressInfo(boolean show);

        void startPaymentActivity();

        void startSummaryActivity();

        void stopListening();

        void showListeningErrorInfoMatchInfo(boolean show);
    }

    interface Presenter {
        void setContext(Context context);

        void setView(SpeechPaymentMethodInterface.View view);

        void findMatch(ArrayList<String> voiceResults);
    }
}
