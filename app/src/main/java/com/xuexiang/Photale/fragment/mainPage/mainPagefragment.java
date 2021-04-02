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

import android.graphics.Color;

import android.view.View;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.style.PictureCropParameterStyle;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.xuexiang.Photale.R;
import com.xuexiang.Photale.activity.SmartAlbumActivity;
import com.xuexiang.Photale.activity.TestActivity;
import com.xuexiang.Photale.activity.TestActivity2;
import com.xuexiang.Photale.components.LongPictureStyle.GlideEngine;
import com.xuexiang.Photale.components.PictureSelectorFragment;
import com.xuexiang.Photale.core.BaseFragment;
import com.xuexiang.Photale.fragment.Dairy.DairyFragment;
import com.xuexiang.Photale.fragment.SmartAlbumFragment;
import com.xuexiang.Photale.utils.XToastUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;
import com.xuexiang.xui.widget.banner.widget.banner.SimpleImageBanner;
import com.xuexiang.xui.widget.banner.widget.banner.base.BaseBanner;
import com.xuexiang.xutil.app.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Page(name = "主页")
public class mainPagefragment extends BaseFragment implements BaseBanner.OnItemClickListener<BannerItem>{

    private List<BannerItem> mData;

    @BindView(R.id.sib_simple_usage)
    SimpleImageBanner sib_simple_usage;

    private List<LocalMedia> mSelectList = new ArrayList<>();

    private int REQUEST_CODE_CHOOSE = 0x000011;

    private PictureParameterStyle mPictureParameterStyle;
    private PictureCropParameterStyle mCropParameterStyle;

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

    /***
     * 打开文案生成界面
     */
    @OnClick(R.id.autoText)
    public void onViewClicked() {
        openPage(PictureSelectorFragment.class);
    }

    /***
     * 打开智能排版页面
     */
    @OnClick(R.id.autoAdjust)
    public void onAutoAdjustViewClicked() {
        ActivityUtils.startActivity(TestActivity2.class);
//        openPage(TestActivity.class);
//        openPage(SmartAlbumFragment.class);
    }

    /***
     * 打开生活日志页面
     */
    @OnClick(R.id.dairy)
    public void onDairyClicked() {
        openPage(DairyFragment.class);
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


    public void onAddPicClick() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .setPictureStyle(mPictureParameterStyle)
                .isEnableCrop(true)// 是否裁剪 true or false
                .maxSelectNum(10)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .hideBottomControls(false)
                .imageSpanCount(4)// 每行显示个数 int
                .loadImageEngine(GlideEngine.createGlideEngine())
                .selectionMode(PictureConfig.MULTIPLE)
                .forResult(REQUEST_CODE_CHOOSE);//结果回调onActivityResult code
    }
}