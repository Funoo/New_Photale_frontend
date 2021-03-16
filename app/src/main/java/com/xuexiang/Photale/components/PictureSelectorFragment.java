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

package com.xuexiang.Photale.components;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.xuexiang.Photale.core.BaseFragment;
import com.xuexiang.Photale.utils.Utils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.ArrayList;
import java.util.List;
import com.xuexiang.Photale.R;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

import butterknife.BindView;
import butterknife.OnClick;

@Page(name = "图片选择")
public class PictureSelectorFragment extends BaseFragment implements ImageSelectGridAdapter.OnAddPicClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.flowlayout_display)
    FlowTagLayout mDisplayFlowTagLayout;

    @BindView(R.id.tagEdit)
    MaterialEditText tagEdit;

    private ImageSelectGridAdapter mAdapter;

    private List<LocalMedia> mSelectList = new ArrayList<>();

    private String tagString;


    @SuppressLint("ResourceAsColor")
    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        titleBar.setBackgroundColor(Color.parseColor("#62c2af"));

        return titleBar;
    }

    /**
     * 布局的资源id
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_picture_selector;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter = new ImageSelectGridAdapter(getActivity(), this));
        mAdapter.setSelectList(mSelectList);
        mAdapter.setSelectMax(8);
        mAdapter.setOnItemClickListener((position, v) -> PictureSelector.create(PictureSelectorFragment.this).themeStyle(R.style.XUIPictureStyle).openExternalPreview(position, mSelectList));
        Utils.getPictureSelector(this)
                .selectionMedia(mSelectList)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }



//    public void initClicked() {
//        Utils.getPictureSelector(this)
//                .selectionMedia(mSelectList)
//                .forResult(PictureConfig.CHOOSE_REQUEST);
//        System.out.println("--------step in click status---------------");
//    }
//
//    @OnClick(R.id.button_no_camera)
//    public void onViewClicked() {
//        initClicked();
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    mAdapter.setSelectList(mSelectList);
                    mAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onAddPicClick() {
        Utils.getPictureSelector(this)
                .selectionMedia(mSelectList)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @OnClick({R.id.btn_add_tag, R.id.btn_clear_tag})
    void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_add_tag:
                tagString = tagEdit.getText().toString();
                mDisplayFlowTagLayout.addTag(tagString);
                tagEdit.clear();
                System.out.println("--------------addTag-----------------");
                break;
            case R.id.btn_clear_tag:
                mDisplayFlowTagLayout.clearTags();
                break;
            default:
                break;
        }
    }
}