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

package com.xuexiang.Photale.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.style.PictureCropParameterStyle;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.xuexiang.Photale.R;
import com.xuexiang.Photale.components.LongPictureStyle.GlideEngine;
//import com.xuexiang.Photale.activity.SmartAlbumActivity
import com.xuexiang.Photale.core.BaseActivity;
import com.xuexiang.Photale.fragment.SmartAlbumFragment;
import com.xuexiang.Photale.utils.LongPictures.LongPictureCreate;
import com.xuexiang.Photale.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xutil.common.ClickUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.tool.ui.ToastUtil;

public class SmartAlbumActivity extends BaseActivity implements View.OnClickListener, ClickUtils.OnClick2ExitListener {

    private int REQUEST_CODE_CHOOSE = 0x000011;

    private LongPictureCreate drawLongPictureUtil;

    private String resultPath;

    private List<String> mCurrentSelectedPath = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_smart_album;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        openPage(SmartAlbumFragment.class, getIntent().getExtras());
    }
//
    private void initView() {
        drawLongPictureUtil = new LongPictureCreate(SmartAlbumActivity.this);
//        generateLongPic = generateLongPic.findViewById(R.id.generateLongPic);
        drawLongPictureUtil.setListener(new LongPictureCreate.Listener() {
            @Override public void onSuccess(String path) {
                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        resultPath = path;
                    }
                });
            }

            @Override public void onFail() {
                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        Toast.makeText(SmartAlbumActivity.this.getApplicationContext(), "onFail！",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }


//    @OnClick(R.id.generateLongPic)
    public void generateLongImages(View view){
        System.out.println(mCurrentSelectedPath.size());
        if (mCurrentSelectedPath.size() <1 ) {
            ToastUtil.getInstance()._short(this,"请选择长图");
            return;
        }
        Glide.with(this).asBitmap().load("https://source.unsplash.com/random").into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                drawLongPictureUtil.setButtomBitmap(resource);
                drawLongPictureUtil.setData(mCurrentSelectedPath);
                drawLongPictureUtil.startDraw();
            }
        });
        if (TextUtils.isEmpty(resultPath)) {
            ToastUtil.getInstance()._short(this,"暂无长图");
            return;
        }
        ImagePreview
                .getInstance()
                .setContext(SmartAlbumActivity.this)
                .setIndex(0)
                .setImage(resultPath)
                .start();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("jinru onActivityResult");

        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            mCurrentSelectedPath.clear();

            final StringBuffer stringBuffer = new StringBuffer();
            for (LocalMedia paths : selectList) {
                stringBuffer.append(paths.getCutPath()).append("\n");
                mCurrentSelectedPath.add(paths.getCutPath());
            }
        }
    }



    @Override
    public void onRetry() {

    }

    @Override
    public void onExit() {

    }

    @Override
    public void onClick(View view) {

    }
}
