package com.example.myapplication.Fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_DiaNhac extends Fragment {
    View view;
    CircleImageView circleImageView;
    ObjectAnimator objectAnimator; /* khi click vào sẽ tạo ra các hoạt ảnh*/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dianhac,container,false);
        circleImageView=view.findViewById(R.id.imageviewcircle);
        /* hình hiển thị , thuộc tính xoay , giá trị bắt đầu quay,giá trị kết thúc*/
        objectAnimator=ObjectAnimator.ofFloat(circleImageView,"rotation",0f,360f);
        /* duration : chạy trong bao nhiêu miligiây*/
        objectAnimator.setDuration(10000);
        /* khi chạy xong 10 lần , sẽ lặm tiếp*/
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        /* Interpolator : tránh tình trạng xoay xong 1 vòng thì sẽ dừng lại 1 lúc */
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
        return view;
    }
    public void Playnhac(String hinhanh) {
        Picasso.with(getActivity()).load(hinhanh).into(circleImageView);
    }
}
