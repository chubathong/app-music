package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Adapter.BannerAdapter;
import com.example.myapplication.Model.QuangCao;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIService;
import com.example.myapplication.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Banner extends Fragment {
    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    BannerAdapter bannerAdapter;
    Runnable runnable;
    Handler handler;
    int currentItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner,container,false);
        GetData();
        anhxa();
        return view;
    }

    private void anhxa() {
        viewPager=view.findViewById(R.id.viewpager);
        circleIndicator=view.findViewById(R.id.indicatordetault);

    }

    private void GetData(){
        Dataservice dataservice= APIService.getService();
        Call<List<QuangCao>> callback=dataservice.GetDataBanner();
        callback.enqueue(new Callback<List<QuangCao>>() {
            @Override
            public void onResponse(Call<List<QuangCao>> call, Response<List<QuangCao>> response) {/* lắng nghe kết quả trả về*/
                ArrayList<QuangCao> banners= (ArrayList<QuangCao>) response.body();
                bannerAdapter=new BannerAdapter(getActivity(),banners);
                viewPager.setAdapter(bannerAdapter);
                circleIndicator.setViewPager(viewPager);
                handler=new Handler();
                runnable=new Runnable() {   /* lắng nghe Handler */
                    @Override
                    public void run() {
                          currentItem=viewPager.getCurrentItem();
                          currentItem++;
                          if(currentItem >=viewPager.getAdapter().getCount())
                              /* check chuyen qua page khac nhung ko con page do */
                              /* vd co 5 page qua page 6 ko có*/
                          {
                              currentItem=0;
                          }
                          viewPager.setCurrentItem(currentItem,true);
                          handler.postDelayed(runnable,4500);
                    }
                };
                handler.postDelayed(runnable,4500);
            }

            @Override
            public void onFailure(Call<List<QuangCao>> call, Throwable t) { /* lắng nghe kết quả thất bại*/

            }
        }); /* sự kiện lắng nghe  */
    }
}
