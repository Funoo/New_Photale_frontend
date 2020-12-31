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

package com.xuexiang.Photale.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.xuexiang.Photale.R;
import com.xuexiang.Photale.components.tabbar.TestPageFragment;
import com.xuexiang.Photale.components.tabbar.tablayout.ContentPage;
import com.xuexiang.Photale.core.BaseActivity;
import com.xuexiang.Photale.fragment.AboutFragment;
import com.xuexiang.Photale.fragment.SettingsFragment;
import com.xuexiang.Photale.fragment.homePage.HomeFragment;
import com.xuexiang.Photale.utils.XToastUtils;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.tabbar.EasyIndicator;
import com.xuexiang.xutil.common.ClickUtils;
import com.xuexiang.xutil.common.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class HomePageActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener, ClickUtils.OnClick2ExitListener{


    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;

    private String[] mTitles;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        System.out.println("---------------------------step in HomepageActivity onCreate------------------");
        mTitles = ResUtils.getStringArray(R.array.home_titles);
//        mViewPager  = findViewById(R.id.view_pager);
        openPage(HomeFragment.class);


//        mViewPager.setOffscreenPageLimit(ContentPage.size() - 1);

        initListeners();
        super.onCreate(savedInstanceState);
    }


    protected void initListeners() {

        //主页事件监听
//        mViewPager.addOnPageChangeListener(this);
    }

    /**
     * 底部导航栏点击事件
     *
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int index = CollectionUtils.arrayIndexOf(mTitles, menuItem.getTitle());
        if (index != -1) {
//            mViewPager.setCurrentItem(index, false);

            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onRetry() {

    }

    @Override
    public void onExit() {

    }
}
