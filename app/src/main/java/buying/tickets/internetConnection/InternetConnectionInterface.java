/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 08.03.19 21:31
 */

package buying.tickets.internetConnection;

/**
 * Created by Sebastian Paciorek
 */
public interface InternetConnectionInterface {
    interface View {
        void showInternetTextView(boolean isConnected);
    }

    interface Presenter {
        void changedStatusInternet(boolean isConnected);

        void getView(InternetConnectionInterface.View view);

        boolean isConnected();

        void setConnected(boolean connected);
    }
}
