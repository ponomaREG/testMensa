package com.example.mensatest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initButtonsAndSetOcl();
//        initLanguageButtonAndInitForOcl();
    }


    private void initButtonsAndSetOcl(){
        Button mainBegin = findViewById(R.id.mainBegin);
        Button mainInfo = findViewById(R.id.mainInfo);

        View.OnClickListener ocl = getOclForButtons();

        mainBegin.setOnClickListener(ocl);
        mainInfo.setOnClickListener(ocl);

    }


    private View.OnClickListener getOclForButtons(){
        return v->{
            switch(v.getId()){
                case R.id.mainBegin:
                    startActivity(new Intent(MainActivity.this, MensaTest.class));
                    break;
                case R.id.mainInfo:
                    startDialogInfo();
                    break;
            }
        };
    }

    private void startDialogInfo(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog_info);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        View.OnClickListener ocl = v -> dialog.cancel();
        dialog.findViewById(R.id.dialogButtonMoveOn).setOnClickListener(ocl);
        dialog.show();
    }
//
//
//    private void setImageInLanguageFirstLaunch(){
//        if(SwitchLanguage.getLanguage(this).equals("ru")) setLanguageImageToRu();
//        else setLanguageImageToEn();
//    }
//
//
//
//    private void initLanguageButtonAndInitForOcl(){
//        setImageInLanguageFirstLaunch();
//        final String language_ru = "ru";
//        final String language_en = "en";
//        ImageView languageView = findViewById(R.id.currentLanguage);
//        languageView.setOnClickListener(v -> {
//            String language = SwitchLanguage.getLanguage(getBaseContext());
//            Log.d("CURRENT LANGUAGE 1",SwitchLanguage.getLanguage(getBaseContext()));
//            if(language.equals(language_ru)){
//                setLanguageImageToEn();
//                SwitchLanguage.setLocale(getBaseContext(),language_en);
//                Log.d("CURRENT LANGUAGE 2",SwitchLanguage.getLanguage(getBaseContext()));
//            }else{
//                setLanguageImageToRu();
//                SwitchLanguage.setLocale(getApplicationContext(),language_ru);
//                Log.d("CURRENT LANGUAGE",SwitchLanguage.getLanguage(getBaseContext()));
//            }
//            recreate();
//        });
//    }
//
//    private void setLanguageImageToRu(){
//        ((ImageView) findViewById(R.id.currentLanguage)).setImageDrawable(getResources().getDrawable(R.drawable.ru_flag));
//    }
//
//    private void setLanguageImageToEn(){
//        ((ImageView) findViewById(R.id.currentLanguage)).setImageDrawable(getResources().getDrawable(R.drawable.en_flag));
//    }

}
