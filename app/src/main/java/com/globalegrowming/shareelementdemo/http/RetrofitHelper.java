package com.globalegrowming.shareelementdemo.http;

import android.icu.text.RelativeDateTimeFormatter;

import com.globalegrowming.shareelementdemo.bean.BackgroundListBean;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：Create on 2017/5/31 15:43 by Rayming
 * 邮箱：clm2733227@163.com
 * 描述：TODO
 * 最近修改：2017/5/31 15:43 modify by Rayming
 */
public class RetrofitHelper {
    private static final String BASE_URL = "https://bing.ioliu.cn/";

    private static OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

    private BingClient bingClient;

    private static BingClient getBingBackgroundService() {

        Retrofit bingRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return bingRetrofit.create(BingClient.class);
    }

    public RetrofitHelper() {
        if (bingClient == null)
            init();
    }

    private void init() {
        bingClient = getBingBackgroundService();
    }

    public void getListBackground (String page, String size, Callback<BackgroundListBean> callback){
        Call<BackgroundListBean>  call = bingClient.getBackgroundPics(page, size);
        call.enqueue(callback);
    }

}
