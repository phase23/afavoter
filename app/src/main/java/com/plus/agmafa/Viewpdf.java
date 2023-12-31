package com.plus.agmafa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

public class Viewpdf extends AppCompatActivity {
PDFView pdfView;
    Button back;
    String thisclub;
    String thisclick;
    String linkpdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpdf);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        thisclub = getIntent().getExtras().getString("club");
        thisclick = getIntent().getExtras().getString("click");

        //Toast.makeText(Viewpdf.this, thisclick , Toast.LENGTH_SHORT).show();


        switch(thisclick){
            case "one":

                linkpdf = "2023_Agenda_October_21ST.pdf";
            break;
            case "two":
                linkpdf = "2022_ANNUAL_GENERAL_MEETING_MINUTES.pdf";
                break;
            case "three":
                linkpdf = "2022_AUDITED_FINANCIAL_STATEMENTS.pdf";
                break;
            case "four":
                linkpdf = "Anguilla_FA_Activity_Report.pdf";
                break;
            case "five":
                linkpdf = "2024_BUDGET_SUMMARY.pdf";
                break;

            case "six":
                linkpdf = "ELECTIONS_NOMINEES.pdf";
                break;

            case "seven":
                linkpdf = "2022_Activity_Presentation.pdf";
                break;
        }

        pdfView = findViewById(R.id.pdfView);
        pdfView.fromAsset(linkpdf).load();

        back = (Button)findViewById(R.id.backbtn);


        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(Viewpdf.this, pdflayer.class);

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