package com.hanshin.ncs_imprintsaga;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.opencsv.CSVReader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


public class BattleActivity extends AppCompatActivity {

    public static Activity BattlePageActivity;

    CsvData[] dataArray = new CsvData[30];
    TextView questionTV, timerTV, answerTV, hintTV, userPB_TV,enemyPB_TV,enemyTalk_TV;
    ProgressBar userPB, enemyPB, timerPB;
    int count_word = 0;
    LinearLayout setBtnLayout;
    String word;
    int countWordLength = 0;
    Button wordBtn[] = new Button[20], skillBTN;

    int count_onStart= 3;
    CountDownTimer countDownTimer_onStart;
    int count_timer= 11;
    CountDownTimer countDownTimer_battle;

    int count_total_question = 0;
    int count_correct_question = 0;
    boolean isDidIt = false;
    boolean isItemExist = true;
    String forSkill;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battle);
        BattlePageActivity = BattleActivity.this;

        questionTV = findViewById(R.id.questionTV);
        timerTV = findViewById(R.id.timerTV);
        answerTV = findViewById(R.id.answerTV);
        hintTV = findViewById(R.id.hintTV);
        userPB = findViewById(R.id.userPB);
        userPB_TV = findViewById(R.id.userPB_TV);
        enemyPB = findViewById(R.id.enemyPB);
        enemyPB_TV = findViewById(R.id.enemyPB_TV);
        enemyTalk_TV = findViewById(R.id.enemyTalkTV);
        timerPB = findViewById(R.id.timerPB);
        setBtnLayout = findViewById(R.id.wordBtns);
        skillBTN = findViewById(R.id.skillBTN);

        if(isItemExist) { // 아이템을 보유하고 있을 경우 스킬 기능 활성화 ( 단 1회 )
            skillBTN.setVisibility(View.VISIBLE);
            skillBTN.setClickable(true);
            skillBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int randomNum = (int) (Math.random() * 10); // 0 ~ 10 사이의 난수값 발생

                    if(randomNum>5) { // 6 이상일 경우 스킬 발동
                        String splitHint[] = forSkill.split(""); // 단어를 한글자씩 나누어 배열화
                        String hint = "";
                        String left = "";
                        for (int i = 0; i < splitHint.length; i++) { // 단어의 절반(나누기 2, 내림)을 hint에 저장
                            if (i < Math.floor((splitHint.length) / 2))
                                hint += splitHint[i];
                            else
                                left += splitHint[i];
                        }
                        hint += " /";

                        String splitLeft[] = left.split("");
                        splitLeft = shuffle(splitLeft);
                        splitLeft = shuffle(splitLeft);
                        splitLeft = shuffle(splitLeft); // 절반 이후의 부분은 랜덤으로 섞는다
                        for (int i = 0; i < splitLeft.length; i++)
                            hint += " " + splitLeft[i];
                        hintTV.setText(hint);
                        skillBTN.setVisibility(View.GONE);
                    }
                    else { // 5 이하일 경우 스킬이 발동되지 않음
                        Toast.makeText(BattleActivity.this, "스킬이 발동되지 않았다..!", Toast.LENGTH_SHORT).show();
                        skillBTN.setVisibility(View.GONE);
                    }
                }
            });
        }

        userPB.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        enemyPB.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

        Intent getintent = getIntent();
        String stageNum = getintent.getStringExtra("stageNum");
        InputStream input;
        switch (stageNum){
            case "Stage1":
                input = getResources().openRawResource(R.raw.stage1);
                try {
                    readDataFromCsv(input);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Stage2":
                input = getResources().openRawResource(R.raw.stage2);
                try {
                    readDataFromCsv(input);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Stage3":
                input = getResources().openRawResource(R.raw.stage3);
                try {
                    readDataFromCsv(input);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Stage4":
                input = getResources().openRawResource(R.raw.stage4);
                try {
                    readDataFromCsv(input);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Stage5":
                input = getResources().openRawResource(R.raw.stage5);
                try {
                    readDataFromCsv(input);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Stage6":
                input = getResources().openRawResource(R.raw.stage6);
                try {
                    readDataFromCsv(input);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Stage7":
                input = getResources().openRawResource(R.raw.stage7);
                try {
                    readDataFromCsv(input);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Stage8":
                input = getResources().openRawResource(R.raw.stage8);
                try {
                    readDataFromCsv(input);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Stage9":
                input = getResources().openRawResource(R.raw.stage9);
                try {
                    readDataFromCsv(input);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Stage10":
                input = getResources().openRawResource(R.raw.stage10);
                try {
                    readDataFromCsv(input);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }   // 스테이지별 CSV 파일 input

        countDownTimer_onStart();
        countDownTimer_onStart.start();
    }


    public void countDownTimer_onStart(){

        countDownTimer_onStart = new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                hintTV.setText(String.valueOf(count_onStart));
                count_onStart --;
                enemyTalk_TV.setText("Excuse Me");
            }
            public void onFinish() {
                countDownTimer_onStart.cancel();
                questionTV.setText(dataArray[count_word].meaning);

                setBtnLayout.removeAllViews(); // 버튼 레이아웃 초기화
                answerTV.setText(""); // 정답창 초기화
                hintTV.setText(""); // 힌트창 초기화
                countWordLength=0;

                word = dataArray[count_word].words; // CSC 파일에서 단어를 불러옴
                forSkill = word;
                String splitWord[] = word.split(""); // 단어를 한글자씩 나누어 배열화
                splitWord = shuffle(splitWord);
                splitWord = shuffle(splitWord);
                splitWord = shuffle(splitWord); // 배열을 랜덤으로 섞는다

                for (int i = 0; i < word.length(); i++) { // 랜덤으로 섞을 단어배열을 버튼화한다
                    hintTV.setText(hintTV.getText() + splitWord[i]+" ");

                    wordBtn[i] = new Button(BattleActivity.this);
                    wordBtn[i].setText(splitWord[i]);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150, 150);
                    params.setMargins(12,10,12,10);
                    wordBtn[i].setLayoutParams(params);
                    wordBtn[i].setTextColor(Color.parseColor("#00A3E1"));
                    wordBtn[i].setTextSize(28);
                    wordBtn[i].setBackgroundResource(R.drawable.wordbtn);

                    final String getSplitWord = splitWord[i];
                    final int geti = i;

                    wordBtn[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            answerTV.setText(answerTV.getText()+getSplitWord); // 클릭한 버튼의 텍스트 정답창에 입력
                            setBtnLayout.removeView(wordBtn[geti]); // 입력한 버튼 삭제
                            countWordLength++;

                            if(countWordLength == word.length()){
                                if(word.equals(answerTV.getText())) { // 사용자가 입력한 단어가 정답일 경우
                                    hintTV.setText("HIT!");
                                    enemyPB.setProgress(enemyPB.getProgress() - 10);
                                    enemyTalk_TV.setText("I see!");
                                    enemyPB_TV.setText(String.valueOf(enemyPB.getProgress()));
                                    isDidIt = true;
                                    count_total_question++;
                                    count_correct_question++;
                                    if(enemyPB.getProgress()==0)   // 적의 체력이 0이 되었을 경우 -> WIN
                                        finish(true);
                                    count_word++;
                                }
                                else {                              // 사용자가 입력한 단어가 오답일 경우
                                    hintTV.setText("! "+word+" !");
                                    userPB.setProgress(userPB.getProgress() - 10);
                                    enemyTalk_TV.setText("Parden?");
                                    userPB_TV.setText(String.valueOf(userPB.getProgress()));
                                    isDidIt = true;
                                    count_total_question++;
                                    try {
                                        BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "Wrong_answer_notes.txt", true));
                                        bw.write(word+","+dataArray[count_word].meaning+"\n");
                                        bw.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    if(userPB.getProgress()==0)   // 사용자의 체력이 0이 되었을 경우 -> LOSE
                                        finish(false);
                                    count_word++;
                                }
                            }
                        }
                    });
                    setBtnLayout.addView(wordBtn[i]); // 동적 버튼 추가
                }
                enemyTalk_TV.setText("");
                countDownTimer_battle();
                countDownTimer_battle.start();
            }
        };
    }

    public void countDownTimer_battle() {
        countDownTimer_battle = new CountDownTimer(11000,2000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerTV.setText(String.valueOf(count_timer));
                timerPB.setProgress(count_timer);
                count_timer--;
                if(count_timer == 0 & !isDidIt) {
                    hintTV.setText("! "+word+" !");
                    enemyTalk_TV.setText("Parden?");
                    userPB.setProgress(userPB.getProgress() - 10);
                    userPB_TV.setText(String.valueOf(userPB.getProgress()));
                    count_total_question++;
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "Wrong_answer_notes.txt", true));
                        bw.write(word+","+dataArray[count_word].meaning+"\n");
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(userPB.getProgress()==0)   // 사용자의 체력이 0이 되었을 경우 -> LOSE
                        finish(false);
                    count_word++;
                }
            }

            @Override
            public void onFinish() {
                count_timer = 11;
                isDidIt = false;
                countDownTimer_battle.start();
                questionTV.setText(dataArray[count_word].meaning);

                setBtnLayout.removeAllViews(); // 버튼 레이아웃 초기화
                answerTV.setText(""); // 정답창 초기화
                hintTV.setText(""); // 힌트창 초기화
                countWordLength=0;

                word = dataArray[count_word].words; // CSC 파일에서 단어를 불러옴
                forSkill = word;
                String splitWord[] = word.split(""); // 단어를 한글자씩 나누어 배열화
                splitWord = shuffle(splitWord);
                splitWord = shuffle(splitWord);
                splitWord = shuffle(splitWord); // 배열을 랜덤으로 섞는다

                for (int i = 0; i < word.length(); i++) { // 랜덤으로 섞을 단어배열을 버튼화한다
                    hintTV.setText(hintTV.getText() + splitWord[i]+" ");

                    wordBtn[i] = new Button(BattleActivity.this);
                    wordBtn[i].setText(splitWord[i]);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(130, 130);
                    params.setMargins(12,10,12,10);
                    wordBtn[i].setLayoutParams(params);
                    wordBtn[i].setTextColor(Color.parseColor("#00A3E1"));
                    wordBtn[i].setTextSize(28);
                    wordBtn[i].setBackgroundResource(R.drawable.wordbtn);

                    final String getSplitWord = splitWord[i];
                    final int geti = i;

                    wordBtn[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            answerTV.setText(answerTV.getText()+getSplitWord); // 클릭한 버튼의 텍스트 정답창에 입력
                            setBtnLayout.removeView(wordBtn[geti]); // 입력한 버튼 삭제
                            countWordLength++;

                            if(countWordLength == word.length()){
                                if(word.equals(answerTV.getText())) { // 사용자가 입력한 단어가 정답일 경우
                                    hintTV.setText("HIT!");
                                    enemyTalk_TV.setText("I see!");
                                    enemyPB.setProgress(enemyPB.getProgress() - 10);
                                    enemyPB_TV.setText(String.valueOf(enemyPB.getProgress()));
                                    isDidIt = true;
                                    count_total_question++;
                                    count_correct_question++;
                                    if(enemyPB.getProgress()==0)   // 적의 체력이 0이 되었을 경우 -> WIN
                                        finish(true);
                                    count_word++;
                                }
                                else {                              // 사용자가 입력한 단어가 오답일 경우
                                    hintTV.setText("! "+word+" !");
                                    userPB.setProgress(userPB.getProgress() - 10);
                                    enemyTalk_TV.setText("Parden?");
                                    userPB_TV.setText(String.valueOf(userPB.getProgress()));
                                    isDidIt = true;
                                    count_total_question++;
                                    try {
                                        BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "Wrong_answer_notes.txt", true));
                                        bw.write(word+","+dataArray[count_word].meaning+"\n");
                                        bw.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    if(userPB.getProgress()==0)   // 사용자의 체력이 0이 되었을 경우 -> LOSE
                                        finish(false);
                                    count_word++;
                                }
                            }
                        }
                    });
                    setBtnLayout.addView(wordBtn[i]); // 동적 버튼 추가
                }
                enemyTalk_TV.setText("");
            }
        }.start();
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            countDownTimer_battle.cancel();
            countDownTimer_onStart.cancel();
        } catch (Exception e) {
        }
        countDownTimer_battle = null;
        countDownTimer_onStart = null;
    }

    private void finish(boolean b){
        countDownTimer_battle.cancel();
        boolean result = b;
        Battle_FinishDialog battleFinishDialog = new Battle_FinishDialog(BattleActivity.this);
        battleFinishDialog.callFunction(result,count_correct_question,count_total_question);
    }

    public void readDataFromCsv(InputStream input) throws IOException {

        InputStreamReader streamReader = null;
        streamReader = new InputStreamReader(input, Charset.forName("UTF-8"));
        CSVReader reader = new CSVReader(streamReader); // 1. CSVReader 생성

        String[] nextLine;

        int j = 0;

        while ((nextLine = reader.readNext()) != null) {   // 2. CSV 파일을 한줄씩 읽음
            Log.d("TAG", nextLine[0] + "|" + nextLine[1] + "|"
                    + nextLine[2] + "|" + nextLine[3] +"\n");
            dataArray[j] = new CsvData();
            dataArray[j].number = nextLine[0];
            dataArray[j].words = nextLine[1];
            dataArray[j].meaning = nextLine[2];
            dataArray[j].grade = nextLine[3];
            j++;
            if (j == 30)
                break;
        }
    }

    public class CsvData {
        String number;
        String words;
        String meaning;
        String grade;
    }

    public static String[] shuffle(String[] arr) {
        for (int x = 0; x < arr.length; x++) {
            int i = (int) (Math.random() * arr.length);
            int j = (int) (Math.random() * arr.length);

            String tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return arr;
    }

}