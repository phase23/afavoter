package com.plus.agmafa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class pdflayer extends AppCompatActivity {
    String thisclub;
    String getclub;
    TextView select;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button presdoc;

    Button btninv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdflayer);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
       // thisclub = getIntent().getExtras().getString("club");
        SharedPreferences shared = getSharedPreferences("autoLogin", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = shared.edit();
        getclub = shared.getString("thisclub", "");

        select = (TextView) findViewById(R.id.select);
        select.setText(getclub);

        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        btn5 = (Button)findViewById(R.id.btn5);
        btn6 = (Button)findViewById(R.id.btn6 );
        presdoc = (Button)findViewById(R.id.presdoc );

        btn1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(pdflayer.this, Viewpdf.class);
                //Toast.makeText(pdflayer.this, "clicked one" , Toast.LENGTH_SHORT).show();

                intent.putExtra("click","one");
                intent.putExtra("club",thisclub);
                startActivity(intent);


            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(pdflayer.this, Viewpdf.class);
                //Toast.makeText(pdflayer.this, "clicked two" , Toast.LENGTH_SHORT).show();

                intent.putExtra("click","two");
                intent.putExtra("club",thisclub);
                startActivity(intent);


            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(pdflayer.this, Viewpdf.class);

                //Toast.makeText(pdflayer.this, "clicked three" , Toast.LENGTH_SHORT).show();


                intent.putExtra("click","three");
                intent.putExtra("club",thisclub);
                startActivity(intent);


            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(pdflayer.this, Viewpdf.class);
                //Toast.makeText(pdflayer.this, "clicked four" , Toast.LENGTH_SHORT).show();

                intent.putExtra("click","four");
                intent.putExtra("club",thisclub);
                startActivity(intent);


            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(pdflayer.this, Viewpdf.class);

                //Toast.makeText(pdflayer.this, "clicked five" , Toast.LENGTH_SHORT).show();


                intent.putExtra("click","five");
                intent.putExtra("club",thisclub);
                startActivity(intent);


            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(pdflayer.this, Viewpdf.class);

                //Toast.makeText(pdflayer.this, "clicked five" , Toast.LENGTH_SHORT).show();


                intent.putExtra("click","six");
                intent.putExtra("club",thisclub);
                startActivity(intent);


            }
        });

        presdoc.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(pdflayer.this, Viewpdf.class);

                //Toast.makeText(pdflayer.this, "clicked five" , Toast.LENGTH_SHORT).show();


                intent.putExtra("click","seven");
                intent.putExtra("club",thisclub);
                startActivity(intent);


            }
        });







    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

}