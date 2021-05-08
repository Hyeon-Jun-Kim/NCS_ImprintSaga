package com.hanshin.ncs_imprintsaga;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopViewAdapter extends BaseAdapter {
    Context context;

    //그리드뷰 이미지 저장위치
    Integer[] shopListImage ={
            R.drawable.item1, R.drawable.item2, R.drawable.item3,
            R.drawable.item4, R.drawable.item5, R.drawable.item6,
            R.drawable.item7, R.drawable.item8, R.drawable.item9
    };

    //그리드뷰 이미지 가격
    Integer[] shopListPrice ={
            1000, 3000, 5000,
            500, 1000, 2000,
            800, 1500, 3000
    };

    //그리드뷰 이미지 타이틀
    String[] shopListTitle=  {
            "item1", "item2", "item3",
            "item4", "item5", "item6",
            "item7", "item8", "item9"
    };
    public ShopViewAdapter(Context c) {
        context = c;
    }

    @Override
    public int getCount() {
        return shopListImage.length;
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
            convertView = inflater.inflate(R.layout.shop_list_image, parent, false);
        }
        ImageView image = convertView.findViewById(R.id.shopListImage);
        TextView textView  = convertView.findViewById(R.id.shopListTV);

        image.setImageResource(shopListImage[position]);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        image.setPadding(20,20,20,20);
        textView.setText(shopListPrice[position].toString());

        return  convertView;
    }
}
