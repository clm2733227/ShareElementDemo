package com.globalegrowming.shareelementdemo.http;

import com.globalegrowming.shareelementdemo.bean.BackgroundListBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 作者：Create on 2017/5/31 15:18 by Rayming
 * 邮箱：clm2733227@163.com
 * 描述：TODO
 * 最近修改：2017/5/31 15:18 modify by Rayming
 */
public interface BingClient {
    @GET("v1/")
    Call<BackgroundListBean> getBackgroundPics(@Query("p") String page, @Query("size") String size);

}
