package com.example.myapplication.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRecofitClient {   /* lay du lieu tu server */
    private static Retrofit retrofit=null;
    public static Retrofit getClient(String base_url){
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                                    .readTimeout(10000, TimeUnit.MILLISECONDS)/* chờ dữ liệu quá lâu từ server thì sẽ ngắt*/
                                    .writeTimeout(10000,TimeUnit.MILLISECONDS)
                                    .connectTimeout(10000,TimeUnit.MILLISECONDS) /* đợi quá lâu thì ngắt kết nối */
                                    .retryOnConnectionFailure(true) /* lỗi mạng sẽ cố gắng kết nối */
                                    .protocols(Arrays.asList(Protocol.HTTP_1_1))
                                    .build();
        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .client(okHttpClient) /*  tượng tác của mạng */
                    .addConverterFactory(GsonConverterFactory.create(gson)) /* convert dữ liệu API -> java  */
                    .build();
        return retrofit;
    }
}
