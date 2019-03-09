/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 09.03.19 12:35
 */

package buying.tickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import buying.tickets.choosemethod.ChooseMethodActivity;
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
