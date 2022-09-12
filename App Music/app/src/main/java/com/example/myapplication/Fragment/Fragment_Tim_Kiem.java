package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.SearchBaiHatAdapter;
import com.example.myapplication.Model.Baihat;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIService;
import com.example.myapplication.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Tim_Kiem extends Fragment {
    View view;
    Toolbar toolbarsearchbaihat;
    RecyclerView recyclerViewsearchbaihat;
    TextView textViewkhongcodulieu;
    SearchBaiHatAdapter searchBaiHatAdapter;
    ArrayList<Baihat> mangbaihat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_tim_kiem,container,false);
        toolbarsearchbaihat=view.findViewById(R.id.toolbarsearchbaihat);
        recyclerViewsearchbaihat=view.findViewById(R.id.recyclerviewsearchbaihat);
        textViewkhongcodulieu=view.findViewById(R.id.textviewkhongdulieu);
        /* vì đây ko phải là 1 activity nên ko hiện dc toolbar , nên phải tạo ra AppCompatActivity*/
    //    ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarsearchbaihat);
        toolbarsearchbaihat.setTitle("");
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.searchview,menu);
        MenuItem menuItem=menu.findItem(R.id.menusearch);

       SearchView searchView=(SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE); /* độ dài */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /* sk lắng nghe khi text thay đổi*/
            @Override
            public boolean onQueryTextSubmit(String query) {
                /* nhập đẩy đủ nhấn enter sẽ tìm kiếm theo phương thức này */

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /* khi text thay đổi bắt sk search ngay */
                SearchTuKhoaBaiHat(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void SearchTuKhoaBaiHat(String tukhoa){
        Dataservice dataservice = APIService.getService();
        Call<List<Baihat>> callback = dataservice.GetSearchbaihat(tukhoa);
        callback.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                    mangbaihat=(ArrayList<Baihat>) response.body();
                    if(mangbaihat.size()>0){
                        searchBaiHatAdapter =new SearchBaiHatAdapter(getActivity(),mangbaihat);
                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
                        recyclerViewsearchbaihat.setLayoutManager(linearLayoutManager);
                        recyclerViewsearchbaihat.setAdapter(searchBaiHatAdapter);
                        textViewkhongcodulieu.setVisibility(View.GONE);
                        recyclerViewsearchbaihat.setVisibility(View.VISIBLE);
                    }else{
                        recyclerViewsearchbaihat.setVisibility(View.GONE);
                        textViewkhongcodulieu.setVisibility(View.VISIBLE);
                    }
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });
    }
}
