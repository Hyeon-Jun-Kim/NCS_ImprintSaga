package com.hanshin.ncs_imprintsaga;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyPageActivity extends AppCompatActivity {
    ImageView mypage_charIv;
    ImageButton mypage_closeBtn;
    TextView mypage_levelTv;
    TextView mypage_pointTv;
    TextView mypage_stageTv;
    TextView mypage_hpTv;
    TextView mypage_atkTv;
    TextView mypage_dfdTv;
    TextView mypage_skillTv ;
    GridView mypage_gridview;
    ImageButton medal1, medal2, medal3, medal4, medal5;
    ProgressBar mypage_achievementPb, mypage_answerratePb;

    //그리드뷰 이미지 타이틀
    String[] shopListTitle=  {
            "character1", "character2", "character3",
            "pet1", "pet2", "pet3",
            "weapon1", "weapon2", "weapon3"
    };
//    //그리드뷰 이미지 저장위치
//    Integer[] shopListImage ={
//            R.drawable.char1, R.drawable.char2, R.drawable.char3,
//            R.drawable.pet1, R.drawable.pet2, R.drawable.pet3,
//            R.drawable.wp1, R.drawable.wp2, R.drawable.wp3
//    };
    //그리드뷰 이미지 가격
    Integer[] shopPriceListID ={
            1000, 3000, 5000,
            500, 1000, 2000,
            800, 1500, 3000
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        //위젯 연결
        mypage_charIv = findViewById(R.id.mypage_charIv);  // 캐릭터 이미지
        mypage_closeBtn = findViewById(R.id.mypage_closeBtn); // 창닫기 버튼
        //데이터 위젯
        mypage_levelTv = findViewById(R.id.mypage_levelTv); // 레벨
        mypage_pointTv = findViewById(R.id.mypage_pointTv); // 포인트
        mypage_stageTv = findViewById(R.id.mypage_stageTv); // 스테이지
        mypage_hpTv = findViewById(R.id.mypage_hpTv); //HP
        mypage_atkTv = findViewById(R.id.mypage_atkTv); //공격력
        mypage_dfdTv = findViewById(R.id.mypage_dfdTv); //방어력
        mypage_skillTv = findViewById(R.id.mypage_skillTv); //능력

        mypage_gridview = findViewById(R.id.mypage_gridview); //그리드뷰
        medal1 = findViewById(R.id.medal1); medal2 = findViewById(R.id.medal2); //메달
        medal3 = findViewById(R.id.medal3); medal4 = findViewById(R.id.medal4);
        medal5 = findViewById(R.id.medal5);
        mypage_achievementPb = findViewById(R.id.mypage_achievementPb); //달성도
        mypage_answerratePb = findViewById(R.id.mypage_answerratePb);//정답률

        //개인페이지 그리드뷰 및 어댑터 설정
        Adapter adapter = new MypageViewAdapter(this);
        mypage_gridview.setAdapter(( MypageViewAdapter) adapter);

        //파이어베이스 데이터 정보가져오기
        FirebaseFirestore db = FirebaseFirestore.getInstance();
         db.collection("mypage").document("item"). get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                 DocumentSnapshot document = task.getResult();
                 //객체(MYPage_Item)에 뿌려주기
                 com.hanshin.ncs_imprintsaga.MyPage_Item item = (com.hanshin.ncs_imprintsaga.MyPage_Item) document.toObject(com.hanshin.ncs_imprintsaga.MyPage_Item.class);
                 //파이어베이스에서 데이터 가져와서, 각 위젯에 데이터 설정해주기.
                //클래스 객체 필드와 파이어베이스 필드명 같아야함 (틀리면 값을 못가져온다)
                 mypage_levelTv.setText(item.getLevel());
                 mypage_pointTv.setText(item.getPoint());
                 mypage_stageTv.setText(item.getStage());
                 mypage_hpTv.setText(item.getHp());
                 mypage_atkTv.setText(item.getAtk());
                 mypage_dfdTv.setText(item.getDfd());
                 mypage_skillTv.setText(item.getSkill());
             }
         });


//        //메달숨기기
//        medal1.setVisibility(View.GONE);

        // 달성도 프로그레스바 설정
        mypage_achievementPb.setProgress(40);
        // 정답률 프로그레스바 설정
        mypage_answerratePb.setProgress(80);
    }
}
