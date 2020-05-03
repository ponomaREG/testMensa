package com.example.mensatest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mensatest.Managers.Mensa;

import java.util.Objects;


public class MensaResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensa_results);

        initButtonsTryAgainAndExit();
        showResults();
    }

    private void showResults(){
        Intent intent = getIntent();
        int score = intent.getIntExtra("score",-1);
        if(score == -1) finish();
        int left_minute = intent.getIntExtra("left_minute",-1);
        int final_score = Mensa.getFinalScore(score,left_minute);
        ((TextView) findViewById(R.id.resultsScore)).setText(String.format(getResources().getString(R.string.resultsFinalScorePlusDeviation),final_score));
        ((TextView) findViewById(R.id.resultsDescription)).setText(String.format(getResources().getString(R.string.resultsScoreDescription),Mensa.getDescriptionOfScore(final_score)));
    }

    private void initButtonsTryAgainAndExit(){
        View.OnClickListener ocl = getOclForButtons();
        findViewById(R.id.resultsTryAgain).setOnClickListener(ocl);
        findViewById(R.id.resultsExit).setOnClickListener(ocl);

    }

    private View.OnClickListener getOclForButtons(){
        return v->{
          switch (v.getId()){
              case R.id.resultsExit:
                  startDialogForExit();
                  break;
              case R.id.resultsTryAgain:
                  startDialogForOfferSetMark();
                  break;
          }
        };
    }

    private void startDialogForExit(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog_info_exit);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        View.OnClickListener ocl = v -> {
            switch (v.getId()){
                case R.id.dialogYes:
                    finishAffinity();
                    break;
                case R.id.dialogNo:
                    dialog.cancel();
                    break;
            }
        };
        dialog.findViewById(R.id.dialogYes).setOnClickListener(ocl);
        dialog.findViewById(R.id.dialogNo).setOnClickListener(ocl);
        dialog.show();
    }


    private void startDialogForOfferSetMark(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog_info_set_mark);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.findViewById(R.id.dialogSetMark).setOnClickListener(v -> {
            final String appPackageName = "com.test.mensa";
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
            dialog.cancel();
        });
        dialog.setOnCancelListener(dialog1 -> startAgain());
        dialog.show();

    }

    private void startAgain(){
        finish();
    }


}
