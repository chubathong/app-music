package com.example.myapplication.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Playlist implements Serializable {

@SerializedName("Idplaylist")
@Expose
private String idplaylist;
@SerializedName("Ten")
@Expose
private String ten;
@SerializedName("HinhPlayList")
@Expose
private String hinhPlayList;
@SerializedName("Icon")
@Expose
private String icon;

public String getIdplaylist() {
return idplaylist;
}

public void setIdplaylist(String idplaylist) {
this.idplaylist = idplaylist;
}

public String getTen() {
return ten;
}

public void setTen(String ten) {
this.ten = ten;
}

public String getHinhPlayList() {
return hinhPlayList;
}

public void setHinhPlayList(String hinhPlayList) {
this.hinhPlayList = hinhPlayList;
}

public String getIcon() {
return icon;
}

public void setIcon(String icon) {
this.icon = icon;
}

}