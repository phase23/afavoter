package com.plus.agmafa;

import static android.content.ContentValues.TAG;
import static android.graphics.Color.RED;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    String thismydevice;
    TextView devicip;
    Handler handler4;

    SharedPreferences sharedpreferences;
    int autoSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();


            StrictMode.setThreadPolicy(policy);
            //your codes here

        }


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

/*
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
        }

 */

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo mobileNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (mobileNetworkInfo != null && mobileNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
                // Connected to mobile network
                connected = true;
            } else if (wifiNetworkInfo != null && wifiNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
                // Connected to Wi-Fi network
                connected = true;
            }
        }

// Use the 'connected' boolean as needed



        devicip = (TextView)findViewById(R.id.device);
        thismydevice = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        //thismydevice ="6767";



        if(!connected) {
            Toast.makeText(getApplicationContext(),"Check Internet & Restart App",Toast.LENGTH_LONG).show();
            Intent nointernet = new Intent(MainActivity.this, Nointernet.class);
            startActivity(nointernet);


        }else {




            Log.i("ddevice", thismydevice);

            devicip.setText(thismydevice);
            Toast.makeText(getApplicationContext(), "Preparing your dasboard..", Toast.LENGTH_LONG).show();
//https://voter-e8e2f-default-rtdb.firebaseio.com/
            // FirebaseDatabase database = FirebaseDatabase.getInstance("https://axcessdrivers-default-rtdb.firebaseio.com/");
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://voter-e8e2f-default-rtdb.firebaseio.com/");

            DatabaseReference newdriver = database.getReference("4050"); // yourwebisteurl/rootNode if it exist otherwise don't pass any string to it.
            newdriver.child("thisstatus").setValue("onhold");


            newdriver.child("thisstatus").setValue("onhold", new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Log.e("Firebase", "Data could not be saved: " + databaseError.getMessage());
                    } else {
                        Log.d("Firebase", "Data saved successfully.");
                    }
                }
            });

            newdriver.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.hasChild("thisstatus")) {
                        //  Toast.makeText(getApplicationContext(), "Allready", Toast.LENGTH_LONG).show();

                        // Exist! Do whatever.
                    } else {
                        // Don't exist! Do something.

                        newdriver.child("thisstatus").setValue("waiting");
                        // Toast.makeText(getApplicationContext(), "Set Status", Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed, how to handle?

                }

            });


            // Read from the database
            newdriver.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    //Double value = dataSnapshot.child("latitude").getValue(Double.class);
                    String alert = dataSnapshot.child("thisstatus").getValue(String.class);
                    //boolean isSeen = ds.child("isSeen").getValue(Boolean.class);
                    //Log.d(TAG, "Value is: " + value);
                    // Toast.makeText(getApplicationContext(), "Value is:" + value + " Alert: " + alert, Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), "changed : " + value, Toast.LENGTH_LONG).show();

                    if (alert.equals("alert")) {
                        //Toast.makeText(getApplicationContext(), "Effect Change: " + alert, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(MainActivity.this, Votestart.class);
                        startActivity(intent);
                    }else if(alert.equals("reset")) {

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    } else {


                    }


                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "AxcessEats Welcome.", error.toException());
                }
            });

            sharedpreferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
            int j = sharedpreferences.getInt("key", 0);

            listView = (ListView) findViewById(R.id.list);
            ArrayList<String> arrayList = new ArrayList<>();

            arrayList.add("Shining Stars FC");
            arrayList.add("Salsa Ballers FC");
            arrayList.add("Attackers FC");
            arrayList.add("De Youngsters FC");
            arrayList.add("Diamond FC");

            arrayList.add("Gazelles FC");
            arrayList.add("Kicks United FC");
            arrayList.add("Lil Soldiers FC");
            arrayList.add("Spartans FC");
            arrayList.add("Supers Stars Fc");
            arrayList.add("Roaring Lions FC");
            arrayList.add("Lymers FC");
            arrayList.add("Uprising FC");
            arrayList.add("Docs United");


            ArrayAdapter arrayAdapter;
            listView = (ListView) findViewById(R.id.list);
            arrayAdapter = new ArrayAdapter(this, R.layout.mytextview, arrayList);
            listView.setAdapter(arrayAdapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    //Toast.makeText(MainActivity.this, "clicked" + position + " " + arrayList.get(position).toString(), Toast.LENGTH_SHORT).show();

                    try {
                        collectdevice("https://axfull.com/vote/api_devices.php?deviceid=" + thismydevice);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Intent intent = new Intent(MainActivity.this, pdflayer.class);
                    //intent.putExtra("userid",releaseid);
                    intent.putExtra("club", arrayList.get(position).toString());
                    autoSave = 1;
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt("key", autoSave);
                    editor.putString("thisclub", arrayList.get(position).toString());
                    editor.apply();

                    startActivity(intent);




                }
            });
            handler4 = new Handler(Looper.getMainLooper());
        }//end no internet
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }


    void collectdevice(String url) throws IOException {
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


                        String senddevice = response.body().string();
                        Log.i("ddevice",senddevice);

                        handler4.post(new Runnable() {
                            @Override
                            public void run() {



                            }
                        });


                    }//end if




                });

    }

}