package com.example.myapplication.Service;

import com.example.myapplication.Model.Album;
import com.example.myapplication.Model.Baihat;
import com.example.myapplication.Model.ChuDe;
import com.example.myapplication.Model.Playlist;
import com.example.myapplication.Model.QuangCao;
import com.example.myapplication.Model.TheLoai;
import com.example.myapplication.Model.Theloaitrongngay;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Dataservice {    /* gửi phương thức và nhận dữ liệu từ phía sẽ server */

    @GET("songbanner.php") /* tương tác link để lấy dữ liệu*/
    Call<List<QuangCao>> GetDataBanner();     /* server trả gì nhận theo vd songbanner trả về mảng */

    @GET("playlistforcurrentday.php") /* tương tác link để lấy dữ liệu*/
    Call<List<Playlist>> GetPlaylistCurrentDay();

    @GET("chudevatheloaitrongngay.php") /* tương tác link để lấy dữ liệu*/
    Call<Theloaitrongngay> GetCategoryMusic();

    @GET("album.php") /* tương tác link để lấy dữ liệu*/
    Call<List<Album>> GetAlbum();

    @GET("baihat.php") /* tương tác link để lấy dữ liệu*/
    Call<List<Baihat>> GetBaiHat();

    @FormUrlEncoded /* để sử dụng phuong thức post */
    @POST("danhsachbaihat.php") /* tương tác client gửi dữ liệu lên server để server trả dữ liệu về */
    Call<List<Baihat>> GetDanhsachbaihattheoquangcao(@Field("idquangcao") String idquangcao);
                                                     /* từ khoá client gửi lên */

    @FormUrlEncoded /* để sử dụng phuong thức post */
    @POST("danhsachbaihat.php") /* tương tác client gửi dữ liệu lên server để server trả dữ liệu về */
    Call<List<Baihat>> GetDanhsachbaihattheoplaylist(@Field("idplaylist") String idplaylist);

    @GET("danhsachcacplaylist.php")
    Call<List<Playlist>> GetDanhsachcacplaylist();

    @FormUrlEncoded /* để sử dụng phuong thức post */
    @POST("danhsachbaihat.php") /* tương tác client gửi dữ liệu lên server để server trả dữ liệu về */
    Call<List<Baihat>> GetdanhsachbaihattheoTheLoai(@Field("idtheloai") String idtheloai);

    @GET("tatcachude.php")
    Call<List<ChuDe>> Getdanhsachtatcachude();


    @FormUrlEncoded /* để sử dụng phuong thức post */
    @POST("theloaitheochude.php") /* tương tác client gửi dữ liệu lên server để server trả dữ liệu về */
    Call<List<TheLoai>> GetTheloaitheochude(@Field("idchude") String idchude);

    @GET("tatcaalbum.php")
    Call<List<Album>> GettatcaAlbum();

    @FormUrlEncoded /* để sử dụng phuong thức post */
    @POST("danhsachbaihat.php") /* tương tác client gửi dữ liệu lên server để server trả dữ liệu về */
    Call<List<Baihat>> Getdanhsachbaihattheoalbum(@Field("idalbum") String idalbum);

    @FormUrlEncoded /* để sử dụng phuong thức post */
    @POST("updateluotthich.php") /* tương tác client gửi dữ liệu lên server để server trả dữ liệu về */
    Call<String> Getupdateluotthich(@Field("luotthich") String luotthich,@Field("idbaihat") String idbaihat);

    @FormUrlEncoded /* để sử dụng phuong thức post */
    @POST("searchtenbaihat.php") /* tương tác client gửi dữ liệu lên server để server trả dữ liệu về */
    Call<List<Baihat>> GetSearchbaihat(@Field("tukhoa") String tukhoa);
}
