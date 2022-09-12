package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.ViewPagerPlaylistnhacAdapter;
import com.example.myapplication.Fragment.Fragment_DiaNhac;
import com.example.myapplication.Fragment.Fragment_Play_DanhSachCacBaiHat;
import com.example.myapplication.Model.Baihat;
import com.example.myapplication.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayNhacActivity extends AppCompatActivity {
    Toolbar toolbarplaynhac;
    TextView txtTimesong,txtTotaltimesong;
    SeekBar sktime;
    ImageButton imgplay,imgpre,imgnext,imgrepeat,imgrandom;
    ViewPager viewPagerplaynhac;
    public static ArrayList<Baihat> mangbaihat= new ArrayList<>();
    public static ViewPagerPlaylistnhacAdapter adapternhac;
    Fragment_DiaNhac fragment_diaNhac;
    Fragment_Play_DanhSachCacBaiHat fragment_play_danhSachCacBaiHat;
    MediaPlayer mediaPlayer; /* thư viện để play 1 ca khúc nào đó  */
    int position=0;
    boolean repeat = false;
    boolean checkrandom=false;
    boolean next=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GetDataFromIntent();
        init();
        eventClick();
    }

    private void eventClick() { /* khi nhấn nút play sẽ thay đổi hình dạng nút */
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(adapternhac.getItem(1) !=null)
                {
                    if(mangbaihat.size()>0){
                        fragment_diaNhac.Playnhac(mangbaihat.get(0).getHinhbaihat());
                        handler.removeCallbacks(this);
                    }else{
                        handler.postDelayed(this,300);
                    }
                }
            }
        },500);
        imgplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imgplay.setImageResource(R.drawable.iconplay);
                }else{
                    mediaPlayer.start();
                    imgplay.setImageResource(R.drawable.iconpause);
                }
            }
        });
        imgrepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(repeat==false){ /* click 1 trong 2 repeat hoặc random */
                    /* khi chưa chọn repeat giá trị repeat mặc dinh la false */
                    /* sau đó check giá trị random có tồn tại hay không */
                    if(checkrandom==true){
                        checkrandom=false;
                        imgrepeat.setImageResource(R.drawable.iconsyned);
                        imgrandom.setImageResource(R.drawable.iconsuffle);
                    }
                    imgrepeat.setImageResource(R.drawable.iconsyned);
                    repeat=true;
                }else{
                    imgrepeat.setImageResource(R.drawable.iconrepeat);
                    repeat=false;
                }
            }
        });
        imgrandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkrandom==false){ /* click 1 trong 2 repeat hoặc random */
                    /* khi chưa chọn random giá trị random mặc dinh la false */
                    /* sau đó check giá trị repeat có tồn tại hay không */
                    if(repeat==true){
                        repeat=false;
                        imgrandom.setImageResource(R.drawable.iconshuffled);
                        imgrepeat.setImageResource(R.drawable.iconrepeat);
                    }
                    imgrandom.setImageResource(R.drawable.iconshuffled);/* repeat ==false */
                    checkrandom=true;
                }else{
                    imgrandom.setImageResource(R.drawable.iconsuffle);
                    checkrandom=false;
                }
            }
        });
        sktime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /*hàm này để lắng nghe khi có sự thay đổi seekbar*/
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                /* khi người dùng cầm cục thumb kéo đến đâu rồi bỏ ra thì sẽ phát nhạc từ khúc đó trở đi */
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        imgnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mangbaihat.size()>0){
                    if(mediaPlayer.isPlaying()  || mediaPlayer !=null){/* kiểm tra 1 ca khúc dang hát */
                        mediaPlayer.stop();
                        mediaPlayer.release();/* đồng bộ */
                        mediaPlayer=null;
                    }
                    if(position < (mangbaihat.size())){ /* đk position< 0, nếu vượt index ko lấy vị trí được*/
                        imgplay.setImageResource(R.drawable.iconpause);
                        position++;
                        if(repeat==true){
                            if(position==0){/* khi click repeat giá trị = 0 */
                                position=mangbaihat.size();
                            }
                            position -= 1;/* khi next function chuyển bài hát sẽ +1 position */
                            /* và muốn giá trị trở lại như cũ thì -1 */
                        }
                        if(checkrandom==true){
                            Random random=new Random();
                            int index =random.nextInt(mangbaihat.size());
                            if(index==position) /* trường hợp index = position */
                            {
                                position=index-1;
                            }
                            position=index;

                        }
                        if(position > (mangbaihat.size()-1)){
                            position = 0;
                        }
                        new PlayMp3().execute(mangbaihat.get(position).getLinkbaihat());
                        fragment_diaNhac.Playnhac(mangbaihat.get(position).getHinhbaihat());
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenbaihat());
                        UpdateTime();
                    }
                }
        /*  khi next hoặc pre cho 1 khoảng thời gian, tránh trường hợp người dùng click nhiều quá */
                imgpre.setClickable(false);
                imgnext.setClickable(false);
                Handler handler1=new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgpre.setClickable(true);
                        imgnext.setClickable(true);
                    }
                },5000);
            }
        });
        imgpre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mangbaihat.size()>0){
                    if(mediaPlayer.isPlaying()  || mediaPlayer !=null){/* kiểm tra 1 ca khúc dang hát */
                        mediaPlayer.stop();
                        mediaPlayer.release();/* đồng bộ */
                        mediaPlayer=null;
                    }
                    if(position < (mangbaihat.size())){ /* đk position< 0, nếu vượt index ko lấy vị trí được*/
                        imgplay.setImageResource(R.drawable.iconpause);
                        position--;

                        if(position <0){
                            position=mangbaihat.size()-1;
                        }
                        if(repeat==true){
                            position += 1;/* khi next function chuyển bài hát sẽ +1 position */
                            /* và muốn giá trị trở lại như cũ thì -1 */
                        }
                        if(checkrandom==true){
                            Random random=new Random();
                            int index =random.nextInt(mangbaihat.size());
                            if(index==position) /* trường hợp index = position */
                            {
                                position=index-1;
                            }
                            position=index;

                        }
                        new PlayMp3().execute(mangbaihat.get(position).getLinkbaihat());
                        fragment_diaNhac.Playnhac(mangbaihat.get(position).getHinhbaihat());
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenbaihat());
                        UpdateTime();
                    }
                }
                /*  khi next hoặc pre cho 1 khoảng thời gian, tránh trường hợp người dùng click nhiều quá */
                imgpre.setClickable(false);
                imgnext.setClickable(false);
                Handler handler1=new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgpre.setClickable(true);
                        imgnext.setClickable(true);
                    }
                },5000);
            }
        });
    }

    private void GetDataFromIntent() {
        Intent intent =getIntent();
        mangbaihat.clear();/* clear mảng tránh để dữ liệu cũ chồng lên nhau*/
        if(intent != null){  /* nếu dữ liệu ko có*/
            if(intent.hasExtra("cakhuc")){
                Baihat baihat=intent.getParcelableExtra("cakhuc");
                mangbaihat.add(baihat);
            }
            if(intent.hasExtra("cacbaihat")){
                ArrayList<Baihat> baihatArrayList =intent.getParcelableArrayListExtra("cacbaihat");
                mangbaihat= baihatArrayList;
            }
        }
    }

    private void init(){
        toolbarplaynhac=findViewById(R.id.toolbarplaynhac);
        txtTimesong=findViewById(R.id.textviewtimesong);
        txtTotaltimesong=findViewById(R.id.textviewtotaltimesong);
        sktime=findViewById(R.id.seekbarsong);
        imgnext=findViewById(R.id.imagebuttonnext);
        imgplay=findViewById(R.id.imagebuttonplay);
        imgrepeat=findViewById(R.id.imagebuttonrepeat);
        imgpre=findViewById(R.id.imagebuttonpre);
        imgrandom=findViewById(R.id.imagebuttonsuffle);
        viewPagerplaynhac=findViewById(R.id.viewpagerplaynhac);
        setSupportActionBar(toolbarplaynhac);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarplaynhac.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mediaPlayer.stop();/* kiểm tra khi đang hát thì dừng */
                mangbaihat.clear();
/* clear mảng để khi người dùng lần sau chọn 1 danh sách mới thì sẽ add ca khúc mới chứ không add chồng lên nhau  */
            }
        });
        toolbarplaynhac.setTitleTextColor(Color.WHITE);
        fragment_diaNhac=new Fragment_DiaNhac();
        fragment_play_danhSachCacBaiHat=new Fragment_Play_DanhSachCacBaiHat();
        adapternhac=new ViewPagerPlaylistnhacAdapter(getSupportFragmentManager());
        adapternhac.AddFragment(fragment_play_danhSachCacBaiHat);
        adapternhac.AddFragment(fragment_diaNhac);
        viewPagerplaynhac.setAdapter(adapternhac);
        fragment_diaNhac= (Fragment_DiaNhac) adapternhac.getItem(1);
        if(mangbaihat.size()>0){
            getSupportActionBar().setTitle(mangbaihat.get(0).getTenbaihat());
            new PlayMp3().execute(mangbaihat.get(0).getLinkbaihat());
            imgplay.setImageResource(R.drawable.iconpause);
        }
    }
    class PlayMp3 extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {/* trả về đường link nhạc */
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) { /* nhận dữ liệu từ doInBackground */
            super.onPostExecute(baihat);
            try {
            mediaPlayer=new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);/* play ca khúc online */
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                /* media player -> bất đồng bộ  */
                @Override
                public void onCompletion(MediaPlayer mp) {
     /* khi đưa vào 1 ca khúc (vd thời gian cập nhật lâu quá thì nhảy vô 1 con số nào đó thì sẽ bị lỗi)  */
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            });
                mediaPlayer.setDataSource(baihat);
                mediaPlayer.prepare(); /*  để cho media phát nhạc được */
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            TimeSong();
            UpdateTime();
        }
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");  /* format chuỗi */
        txtTotaltimesong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        sktime.setMax(mediaPlayer.getDuration());/* thơi gian dài bao nhiêu thì sẽ tương ứng với % seekbar đang kéo */
    }
    private void UpdateTime(){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null){ /* check media nếu null */
                    sktime.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("mm:ss");
                    txtTimesong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this,300);/* mỗi 3s cập nhật thời gian */
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        /* lắng nghe sau khi media hoàn tất */
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            next=true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        },300);
        Handler handler1=new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(next==true){
                    if(position < (mangbaihat.size())){ /* đk position< 0, nếu vượt index ko lấy vị trí được*/
                        imgplay.setImageResource(R.drawable.iconpause);
                        position++;
                        if(repeat==true){
                            if(position==0){/* khi click repeat giá trị = 0 */
                                position=mangbaihat.size();
                            }
                            position -= 1;/* khi next function chuyển bài hát sẽ +1 position */
                            /* và muốn giá trị trở lại như cũ thì -1 */
                        }
                        if(checkrandom==true){
                            Random random=new Random();
                            int index =random.nextInt(mangbaihat.size());
                            if(index==position) /* trường hợp index = position */
                            {
                                position=index-1;
                            }
                            position=index;

                        }
                        if(position > (mangbaihat.size()-1)){
                            position = 0;
                        }
                        new PlayMp3().execute(mangbaihat.get(position).getLinkbaihat());
                        fragment_diaNhac.Playnhac(mangbaihat.get(position).getHinhbaihat());
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenbaihat());
                    }
                /*  khi next hoặc pre cho 1 khoảng thời gian, tránh trường hợp người dùng click nhiều quá */
                imgpre.setClickable(false);
                imgnext.setClickable(false);
                Handler handler1=new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgpre.setClickable(true);
                        imgnext.setClickable(true);
                    }
                },5000);
                /* sau khi chuyển bài */
                next=false;
                handler1.removeCallbacks(this);/* xoá đi sk lắng nghe */
                }else{
                    handler1.postDelayed(this,1000);
                }
            }
        },1000);
    }
}