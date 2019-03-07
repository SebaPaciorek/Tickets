/*
 * Created by Sebastian Paciorek on 7.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 07.03.19 11:30
 */

package buying.tickets.speech.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import buying.tickets.R;

/**
 * Created by Sebastian Paciorek
 */
public class SpeechMainActivity extends AppCompatActivity {

    private static SpeechMainActivity speechMainActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_main);

        speechMainActivity = this;
    }

    public static SpeechMainActivity getInstance() {
        return speechMainActivity;
    }
}
