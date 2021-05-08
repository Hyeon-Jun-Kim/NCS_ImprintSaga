package com.hanshin.ncs_imprintsaga;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MypageViewAdapter extends BaseAdapter {
    Context context;


    //그리드뷰 이미지 타이틀
    String[] mypageListTitle=  {

    };

    //개인페이지에 내가 갖고 있는 아이템 이미지 리스트
    Integer[] mypageListImage = {

    };



    Integer[] mypageListPrice ={

    };

    public MypageViewAdapter(Context c) {
        context = c;
    }



    @Override
    public int getCount() {
        return mypageListImage.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mypage_list_image, parent, false);
        }
        ImageView image = convertView.findViewById(R.id.mypage_ListImage);
        TextView text = convertView.findViewById(R.id.mypage_ListTV);


        image.setImageResource(mypageListImage[position]);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        image.setPadding(20,20,20,20);

        return  convertView;
    }
}
