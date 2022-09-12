package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.myapplication.Activity.DanhsachbaihatActivity;
import com.example.myapplication.Model.QuangCao;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {
    Context context;
    ArrayList<QuangCao> arrayListbanner;
    /* alt + insert */

    public BannerAdapter(Context context, ArrayList<QuangCao> arrayListbanner) {
        this.context = context;
        this.arrayListbanner = arrayListbanner;
    }

    @Override
    public int getCount() {/* vẽ bao nhiêu page trong viewpager */
        return arrayListbanner.size();   /* trong mảng có bao nhiêu hình thì sẽ vẽ bấy nhiệu pager */
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dong_banner,null);
        ImageView imgbackgroundbanner=view.findViewById(R.id.imageviewbackgroundbanner);
        ImageView imgsongbanner=view.findViewById(R.id.imageviewbanner);
        TextView txttitlesongbanner=view.findViewById(R.id.textviewtitlebannerbaihat);
        TextView txtnoidung=view.findViewById(R.id.textviewnoidung);
        Picasso.with(context).load(arrayListbanner.get(position).getHinhanh()).into(imgbackgroundbanner);
        Picasso.with(context).load(arrayListbanner.get(position).getHinhBaiHat()).into(imgsongbanner);
        txttitlesongbanner.setText(arrayListbanner.get(position).getTenBaiHat());
        txtnoidung.setText(arrayListbanner.get(position).getNoidung());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* intent chuyển màn hình ( danh sách bài hát) */
                Intent intent=new Intent(context, DanhsachbaihatActivity.class);
                /* đẩy dữ liệu đi */ /* cần phải cast đối tượng về Object trước(implements Serializable model) */
                intent.putExtra("banner",arrayListbanner.get(position));
                context.startActivity(intent);
            }
        });
        /* định hình gán dữ liệu cho mỗi object tượng trưng mỗi page*/
        /* view mẫu cho mỗi page ,page khác sẽ nhìn view mẫu này sẽ thiết kế những page còn lại*/
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
