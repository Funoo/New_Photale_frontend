/*
 * Copyright (C) 2021 xuexiangjys(xuexiangjys@163.com)
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

package com.xuexiang.Photale.fragment.Dairy;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xuexiang.Photale.R;
import com.xuexiang.Photale.adapter.NineGridRecycleAdapter;
import com.xuexiang.Photale.components.NineGridInfo;
import com.xuexiang.Photale.core.BaseFragment;
import com.xuexiang.Photale.fragment.SettingsFragment;
import com.xuexiang.Photale.utils.DemoDataProvider;
import com.xuexiang.Photale.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.adapter.recyclerview.XLinearLayoutManager;
import com.xuexiang.xui.widget.actionbar.TitleBar;


import java.util.List;

import butterknife.BindView;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

@Page(name = "我的日志")
public class DairyFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private NineGridRecycleAdapter mAdapter;

    private int mPage = -1;

    private boolean mIsVideo = false;

    public static DairyFragment newInstance() {
        DairyFragment dairyFragment = new DairyFragment();
        return dairyFragment;
    }

    @Override
    protected com.xuexiang.xui.widget.actionbar.TitleBar initTitle() {
        TitleBar titleBar = super.initTitle()
                .setImmersive(true);
        titleBar.disableLeftView();
        titleBar.setHeight(0);
        titleBar.setBackgroundColor(Color.TRANSPARENT);
        titleBar.setTitle("");
        return titleBar;
    }

    /**
     * 布局的资源id
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dairy;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        mRecyclerView.setLayoutManager(new XLinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), VERTICAL));
        mRecyclerView.setAdapter(mAdapter = new NineGridRecycleAdapter());
        mRefreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果
    }

    @Override
    public void onResume() {
        super.onResume();
        getFocus();
    }

    //主界面获取焦点
    private void getFocus() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_BACK) {
                    getActivity().onBackPressed();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void initListeners() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(final @NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(() -> {
                    mPage++;
                    if (mPage < getMediaRes().size()) {
                        mAdapter.loadMore(getMediaRes().get(mPage));
                        refreshLayout.finishLoadMore();
                    } else {
                        XToastUtils.toast("数据全部加载完毕");
                        refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                    }
                }, 1000);
            }

            @Override
            public void onRefresh(final @NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(() -> {
                    mPage = 0;
                    mAdapter.refresh(getMediaRes().get(mPage));
                    refreshLayout.finishRefresh();
                }, 1000);

            }
        });
    }

    private List<List<NineGridInfo>> getMediaRes() {
        if (mIsVideo) {
            return DemoDataProvider.sNineGridVideos;
        } else {
            return DemoDataProvider.sNineGridPics;
        }
    }

}