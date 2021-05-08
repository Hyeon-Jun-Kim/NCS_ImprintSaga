package com.hanshin.ncs_imprintsaga;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import org.xmlpull.v1.XmlPullParserFactory;

public class BattleActivity extends AppCompatActivity {

    CsvData[] dataArray = new CsvData[30];
    TextView questionTV,timerTV,answerTV,hintTV;
    ProgressBar userPB,enemyPB,timerPB;
    Button next;
    int count;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battle);

        questionTV = findViewById(R.id.questionTV);
        timerTV = findViewById(R.id.timerTV);
        answerTV = findViewById(R.id.answerTV);
        hintTV = findViewById(R.id.hintTV);
        userPB =findViewById(R.id.userPB);
        enemyPB =findViewById(R.id.enemyPB);
        timerPB =findViewById(R.id.timerPB);

        next = findViewById(R.id.testbtn);

        count = 0;

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
                answerTV.setText(dataArray[count].words);
            }
        });

        //while (count < dataArray.length) { }
    }

    public class CsvData{
        String number;
        String words;
        String meaning;
        String grade;
    }
}