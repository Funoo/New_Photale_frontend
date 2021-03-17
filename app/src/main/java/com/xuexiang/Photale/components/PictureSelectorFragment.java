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
import com.xuexiang.Photale.utils.XToastUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.ArrayList;
import java.util.List;
import com.xuexiang.Photale.R;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

import butterknife.BindView;
import butterknife.OnClick;

@Page(name = "文案生成")
public class PictureSelectorFragment extends BaseFragment implements ImageSelectGridAdapter.OnAddPicClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.flowlayout_display)
    FlowTagLayout mDisplayFlowTagLayout;

    @BindView(R.id.tagEdit)
    MaterialEditText tagEdit;

    @BindView(R.id.article)
    MultiLineEditText myarticle;

    @BindView(R.id.generateText)
    RoundButton generateText;

    private ImageSelectGridAdapter mAdapter;

    private List<LocalMedia> mSelectList = new ArrayList<>();

    private String tagString;

    private int tagListNum = 0;

    private List<String> tagInfo = new ArrayList<>();


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
        myarticle.setVisibility(View.GONE);
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
                if (tagEdit.getText().length() == 0){
                    XToastUtils.toast("请输入标签内容喔~");
                }else {
                    tagString = tagEdit.getText().toString();
                    mDisplayFlowTagLayout.addTag(tagString);
                    tagInfo.add(tagListNum,tagString);
                    tagListNum++;
                    System.out.println("---------" + tagInfo + "-------------" + tagListNum);
                    tagEdit.clear();
                }
                break;
            case R.id.btn_clear_tag:
                mDisplayFlowTagLayout.clearTags();
                tagInfo.clear();
                tagListNum = 0;
                System.out.println("---------" + tagInfo + "-------------" + tagListNum);
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.generateText)
    public void onGenerateClicked() {
        if (generateText.getText().toString().equals("生成文案")){
            myarticle.setVisibility(View.VISIBLE);
            myarticle.setContentText("      我们要学会珍惜我们生活的每一天，因为，这每一天的开始，都将是我们余下生命之中的第一天。除非我们即将死去。");
            generateText.setText("换个文案");
        }else {
            myarticle.setVisibility(View.VISIBLE);
            myarticle.setContentText("      也许每一个男子全都有过这样的两个女人，至少两个。娶了红玫瑰，久而久之，红的变了墙上的一抹蚊子血，白的还是“床前明月光”；娶了白玫瑰，白的便是衣服上的一粒饭粘子，红的却是心口上的一颗朱砂痣。");
        }
    }
}