/*
 * Created by Sebastian Paciorek on 7.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 07.03.19 20:07
 */

package buying.tickets.internetConnection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import buying.tickets.application.TicketsApplication;
import buying.tickets.speech.view.SpeechMainActivity;

/**
 * Created by Sebastian Paciorek
 */
public class InternetConnectorReceiver extends BroadcastReceiver implements InternetConnectionInterface.Presenter {

    private InternetConnectionInterface.View view;
    boolean connected;

    public InternetConnectorReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            boolean isConnected = true;
            boolean isVisible = TicketsApplication.isActivityVisible();

            if (isVisible) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager
                        .getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    changedStatusInternet(true);
                    connected = true;
                } else {
                    changedStatusInternet(false);
                    connected = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changedStatusInternet(boolean isConnected) {
        try {
            SpeechMainActivity.getInstance().showInternetTextView(isConnected);
        } catch (Exception e) {
        }
    }

    @Override
    public void getView(InternetConnectionInterface.View view) {
        this.view = view;
    }
}