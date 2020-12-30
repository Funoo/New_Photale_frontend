/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.Photale.fragment.mainPage;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuexiang.Photale.R;
import com.xuexiang.Photale.core.BaseFragment;
import com.xuexiang.Photale.fragment.homePage.HomeFragment;
import com.xuexiang.Photale.utils.XToastUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.ThemeUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;
import com.xuexiang.xui.widget.banner.widget.banner.SimpleImageBanner;
import com.xuexiang.xui.widget.banner.widget.banner.base.BaseBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Page(name = "主页")
public class mainPagefragment extends BaseFragment implements BaseBanner.OnItemClickListener<BannerItem>{

    private List<BannerItem> mData;

    @BindView(R.id.sib_simple_usage)
    SimpleImageBanner sib_simple_usage;



    public static mainPagefragment newInstance() {
        mainPagefragment firstPageFragment = new mainPagefragment();
        return firstPageFragment;
    }

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle()
                .setImmersive(true);
        titleBar.disableLeftView();
        titleBar.setHeight(0);
        titleBar.setBackgroundColor(Color.TRANSPARENT);
        titleBar.setTitle("");
        return titleBar;
    }

    /**
     * 图片轮播
     */
    private void sib_simple_usage() {
        sib_simple_usage.setSource(mData)
                .setOnItemClickListener((view, t, position) -> {
                })
                .setIsOnePageLoop(false).startScroll();
        System.out.println("------------mData--------------");
        System.out.println(mData);

    }

    public static String[] titles = new String[]{
            "风景",
            "风景",
            "风景",
            "风景",
            "风景",
    };

    public static String[] urls = new String[]{//640*360 360/640=0.5625
            "http://pic.5tu.cn/uploads/allimg/1905/pic_5tu_big_2019050601001168045.jpg",
            "http://pic1.win4000.com/wallpaper/2020-04-13/5e93d67c42c62.jpg",
            "http://pic1.win4000.com/wallpaper/2020-04-13/5e93d67d16c6d.jpg",
            "http://pic1.win4000.com/wallpaper/2020-04-13/5e93d67dcee9f.jpg",
            "http://pic1.win4000.com/wallpaper/2020-04-13/5e93d67ebdab1.jpg",
    };

    public static List<BannerItem> getBannerList() {
        System.out.println("进入getBannerList()");
        ArrayList<BannerItem> list = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            BannerItem item = new BannerItem();
            item.imgUrl = urls[i];
            item.title = titles[i];

            list.add(item);
        }

        return list;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_pagefragment;
    }

    @Override
    protected void initViews() {
        mData = getBannerList();
        System.out.println("initViews()");
//        BannerItem item = new BannerItem();
//            item.imgUrl = "src\\main\\res\\drawable-hdpi\\bg.png";
//            item.title = "test";
//         mData.add(item);
         sib_simple_usage();
    }

    @Override
    public void onDestroyView() {
        sib_simple_usage.recycle();
        super.onDestroyView();
    }


    @Override
    public void onItemClick(View view, BannerItem item, int position) {
        XToastUtils.toast("position--->" + position + ", item:" + item.title);
    }
}