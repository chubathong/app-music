package com.example.myapplication.Service;

public class APIService {   /* kết hợp apiservice và dataservice  */
   // private static String base_url="https://phatnguyena.000webhostapp.com/Server/";
      private static String base_url="https://appnhacne.000webhostapp.com/Server/";

    public static Dataservice getService(){
        return  APIRecofitClient.getClient(base_url).create(Dataservice.class);  /* khởi tạo phương thức dataservice */
    }
}
