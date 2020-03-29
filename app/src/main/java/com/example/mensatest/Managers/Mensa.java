package com.example.mensatest.Managers;


import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.mensatest.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Mensa {


    private int score = 0, current_question = 0;
    private ArrayList<String> skipped_tasks = new ArrayList<>();
    private final static int NUMBER = 35;


    private static String[] answers = new String[]{
            "A","E","F","F","D",
            "E","E","C","D","D",
            "A","A","B","A","F",
            "B","D","C","A","A",
            "B","E","F","F","E",
            "F","A","A","C","E",
            "D","E","E","A","D"
    };

    public Mensa(){
        for(int i = 0 ;i < COUNT;i++) this.skipped_tasks.add(String.valueOf(i));
    }

    public static int COUNT = answers.length;


    public String[] getSkippedTasks(){return this.skipped_tasks.toArray(new String[0]);}



    public int getScore(){return this.score;}

    public void incScore(){this.score++;}

    public void setCurrent_question(int current_question){ this.current_question = current_question;}

    public int getCurrent_question(){ return this.current_question;}

    public void incCurrentQuestion(){ this.current_question++;}

    public String getCorrectAnswer(int number_of_question){return answers[number_of_question];}

    public static Drawable[] getTaskInfo(int number_question, Context context) {
        number_question++;
        Drawable[] task_and_answers = new Drawable[7];
        String folder = String.format("matrise-%s",number_question);
        for(int i = 0;i < 7;i++) {
            String filepath = null;
            if(i == 0) {filepath = String.format("%s/%s.png",folder,folder);}
            else{filepath = String.format("%s/%s",folder,String.format("answer-%s-%s.png",number_question,i));}
            InputStream ip = null;
            try {
                ip = context.getAssets().open(filepath);
            } catch (IOException e) {
                task_and_answers[i] = context.getResources().getDrawable(R.drawable.question);
                continue;
            }
            task_and_answers[i] = Drawable.createFromStream(ip,null);
        }
        return task_and_answers;
    }

    public void popSkippedQuestion(int number_question){
        this.skipped_tasks.remove(String.valueOf(number_question));
    }

    public void pushSkippedTaskIntoEnd(int number_of_question){
        skipped_tasks.remove(String.valueOf(number_of_question));
        skipped_tasks.add(String.valueOf(number_of_question));
    }

    @Deprecated
    public boolean ifTaskContainsInSkippedTasks(int number_of_question){
        return this.skipped_tasks.contains(String.valueOf(number_of_question));
    }

    public int getSkippedQuestion(){
        if(this.skipped_tasks.size() == 0) return -1;
        for(int i = 0;i < this.skipped_tasks.size();i++){
            if(this.skipped_tasks.get(i)!=null) return Integer.parseInt(this.skipped_tasks.get(i));
        }
        return -1;
    }



    public static int getFinalScore(int score,int left_minute){
        int finalscore;
        int max_score = 150;
        finalscore = max_score-(NUMBER-score)*5;
        if(finalscore < 60) finalscore = 60;
        if(left_minute > 5) finalscore = finalscore+ 5;
        return finalscore;
    }

    public static double getDescriptionOfScore(int final_score){
        switch (final_score){
            case 150:
                return 99.957;
            case 145:
                return 99.865;
            case 140:
                return 99.617;
            case 135:
                return 99.018;
            case 130:
                return 97.725;
            case 125:
                return 95.221;
            case 120:
                return 90.879;
            case 115:
                return 84.134;
            case 110:
                return 74.751;
            case 105:
                return 63.056;
            case 100:
                return 50.0;
            case 95:
                return 36.944;
            case 90:
                return 25.249;
            case 85:
                return 15.866;
            case 80:
                return 9.121;
            case 75:
                return 4.779;
            case 70:
                return 2.275;
            case 65:
                return 0.982;
            case 60:
                return 0.383;

        }
    return -1.0;
    }

}