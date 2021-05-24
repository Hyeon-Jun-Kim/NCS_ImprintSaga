package com.hanshin.ncs_imprintsaga;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class Battle_FinishDialog {
    private Context context;

    public Battle_FinishDialog(Context context) {
        this.context = context;
    }

    public void callFunction(boolean b , int correctNum, int totalNum) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.stage_end_info);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        int correct = correctNum;
        int total = totalNum;
        double answerRate = ((double)correct/total)*100; // 정답률

        int rank;
        if(answerRate<40)
            rank = 1;
        else if(answerRate<80)
            rank = 2;
        else
            rank = 3;

        final TextView resultTv = (TextView) dlg.findViewById(R.id.resultTv_onBattle);
        if(b)
            resultTv.setText("WIN");
        else
            resultTv.setText("LOSE");

        final RatingBar rankRb = (RatingBar) dlg.findViewById(R.id.rankRb_onBattle);
        rankRb.setRating(rank);

        final TextView rateTv = (TextView) dlg.findViewById(R.id.rateTv_onBattle);
        rateTv.setText(rateTv.getText() + String.valueOf(correctNum) + "/" + String.valueOf(totalNum));

        final Button endBtn = (Button) dlg.findViewById(R.id.endBtn_onBattle);
        final BattleActivity Ba = (BattleActivity)BattleActivity.BattlePageActivity;
        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ba.finish();
            }
        });

        final Button retryBtn = (Button) dlg.findViewById(R.id.retryBtn_onBattle);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ba.finish();
                Intent intent=new Intent();
                intent.setClass(Ba, Ba.getClass());
                Ba.startActivity(intent);
            }
        });
    }
}
