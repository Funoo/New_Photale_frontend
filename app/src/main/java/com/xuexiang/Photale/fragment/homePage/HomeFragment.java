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

package com.xuexiang.Photale.fragment.homePage;




import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.xuexiang.Photale.R;
import com.xuexiang.Photale.activity.HomePageActivity;
import com.xuexiang.Photale.adapter.FragmentCacheAdapter;
import com.xuexiang.Photale.components.tabbar.tablayout.ContentPage;
import com.xuexiang.Photale.core.BaseFragment;
import com.xuexiang.Photale.fragment.mainPage.mainPagefragment;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.tabbar.EasyIndicator;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;


@Page(name = "主页总页面")
public class HomeFragment extends BaseFragment {

    public mainPagefragment mainPagefragment;

    @BindView(R.id.easy_indicator)
    EasyIndicator mEasyIndicator;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle().setImmersive(true);
        titleBar.setBackgroundColor(Color.TRANSPARENT);
        titleBar.setTitle("");
        titleBar.setLeftImageResource(R.drawable.ic_photale_img);
        titleBar.setLeftText("PhoTale");
        titleBar.setLeftTextColor(R.color.colorPrimary);
        titleBar.setLeftTextBold(true);
        titleBar.addAction(new TitleBar.ImageAction(R.drawable.ic_mico) {
            @Override
            public void performAction(View view) {

            }
        });
        return titleBar;
    }

    private Map<ContentPage, View> mPageMap = new HashMap<>();

    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return ContentPage.size();
        }


        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            ContentPage page = ContentPage.getPage(position);
            System.out.println("print ContentPage.getPage(position)");
            System.out.println(position);
            System.out.println(page.getPosition());
            System.out.println(ContentPage.getPage(position));
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(page.getPosition(), null);
//            View view = getPageView(page.getPosition());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(view, params);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    private View getPageView(ContentPage page) {
        View view = mPageMap.get(page);
        if (view == null) {
            TextView textView = new TextView(getContext());
            textView.setTextAppearance(getContext(), R.style.TextStyle_Content_Match);
            textView.setGravity(Gravity.CENTER);
            textView.setText(String.format("这个是%s页面的内容", page.name()));
            view = textView;
            mPageMap.put(page, view);
        }
        return view;
    }

    @Override
    protected void initViews() {
        mEasyIndicator.setTabTitles(ContentPage.getPageNames());
        mEasyIndicator.setViewPager(mViewPager, mPagerAdapter);
        mViewPager.setOffscreenPageLimit(ContentPage.size() - 1);
        mViewPager.setCurrentItem(0);
    }

}