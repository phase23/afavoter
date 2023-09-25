package com.plus.agmafa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }

        listView = (ListView) findViewById(R.id.list);
        ArrayList<String> arrayList=new ArrayList<>();

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
        arrayAdapter = new ArrayAdapter(this,R.layout.mytextview, arrayList);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "clicked" + position + " " + arrayList.get(position).toString(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, pdflayer.class);
                //intent.putExtra("userid",releaseid);
                intent.putExtra("club",arrayList.get(position).toString());
                startActivity(intent);



            }
        });

    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

}