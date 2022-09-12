package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Model.Playlist;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaylistAdapter extends ArrayAdapter<Playlist> {
    public PlaylistAdapter(@NonNull Context context, int resource, @NonNull List<Playlist> objects) {
        super(context, resource, objects);
    }
    /* lưu lại giá trị ánh xạ cho lần đầu tiên,lần 2 run có những giá trị trước đó ->gắn dữ liệu vào ->ko cần khởi tạo lại*/
    class ViewHolder{
        TextView txttenplaylist;
        ImageView imgbackground,imgplaylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder=null;/* cấp phát giá trị */
        if(convertView==null){ /* nếu run lần đầu ko có view hiển thị thì bằng null */
            LayoutInflater inflater=LayoutInflater.from(getContext()) ;
            convertView=inflater.inflate(R.layout.dong_playlist,null);
            viewHolder=new ViewHolder();
            viewHolder.txttenplaylist=convertView.findViewById(R.id.textviewtenplaylist);
            viewHolder.imgplaylist=convertView.findViewById(R.id.imageviewplaylist);
            viewHolder.imgbackground=convertView.findViewById(R.id.imageviewbackgroundplaylist);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        Playlist playlist=getItem(position);
        Picasso.with(getContext()).load(playlist.getHinhPlayList()).into(viewHolder.imgbackground);
        Picasso.with(getContext()).load(playlist.getIcon()).into(viewHolder.imgplaylist);
        viewHolder.txttenplaylist.setText(playlist.getTen());
        return convertView;
    }
}
