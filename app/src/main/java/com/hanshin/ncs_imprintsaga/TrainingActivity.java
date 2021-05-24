package com.hanshin.ncs_imprintsaga;

import android.app.Activity;
import android.graphics.Color;
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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class TrainingActivity extends AppCompatActivity{
    public static Activity TrainingPageActivity;

    int dataSize = 100;
    txtData[] dataArray = new txtData[dataSize];
    int wordsTotalLength=0;
    TextView questionTV, timerTV, answerTV, hintTV;
    ProgressBar timerPB;
    int count_word = 0;
    LinearLayout setBtnLayout;
    String word;
    int countWordLength = 0;
    Button wordBtn[] = new Button[10];

    int count_onStart= 3;
    CountDownTimer countDownTimer_onStart;
    int count_timer= 11;
    CountDownTimer countDownTimer_training;

    int count_total_question = 0;
    int count_correct_question = 0;
    boolean isDidIt = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training);
        TrainingPageActivity = TrainingActivity.this;

        questionTV = findViewById(R.id.questionTV_t);
        timerTV = findViewById(R.id.timerTV_t);
        answerTV = findViewById(R.id.answerTV_t);
        hintTV = findViewById(R.id.hintTV_t);
        timerPB = findViewById(R.id.timerPB_t);
        setBtnLayout = findViewById(R.id.wordBtns_t);

        try {
            BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "Wrong_answer_notes.txt"));
            String readStr = "";
            String str = null;
            while (((str = br.readLine()) != null)) {
                readStr += str + "\n";
                wordsTotalLength++;
            }
            br.close();

            String readStrSplit[] = readStr.split("\n"); // 줄 단위로 split

            for (int i = 0; i < wordsTotalLength; i++) {
                String splitter[] = readStrSplit[i].split(","); // 단어와 뜻으로 나누어 dataArray에 저장
                dataArray[i] = new txtData();
                dataArray[i].words = splitter[0];
                dataArray[i].meaning = splitter[1];
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(TrainingActivity.this, "File not Found", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        countDownTimer_onStart();
        countDownTimer_onStart.start();
    }


    public void countDownTimer_onStart(){

        countDownTimer_onStart = new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                hintTV.setText(String.valueOf(count_onStart));
                count_onStart --;
            }
            public void onFinish() {
                countDownTimer_onStart.cancel();
                questionTV.setText(dataArray[count_word].meaning);

                setBtnLayout.removeAllViews(); // 버튼 레이아웃 초기화
                answerTV.setText(""); // 정답창 초기화
                hintTV.setText(""); // 힌트창 초기화
                countWordLength=0;

                word = dataArray[count_word].words; // txt 파일에서 단어를 불러옴
                String splitWord[] = word.split(""); // 단어를 한글자씩 나누어 배열화
                splitWord = shuffle(splitWord); // 배열을 랜덤으로 섞는다

                for (int i = 0; i < word.length(); i++) { // 랜덤으로 섞을 단어배열을 버튼화한다
                    hintTV.setText(hintTV.getText() + splitWord[i]+" ");

                    wordBtn[i] = new Button(TrainingActivity.this);
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
                                    hintTV.setText("That's it!");
                                    isDidIt = true;
                                    count_total_question++;
                                    count_correct_question++;
                                    count_word++;
                                    if(count_word == wordsTotalLength)
                                        finish(count_word);
                                }
                                else {                              // 사용자가 입력한 단어가 오답일 경우
                                    hintTV.setText("! "+word+" !");
                                    isDidIt = true;
                                    count_total_question++;
                                    count_word++;
                                    if(count_word == wordsTotalLength)
                                        finish(count_word);
                                }
                            }
                        }
                    });
                    setBtnLayout.addView(wordBtn[i]); // 동적 버튼 추가
                }
                countDownTimer_battle();
                countDownTimer_training.start();
            }
        };
    }

    public void countDownTimer_battle() {
        countDownTimer_training = new CountDownTimer(11000,2000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerTV.setText(String.valueOf(count_timer));
                timerPB.setProgress(count_timer);
                count_timer--;
                if(count_timer == 0 & !isDidIt) {
                    hintTV.setText("! "+word+" !");
                    count_word++;
                    if(count_word == wordsTotalLength)
                        finish(count_word);
                }
            }

            @Override
            public void onFinish() {
                count_timer = 11;
                isDidIt = false;
                countDownTimer_training.start();
                questionTV.setText(dataArray[count_word].meaning);

                setBtnLayout.removeAllViews(); // 버튼 레이아웃 초기화
                answerTV.setText(""); // 정답창 초기화
                hintTV.setText(""); // 힌트창 초기화
                countWordLength=0;

                word = dataArray[count_word].words; // txt 파일에서 단어를 불러옴
                String splitWord[] = word.split(""); // 단어를 한글자씩 나누어 배열화
                splitWord = shuffle(splitWord); // 배열을 랜덤으로 섞는다

                for (int i = 0; i < word.length(); i++) { // 랜덤으로 섞을 단어배열을 버튼화한다
                    hintTV.setText(hintTV.getText() + splitWord[i]+" ");

                    wordBtn[i] = new Button(TrainingActivity.this);
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
                                    isDidIt = true;
                                    count_total_question++;
                                    count_correct_question++;
                                    count_word++;
                                    if(count_word == wordsTotalLength)
                                        finish(count_word);
                                }
                                else {                              // 사용자가 입력한 단어가 오답일 경우
                                    hintTV.setText("! "+word+" !");
                                    isDidIt = true;
                                    count_total_question++;
                                    count_word++;
                                    if(count_word == wordsTotalLength)
                                        finish(count_word);
                                }
                            }
                        }
                    });
                    setBtnLayout.addView(wordBtn[i]); // 동적 버튼 추가
                }
            }
        }.start();
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            countDownTimer_training.cancel();
            countDownTimer_onStart.cancel();
        } catch (Exception e) {
        }
        countDownTimer_training = null;
        countDownTimer_onStart = null;
    }

    private void finish(int count_word){
        countDownTimer_training.cancel();
        Training_FinishDialog Training_FinishDialog = new Training_FinishDialog(TrainingActivity.this);
        Training_FinishDialog.callFunction(count_word);
    }

    public class txtData {
        String words;
        String meaning;
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
