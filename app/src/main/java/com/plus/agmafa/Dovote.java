package com.plus.agmafa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.DialogInterface;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Dovote extends AppCompatActivity {
    TextView ttlr;
    String votetitile;
    String istimer;
    String voteresp;
    Handler handler3;
    String thismydevice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dovote);
        ttlr = (TextView)findViewById(R.id.tv_title);


         thismydevice = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);


        String data = getIntent().getExtras().getString("items","defaultKey");
        Log.i("side",data);

        final LinearLayout layout = findViewById(R.id.layout_buttons);

        int totalWidth = getResources().getDisplayMetrics().widthPixels;
        int margin = (int) (totalWidth * 0.30);  // 30% of screen width






        try {
            JSONObject jsonObject = new JSONObject(data);
             votetitile = jsonObject.getString("111");
             istimer = jsonObject.getString("112");


            int myNum = 0;
            try {
                myNum = Integer.parseInt(istimer);
            } catch(NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }
            int gettimer = myNum * 1000;
            Log.i("side", String.valueOf(gettimer));

            new CountDownTimer(gettimer, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    ttlr.setText(votetitile +" - you have  " + millisUntilFinished / 1000 + " seconds to complete your vote");
                }

                @Override
                public void onFinish() {
                    // Handle what happens after countdown is finished, e.g., navigate to another activity
                    Intent intent = new Intent(Dovote.this, Novote.class);
                    startActivity(intent);
                }
            }.start();

            //ttlr.setText(title + " -  You have " + sec + " seconds to complete your vote") ;
            // Set the title to a TextView
            TextView titleTextView = new TextView(this);
            //titleTextView.setText(title);
            //layout.addView(titleTextView);

            // Remove the title so that we're left with only the button data
            jsonObject.remove("111");
            jsonObject.remove("112");
            // Iterate through the JSON object and create buttons dynamically
            for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                String key = it.next();
                String value = jsonObject.getString(key);

                Button button = new Button(this);
                button.setText(value.trim());
                button.setTag(key);


                // Setting button height
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                buttonParams.height = 80;  // adjust this value to your liking
                button.setLayoutParams(buttonParams);

                // Setting text size
                button.setTextSize(25);  // adjust this value to your liking

                // Setting padding
                int padding = 20;  // adjust this value to your liking
                button.setPadding(padding, padding, padding, padding);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParams.setMargins(margin, 0, margin, 30);
                button.setLayoutParams(layoutParams);


                layout.addView(button);
                handler3 = new Handler(Looper.getMainLooper());
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                        // Set the message for the AlertDialog
                        builder.setMessage("Please confirm vote for " + button.getText())
                                .setCancelable(false)  // Disallow the back button to cancel
                                .setPositiveButton("Vote", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Handle 'Vote' action here

                                        String buttonTag = (String) button.getTag();
                                        String buttonText = button.getText().toString();

                                        // Print the tag and text
                                        System.out.println("ppr Tag: " + buttonTag);
                                        System.out.println("ppr Text: " + buttonText);
                                        handler3.removeCallbacksAndMessages(null);



                                        try {
                                            sendvote("https://axfull.com/vote/api_sendvote.php?voteid="+buttonTag + "&deviceid="+thismydevice);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }



                                    }
                                })
                                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Handle 'Cancel' action here (or simply dismiss the dialog)
                                        dialog.cancel();
                                    }
                                });

                        // Create and display the AlertDialog
                        AlertDialog alert = builder.create();

                        // Increase the size of the AlertDialog


                        alert.show();
                        Window window = alert.getWindow();
                        if (window != null) {
                            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                            layoutParams.copyFrom(window.getAttributes());
                            layoutParams.width = 800;  // Width in pixels
                            layoutParams.height = 200; // Height in pixels
                            layoutParams.gravity = Gravity.CENTER;
                            window.setAttributes(layoutParams);
                        }
                        TextView messageView = (TextView) alert.findViewById(android.R.id.message);
                        if (messageView != null) {
                            messageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // 20sp as an example
                        }
                        // Increase the text size for the positive button
                        Button positiveButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
                        if (positiveButton != null) {
                            positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // 20sp as an example
                        }

// Increase the text size for the negative button
                        Button negativeButton = alert.getButton(AlertDialog.BUTTON_NEUTRAL);
                        if (negativeButton != null) {
                            negativeButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // 20sp as an example
                        }

                        ViewGroup buttonBarLayout = (ViewGroup) positiveButton.getParent();
                        if (buttonBarLayout != null) {
                            buttonBarLayout.setPadding(buttonBarLayout.getPaddingLeft(),
                                    buttonBarLayout.getPaddingTop() + 50, // Add 50 pixels of padding to the top as an example
                                    buttonBarLayout.getPaddingRight(),
                                    buttonBarLayout.getPaddingBottom());
                        }

                    }
                });


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }



    void sendvote(String url) throws IOException {
        System.out.println("ppr: " + url);

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


                        voteresp = response.body().string();
                        Log.i("ddevice",voteresp);

                        voteresp = voteresp.trim();
                        handler3.post(new Runnable() {
                            @Override
                            public void run() {

                        if(voteresp.equals("success")) {

                         Intent intent = new Intent(Dovote.this, Votedone.class);
                         startActivity(intent);


                        }else{

                            Toast.makeText(getApplicationContext(), "Something went wrong - try again", Toast.LENGTH_LONG).show();


                        }//end if succsss

                            }
                        });








                    }//end if




                });

    }


}