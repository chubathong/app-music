package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Adapter.DanhsachbaihatAdapter;
import com.example.myapplication.Model.Album;
import com.example.myapplication.Model.Baihat;
import com.example.myapplication.Model.Playlist;
import com.example.myapplication.Model.QuangCao;
import com.example.myapplication.Model.TheLoai;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIService;
import com.example.myapplication.Service.Dataservice;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachbaihatActivity extends AppCompatActivity {

    QuangCao quangCao;
    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerViewdanhsachbaihat;
    FloatingActionButton floatingActionButton;
    ImageView imgdanhsachcakhuc;
    ArrayList<Baihat> mangbaihat;
    DanhsachbaihatAdapter danhsachbaihatAdapter;
    Playlist playlist;
    TheLoai theLoai;
    Album album;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachbaihat);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        DataIntent();
        anhxa();
        init();/* kh???i t???o */
        /* trong Activity c?? nhi???u object,nhi???u id s??? g???i l??n ????? l???y danh s??ch c??c b??i h??t v???  */
        /* t???o ra ??i???u ki???n b???t tr???ng th??i n???u c?? d??? li???u th?? m???i g???i l??n */
        if(quangCao!=null && !quangCao.getTenBaiHat().equals("")){
            setValueInView(quangCao.getTenBaiHat(), quangCao.getHinhBaiHat());
            GetDataQuangcao(quangCao.getIdquangcao()); /* l???y d??? li???u qu???ng c??o theo idquangcao */
        }
        if(playlist!=null && !playlist.getTen().equals("")){
            setValueInView(playlist.getTen(), playlist.getHinhPlayList());
            GetDataPlaylist(playlist.getIdplaylist()); /* l???y d??? li???u qu???ng c??o theo idplaylist */
        }
        if(theLoai!=null && !theLoai.getTenTheLoai().equals("")){
            setValueInView(theLoai.getTenTheLoai(), theLoai.getHinhTheLoai());
            GetDataTheLoai(theLoai.getIdTheLoai()); /* l???y d??? li???u the loai theo idtheloai */
        }
        if(album!=null && !album.getTenAlbum().equals("")){
            setValueInView(album.getTenAlbum(), album.getHinhAlbum());
            GetDataAlbum(album.getIdAlbum()); /* l???y d??? li???u the loai theo idtheloai */
        }

    }
    private void GetDataAlbum(String idalbum) {
        Dataservice dataservice=APIService.getService();
        Call<List<Baihat>> callback=dataservice.Getdanhsachbaihattheoalbum(idalbum);
        callback.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                mangbaihat=(ArrayList<Baihat>) response.body();
                danhsachbaihatAdapter=new DanhsachbaihatAdapter(DanhsachbaihatActivity.this,mangbaihat);
                recyclerViewdanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                recyclerViewdanhsachbaihat.setAdapter(danhsachbaihatAdapter);
                evenClick();
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });

    }

    private void GetDataTheLoai(String idtheloai) {
        Dataservice dataservice=APIService.getService();
        Call<List<Baihat>> callback=dataservice.GetdanhsachbaihattheoTheLoai(idtheloai);
        callback.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                mangbaihat=(ArrayList<Baihat>) response.body();
                danhsachbaihatAdapter=new DanhsachbaihatAdapter(DanhsachbaihatActivity.this,mangbaihat);
                recyclerViewdanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                recyclerViewdanhsachbaihat.setAdapter(danhsachbaihatAdapter);
                evenClick();
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });

    }

    private void GetDataPlaylist(String idplaylist) {
        Dataservice dataservice=APIService.getService();
        Call<List<Baihat>> callback=dataservice.GetDanhsachbaihattheoplaylist(idplaylist);
        callback.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                mangbaihat=(ArrayList<Baihat>) response.body();
                danhsachbaihatAdapter=new DanhsachbaihatAdapter(DanhsachbaihatActivity.this,mangbaihat);
                recyclerViewdanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                recyclerViewdanhsachbaihat.setAdapter(danhsachbaihatAdapter);
                evenClick();
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });
    }

    private void GetDataQuangcao(String idquangcao) {
        Dataservice dataservice= APIService.getService();
        Call<List<Baihat>> callback = dataservice.GetDanhsachbaihattheoquangcao(idquangcao);
        callback.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                mangbaihat= (ArrayList<Baihat>) response.body();
                danhsachbaihatAdapter=new DanhsachbaihatAdapter(DanhsachbaihatActivity.this,mangbaihat);
                recyclerViewdanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                recyclerViewdanhsachbaihat.setAdapter(danhsachbaihatAdapter);
                evenClick();
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });
    }

    private void setValueInView(String ten,String hinh) {
        /* l???y d??? li???u t??n g???n l??n textview toolbar ,l???y h??nh g??n background collapsing toolbar */
        collapsingToolbarLayout.setTitle(ten); /* set title cho th??nh ph???n co d??n thanh toolbar */
        try{
            URL url=new URL(hinh);
            /* Convert url ????? g???n d??? li???u cho layout collapsing toolbar */
            Bitmap bitmap= BitmapFactory.decodeStream(url.openConnection().getInputStream());
            BitmapDrawable bitmapDrawable=new BitmapDrawable(getResources(),bitmap);
            collapsingToolbarLayout.setBackground(bitmapDrawable);
        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Picasso.with(this).load(hinh).into(imgdanhsachcakhuc);

    }

    private void init(){
        setSupportActionBar(toolbar);/* b???t ch???c n??ng actionbar -> thanh toolbar */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); /* t???o ra c??i view(m??i t??n) tr??? v??? trang tr?????c ???? */
        /* b???t ch???c n??ng actionbar -> thanh toolbar */
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE); /* set Text d??? li???u cho thanh toolbar ????? bik ??ang xem t???i ca kh??c n??o */
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE); /* set Text cho m??u khi ????ng m??? c?? m??u tr???ng*/
        floatingActionButton.setEnabled(false);
    }
    private void anhxa() {
        coordinatorLayout=findViewById(R.id.coordinatorlayout);
        collapsingToolbarLayout=findViewById(R.id.collapsingtoolbar);
        toolbar=findViewById(R.id.toolbardanhsach);
        recyclerViewdanhsachbaihat=findViewById(R.id.recyclerviewdanhsachbaihat);
        floatingActionButton=findViewById(R.id.floatingactionbutton);
        imgdanhsachcakhuc=findViewById(R.id.imageviewdanhsachcakhuc);
    }

    private void DataIntent() {
        Intent intent = getIntent();
        if(intent !=null)
        {
            if(intent.hasExtra("banner"))
            {
                quangCao=(QuangCao) intent.getSerializableExtra("banner");

            }
            if(intent.hasExtra("itemplaylist"))
            {
                playlist=(Playlist) intent.getSerializableExtra("itemplaylist");
            }
            if(intent.hasExtra("idtheloai"))
            {
                theLoai=(TheLoai) intent.getSerializableExtra("idtheloai");
            }
            if(intent.hasExtra("album"))
            {
                album=(Album) intent.getSerializableExtra("album");
            }
        }
    }
    private  void evenClick(){
        floatingActionButton.setEnabled(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(DanhsachbaihatActivity.this,PlayNhacActivity.class);
                intent.putExtra("cacbaihat",mangbaihat);
                startActivity(intent);
            }
        });
    }
}