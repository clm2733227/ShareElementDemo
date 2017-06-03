package com.globalegrowming.shareelementdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.globalegrowming.shareelementdemo.bean.BackgroundBean;
import com.globalegrowming.shareelementdemo.bean.BackgroundListBean;
import com.globalegrowming.shareelementdemo.http.RetrofitHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    ViewPager viewPager;
    private String thumbnail;
    private AutoScrollViewPagerAdapter autoScrollViewPagerAdapter;
    private int listPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        thumbnail = getIntent().getExtras().getString("IMG");
        listPosition = getIntent().getExtras().getInt("position");
        initView();
        initData();
        initEvent();
    }


    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.vp);

    }


    private void initData() {
        if (autoScrollViewPagerAdapter == null) {
            autoScrollViewPagerAdapter = new AutoScrollViewPagerAdapter();
            viewPager.setAdapter(autoScrollViewPagerAdapter);
        }
    }

    private void initEvent() {

    }

    class AutoScrollViewPagerAdapter extends android.support.v4.view.PagerAdapter {
        List<String> picList;
        SparseArray<View> sparseArray = new SparseArray<>();
        private HashMap<Integer, ImageView> imageHashMap = new HashMap<>();
        Object placeHolderDrawable;

        public void setPicListBeans(List<String> picList) {
            this.picList = picList;
        }


        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            final View view = LayoutInflater.from(DetailActivity.this).inflate(R.layout.include_image_view_ripple, container, false);
            final ImageView imageView = (ImageView) view.findViewById(R.id.image_view);

            //第一张图片默认有过渡动画
            sparseArray.put(position, view);
            if (0 == viewPager.getCurrentItem() && position == 0) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    imageView.setTransitionName(getString(R.string.share_animation));
                    imageHashMap.put(0, imageView);
                }
            }

            if (position != 0)
                placeHolderDrawable = R.mipmap.ic_launcher;


            GlideRequest<Bitmap> glideRequest = GlideApp.with(DetailActivity.this).asBitmap();
            if (placeHolderDrawable instanceof Integer)
                glideRequest.placeholder((Integer) placeHolderDrawable);
            else
                glideRequest.placeholder((Drawable) placeHolderDrawable);

            glideRequest.load(picList == null || picList.isEmpty() ? thumbnail : picList.get(position)).listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    placeHolderDrawable = new BitmapDrawable(DetailActivity.this.getResources(), resource);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (position == 0 && picList == null) {
                            startPostponedEnterTransition();
                            sendRequest();
                        }
                    }
                    return false;
                }
            });
            glideRequest.into(imageView);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        //清除其他享有shared element的元素
                        Iterator<Map.Entry<Integer, ImageView>> iterator = imageHashMap.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<Integer, ImageView> entry = iterator.next();
                            ImageView imageView = entry.getValue();
                            imageView.setTransitionName("");
                        }
                        imageHashMap.clear();

                        //始终保持最多一个元素带有shared element 属性
                        View rootView = autoScrollViewPagerAdapter.getCurrentView(viewPager.getCurrentItem());
                        if (rootView != null) {
                            ImageView shareImageView = (ImageView) autoScrollViewPagerAdapter.getCurrentView(viewPager.getCurrentItem()).findViewById(R.id.image_view);
                            shareImageView.setTransitionName(getString(R.string.share_animation));
                            imageHashMap.put(viewPager.getCurrentItem(), shareImageView);
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            container.addView(view);
            return view;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return picList != null ? picList.size() : 1;
        }

        public View getCurrentView(int position) {
            return sparseArray.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    private void sendRequest() {
        RetrofitHelper retrofitHelper = new RetrofitHelper();
        retrofitHelper.getListBackground("1", "20", new Callback<BackgroundListBean>() {
            @Override
            public void onResponse(Call<BackgroundListBean> call, Response<BackgroundListBean> response) {
                List<BackgroundBean> resultList = response.body().getData();
                List<String> picList = new ArrayList<>();
                for (int i = 0; i < resultList.size(); i++) {
                    if (listPosition == i)
                        picList.add(0, resultList.get(i).getOriginal_pic());
                    else
                        picList.add(resultList.get(i).getOriginal_pic());
                }
                autoScrollViewPagerAdapter.setPicListBeans(picList);
                autoScrollViewPagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BackgroundListBean> call, Throwable t) {

            }
        });
    }
}

