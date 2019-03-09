/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 09.03.19 16:17
 */

package buying.tickets;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.List;

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

    private void setAboutButton() {
        aboutButton.setOnClickListener(v -> {
            openAboutWindow();
        });
    }

    private void startChooseMethodActivity() {
        Intent intent = new Intent(this, ChooseMethodActivity.class);
        startActivity(intent);
    }

    public void openAboutWindow() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(getResources().getString(R.string.about_google_drive_link)));

        Intent intentGooglePlay = new Intent(Intent.ACTION_VIEW);
        intentGooglePlay.setData(Uri.parse("https://play.google.com/store/apps/category/COMMUNICATION"));

        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;

        if (isIntentSafe) {
            startActivity(intent);
        } else {
            startActivity(intentGooglePlay);
        }
    }
}
