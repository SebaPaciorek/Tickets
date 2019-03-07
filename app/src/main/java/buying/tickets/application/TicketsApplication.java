/*
 * Created by Sebastian Paciorek on 7.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 07.03.19 11:34
 */

package buying.tickets.application;

import android.app.Application;

import io.realm.Realm;
/**
 * Created by Sebastian Paciorek
 */
public class TicketsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
