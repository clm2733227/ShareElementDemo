package com.globalegrowming.shareelementdemo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.globalegrowming.shareelementdemo.adapter.BackgroundAdapter;
import com.globalegrowming.shareelementdemo.bean.BackgroundBean;
import com.globalegrowming.shareelementdemo.bean.BackgroundListBean;
import com.globalegrowming.shareelementdemo.http.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RetrofitHelper retrofitHelper;
    private RecyclerView recyclerView;
    private List<String> thumbnailList = new ArrayList();
    private BackgroundAdapter backgroundAdapter;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        if (backgroundAdapter == null)
            backgroundAdapter = new BackgroundAdapter(this);
    }

    private void initData() {

        retrofitHelper = new RetrofitHelper();
        retrofitHelper.getListBackground("1", "20", new Callback<BackgroundListBean>() {
            @Override
            public void onResponse(Call<BackgroundListBean> call, Response<BackgroundListBean> response) {
                List<BackgroundBean> resultList = response.body().getData();
                for (BackgroundBean backgroundBean : resultList) {
                    thumbnailList.add(backgroundBean.getThumbnail_pic());
                }
                backgroundAdapter.setData(thumbnailList);
                gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(backgroundAdapter);
            }

            @Override
            public void onFailure(Call<BackgroundListBean> call, Throwable t) {

            }
        });
    }

    private void initEvent() {
        backgroundAdapter.setOnItemClickListener(new BackgroundAdapter.onItemClickListener() {
            @Override
            public void onClick(int position, View view) {
                Bundle options;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, view.findViewById(R.id.iv),
                            getString(R.string.share_animation)).toBundle();
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("IMG", thumbnailList.get(position));
                    intent.putExtra("position", position);
                    MainActivity.this.startActivity(intent, options);
                } else {
                    Toast.makeText(MainActivity.this, "can not show animation", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
