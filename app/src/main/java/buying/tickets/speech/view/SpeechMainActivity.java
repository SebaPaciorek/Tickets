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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import buying.tickets.R;
import buying.tickets.application.TicketsApplication;
import buying.tickets.internetConnection.InternetCheck;
import buying.tickets.internetConnection.InternetConnectionInterface;
import buying.tickets.internetConnection.InternetConnectorReceiver;

/**
 * Created by Sebastian Paciorek
 */
public class SpeechMainActivity extends AppCompatActivity implements RecognitionListener, InternetConnectionInterface.View  {

    private static SpeechMainActivity speechMainActivity;

    private Button buyTicketButton;
    private Button ticketControlButton;
    private TextView internetTextView;

    private SpeechRecognizer speechRecognizer;
    private Intent recognizerIntent;
    private double acceptedConfidence;

    private InternetCheck internetCheck;
    private InternetConnectionInterface.Presenter internetConnectionPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_main);
        setTitle(getResources().getString(R.string.tickets_title));
        speechMainActivity = this;

        checkInternetAccess();

        internetConnectionPresenter = new InternetConnectorReceiver();

        setComponents();
        setAcceptedConfidence();

        requestRecordAudioPermission();
    }

    private void setComponents() {
        buyTicketButton = findViewById(R.id.speech_main_buy_ticketButton);
        ticketControlButton = findViewById(R.id.speech_main_ticket_controlButton);
        internetTextView = findViewById(R.id.speech_main_internetTextView);

        setBuyTicketsButton();
        setTicketControlButtonButton();
    }

    private void setBuyTicketsButton() {
        buyTicketButton.setClickable(false);
    }

    private void setTicketControlButtonButton() {
        ticketControlButton.setClickable(false);
    }

    public double getAcceptedConfidence() {
        return acceptedConfidence;
    }

    public void setAcceptedConfidence() {
        this.acceptedConfidence = Double.parseDouble(getResources().getString(R.string.accepted_confidence));
    }

    private void checkInternetAccess() {
        internetCheck = new InternetCheck(internet -> {
            if (internet) {
                internetTextView.setVisibility(View.GONE);
            } else {
                internetTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void showInternetTextView(boolean isConnected) {
        if (isConnected) {
            internetTextView.setVisibility(View.GONE);
        } else {
            internetTextView.setVisibility(View.VISIBLE);
        }
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

        if (requestCode == 101) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        return;
                    } else {
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
    }

    public static SpeechMainActivity getInstance() {
        return speechMainActivity;
    }

    //Called when the endpointer is ready for the user to start speaking.
    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.d("LOG", "onReadyForSpeech");
    }

    //The user has started to speak.
    @Override
    public void onBeginningOfSpeech() {
        Log.d("LOG", "onBeginningOfSpeech");
    }

    //The sound level in the audio stream has changed.
    @Override
    public void onRmsChanged(float rmsdB) {
        Log.d("LOG", "onRmsChanged");
    }

    //More sound has been received.
    //More sound has been received.
    // The purpose of this function is to allow giving feedback to the user regarding the captured audio. There is no guarantee that this method will be called.
    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.d("LOG", "onBufferReceived");
    }

    //Called after the user stops speaking.
    @Override
    public void onEndOfSpeech() {
        Log.d("LOG", "onEndOfSpeech");
    }

    //A network or recognition error occurred.
    @Override
    public void onError(int error) {
        Log.d("LOG", "onError " + getErrorText(error));

    }

    //Called when recognition results are ready.
    @Override
    public void onResults(Bundle results) {
        Log.d("LOG", "onResults");
        ArrayList<String> voiceResults = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        float[] confidenceScores = results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);

        if (voiceResults != null) {

            Log.d("LOG", "results: " + voiceResults.get(0));
        }

        //Confidence values close to 1.0 indicate high confidence (the speech recognizer is confident that the recognition result is correct),
        // while values close to 0.0 indicate low confidence.
        if (confidenceScores != null) {
            if (confidenceScores.length > 0) {
                Log.d("LOG", "results: " + String.valueOf(confidenceScores[0]));
            } else {
                Log.d("LOG", "confidenceScores != null && confidenceScores.length < 0 : ");
            }

        } else {
            Log.d("LOG", "confidenceScores == null ");
        }

    }

    //Called when partial recognition results are available.
    //Called when partial recognition results are available.
    // The callback might be called at any time between onBeginningOfSpeech() and onResults(Bundle) when partial results are ready.
    // This method may be called zero, one or multiple times for each call to SpeechRecognizer.startListening(Intent), depending on the speech recognition service implementation.
    // To request partial results, use RecognizerIntent.EXTRA_PARTIAL_RESULTS
    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.d("LOG", "onPartialResults");
    }

    //Reserved for adding future events.
    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.d("LOG", "onEvent");
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    @Override
    protected void onStart() {
        super.onStart();
        TicketsApplication.activityResumed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TicketsApplication.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        TicketsApplication.activityPaused();
    }
}
