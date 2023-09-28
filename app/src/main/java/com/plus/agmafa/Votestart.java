package com.plus.agmafa;

import static android.graphics.Color.GREEN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Votestart extends AppCompatActivity {
    private TextView txtCountdown;
    Handler handler2;
    String returnshift;
    String somebits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votestart);


        try {
            doGetRequest("https://axfull.com/vote/api_getoptions.php");
        } catch (IOException e) {
            e.printStackTrace();
        }

        txtCountdown = findViewById(R.id.txtCountdown);

        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtCountdown.setText("Voting will begin in " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                // Handle what happens after countdown is finished, e.g., navigate to another activity
                txtCountdown.setText("Voting starts now!");
                Intent intent = new Intent(Votestart.this, Dovote.class);
                intent.putExtra("items",somebits);
                startActivity(intent);
            }
        }.start();

        handler2 = new Handler(Looper.getMainLooper());

    }



    void doGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        // Error

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // For the example, you can show an error dialog or a toast
                                // on the main UI thread
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {


                        somebits = response.body().string();
                        Log.i("ddevice",somebits);

                        handler2.post(new Runnable() {
                            @Override
                            public void run() {




                            }
                        });


                    }//end if




                });

    }











}