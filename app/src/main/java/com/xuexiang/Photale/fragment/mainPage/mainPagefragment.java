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

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuexiang.Photale.R;
import com.xuexiang.Photale.core.BaseFragment;
import com.xuexiang.Photale.utils.XToastUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;
import com.xuexiang.xui.widget.banner.widget.banner.SimpleImageBanner;
import com.xuexiang.xui.widget.banner.widget.banner.base.BaseBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Page(name = "主页")
public class mainPagefragment extends BaseFragment implements BaseBanner.OnItemClickListener<BannerItem>{

    private List<BannerItem> mData;

    @BindView(R.id.sib_simple_usage1)
    SimpleImageBanner sib_simple_usage1;


    /**
     * 图片轮播
     */
    private void sib_simple_usage() {
        System.out.println("sib_simple_usage()");
        sib_simple_usage1.setSource(mData)
                .setOnItemClickListener((view, t, position) -> {
                })
                .setIsOnePageLoop(false).startScroll();
        System.out.println("------------mData--------------");
        System.out.println(mData);

    }

    public static String[] titles = new String[]{
            "伪装者:胡歌演绎'痞子特工'",
            "无心法师:生死离别!月牙遭虐杀",
            "花千骨:尊上沦为花千骨",
            "综艺饭:胖轩偷看夏天洗澡掀波澜",
            "碟中谍4:阿汤哥高塔命悬一线,超越不可能",
    };

    public static String[] urls = new String[]{//640*360 360/640=0.5625
            "app/src/main/res/drawable-hdpi/bg.png",//伪装者:胡歌演绎"痞子特工"
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144158380433341332.jpg",//无心法师:生死离别!月牙遭虐杀
            "D:\\AndroidProject\\New_Photale_frontend\\app\\src\\main\\res\\drawable-hdpi\\bg.png",//花千骨:尊上沦为花千骨
            "http://photocdn.sohu.com/tvmobilemvms/20150902/144115156939164801.jpg",//综艺饭:胖轩偷看夏天洗澡掀波澜
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144159406950245847.jpg",//碟中谍4:阿汤哥高塔命悬一线,超越不可能
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
        sib_simple_usage1.recycle();
        super.onDestroyView();
    }


    @Override
    public void onItemClick(View view, BannerItem item, int position) {
        XToastUtils.toast("position--->" + position + ", item:" + item.title);
    }
}