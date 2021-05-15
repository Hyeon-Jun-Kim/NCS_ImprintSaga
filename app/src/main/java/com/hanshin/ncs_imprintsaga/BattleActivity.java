package com.hanshin.ncs_imprintsaga;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import org.xmlpull.v1.XmlPullParserFactory;

public class BattleActivity extends AppCompatActivity {

    CsvData[] dataArray = new CsvData[40];
    TextView questionTV, timerTV, answerTV, hintTV;
    ProgressBar userPB, enemyPB, timerPB;
    Button next;
    Handler handler;
    int count = 1;
    int countTime = 10;
    CountDownTimer countDownTimer;
    LinearLayout setBtnLayout;
    String word;
    int countWordLength = 0;
    Button wordBtn[] = new Button[10];

    TextView test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battle);

        questionTV = findViewById(R.id.questionTV);
        timerTV = findViewById(R.id.timerTV);
        answerTV = findViewById(R.id.answerTV);
        hintTV = findViewById(R.id.hintTV);
        userPB = findViewById(R.id.userPB);
        enemyPB = findViewById(R.id.enemyPB);
        timerPB = findViewById(R.id.timerPB);

        next = findViewById(R.id.testbtn);
        setBtnLayout = findViewById(R.id.wordBtns);

        InputStream input = getResources().openRawResource(R.raw.words);
        try {
            readDataFromCsv(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                questionTV.setText(dataArray[count].meaning);

                setBtnLayout.removeAllViews(); // 버튼 레이아웃 초기화
                answerTV.setText(""); // 정답창 초기화
                hintTV.setText(""); // 힌트창 초기화
                countWordLength=0;

                word = dataArray[count].words; // CSC 파일에서 단어를 불러옴
                String splitWord[] = word.split(""); // 단어를 한글자씩 나누어 배열화
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
                                if(word.equals(answerTV.getText())) {
                                    hintTV.setText("HIT!");
                                    enemyPB.setProgress(enemyPB.getProgress() - 1);
                                    count++;
                                } else {
                                    hintTV.setText("! "+word+" !");
                                    userPB.setProgress(userPB.getProgress() - 1);
                                    count++;
                                }
                            }
                        }
                    });
                    setBtnLayout.addView(wordBtn[i]); // 동적 버튼 추가
                }

//        count = 0;
//        //while (userPB.getProgress() == 0 || enemyPB.getProgress() == 0) {};
//        countTime = 10;
//        countDownTimer();
//        countDownTimer.start();
//        while (countTime > -1) {
//            questionTV.setText(dataArray[count].meaning);
//            word = dataArray[count].words;
//            String[] splitWord = word.split("");
//            for (int i = 0; i < splitWord.length; i++) {
//                wordBtn[i].setHeight(20);
//                wordBtn[i].setWidth(20);
//                wordBtn[i].setText(splitWord[i]);
//                setBtnLayout.addView(wordBtn[i]);
//            }
//            //answerTV.setText(word[0]);
//        }

        ////////////////////////////////////////////////////////////////////////////


//                for(int i=0;i<splitWords.length;i++) {
//                    test.setText(splitWords[2]);
//                }
//                countTime = 10;
//                countDownTimer();
//                countDownTimer.start();
            }
        });

    }

    public void readDataFromCsv(InputStream input) throws IOException {

        InputStreamReader streamReader = null;
        streamReader = new InputStreamReader(input, Charset.forName("UTF-8"));
        CSVReader reader = new CSVReader(streamReader); // 1. CSVReader 생성

        String[] nextLine;

        int j = 0;

        while ((nextLine = reader.readNext()) != null) {   // 2. CSV 파일을 한줄씩 읽음
            Log.d("TAG", nextLine[0] + "|" + nextLine[1] + "|"
                    + nextLine[2] + "|" + nextLine[3] + "\n");
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

    public void countDownTimer() {
        countDownTimer = new CountDownTimer(11000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTV.setText(String.valueOf(countTime));
                timerPB.setProgress(countTime);
                countTime--;
            }

            @Override
            public void onFinish() {
            }
        }.start();
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            countDownTimer.cancel();
        } catch (Exception e) {
        }
        countDownTimer = null;
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