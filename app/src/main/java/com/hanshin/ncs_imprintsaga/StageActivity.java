package com.hanshin.ncs_imprintsaga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

public class StageActivity extends AppCompatActivity {
    Button main_MY, main_SHOP, main_SETTING, main_TRAINING;
    ScrollView scrollview;
    Button stageBtn[] = new Button[9];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stage);

        main_MY = findViewById(R.id.main_my_btn);
        main_SHOP = findViewById(R.id.main_shop_btn);
        main_SETTING = findViewById(R.id.main_setting_btn);
        main_TRAINING = findViewById(R.id.main_training_btn);
        scrollview = findViewById(R.id.scrollview);
        




        main_MY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent);
            }
        });
        main_SHOP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
                startActivity(intent);
            }
        });
        main_SETTING.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
        main_TRAINING.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        for(int i =0;i<9;i++){
            int k = getResources().getIdentifier("main_stage1_"+(i+1), "id", getPackageName());
            stageBtn[i] = findViewById( k );
            stageBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mapSelectDialog mapSelectDialog = new mapSelectDialog(StageActivity.this);
                    mapSelectDialog.callFunction();
                }
            });
        }


    }
}