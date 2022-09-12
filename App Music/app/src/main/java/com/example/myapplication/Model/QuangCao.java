package com.example.myapplication.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QuangCao implements Serializable {
    /* implements Serializable :cho phép chuyển dữ liệu dưới dạng Object*/

@SerializedName("Idquangcao")
@Expose
private String idquangcao;
@SerializedName("Hinhanh")
@Expose
private String hinhanh;
@SerializedName("Noidung")
@Expose
private String noidung;
@SerializedName("Idbaihat")
@Expose
private String idbaihat;
@SerializedName("TenBaiHat")
@Expose
private String tenBaiHat;
@SerializedName("HinhBaiHat")
@Expose
private String hinhBaiHat;

public String getIdquangcao() {
return idquangcao;
}

public void setIdquangcao(String idquangcao) {
this.idquangcao = idquangcao;
}

public String getHinhanh() {
return hinhanh;
}

public void setHinhanh(String hinhanh) {
this.hinhanh = hinhanh;
}

public String getNoidung() {
return noidung;
}

public void setNoidung(String noidung) {
this.noidung = noidung;
}

public String getIdbaihat() {
return idbaihat;
}

public void setIdbaihat(String idbaihat) {
this.idbaihat = idbaihat;
}

public String getTenBaiHat() {
return tenBaiHat;
}

public void setTenBaiHat(String tenBaiHat) {
this.tenBaiHat = tenBaiHat;
}

public String getHinhBaiHat() {
return hinhBaiHat;
}

public void setHinhBaiHat(String hinhBaiHat) {
this.hinhBaiHat = hinhBaiHat;
}

}