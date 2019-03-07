/*
 * Created by Sebastian Paciorek on 7.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 07.03.19 11:42
 */

package buying.tickets.speech.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

import buying.tickets.R;

/**
 * Created by Sebastian Paciorek
 */
public class SpeechMainActivity extends AppCompatActivity implements RecognitionListener {

    private static SpeechMainActivity speechMainActivity;

    private Button buyTicketButton;
    private Button ticketControlButton;

    private SpeechRecognizer speechRecognizer;
    private Intent recognizerIntent;
    private boolean recordAudioPermissionGranted = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_main);
        setTitle(getResources().getString(R.string.tickets_title));
        speechMainActivity = this;

        setComponents();

        requestRecordAudioPermission();
    }

    private void setComponents() {
        buyTicketButton = findViewById(R.id.speech_main_buy_ticketButton);
        ticketControlButton = findViewById(R.id.speech_main_ticket_controlButton);

        setBuyTicketsButton();
        setTicketControlButtonButton();
    }

    private void setBuyTicketsButton() {
        buyTicketButton.setClickable(false);
    }

    private void setTicketControlButtonButton() {
        ticketControlButton.setClickable(false);
    }

    private void requestRecordAudioPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String requiredPermission = Manifest.permission.RECORD_AUDIO;

            if (checkCallingOrSelfPermission(requiredPermission) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{requiredPermission}, 101);

            } else {
                promptSpeechInput();
                speechRecognizer.startListening(recognizerIntent);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        recordAudioPermissionGranted = false;

        if (requestCode == 101) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        recordAudioPermissionGranted = false;
                        return;
                    } else {
                        recordAudioPermissionGranted = true;
                        checkIsRecognitionAvailable();
                        promptSpeechInput();
                        speechRecognizer.startListening(recognizerIntent);
                    }
                }
            }
        }
    }

    private void checkIsRecognitionAvailable() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            Toast.makeText(this, getResources().getString(R.string.speech_recognition_available), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getResources().getString(R.string.speech_recognition_unavailable), Toast.LENGTH_SHORT).show();
        }
    }

    private void promptSpeechInput() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());

//        setRecognitionListener();

    }

    public static SpeechMainActivity getInstance() {
        return speechMainActivity;
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {

    }

    @Override
    public void onResults(Bundle results) {

    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}
