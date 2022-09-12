package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.DanhsachtatcaalbumActivity;
import com.example.myapplication.Activity.DanhsachtatcachudeActivity;
import com.example.myapplication.Adapter.AlbumAdapter;
import com.example.myapplication.Model.Album;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIService;
import com.example.myapplication.Service.Dataservice;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Album_Hot extends Fragment {
    View view;
    RecyclerView recyclerViewalbum;
    TextView txtxemthemalbum;
    AlbumAdapter albumAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_album_hot,container,false);
        recyclerViewalbum=view.findViewById(R.id.recyclerviewAlbum);
        txtxemthemalbum=view.findViewById(R.id.textviewxemthemalbum);
        txtxemthemalbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhsachtatcaalbumActivity.class);
                startActivity(intent);
            }
        });
        GetData();
        return view;

    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<Album>> callback=dataservice.GetAlbum();
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> albumArrayList=(ArrayList<Album>) response.body();
                albumAdapter=new AlbumAdapter(getActivity(),albumArrayList);
                /* Recycler view yêu cầu truyền vào 1 viewgroup để sắp xếp item ntn*/
                /* ở đây xài LinearLayout*/
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity()); /* truyền vào màn hình getActivity*/
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewalbum.setLayoutManager(linearLayoutManager);
                recyclerViewalbum.setAdapter(albumAdapter);/* hiển thị */

            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }
}
