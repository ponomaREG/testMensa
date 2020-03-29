package com.example.mensatest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mensatest.Managers.Mensa;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initButtonsAndSetOcl();
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
        View.OnClickListener ocl = v -> {
            dialog.cancel();
        };
        dialog.findViewById(R.id.dialogButtonMoveOn).setOnClickListener(ocl);
        dialog.show();
    }

}
