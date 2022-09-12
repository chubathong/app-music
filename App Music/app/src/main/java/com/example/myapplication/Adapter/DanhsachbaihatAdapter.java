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
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.PlayNhacActivity;
import com.example.myapplication.Model.Baihat;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIService;
import com.example.myapplication.Service.Dataservice;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**/
public class DanhsachbaihatAdapter extends RecyclerView.Adapter<DanhsachbaihatAdapter.ViewHolder>{
    Context context;
    ArrayList<Baihat> mangbaihat;

    public DanhsachbaihatAdapter(Context context, ArrayList<Baihat> mangbaihat) {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        /* biến view đi vào trong layout khai bao, anh xa va tương tác với những view layout  */
        View view= inflater.inflate(R.layout.dong_danhsachbaihat,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Baihat baihat=mangbaihat.get(position); /* mỗi dòng item trả ra 1 object ,hứng object mỗi dòng item ,gắn dữ liệu lên mỗi dòng*/
        holder.txtcasi.setText(baihat.getCasi());
        holder.txttenbaihat.setText(baihat.getTenbaihat());
        holder.txtindex.setText(position + 1 + ""); /* position chạy giá trị đầu tiên là 0 */

    }

    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }  /* custom lại danh sách bài hát*/
  /* khai báo và ánh xạ các view bên danh sách bài hát */
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtindex,txttenbaihat,txtcasi;
        ImageView imgluotthich;

      public ViewHolder(@NonNull View itemView) {
          super(itemView);
          txtcasi=itemView.findViewById(R.id.textviewcasidong);
          txtindex=itemView.findViewById(R.id.textviewdanhsachindex);
          txttenbaihat=itemView.findViewById(R.id.textviewtenbaihatdong);
          imgluotthich=itemView.findViewById(R.id.imageviewluotthichdsbh);
          imgluotthich.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  imgluotthich.setImageResource(R.drawable.iconloved);
                  Dataservice dataservice = APIService.getService();
                  Call<String> callback=dataservice.Getupdateluotthich("1",mangbaihat.get(getPosition()).getIdBaihat());
                  /* cần lấy vị trí idbaihat */
                  callback.enqueue(new Callback<String>() {
                      @Override
                      public void onResponse(Call<String> call, Response<String> response) {
                          String ketqua=response.body();
                          if(ketqua.equals("Success")){
                              Toast.makeText(context,"Đã thích",Toast.LENGTH_SHORT).show();
                          }else{
                              Toast.makeText(context,"Bị lỗi",Toast.LENGTH_SHORT).show();
                          }
                      }

                      @Override
                      public void onFailure(Call<String> call, Throwable t) {

                      }
                  });
                  imgluotthich.setEnabled(false);
              }
          });
          itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(context, PlayNhacActivity.class);
                  intent.putExtra("cakhuc",mangbaihat.get(getPosition()));
                  context.startActivity(intent);
              }
          });
      }
  }
}
