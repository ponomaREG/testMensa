package com.example.mensatest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mensatest.Managers.Mensa;

public class MensaTest extends AppCompatActivity {

    private ImageView task;
    private Mensa mensa;
    private TextView time;
    private int left_minutes = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensa_test);

        generateLayouts();
        initBackButtonOcl();
        initAnswersOcl();
        initVariables();
        drawTask();
        initCountDownTimer();
    }

    private void initBackButtonOcl(){
        findViewById(R.id.mensaBack).setOnClickListener(getOclForBackButton());
    }

    private View.OnClickListener getOclForBackButton(){
        return v-> finish();
    }

    private void initVariables(){
        mensa = new Mensa();
        task = findViewById(R.id.mensaTask);
        time = findViewById(R.id.mensaTimeView);
        initBottomButtons();
    }


    private void initBottomButtons(){
        findViewById(R.id.mensaFinish).setOnClickListener(getOclForBottomButtons());
        findViewById(R.id.mensaSkip).setOnClickListener(getOclForBottomButtons());
    }

    private View.OnClickListener getOclForBottomButtons(){
        return view ->{
            switch (view.getId()){
                case R.id.mensaFinish:
                    pushUserToPageWithResults();
                    break;
                case R.id.mensaSkip:
                    mensa.pushSkippedTaskIntoEnd(mensa.getCurrent_question());
                    setSkippedQuestion();
                    break;
            }
        };
    }

    private void makeTask(){
        drawTask();
    }


    private void setSkippedQuestion(){
        if(mensa.getSkippedQuestion() == -1) {pushUserToPageWithResults();}
        else {
            mensa.setCurrent_question(mensa.getSkippedQuestion());
            makeTask();
        }
    }

    private void generateLayouts(){
        LinearLayout pointsLL = findViewById(R.id.pointsLL);
        for(int i = 0; i < Mensa.COUNT; i++){
            TextView point = (TextView) this.getLayoutInflater().inflate(R.layout.base_point,pointsLL,false);
            point.setTag(R.string.tagPointNumber,i);
            point.setTag(R.string.tagClosed,0);
            pointsLL.addView(point);
        }

    }


    private void initAnswersOcl(){
        View.OnClickListener ocl = getOclForAnswers();
        for(int i = 0;i<6;i++){
            findViewById(getResources().getIdentifier("answer_"+(i+1),"id",getPackageName())).setOnClickListener(ocl);
        }
    }

    private View.OnClickListener getOclForAnswers(){
        return view -> {
            ImageView answer = (ImageView) view;
            String n_answer = (String) answer.getTag();
            if(n_answer.equals(mensa.getCorrectAnswer(mensa.getCurrent_question()))){
                mensa.incScore();
            }
            mensa.popSkippedQuestion(mensa.getCurrent_question());
            setPointChecked(mensa.getCurrent_question());
            setSkippedQuestion();
        };
    }




    private void drawTask(){
        Drawable[] task_and_answers = Mensa.getTaskInfo(mensa.getCurrent_question(),getBaseContext());
        task.setImageDrawable(task_and_answers[0]);
        for(int i = 1;i<7;i++){
            ImageView answer = findViewById(getResources().getIdentifier("answer_"+i,"id",getPackageName()));
            answer.setImageDrawable(task_and_answers[i]);
        }
        setNumberOfQuestion(mensa.getCurrent_question()+1);
    }


    private void setNumberOfQuestion(int number_of_question){
        TextView numberQuestion = findViewById(R.id.mensaQuestionNumber);
        numberQuestion.setText(String.format(getResources().getString(R.string.mensaQuestion),number_of_question));
    }



    private void setPointChecked(int number_of_question){
        LinearLayout pointsLL = findViewById(R.id.pointsLL);
        TextView point = (TextView) pointsLL.getChildAt(number_of_question);
        point.setBackground(getResources().getDrawable(R.drawable.points_green_style));
    }


    private void pushUserToPageWithResults(){
        Intent intent = new Intent(MensaTest.this,MensaResults.class).putExtra("score",mensa.getScore());
        intent.putExtra("left_minute",left_minutes);
        startActivity(intent);
        finish();
    }


    private void initCountDownTimer(){
        new CountDownTimer(1500000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished/1000/60;
                String seconds = String.valueOf(millisUntilFinished/1000 - minutes*60);
                left_minutes = (int) minutes;
                if(Integer.parseInt(seconds)<10) seconds = "0" + seconds;
                time.setText(String.format(
                        getResources().getString(R.string.mensaTime),
                        minutes,
                        seconds
                ));
            }

            @Override
            public void onFinish() {
                pushUserToPageWithResults();
            }
        }.start();
    }


}
