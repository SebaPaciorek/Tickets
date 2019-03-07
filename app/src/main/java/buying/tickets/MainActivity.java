/*
 * Created by Sebastian Paciorek on 7.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 07.03.19 00:13
 */

package buying.tickets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import buying.tickets.choosemethod.ChooseMethodActivity;
import buying.tickets.gesture.view.GestureMainActivity;
import buying.tickets.gesture.view.GestureTicketsActivity;
import buying.tickets.touch.view.TouchMainActivity;
/**
 * Created by Sebastian Paciorek
 */
public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setComponents();
    }

    private void setComponents() {
        startButton = findViewById(R.id.startButton);
        aboutButton = findViewById(R.id.aboutAppButton);

        setStartButton();
        setAboutButton();
    }

    private void setStartButton() {
        startButton.setOnClickListener(v -> startChooseMethodActivity());
    }

    private void setAboutButton(){
        aboutButton.setOnClickListener(v -> {

        });
    }

    private void startChooseMethodActivity() {
        Intent intent = new Intent(this, ChooseMethodActivity.class);
        startActivity(intent);
    }
}
