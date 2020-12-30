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




import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.xuexiang.Photale.R;
import com.xuexiang.Photale.activity.HomePageActivity;
import com.xuexiang.Photale.adapter.FragmentCacheAdapter;
import com.xuexiang.Photale.components.tabbar.tablayout.ContentPage;
import com.xuexiang.Photale.core.BaseFragment;
import com.xuexiang.Photale.fragment.LoginFragment;
import com.xuexiang.Photale.fragment.SettingsFragment;
import com.xuexiang.Photale.fragment.mainPage.mainPagefragment;
import com.xuexiang.Photale.utils.XToastUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.tabbar.EasyIndicator;
import com.xuexiang.xutil.app.ActivityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


@Page(name = "主页总页面")
public class HomeFragment extends BaseFragment {

    ContentPagerAdapter mContentPagerAdapter;
    private String[] topTitles ;


    @BindView(R.id.view_pager_home)
    ViewPager mViewPager;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        topTitles = ResUtils.getStringArray(R.array.home_titles);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mContentPagerAdapter = new ContentPagerAdapter(getChildFragmentManager());
        mContentPagerAdapter.addFragment(mainPagefragment.newInstance());
        mContentPagerAdapter.addFragment(LoginFragment.newInstance());
        mContentPagerAdapter.addFragment(SettingsFragment.newInstance());
        mViewPager = view.findViewById(R.id.view_pager_home);
        mViewPager.setAdapter(mContentPagerAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);
        for(int i=0;i<topTitles.length;i++){
            tabLayout.getTabAt(i).setText(topTitles[i]);
        }
        TitleBar mTitleBar2 = view.findViewById(R.id.titlebar2);
        mTitleBar2.setLeftClickListener(v -> XToastUtils.toast("点击主图标"))
                .addAction(new TitleBar.ImageAction(R.drawable.ic_mico) {
                    @Override
                    public void performAction(View view) {
                        XToastUtils.toast("点击语音输入");
                    }});
    }



    //    private Map<ContentPage, View> mPageMap = new HashMap<>();



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }



    @Override
    protected void initViews() {
        System.out.println("进入了initViews()");
    }

    public class ContentPagerAdapter extends FragmentPagerAdapter {

        private List<BaseFragment> fragmentList;

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentList = new ArrayList<>();
        }

        public void addFragment(BaseFragment fragment) {
            fragmentList.add(fragment);
        }

        @Override
        public BaseFragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }



        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        }
    }

    public static class DemoObjectFragment extends Fragment {
        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_settings, container, false);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            Bundle args = getArguments();
            System.out.println("ARGS IS " + args);
        }
    }

}