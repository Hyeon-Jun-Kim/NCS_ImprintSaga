package com.hanshin.ncs_imprintsaga;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class mapSelectDialog {
    private Context context;

    public mapSelectDialog(Context context) {
        this.context = context;
    }

    public void callFunction() {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.stage_info);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final TextView stageTv = (TextView) dlg.findViewById(R.id.stageTv);
        final RatingBar rankRb = (RatingBar) dlg.findViewById(R.id.rankRb);
        final TextView levelTv = (TextView) dlg.findViewById(R.id.levelTb);
        final TextView rateTv = (TextView) dlg.findViewById(R.id.rateTv);
        final Button selectBtn = (Button) dlg.findViewById(R.id.selectBtn);

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), BattleActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
